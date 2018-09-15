package servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import modelos.Datos;
import modelos.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import tools.Date_Hour;
import tools.FirstInCapitals;

import coneccion.DB;

@Service
@SuppressWarnings("deprecation")
public class Serv_Usuario extends DB {
	
	@Autowired
	private Serv_Datos serv_Datos;
	@Autowired
	private Serv_Rol serv_Rol;
	
	private Date_Hour dh = new Date_Hour();
	private FirstInCapitals fic = new FirstInCapitals();
	
	private class to_Object implements ParameterizedRowMapper<Usuario>{
		@Override
		public Usuario mapRow(ResultSet rs, int rows) throws SQLException {
			Usuario us = new Usuario();
			us.setCi(rs.getString("ci"));
			us.setNombre(rs.getString("nombre"));
			us.setAp(rs.getString("ap"));
			us.setAm(rs.getString("am"));
			us.setSexo(rs.getString("sexo"));
			us.setFechanac(rs.getDate("fechanac").toString());
			us.setEcivil(rs.getString("ecivil"));
			us.setFoto(rs.getString("foto"));
			us.setEmail(rs.getString("email"));
			us.setEstado(rs.getInt("estado"));
			us.setTelefono(rs.getString("telefono"));
			try {
				us.setDatos(serv_Datos.obteber_por_Ci(rs.getString("ci")));
			} catch (Exception e) {
				us.setDatos(null);
			}
			try {
				us.setRoles(serv_Rol.roles_de_Usuario(rs.getString("ci")));
			} catch (Exception e) {
				us.setRoles(null);
			}
			return us;
		}
	}
	
	public Usuario obtener_por_Ci(String ci){
		String sql = "SELECT * FROM usuario WHERE ci=?;";
		return db.queryForObject(sql, new to_Object(), ci);
	}
	
	public List<Usuario> listar(){
		String sql = "SELECT * FROM usuario;";
		return db.query(sql, new to_Object());
	}
	
	public Usuario iniciar_sesion(Datos dt){
		String sql = "SELECT usuario.* FROM usuario INNER JOIN datos ON usuario.ci = datos.ci " +
				"WHERE datos.login = ? AND datos.clave = ?;";
		return db.queryForObject(sql, new to_Object(), new Object[]{dt.getLogin(), dt.getClave()});
	}
	
	public void adicionar(Usuario us){
		us.setNombre(fic.FiC(us.getNombre()));
		us.setAp(fic.FiC(us.getAp()));
		us.setAm(fic.FiC(us.getAm()));
		String sql = "INSERT INTO usuario(ci, nombre, ap, am, sexo, fechanac, ecivil, foto, email, telefono) VALUES(?,?,?,?,?,?,?,?,?,?);";
		db.update(sql, new Object[]{us.getCi(), us.getNombre(), us.getAp(), us.getAm(), us.getSexo(), dh.y_m_d(us.getFechanac()), us.getEcivil(), us.getFoto(), us.getEmail(), us.getTelefono()});
	}
	
	public void modificar(Usuario us){
		us.setNombre(fic.FiC(us.getNombre()));
		us.setAp(fic.FiC(us.getAp()));
		us.setAm(fic.FiC(us.getAm()));
		String sql = "UPDATE usuario SET nombre=?, ap=?, am=?, sexo=?, fechanac=?, ecivil=?, foto=?, email=?, telefono=?, estado=1 WHERE ci=?;";
		db.update(sql, new Object[]{us.getNombre(), us.getAp(), us.getAm(), us.getSexo(), dh.y_m_d(us.getFechanac()), us.getEcivil(), us.getFoto(), us.getEmail(), us.getTelefono(), us.getCi()});
	}
	
	public void borrado_log(String ci){
		serv_Datos.borrado_log(ci);
		String sql = "UPDATE usuario SET estado=0 WHERE ci=?;";
		db.update(sql, ci);
	}
	
	public void asignar_roles_a_Usuario(String ci, Integer codr){
		String sql = "INSERT INTO usurol(ci, codr) VALUES(?,?);";
		db.update(sql, new Object[]{ci, codr});
	}
	
	public void eliminar_usurol(String ci){
		String sql = "DELETE FROM usurol WHERE ci=?;";
		db.update(sql, ci);
	}
}