
package com.CatalogoWeb.Entidades;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;


@Entity
public class Cotizacion_Detalle implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private Integer Item;
    
    @JoinColumn(name = "idCotizacionEncabezado")
    private Cotizacion_Encabezado idCotizacion;
    
    @ManyToOne
    @JoinColumn(name = "idProducto")
    private Productos producto;
    private Integer cantidad;
    private Double precioUnit;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getItem() {
		return Item;
	}
	public void setItem(Integer item) {
		Item = item;
	}
	public Cotizacion_Encabezado getIdCotizacion() {
		return idCotizacion;
	}
	public void setIdCotizacion(Cotizacion_Encabezado idCotizacion) {
		this.idCotizacion = idCotizacion;
	}
	public Productos getProducto() {
		return producto;
	}
	public void setProducto(Productos producto) {
		this.producto = producto;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Double getPrecioUnit() {
		return precioUnit;
	}
	public void setPrecioUnit(Double precioUnit) {
		this.precioUnit = precioUnit;
	}
	@Override
	public int hashCode() {
		return Objects.hash(Item, cantidad, id, idCotizacion, precioUnit, producto);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cotizacion_Detalle other = (Cotizacion_Detalle) obj;
		return Objects.equals(Item, other.Item) && Objects.equals(cantidad, other.cantidad)
				&& Objects.equals(id, other.id) && Objects.equals(idCotizacion, other.idCotizacion)
				&& Objects.equals(precioUnit, other.precioUnit) && Objects.equals(producto, other.producto);
	}
       
}
