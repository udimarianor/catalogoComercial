
package com.CatalogoWeb.Repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.CatalogoWeb.Entidades.Empresa;

@Repository
public interface EmpresaRepositorio extends JpaRepository<Empresa, String>{
    
    @Query ("select e from Empresa e where e.activo = true order by e.nombre")
    public List<Empresa> buscarEmpresasActivas();
    
    @Query("select e from Empresa e where e.nombre like :nombre")
    public Empresa verifNombre(@Param("nombre")String nombre);
    
}
