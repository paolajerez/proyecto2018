package servicios;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;



import modelos.Compras;
import modelos.Ventas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;


import coneccion.DB;


import herramientas.fecha_hora;

@Service
public class Serv_Compras extends DB {
	
	@Autowired
	Serv_Detalle_Compras serv_detalle_compras;
	
	
	
	
	private class to_Object implements ParameterizedRowMapper<Compras>{
		@Override
		public Compras mapRow(ResultSet rs, int rows) throws SQLException {
			Compras f = new Compras();
			
			f.setCodc(rs.getInt("codc"));
			
			f.setDescripcion(rs.getString("descripcion"));
			f.setEstado(rs.getInt("estado"));
			f.setFecha(rs.getString("fecha"));
			f.setMonto_total(rs.getFloat("monto_total"));
			f.setCodu(rs.getString("codu"));
			f.setCodpro(rs.getInt("codpro"));
			
			
			
			try {
				f.setDetalles(serv_detalle_compras.obtener_por_codc(rs.getInt("codc")));
			} catch (Exception e) {
				f.setDetalles(null);
			}
			
			return f;
		}
	}
	
	public Compras obtener_por_codc(Integer codc){
		String sql = "SELECT * FROM compras WHERE codc=?";
		return db.queryForObject(sql, new to_Object(), codc);
	}
	
	public List<Compras> listar(){
		String sql = "SELECT * FROM compras";
		return db.query(sql, new to_Object());
	}
	
	public List<Compras> listarActivos(){
		String sql = "SELECT * FROM compras where estado = 1";
		return db.query(sql, new to_Object());
	}
	
	public List<Compras> listarInactivos(){
		String sql = "SELECT * FROM compras where estado = 0";
		return db.query(sql, new to_Object());
	}
	
	public List<Compras> compras_usuario(String codu){
		String sql = "SELECT * FROM compras WHERE codu=?";
		return db.query(sql, new to_Object(), codu);
	}
	
	
public List<Compras>  obtener_por_rango(Date fechaini,Date fechafin){
		
		String sql = "SELECT * FROM compras	where fecha>=? AND fecha<=?";
		return db.query(sql, new to_Object(), fechaini,fechafin);
	}
	
	public void adicionar(Compras f){
		String sql = "INSERT INTO compras(codc,descripcion,fecha,monto_total,codu,codpro) VALUES(?,?,?,?,?,?)";
		db.update(sql, new Object[]{f.getCodc(),f.getDescripcion(),new fecha_hora().fecha(f.getFecha()),f.getMonto_total(),f.getCodu(),f.getCodpro()});
		

	}
	
	public void modificar(Compras f){
		String sql = "UPDATE compras SET codu=?,codpro=?,fecha=? WHERE codc=?";
		db.update(sql, new Object[]{f.getCodu(),f.getCodpro(),new fecha_hora().fecha(f.getFecha()),f.getCodc()});
	}
	
	public void eliminar(Integer codc){
		serv_detalle_compras.eliminar_logicamente(codc);
		
		String sql = "update compras set estado=0 where codc=?";
		db.update(sql, codc);
	}
	public Integer generarCodc(){
		Integer num=listar().size()+1;
		List<Compras> lista=listar();
		for (Compras genera : lista) {
			if(genera.getCodc()==num)num++;
		}
		return num;
	}
	
	


}