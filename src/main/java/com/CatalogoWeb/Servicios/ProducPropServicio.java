package com.CatalogoWeb.Servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.CatalogoWeb.Entidades.Producto_Propiedad;
import com.CatalogoWeb.Entidades.Productos;
import com.CatalogoWeb.Repositorios.ProducPropRepositorio;

@Service
public class ProducPropServicio {

	@Autowired
	private ProducPropRepositorio ProdProp;
	
	@Transactional(readOnly = true)
	public List<Productos> buscarProductosPorPropiedad(String propiedad){
		return ProdProp.listarProductosPorPropiedad(propiedad);
	}
	
	@Transactional(readOnly = true)
	public List<Producto_Propiedad> buscarPropiedadesDeProducto(String producto){
		return ProdProp.listaPropiedadesPorProducto(producto);
	}
}
