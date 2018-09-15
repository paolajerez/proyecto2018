package modelos;


public class Detalle_Ventas {
	
	private Integer codv, cant,cod_det,estado;
	
	private String codp, descripcion;
	
	private Float punit,subtotal;

	public Integer getCodv() {
		return codv;
	}

	public void setCodv(Integer codv) {
		this.codv = codv;
	}

	public Integer getCant() {
		return cant;
	}

	public void setCant(Integer cant) {
		this.cant = cant;
	}

	public Integer getCod_det() {
		return cod_det;
	}

	public void setCod_det(Integer cod_det) {
		this.cod_det = cod_det;
	}

	public String getCodp() {
		return codp;
	}

	public void setCodp(String codp) {
		this.codp = codp;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Float getPunit() {
		return punit;
	}

	public void setPunit(Float punit) {
		this.punit = punit;
	}

	public Float getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Float subtotal) {
		this.subtotal = subtotal;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	
	
	

}