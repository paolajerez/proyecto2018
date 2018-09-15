package servicios;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;



import modelos.Rol;
import modelos.Ventas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;



import coneccion.DB;


import herramientas.fecha_hora;

@Service
public class Serv_Ventas extends DB {
	
	@Autowired
	Serv_Detalle_Ventas serv_detalle_venta;
	
	
	@Autowired
	Serv_Genera_fac serv_genera_fac;
	
	private class to_Object implements ParameterizedRowMapper<Ventas>{
		@Override
		public Ventas mapRow(ResultSet rs, int rows) throws SQLException {
			Ventas f = new Ventas();
			
			f.setCodv(rs.getInt("codv"));
			
			f.setDescripcion(rs.getString("descripcion"));
			f.setEstado(rs.getInt("estado"));
			f.setFecha(rs.getString("fecha"));
			f.setMonto_total(rs.getFloat("monto_total"));
			f.setCodu(rs.getString("codu"));
			f.setCodcli(rs.getInt("codcli"));
			f.setCodgf(rs.getInt("codgf"));
			f.setNit_ci(rs.getString("nit_ci"));
			f.setCod_control(rs.getString("cod_control"));
			
			
			try {
				f.setDetalles(serv_detalle_venta.obtener_por_codv(rs.getInt("codv")));
			} catch (Exception e) {
				f.setDetalles(null);
			}
			try {
				f.setGenera_fac(serv_genera_fac.obtener_por_Codgf(rs.getInt("codgf")));
			} catch (Exception e) {
				f.setGenera_fac(null);
			}
			return f;
		}
	}
	
	public Ventas obtener_por_codv(Integer codv){
		String sql = "SELECT * FROM ventas WHERE codv=?";
		return db.queryForObject(sql, new to_Object(), codv);
	}
	
	
	public List<Ventas> ventas_usuario(String codu){
		String sql = "SELECT * FROM ventas WHERE codu=?";
		return db.query(sql, new to_Object(), codu);
	}
	
	public List<Ventas>  obtener_por_fecha(Date fecha){
		
		String sql = "SELECT * FROM ventas	where fecha<=?";
		return db.query(sql, new to_Object(), fecha);
	}
	
public List<Ventas>  obtener_por_rango(Date fechaini,Date fechafin){
		
		String sql = "SELECT * FROM ventas	where fecha>=? AND fecha<=?";
		return db.query(sql, new to_Object(), fechaini,fechafin);
	}
	
	
	
	public List<Ventas> listar(){
		String sql = "SELECT * FROM ventas";
		return db.query(sql, new to_Object());
	}
	
	public List<Ventas> listarActivos(){
		String sql = "SELECT * FROM ventas where estado = 1";
		return db.query(sql, new to_Object());
	}
	
	public List<Ventas> listarInactivos(){
		String sql = "SELECT * FROM ventas where estado = 0";
		return db.query(sql, new to_Object());
	}
	
	public void adicionar(Ventas f){
		String sql = "INSERT INTO ventas(codgf,codv,descripcion,fecha,monto_total,codu,codcli,nit_ci,cod_control) VALUES(?,?,?,?,?,?,?,?,?)";
		db.update(sql, new Object[]{f.getCodgf(),f.getCodv(),f.getDescripcion(),new fecha_hora().fecha(f.getFecha()),f.getMonto_total(),f.getCodu(),f.getCodcli(),f.getNit_ci(),f.getCod_control()});
		sql="UPDATE genera_fac set num_emitidos=num_emitidos+1 WHERE codgf=?";
		db.update(sql,f.getCodgf());
	}
	
	public void modificar(Ventas f){
		String sql = "UPDATE ventas SET codu=?,codcli=?,nit_ci=?,fecha=?,cod_control=? WHERE codv=?";
		db.update(sql, new Object[]{f.getCodu(),f.getCodcli(),f.getNit_ci(),new fecha_hora().fecha(f.getFecha()),f.getCod_control(),f.getCodv()});
	}
	
	public void eliminar(Integer codv){
		serv_detalle_venta.eliminar_logicamente(codv);
		
		String sql = "update ventas set estado=0 where codv=?";
		db.update(sql, codv);
	}
	public Integer generarCodv(){
		Integer num=listar().size()+1;
		List<Ventas> lista=listar();
		for (Ventas genera : lista) {
			if(genera.getCodv()==num)num++;
		}
		return num;
	}
	
	


}