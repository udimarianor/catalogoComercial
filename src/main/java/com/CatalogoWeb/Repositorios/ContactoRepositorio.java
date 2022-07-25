
package com.CatalogoWeb.Repositorios;

import com.CatalogoWeb.Entidades.Contacto;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactoRepositorio extends JpaRepository<Contacto, String>{
    
    @Query ("select c from Contacto c where c.correo like :correo and c.mensaje like :mensaje")
    public List<Contacto> buscarPorNombre(@Param("correo")String correo, @Param("mensaje")String mensaje);
    
    @Query("select c from Contacto c where c.fecha between :desde and :hasta")
    public List<Contacto> busquedaEntreFechas(@Param("desde")Date fechaDesde, @Param("hasta")Date fechaHasta);
    
    
}
