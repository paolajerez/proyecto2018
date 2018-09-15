package servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import modelos.Proceso;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import coneccion.DB;

@Service
@SuppressWarnings("deprecation")
public class Serv_Proceso extends DB {
	
	private class to_Object implements ParameterizedRowMapper<Proceso>{
		@Override
		public Proceso mapRow(ResultSet rs, int rows) throws SQLException {
			Proceso pr = new Proceso();
			pr.setCodp(rs.getInt("codp"));
			pr.setNombre(rs.getString("nombre"));
			pr.setEnlace(rs.getString("enlace"));
			pr.setDescrip(rs.getString("descrip"));
			pr.setLogo(rs.getString("logo"));
			pr.setEstado(rs.getInt("estado"));
			return pr;
		}
	}
	
	private Integer generar_Codp(){
		try {
			String sql = "SELECT MAX(codp) FROM proceso;";
			return db.queryForInt(sql)+1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public Proceso obtener_por_Codp(Integer codp){
		String sql = "SELECT * FROM proceso WHERE codp=?;";
		return db.queryForObject(sql, new to_Object(), codp);
	}
	
	public List<Proceso> listar(){
		String sql = "SELECT * FROM proceso;";
		return db.query(sql, new to_Object());
	}
	
	public List<Proceso> procesos_de_Rol(Integer codr){
		String sql = "SELECT proceso.* FROM proceso INNER JOIN rolpro ON proceso.codp = rolpro.codp WHERE rolpro.codr = ?;";
		return db.query(sql, new to_Object(), codr);
	}
	
	public List<Proceso> procesos_sin_asignar_a_Rol(Integer codr){
		String sql = "SELECT proceso.* FROM proceso " +
				"WHERE proceso.codp NOT IN (SELECT codp FROM rolpro WHERE rolpro.codr=?);";
		return db.query(sql, new to_Object(), codr);
	}
	
	public void adicionar(Proceso pr){
		pr.setCodp(generar_Codp());
		String sql = "INSERT INTO proceso(codp, nombre, enlace, descrip, logo) VALUES(?,?,?,?,?);";
		db.update(sql, new Object[]{pr.getCodp(), pr.getNombre(), pr.getEnlace(), pr.getDescrip(), pr.getLogo()});
	}
	
	public void modificar(Proceso pr){
		String sql = "UPDATE proceso SET nombre=?, enlace=?, descrip=?, logo=?, estado=1 WHERE codp=?;";
		db.update(sql, new Object[]{pr.getNombre(), pr.getEnlace(), pr.getDescrip(), pr.getLogo(), pr.getCodp()});
	}
	
	public void eliminar(Integer codp){
		eliminar_de_rolpro(codp);
		String sql = "DELETE FROM proceso WHERE codp=?;";
		db.update(sql, codp);
	}
	
	public void eliminar_de_rolpro(Integer codp){
		String sql = "DELETE FROM rolpro WHERE codp=?;";
		db.update(sql, codp);
	}
}