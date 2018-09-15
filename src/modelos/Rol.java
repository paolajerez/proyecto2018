package modelos;

import java.util.List;

public class Rol {
	
	private Integer codr, estado;
	private String nombre, descrip, logo;
	
	private List<Proceso> procesos;
	
	
	
	
	public Integer getCodr() {
		return codr;
	}
	public void setCodr(Integer codr) {
		this.codr = codr;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescrip() {
		return descrip;
	}
	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public List<Proceso> getProcesos() {
		return procesos;
	}
	public void setProcesos(List<Proceso> procesos) {
		this.procesos = procesos;
	}
}