package com.CatalogoWeb.Entidades;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Producto_Propiedad implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@ManyToOne
	@JoinColumn(name = "idProducto")
	private Productos producto;
	
	@ManyToOne
	@JoinColumn(name = "idPropiedad")
	private Propiedades propiedades;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Productos getProducto() {
		return producto;
	}

	public void setProducto(Productos producto) {
		this.producto = producto;
	}

	public Propiedades getPropiedades() {
		return propiedades;
	}

	public void setPropiedades(Propiedades propiedades) {
		this.propiedades = propiedades;
	}

	public Producto_Propiedad() {
		super();
	}
	
	

}
