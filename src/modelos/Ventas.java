package modelos;

import java.util.List;

public class Ventas {

	private Integer codv,estado,codcli,codgf;
	private String codu,descripcion,fecha,cod_control,nit_ci;
	private float monto_total;
	
	private List<Detalle_Ventas> detalles;
	private Genera_fac genera_fac;
	public Integer getCodv() {
		return codv;
	}
	public void setCodv(Integer codv) {
		this.codv = codv;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	public Integer getCodcli() {
		return codcli;
	}
	public void setCodcli(Integer codcli) {
		this.codcli = codcli;
	}
	public Integer getCodgf() {
		return codgf;
	}
	public void setCodgf(Integer codgf) {
		this.codgf = codgf;
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
	public String getCod_control() {
		return cod_control;
	}
	public void setCod_control(String cod_control) {
		this.cod_control = cod_control;
	}
	public String getNit_ci() {
		return nit_ci;
	}
	public void setNit_ci(String nit_ci) {
		this.nit_ci = nit_ci;
	}
	public List<Detalle_Ventas> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<Detalle_Ventas> detalles) {
		this.detalles = detalles;
	}
	public Genera_fac getGenera_fac() {
		return genera_fac;
	}
	public void setGenera_fac(Genera_fac genera_fac) {
		this.genera_fac = genera_fac;
	}
	public float getMonto_total() {
		return monto_total;
	}
	public void setMonto_total(float monto_total) {
		this.monto_total = monto_total;
	}
	
	
	
	
	
		
}
