package com.CatalogoWeb.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.CatalogoWeb.Entidades.Propiedades;

@Repository
public interface PropiedadRepositorio extends JpaRepository<Propiedades, String>{

	@Query("SELECT p FROM Propiedades p WHERE p.activo = true")
	public List<Propiedades> listarPropiedadActiva();
	
	@Query("select p from Propiedades p where p.nombre = :nombre")
    public Propiedades verifNombre(@Param("nombre")String nombre);
}
