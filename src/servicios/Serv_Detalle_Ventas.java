package servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import modelos.Detalle_Ventas;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import coneccion.DB;




@Service
public class Serv_Detalle_Ventas extends DB  {

	private class to_Object implements ParameterizedRowMapper<Detalle_Ventas>{
		@Override
		public Detalle_Ventas mapRow(ResultSet rs, int rows) throws SQLException {
			Detalle_Ventas f = new Detalle_Ventas();
			
			f.setCod_det(rs.getInt("cod_det"));
			f.setCodv(rs.getInt("codv"));
			f.setCodp(rs.getString("codp"));
			f.setDescripcion(rs.getString("descripcion"));
			f.setCant(rs.getInt("cant"));			
			f.setPunit(rs.getFloat("punit"));
			f.setSubtotal(rs.getFloat("subtotal"));			
			f.setEstado(rs.getInt("estado"));
			
			return f;
		}
	}
	
	public Detalle_Ventas obtener_por_cod_det(Integer cod_det){
		String sql = "SELECT * FROM detalle_ventas WHERE cod_det=?";
		return db.queryForObject(sql, new to_Object(), cod_det);
	}
	
	public List<Detalle_Ventas> obtener_por_codv(Integer codv){
		String sql = "SELECT * FROM detalle_ventas WHERE codv=?";
		return db.query(sql, new to_Object(), codv);
	}
	
	public void adicionar(Detalle_Ventas d){
		String sql = "INSERT INTO detalle_ventas(cod_det,codv,codp,descripcion,cant,punit,subtotal) VALUES(?,?,?,?,?,?,?)";
		db.update(sql, new Object[]{generarCod_Det(),d.getCodv(),d.getCodp(),d.getDescripcion(),d.getCant(),d.getPunit(),d.getSubtotal()});
	}
	
	public void eliminar_logicamente(Integer codv){
		String sql = "update detalle_ventas set estado=0 where codv=?";
		db.update(sql, codv);
	}
	public void eliminar_fisicamente(Integer codv){
		String sql = "delete from detalle_ventas where codv=?";
		db.update(sql, codv);
	}
	public List<Detalle_Ventas> listar(){
		String sql = "SELECT * FROM detalle_ventas";
		return db.query(sql,new to_Object());
	}
	public Integer generarCod_Det(){
		List<Detalle_Ventas> lista=listar();
		Integer num=lista.size()+1;
		for (Detalle_Ventas genera : lista) {
			if(genera.getCod_det()==num)num++;
		}
		return num;
	}

}