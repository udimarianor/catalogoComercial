
package com.CatalogoWeb.Servicios;

import com.CatalogoWeb.Entidades.Contacto;
import com.CatalogoWeb.Errores.CatalogoError;
import com.CatalogoWeb.Repositorios.ContactoRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactoServicio {
    
    @Autowired
    private ContactoRepositorio cr;

    @Transactional(readOnly = true)
    public List<Contacto> contactosPorNombre(String nombre, String mensaje) throws CatalogoError {
        List<Contacto> contactos = cr.buscarPorNombre(nombre, mensaje);
        if (contactos.isEmpty()) {
            throw new CatalogoError("No existen contactos para ese nombre");
        } else {
            return contactos;
        }
    }
    
    @Transactional
    public Contacto guardarContacto(String nombre, String correo, String asunto, String mensaje)
            throws CatalogoError {

            
            Contacto nuevoContacto = new Contacto();

            if (cr.buscarPorNombre(nombre, mensaje).isEmpty()) {
                nuevoContacto.setNombre(nombre);
                nuevoContacto.setCorreo(correo);
                nuevoContacto.setAsunto(asunto);
                nuevoContacto.setMensaje(mensaje);

                return cr.save(nuevoContacto);
            } else {
                throw new CatalogoError("El contacto se guardó y será respondido a la brevedad");
            }
    }
    
    @Autowired
    private JavaMailSender javaMailSender;
	
	public void sendEmail(String nombre, String correo, String asunto, String mensaje) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("correogenerico@correo.com");

        msg.setSubject("Formulario de Contacto: " + nombre);
        msg.setText("Un posible cliente llamado: " + nombre + " ha mandado un mensaje mediante el formulario de contacto:\n" +
        		"Asunto: " + asunto + "\nMensaje: " + mensaje + "\nCorreo de Contacto: " + correo);

        javaMailSender.send(msg);

    }
    
}
