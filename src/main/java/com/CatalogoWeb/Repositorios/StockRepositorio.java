
package com.CatalogoWeb.Repositorios;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.CatalogoWeb.Entidades.Stock;

@Repository
public interface StockRepositorio extends JpaRepository<Stock, String>{
    
    @Query ("select s from Stock s where s.producto like :producto")
    public List<Stock> buscarPorProducto();
    
    @Query("select s from Stock s where s.fecha between :desde and :hasta")
    public List<Stock> busquedaEntreFechas(@Param("desde")Date fechaDesde, @Param("hasta")Date fechaHasta);
    
    @Query("select sum(s.cantidad), s.producto.descripcion from Stock s "
            + "where s.producto.id = :producto group by s.producto.descripcion")
    public Map<Integer, String> consultaStockPorProducto(@Param("producto") String producto);
    
}
