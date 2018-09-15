package modelos;


public class Proveedores {
	
	private Integer ci, estado, telefono;
	
	private String nombre, ap, am, direccion, foto, empresa;
	
	public Integer getCi() {
	
		return ci;
	
	}
	
	public void setCi(Integer ci) {
	
		this.ci = ci;
	
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
	
	public String getAp() {
	
		return ap;
	
	}
	
	public void setAp(String ap) {
	
		this.ap = ap;
	
	}
	
	public String getAm() {
	
		return am;
	
	}
	
	public void setAm(String am) {
	
		this.am = am;
	
	}
	
	public String getDireccion() {
	
		return direccion;
	
	}
	
	public void setDireccion(String direccion) {
	
		this.direccion = direccion;
	
	}
	
	public Integer getTelefono() {
	
		return telefono;
	
	}
	
	public void setTelefono(Integer telefono) {
	
		this.telefono = telefono;
	
	}
	
	public String getFoto() {
	
		return foto;
	
	}
	
	public void setFoto(String foto) {
	
		this.foto = foto;
	
	}
	
	public String getEmpresa() {
	
		return empresa;
	
	}
	
	public void setEmpresa(String empresa) {
	
		this.empresa = empresa;
	
	}
	
	@Override
	public String toString() {
		
		return String.format("%s %s %s", nombre, ap, am == null ? "" : am);
	
	}
}