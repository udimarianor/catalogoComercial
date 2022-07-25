
package com.CatalogoWeb.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.CatalogoWeb.Entidades.Productos;
import com.CatalogoWeb.Servicios.ProductoServicio;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@PreAuthorize("hasAnyRole('ROLE_CLIENTE', 'ROLE_VENDEDOR', 'ROLE_ADMIN')")
@RequestMapping("/catalogo")
public class CatalogoControlador {
    
	 
    @Autowired
    private ProductoServicio ps;	
	
    @GetMapping(path = {""})
    public String IrCatalogo(ModelMap modelo, @RequestParam(required = false) String keyword, @RequestParam(required = false) String categoria){    	 
    	
    	//System.out.println(keyword);
    	
    	List<String> categorias = ps.listarCategoria();
    	if(keyword == null) keyword = "";
    	if(categoria == null) categoria = "%%";
    	List<Productos> productos =  ps.buscarPorKeywordYCategoria("%"+keyword+"%", categoria);  
    	
    	System.out.println(categoria);
    	
    	modelo.addAttribute("productos", productos);
    	modelo.addAttribute("categorias", categorias);
    	
        return "catalogoPrueba.html";
    }   

    
}
