package com.CatalogoWeb.Servicios;

import com.CatalogoWeb.Repositorios.UsuarioRepositorio;
import com.CatalogoWeb.Errores.CatalogoError;
import com.CatalogoWeb.Entidades.Usuario;
import com.CatalogoWeb.Enums.Role;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio ur;
    @Autowired
    private JavaMailSender javaMailSender;

    @Transactional(readOnly = true)
    public List<Usuario> usuariosActivos() throws CatalogoError {
        List<Usuario> usuarios = ur.buscarUsuariosActivos();
        if (usuarios.isEmpty()) {
            throw new CatalogoError("No existen usuarios activos");
        } else {
            return usuarios;
        }
    }

    @Transactional(readOnly = true)
    public Usuario devolverUsuario(String correo) throws CatalogoError {
        if (correo == null || correo.isEmpty()) {
            throw new CatalogoError("El correo del usuario no puede estar vacío");
        } else {
            Usuario user = ur.buscarPorCorreo(correo);
            return user;
        }
    }

    @Transactional(readOnly = true)
    public Boolean validacionUsuario(String nombre, String apellido, String usuario, String contrasenia,
            String contrasenia1, String pregunta, String correo) throws CatalogoError {

        if (nombre == null || nombre.isEmpty()) {
            throw new CatalogoError("El nombre del usuario no puede estar vacío");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new CatalogoError("El apellido del usuario no puede estar vacío");
        }
        if (usuario == null || usuario.isEmpty()) {
            throw new CatalogoError("El campo usuario no puede estar vacío");
        }
        if (contrasenia == null || contrasenia1 == null || contrasenia.isEmpty() || contrasenia1.isEmpty()) {
            throw new CatalogoError("El campo contraseña no puede estar vacío");
        }
        if (!contrasenia.equals(contrasenia1)) {
            throw new CatalogoError("Las contraseñas no coinciden");
        }
        if (pregunta == null || nombre.isEmpty()) {
            throw new CatalogoError("No contestaste la pregunta");
        }
        if (correo == null || correo.isEmpty()) {
            throw new CatalogoError("El correo del usuario no puede estar vacío");
        }

        return true;
    }

    @Transactional
    public Usuario guardarUsuario(String nombre, String apellido, String usuario, String contrasenia,
            String contrasenia1, String pregunta, String correo) throws CatalogoError {

        if (validacionUsuario(nombre, apellido, usuario, contrasenia, contrasenia1, pregunta, correo)) {

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            Usuario usuarioNuevo = new Usuario();

            if (ur.verificarUsuario(usuario) == null) {
                usuarioNuevo.setNombre(nombre);
                usuarioNuevo.setApellido(apellido);
                usuarioNuevo.setUsuario(usuario);
                usuarioNuevo.setContrasenia(encoder.encode(contrasenia));
                usuarioNuevo.setPregunta(encoder.encode(pregunta));
                usuarioNuevo.setCorreo(correo);
                usuarioNuevo.setActivo(true);
                usuarioNuevo.setRol(Role.CLIENTE);
                usuarioNuevo.setFecha(new GregorianCalendar());

                return ur.save(usuarioNuevo);
            } else {
                throw new CatalogoError("Ya existe el usuario");
            }
        } else {
            throw new CatalogoError("Hay algún problema en el servicio guardarUsuario");
        }
    }
    
    @Transactional
    public Usuario guardarModif(String id, String nombre, String apellido, String usuario, String correo,
            String pregunta) throws CatalogoError{
        Optional<Usuario> respuesta = ur.findById(id);
        
        if (respuesta.isPresent()) {
            Usuario usuarioObj = respuesta.get();
            if (ur.buscarPorCorreo(correo) == null || correo.equalsIgnoreCase(usuarioObj.getCorreo())) {
                if (ur.verificarUsuario(usuario) == null || usuario.equalsIgnoreCase(usuarioObj.getUsuario())) {
                    usuarioObj.setNombre(nombre);
                    usuarioObj.setApellido(apellido);
                    usuarioObj.setUsuario(usuario);
                    usuarioObj.setCorreo(correo);
                    if (!pregunta.isEmpty()) {
                        usuarioObj.setPregunta(pregunta);
                    }
                    return ur.save(usuarioObj);
                }else{
                    throw new CatalogoError("El nombre de usuario no está disponible");
                }
            }else{
                throw new CatalogoError("Ya existe un usuario con ese correo");
            }
        }else{
            throw new CatalogoError("El usuario no existe");
        }
    }

    @Transactional
    public Usuario modifContrasenia(Usuario usuario, String contrasenia) throws CatalogoError {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        usuario.setNombre(usuario.getNombre());
        usuario.setApellido(usuario.getApellido());
        usuario.setUsuario(usuario.getUsuario());
        usuario.setContrasenia(encoder.encode(contrasenia));
        usuario.setPregunta(usuario.getPregunta());
        usuario.setCorreo(usuario.getCorreo());
        usuario.setActivo(true);
        usuario.setRol(usuario.getRol());

        return ur.save(usuario);
    }
    
    @Transactional
    public Usuario modifRol(String id, Integer rol) throws CatalogoError {
        
        Optional<Usuario> respuesta = ur.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            switch (rol) {
                case 1:
                    usuario.setRol(Role.ADMIN);
                    break;
                case 2:
                    usuario.setRol(Role.VENDEDOR);
                    break;
                case 3:
                    usuario.setRol(Role.CLIENTE);
                    break;
                default:
                    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$NO TRAJO NADAAAAAAAAAAAAAAAAAAA$$$$$$$$$$$");
            }
            return ur.save(usuario);
        }else{
            throw new CatalogoError("El usuario no está activo");
        }
        
    }

    @Transactional(readOnly = true)
    public Usuario traerUsuario(String Id) {
        return ur.findById(Id).get();
    }

    @Transactional(readOnly = true)
    public Boolean validaRespuesta(String correo, String respuesta) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Usuario user = ur.buscarPorCorreo(correo);
        if (encoder.matches(respuesta, user.getPregunta())) {
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public Boolean login(String usuario, String contrasenia) throws CatalogoError {
        Optional<Usuario> respuesta = ur.findById(usuario);
        if (respuesta.isPresent()) {
            Usuario objUsuario = respuesta.get();
            if (objUsuario.getContrasenia().equals(contrasenia)) {
                return true;
                //podría guardar los logins después...
            } else {
                throw new CatalogoError("Las credenciales no son válidas");
            }
        } else {
            throw new CatalogoError("Las credenciales no son válidas");
        }
    } 

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        Usuario usuarioObj = ur.verificarUsuario(usuario);
        if (usuarioObj != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();

            //acá se agrega un permiso
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + usuarioObj.getRol());
            permisos.add(p1);

            //acá se recupera la info de la sesión
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuarioObj);

            User user = new User(usuarioObj.getUsuario(), usuarioObj.getContrasenia(), permisos);
            return user;
        } else {
            return null;
        }
    }

    public void sendEmail(Usuario user, String asunto, String mensaje) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(user.getCorreo());

        msg.setSubject(asunto);
        msg.setText(mensaje);

        javaMailSender.send(msg);

    }

}
