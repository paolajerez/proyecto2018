package modelos;

public class Genera_fac {
	
	
	
	private Integer codgf,num_inicio,num_fin,num_emitidos,estado;
	
	private String autorizacion,nit,fecha_emision,llave_dosificacion;

	public Integer getCodgf() {
		return codgf;
	}

	public void setCodgf(Integer codgf) {
		this.codgf = codgf;
	}

	public Integer getNum_inicio() {
		return num_inicio;
	}

	public void setNum_inicio(Integer num_inicio) {
		this.num_inicio = num_inicio;
	}

	public Integer getNum_fin() {
		return num_fin;
	}

	public void setNum_fin(Integer num_fin) {
		this.num_fin = num_fin;
	}

	public Integer getNum_emitidos() {
		return num_emitidos;
	}

	public void setNum_emitidos(Integer num_emitidos) {
		this.num_emitidos = num_emitidos;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public String getAutorizacion() {
		return autorizacion;
	}

	public void setAutorizacion(String autorizacion) {
		this.autorizacion = autorizacion;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getFecha_emision() {
		return fecha_emision;
	}

	public void setFecha_emision(String fecha_emision) {
		this.fecha_emision = fecha_emision;
	}

	public String getLlave_dosificacion() {
		return llave_dosificacion;
	}

	public void setLlave_dosificacion(String llave_dosificacion) {
		this.llave_dosificacion = llave_dosificacion;
	}
	
	
	
	@Override
	public String toString() {
		
		return autorizacion;
	}
}
