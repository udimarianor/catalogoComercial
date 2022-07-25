package com.CatalogoWeb.Controller;

import com.CatalogoWeb.Entidades.Usuario;
import com.CatalogoWeb.Enums.Role;
import com.CatalogoWeb.Errores.CatalogoError;
import com.CatalogoWeb.Servicios.UsuarioServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio us;

    @GetMapping("")
    public String irIngreso(Model modelo, @RequestParam(required = false) String error,
            @RequestParam(required = false) String logout) {

        if (error != null) {
            modelo.addAttribute("error", "Las credenciales no son válidas");
        }

        return "ingreso.html";
    }

    @GetMapping("/registro")
    public String irRegistro() {
        return "registro.html";
    }

    @PostMapping("/guardar")
    public String registroUsuarioSave(@RequestParam("nombre") String nombre, @RequestParam("apellido") String apellido,
            @RequestParam("usuario") String usuario, @RequestParam("contrasenia") String contrasenia,
            @RequestParam("contrasenia1") String contrasenia1, @RequestParam("pregunta") String pregunta, @RequestParam("correo") String correo, ModelMap modelo) {
        try {
            us.guardarUsuario(nombre, apellido, usuario, contrasenia, contrasenia1, pregunta, correo);

            us.sendEmail(us.devolverUsuario(correo), "Nuevo Usuario", "Ha creado exitosamente el usuario " + usuario);

            modelo.put("confirmacion", "El usuario se creó con éxito");

            return "ingreso.html";

        } catch (CatalogoError ce) {
            modelo.put("error", ce.getMessage());
            return "registro.html";
        }
    }

    @GetMapping("/restituirContra")
    public String restituirContra() {
        return "restituirContra.html";
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.clearContext();

        return "redirect:/";
    }

    @PostMapping("/recuperacion")
    public String recuperarContrasenia(@RequestParam("pregunta") String pregunta, @RequestParam("correo") String correo,
            ModelMap modelo) throws CatalogoError {
        if (us.validaRespuesta(correo, pregunta)) {
            Usuario user = us.devolverUsuario(correo);
            modelo.put("ID", user.getId());
        } else {
            return "redirect:/usuario/restituirContra";
        }

        return "restituirContra2.html";
    }

    @PostMapping("/modifContrasenia")
    public String modificaContrasenia(@RequestParam("ID") String ID, @RequestParam("contrasenia") String contrasenia,
            ModelMap modelo) {
        try {
            Usuario user = us.traerUsuario(ID);
            us.modifContrasenia(user, contrasenia);
            us.sendEmail(user, "Modificacion de Contraseña", "Su usuario: " + user.getUsuario() + " ha modificado la contraseña.");
            modelo.put("confirmacion", "Usuario modificado OK. Se ha enviado un correo.");

        } catch (CatalogoError ce) {
            modelo.put("error", ce.getMessage());
            return "redirect:/usuario/restituirContra";
        }

        return "ingreso.html";
    }
    
    @GetMapping("/perfiles")
    public String irModifRol(ModelMap modelo) {
        try {
            modelo.put("usuarios", us.usuariosActivos());
        } catch (CatalogoError ce) {
            modelo.put("usuarios", "No hay usuarios activos");
        }
        
        return "admPerfiles.html";
    }
    
    @PostMapping("/modifRol")
    public String guardarModifRol(@RequestParam("usuario")String id, @RequestParam("rol") Integer rol,
            ModelMap modelo) {

        try {
            
            us.modifRol(id, rol);
            modelo.put("confirmacion", "El rol del usuario se modificó con éxito");

        }catch (CatalogoError ce) {
            modelo.put("error", ce.getMessage());
        }

        return "ingreso.html";
    }
    
    @GetMapping("/editarUsuario/{id}")
    public String irModifUsuario(@PathVariable("id") String id, Model modelo){
        
        modelo.addAttribute("usuario", us.traerUsuario(id));
        
        return "modifUsuario.html";
    }
    
    @PostMapping("/guardarModif")
    public String guardarModif(@RequestParam(name = "id")String id, @RequestParam("nombre")String nombre,
            @RequestParam("apellido")String apellido, @RequestParam("usuario")String usuario, 
            @RequestParam("correo")String correo, @RequestParam(name = "pregunta", required = false)String pregunta, 
            Model modelo){
        
        try{
            us.guardarModif(id, nombre, apellido, usuario, correo, pregunta);
            if(pregunta.isEmpty()){
                modelo.addAttribute("confirmacion", "Se modificó el usuario sin nueva pregunta");
            }else{
                modelo.addAttribute("confirmacion", "Se modificó el usuario de manera exitosa");
            }
        }catch(CatalogoError ce){
            modelo.addAttribute("error", ce.getMessage());
        }
        return "ingreso.html";
    }

}
