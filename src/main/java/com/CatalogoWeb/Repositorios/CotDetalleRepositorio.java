package com.CatalogoWeb.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CatalogoWeb.Entidades.Cotizacion_Detalle;

@Repository
public interface CotDetalleRepositorio extends JpaRepository<Cotizacion_Detalle, String>{

}
