package com.CatalogoWeb.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.CatalogoWeb.Entidades.Productos;

@Repository
public interface ProductosRepositorio extends JpaRepository<Productos, String>{

	@Query("SELECT p FROM Productos p WHERE p.activo = true")
	public List<Productos> listarProductosActivos();
	
	@Query("SELECT p FROM Productos p WHERE p.activo = true AND p.categoria LIKE ?2 AND (p.descripcion LIKE ?1 OR p.detalle LIKE ?1)")
	public List<Productos> listarProductosPorKeywordYCategoria(String keyword, String categoria);
	
	@Query("SELECT DISTINCT p.categoria FROM Productos p")
	public List<String> listarCategoria();
	
	@Query("SELECT DISTINCT p.empresa FROM Productos p")
	public List<String> listarEmpresa();
	
	//@Query("SELECT p FROM Productos p WHERE p.activo = true AND p.categoria LIKE ?1")
	//public List<Productos> listarProductosPorCategoria(String categoria);
	
	//@Query("SELECT p FROM Productos p WHERE p.activo = true AND p.categoria = ?1 AND p.descripcion LIKE '%'+?2+'%'")
	//public List<Productos> listarProductosPorCategoria(String categoria, String keyword);
	
}
