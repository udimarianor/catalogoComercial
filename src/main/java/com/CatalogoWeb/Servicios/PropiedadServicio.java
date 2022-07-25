package com.CatalogoWeb.Servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.CatalogoWeb.Entidades.Propiedades;
import com.CatalogoWeb.Repositorios.PropiedadRepositorio;
import com.CatalogoWeb.Errores.CatalogoError;


@Service
public class PropiedadServicio {

	@Autowired
	private PropiedadRepositorio pr;
	
	@Transactional(readOnly=true)
    public void validarPropiedad(String nombre) throws CatalogoError{
        if (nombre.isEmpty() || nombre==null) {
            throw new CatalogoError("El nombre no puede estar vacío");
        }
    }
		
	@Transactional
    public void crearPropiedad(String nombre) throws CatalogoError{
       
        validarPropiedad(nombre);
        Propiedades propiedad = new Propiedades();
        if (pr.verifNombre(nombre) == null) {
        	try {
        		propiedad.setNombre(nombre);
        		propiedad.setActivo(true);
        	
        		pr.save(propiedad);
        	} catch (Exception e) {
        		throw new CatalogoError("Ya ocurrido un error al intentar guardar el registro.");
        	}
        } else {
        	throw new CatalogoError("Ya existe una Propiedad con ese nombre");
        }
	}
	
	@Transactional
    public void modificaPropiedad(String Id, String nombre, Boolean activo) throws CatalogoError{
       
        validarPropiedad(nombre);
        Propiedades propiedad = pr.findById(Id).get();
        if (pr.verifNombre(nombre).getId().equals(Id)) {
        	try {
        		propiedad.setNombre(nombre);
        		propiedad.setActivo(activo);
        	
        		pr.save(propiedad);
        	} catch (Exception e) {
        		throw new CatalogoError("Ha ocurrido un error al intentar guardar el registro.");
        	}
        } else {
        	throw new CatalogoError("Ya existe una Propiedad con ese nombre");
        }
	}
	
	@Transactional(readOnly = true)
	public List<Propiedades> listarPropiedades(){
		return pr.listarPropiedadActiva();
	}
}
