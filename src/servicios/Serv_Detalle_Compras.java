package servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import modelos.Detalle_Compras;


import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import coneccion.DB;




@Service
public class Serv_Detalle_Compras extends DB  {

	private class to_Object implements ParameterizedRowMapper<Detalle_Compras>{
		@Override
		public Detalle_Compras mapRow(ResultSet rs, int rows) throws SQLException {
			
			
			
			Detalle_Compras f = new Detalle_Compras();
			
			f.setCod_det(rs.getInt("cod_det"));
			f.setCodc(rs.getInt("codc"));
			f.setCodp(rs.getString("codp"));
			f.setDescripcion(rs.getString("descripcion"));
			f.setCant(rs.getInt("cant"));			
			f.setPunit(rs.getFloat("punit"));
			f.setSubtotal(rs.getFloat("subtotal"));			
			f.setEstado(rs.getInt("estado"));
			
			return f;
		}
	}
	
	public Detalle_Compras obtener_por_cod_det(Integer cod_det){
		String sql = "SELECT * FROM detalle_compras WHERE cod_det=?";
		return db.queryForObject(sql, new to_Object(), cod_det);
	}
	
	public List<Detalle_Compras> obtener_por_codc(Integer codc){
		String sql = "SELECT * FROM detalle_compras WHERE codc=?";
		return db.query(sql, new to_Object(), codc);
	}
	
	public void adicionar(Detalle_Compras d){
		String sql = "INSERT INTO detalle_compras(cod_det,codc,codp,descripcion,cant,punit,subtotal) VALUES(?,?,?,?,?,?,?)";
		db.update(sql, new Object[]{generarCod_Det(),d.getCodc(),d.getCodp(),d.getDescripcion(),d.getCant(),d.getPunit(),d.getSubtotal()});
	}
	
	public void eliminar_logicamente(Integer codc){
		String sql = "update detalle_compras set estado=0 where codc=?";
		db.update(sql, codc);
	}
	public void eliminar_fisicamente(Integer codc){
		String sql = "delete from detalle_compras where codc=?";
		db.update(sql, codc);
	}
	public List<Detalle_Compras> listar(){
		String sql = "SELECT * FROM detalle_compras";
		return db.query(sql,new to_Object());
	}
	public Integer generarCod_Det(){
		List<Detalle_Compras> lista=listar();
		Integer num=lista.size()+1;
		for (Detalle_Compras genera : lista) {
			if(genera.getCod_det()==num)num++;
		}
		return num;
	}

}