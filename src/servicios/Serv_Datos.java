package servicios;

import java.sql.ResultSet;
import java.sql.SQLException;

import modelos.Datos;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import coneccion.DB;

@Service
@SuppressWarnings("deprecation")
public class Serv_Datos extends DB {
	
	private class to_Object implements ParameterizedRowMapper<Datos>{
		@Override
		public Datos mapRow(ResultSet rs, int rows) throws SQLException {
			Datos dt = new Datos();
			dt.setCi(rs.getString("ci"));
			dt.setLogin(rs.getString("login"));
			dt.setClave(rs.getString("clave"));
			dt.setEstado(rs.getInt("estado"));
			return dt;
		}
	}
	
	public Datos obteber_por_Ci(String ci){
		String sql = "SELECT * FROM datos WHERE ci=?;";
		return db.queryForObject(sql, new to_Object(), ci);
	}
	
	public void adicionar(Datos dt){
		String sql = "INSERT INTO datos(ci, login, clave) VALUES(?,?,?);";
		db.update(sql, new Object[]{dt.getCi(), dt.getLogin(), dt.getClave()});
	}
	
	public void modificar(Datos dt){
		String sql = "UPDATE datos SET login=?, clave=?, estado=1 WHERE ci=?;";
		db.update(sql, new Object[]{dt.getLogin(), dt.getClave(), dt.getCi()});
	}
	
	public void borrado_log(String ci){
		String sql = "UPDATE datos SET estado=0 WHERE ci=?;";
		db.update(sql, ci);
	}
}