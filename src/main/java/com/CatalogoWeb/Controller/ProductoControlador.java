package com.CatalogoWeb.Controller;

import com.CatalogoWeb.Entidades.Productos;
import com.CatalogoWeb.Errores.CatalogoError;
import com.CatalogoWeb.Servicios.ProductoServicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/producto")
public class ProductoControlador {

    @Autowired
    private ProductoServicio pc;

    @GetMapping("")
    public String irProducto(ModelMap modelo) {
        List<String> categ = pc.listarCategoria();
        List<String> empresa = pc.listarEmpresa();

        modelo.put("categorias", categ);
        modelo.put("empresas", empresa);
        return "admProducto.html";
    }

    @PostMapping("/guardar")
    public String registroProducto(@RequestParam("descripcion") String descripcion,
            @RequestParam("detalle") String detalle, @RequestParam("categoria") String categoria,
            @RequestParam("usuario") String usuario, @RequestParam("empresa") String empresa,
            @RequestParam("precio") Double precio, @RequestParam("foto") String foto, ModelMap modelo) {
        try {
            pc.nuevoProducto(descripcion, detalle, categoria, usuario, empresa, precio, foto);

            modelo.put("confirmacion", "El producto se creó");

            return "admProducto.html";

        } catch (CatalogoError ce) {
            modelo.addAttribute("error", ce.getMessage());
            return "admProducto.html";

        }
    }

    @PostMapping("/{Id}")
    public String verProducto(@PathVariable("Id") String id, ModelMap modelo) {

        if (!id.isBlank()) {
            Productos producto = pc.traerProducto(id);
            modelo.put("producto", producto);
        } else {
            return "redirect:/catalogo";
        }

        return "productoDetail.html";
    }

    @GetMapping("modifProducto/{Id}")
    public String irModifProducto(@PathVariable("Id") String id, ModelMap modelo) {

        if (!id.isBlank()) {
            Productos producto = pc.traerProducto(id);
            modelo.put("producto", producto);
        } else {
            return "redirect:/catalogo";
        }

        return "modifProducto.html";
    }

    @PostMapping("/guardarModif")
    public String guardarModif(@RequestParam("Id") String id, @RequestParam("descripcion") String descripcion,
            @RequestParam("detalle") String detalle, @RequestParam("categoria") String categoria,
            @RequestParam("usuario") String usuario, @RequestParam("empresa") String empresa,
            @RequestParam("precio") Double precio, @RequestParam("foto") String foto, ModelMap modelo) {

        try {
            pc.guardarModif(id, descripcion, detalle, categoria, usuario, empresa, precio, foto);
            modelo.put("confirmacion", "El producto se editó con éxito");
                    
        } catch (CatalogoError ce) {
            modelo.addAttribute("error", ce.getMessage());
            return "modifProducto.html";
        }
        return "redirect:/catalogo";
    }
    
    @GetMapping("/baja/{id}")
    public String bajaProducto(@PathVariable("id") String id, ModelMap modelo){
        
        try{
            pc.bajaProd(id);
        }catch(CatalogoError ce){
            modelo.put("error", ce.getMessage());
        }
        
        return "redirect:/catalogo";
    }

}
