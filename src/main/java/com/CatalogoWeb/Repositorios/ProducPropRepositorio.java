package com.CatalogoWeb.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.CatalogoWeb.Entidades.Producto_Propiedad;
import com.CatalogoWeb.Entidades.Productos;

import java.util.List;

@Repository
public interface ProducPropRepositorio extends JpaRepository<Producto_Propiedad, String>{

	@Query("SELECT pr FROM Producto_Propiedad pr WHERE pr.producto.Id = ?1")
	public List<Producto_Propiedad> listaPropiedadesPorProducto(String Id);
	
	@Query("SELECT p FROM Producto_Propiedad pr LEFT OUTER JOIN pr.producto p WHERE p.Id = ?1")
	public List<Productos> listarProductosPorPropiedad(String Id);
	
}
