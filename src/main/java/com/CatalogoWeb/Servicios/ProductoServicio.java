package com.CatalogoWeb.Servicios;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.CatalogoWeb.Entidades.Producto_Propiedad;
import com.CatalogoWeb.Entidades.Productos;
import com.CatalogoWeb.Repositorios.EmpresaRepositorio;
import com.CatalogoWeb.Repositorios.ProductosRepositorio;
import com.CatalogoWeb.Repositorios.UsuarioRepositorio;
import com.CatalogoWeb.Errores.CatalogoError;
import java.util.Optional;

@Service
public class ProductoServicio {

    @Autowired
    private ProductosRepositorio pr;

    @Autowired
    private EmpresaRepositorio er;

    @Autowired
    private UsuarioRepositorio ur;

    @Transactional(readOnly = true)
    public List<Productos> buscarActivos() {
        return pr.listarProductosActivos();
    }

    @Transactional(readOnly = true)
    public List<Productos> buscarPorKeywordYCategoria(String kw, String cat) {
        return pr.listarProductosPorKeywordYCategoria(kw, cat);
    }

    @Transactional(readOnly = true)
    public List<String> listarCategoria() {
        return pr.listarCategoria();
    }

    @Transactional(readOnly = true)
    public List<String> listarEmpresa() {
        return pr.listarEmpresa();
    }

    @Transactional
    public void nuevoProducto(String descripcion, String detalle, String categoria,
            String usuario, String empresa, Double precio, String foto) throws CatalogoError {

        if (!(descripcion.isEmpty() || precio <= 0 || detalle.isEmpty() || categoria.isEmpty() || usuario.isEmpty() || empresa.isEmpty())) {
            try {
                Productos producto = new Productos();

                producto.setDescripcion(descripcion);
                producto.setDetalle(detalle);
                producto.setCategoria(categoria);
                producto.setUsuario(ur.verificarUsuario(usuario));
                producto.setEmpresa(empresa);
                producto.setPrecio(precio);
                producto.setFoto(foto);
                producto.setActivo(true);
                producto.setFecha(new Date());

                pr.save(producto);
            } catch (Exception e) {
                throw new CatalogoError("Ha ocurrido un error al intentar guardar los datos.");
            }
        } else {
            throw new CatalogoError("Alguno de los datos se encuentra incompleto. Verifique y vuelva a intentarlo");
        }
    }

    @Transactional
    public void guardarModif(String id, String descripcion,String detalle,String categoria, String usuario, 
            String empresa, Double precio, String foto) throws CatalogoError {
        
         if (!(descripcion.isEmpty() || precio <= 0 || detalle.isEmpty() || categoria.isEmpty() || usuario.isEmpty() || empresa.isEmpty())) {
            try {
                Productos producto = pr.findById(id).get();

                producto.setDescripcion(descripcion);
                producto.setDetalle(detalle);
                producto.setCategoria(categoria);
                producto.setUsuario(ur.verificarUsuario(usuario));
                producto.setEmpresa(empresa);
                producto.setPrecio(precio);
                producto.setFoto(foto);
                producto.setActivo(true);
                producto.setFecha(new Date());

                pr.save(producto);
            } catch (Exception e) {
                throw new CatalogoError("Ha ocurrido un error al intentar guardar los datos.");
            }
        } else {
            throw new CatalogoError("Alguno de los datos se encuentra incompleto. Verifique y vuelva a intentarlo");
        }
    }

    public Productos traerProducto(String Id) {
        return pr.findById(Id).get();
    }
    
    @Transactional
    public void bajaProd(String id) throws CatalogoError{
        Optional<Productos> option = pr.findById(id);
        
        if (option.isPresent()) {
            Productos producto = option.get();
            if (producto.getActivo() == true) {
                producto.setActivo(false);
                pr.save(producto);
            }else{
                throw new CatalogoError("El producto no está activo");
            }
        }
        
    }
    
}
