package com.CatalogoWeb.Entidades;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Stock implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String Id;
	
	@ManyToOne
	@JoinColumn(name = "idProducto")
	private Productos producto;
	
	private Integer cantidad;
	
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	public Stock() {
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
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

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Id, cantidad, fecha, producto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		return Objects.equals(Id, other.Id) && Objects.equals(cantidad, other.cantidad)
				&& Objects.equals(fecha, other.fecha) && Objects.equals(producto, other.producto);
	}
	
	

}
