package modelos;

import java.util.List;

public class Tipo_Productos {
	
	private Integer estado;
	
	private String codtp, nombre;
	
	private List<Productos> productos;
	
	public Integer getEstado() {
	
		return estado;
	
	}
	
	public void setEstado(Integer estado) {
	
		this.estado = estado;
	
	}
	
	public String getCodtp() {
	
		return codtp;
	
	}
	
	public void setCodtp(String codtp) {
	
		this.codtp = codtp;
	
	}
	
	public String getNombre() {
	
		return nombre;
	
	}
	
	public void setNombre(String nombre) {
	
		this.nombre = nombre;
	
	}
	
	public List<Productos> getProductos() {
	
		return productos;
	
	}
	
	public void setProductos(List<Productos> productos) {
	
		this.productos = productos;
	
	}

}