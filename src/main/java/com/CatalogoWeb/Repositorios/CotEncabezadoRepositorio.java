package com.CatalogoWeb.Repositorios;

import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.CatalogoWeb.Entidades.Cotizacion_Encabezado;

@Repository
public interface CotEncabezadoRepositorio extends JpaRepository<Cotizacion_Encabezado, String>{

	@Query("SELECT c FROM Cotizacion_Encabezado c")
	public List<Cotizacion_Encabezado> listarCotizaciones();
	
	@Query("SELECT c FROM Cotizacion_Encabezado c WHERE c.cliente LIKE CONCAT('%', ?1, '%')")
	public List<Cotizacion_Encabezado> listarCotizacionesPorCliente(String cliente);
	
	@Query("SELECT c FROM Cotizacion_Encabezado c WHERE c.fecha = ?1")
	public List<Cotizacion_Encabezado> listarCotizacionesPorFecha(Calendar fecha);
	
	@Query("SELECT c FROM Cotizacion_Encabezado c WHERE c.fecha BETWEEN ?1 AND ?2")
	public List<Cotizacion_Encabezado> listarCotizacionesEntreFechas(Calendar fechaDesde, Calendar fechaHasta);
}
