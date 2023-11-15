package model;

import java.io.InputStream;
import java.util.Objects;


//Clase Producto
public class Producto {
	
	private String codigo, nombre;
	private Double precio;
	private int disponible;
	private InputStream image;
	
	public Producto(String codigo, String nombre, Double precio, int disponible, InputStream img) {
		this.codigo=codigo;
		this.nombre=nombre;
		this.precio=precio;
		this.disponible=disponible;
		this.image=img;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
	public int getDisponible() {
		return disponible;
	}

	public void setDisponible(int disponible) {
		this.disponible = disponible;
	}

	public InputStream getImage() {
		return image;
	}

	public void setImage(InputStream image) {
		this.image = image;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		return Objects.equals(codigo, other.codigo);
	}

	
	
}
