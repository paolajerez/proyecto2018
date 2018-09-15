package modelos;

import java.util.List;

public class Compras {

	private Integer codc,estado,codpro;
	private String codu,descripcion,fecha;
	private float monto_total;
	
	private List<Detalle_Compras> detalles;

	public Integer getCodc() {
		return codc;
	}

	public void setCodc(Integer codc) {
		this.codc = codc;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Integer getCodpro() {
		return codpro;
	}

	public void setCodpro(Integer codpro) {
		this.codpro = codpro;
	}

	public String getCodu() {
		return codu;
	}

	public void setCodu(String codu) {
		this.codu = codu;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public float getMonto_total() {
		return monto_total;
	}

	public void setMonto_total(float monto_total) {
		this.monto_total = monto_total;
	}

	public List<Detalle_Compras> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<Detalle_Compras> detalles) {
		this.detalles = detalles;
	}
	
	
	
	
	
	
	}
