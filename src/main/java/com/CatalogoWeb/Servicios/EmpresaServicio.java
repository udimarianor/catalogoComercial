
package com.CatalogoWeb.Servicios;

import com.CatalogoWeb.Errores.CatalogoError;
import com.CatalogoWeb.Repositorios.EmpresaRepositorio;
import com.CatalogoWeb.Entidades.Empresa;

import java.util.GregorianCalendar;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpresaServicio {
    
    @Autowired
    private EmpresaRepositorio er;
    
    @Transactional(readOnly=true)
    public void validarDatos(String nombre) throws CatalogoError{
        if (nombre.isEmpty() || nombre==null) {
            throw new CatalogoError("El nombre no puede estar vacío");
        }
    }
    
    @Transactional
    public void crearEmpresa(String nombre) throws CatalogoError{
       
        validarDatos(nombre);
        Empresa empresa = new Empresa();
        if (er.verifNombre(nombre)== null) {
            empresa.setNombre(nombre);
            empresa.setActivo(true);
            empresa.setFecha(new GregorianCalendar());
            
            er.save(empresa);
        }else{
            throw new CatalogoError("Ya existe una empresa con ese nombre");
        }  
    }
    
    @Transactional
    public void bajaEmpresa(String id) throws CatalogoError{
        Optional<Empresa> respuesta = er.findById(id);
        if (respuesta.isPresent()) {
            Empresa empresa = respuesta.get();
            if (empresa.getActivo() == true) {
                empresa.setActivo(false);
            } else {
                empresa.setActivo(true);
            }
            er.save(empresa);
        } else {
            throw new CatalogoError("No se encontró la empresa");
        }
    }
    
    
    
}
