
package com.CatalogoWeb.Repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.CatalogoWeb.Entidades.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
    
    @Query ("select u from Usuario u where u.activo = true order by u.usuario")
    public List<Usuario> buscarUsuariosActivos();
    
    @Query("select u from Usuario u where u.usuario like :usuario")
    public Usuario verificarUsuario(@Param("usuario")String usuario);
    
    @Query("SELECT u FROM Usuario u WHERE u.correo = :correo")
    public Usuario buscarPorCorreo(@Param("correo")String correo);
    
}
