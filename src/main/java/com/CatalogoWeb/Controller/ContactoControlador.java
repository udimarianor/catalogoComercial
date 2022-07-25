package com.CatalogoWeb.Controller;

import com.CatalogoWeb.Errores.CatalogoError;
import com.CatalogoWeb.Servicios.ContactoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/contacto")
public class ContactoControlador {

    @Autowired
    private ContactoServicio cs;

    @PostMapping("/enviar")
    public String enviarContacto(@RequestParam("nombre") String nombre, @RequestParam("correo") String correo,
            @RequestParam("asunto") String asunto, @RequestParam("mensaje") String mensaje, Model modelo) {

        try {
            cs.guardarContacto(nombre, correo, asunto, mensaje);
            
            cs.sendEmail(nombre, correo, asunto, mensaje);
            
            modelo.addAttribute("confirmacion", "Gracias por el contacto, será respondido a la brevedad");

        } catch (CatalogoError ce) {
            modelo.addAttribute("error", ce.getMessage());
        }

        return "ingreso.html";
    }

}
