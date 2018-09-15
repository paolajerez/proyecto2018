package servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import modelos.Clientes;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;


@Service
public class Serv_Clientes {
	
	private SimpleJdbcTemplate template;
	
	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
	
		this.template = new SimpleJdbcTemplate(dataSource);
	
	}
	
	
	private class Cliente_map implements ParameterizedRowMapper<Clientes>{
		@Override
		public Clientes mapRow(ResultSet set, int i) throws SQLException {
			
			Clientes cliente = new Clientes();
			
			cliente.setCi(set.getInt("ci"));
			
			cliente.setNombre(set.getString("nombre"));
			
			cliente.setAp(set.getString("ap"));
			
			cliente.setAm(set.getString("am"));
			
			cliente.setDireccion(set.getString("direccion"));
			
			cliente.setEstado(set.getInt("estado"));
			
			cliente.setFoto(set.getString("foto"));
			
			cliente.setTelefono(set.getInt("telefono"));
			
			cliente.setEmpresa(set.getString("empresa"));
				
			return cliente;
		
		}
	
	}

	public Clientes cl_por_ci(Integer ci){
	
		String sql = "SELECT * FROM clientes WHERE ci = ?";
		
		try {
		
			return template.queryForObject(sql, new Cliente_map(), ci);
		
		} catch (Exception e) {
		
			return null;
		
		}
	
	}
	
	public List<Clientes> listar_clientes(){
	
		String sql = "SELECT * FROM clientes where estado = 1";
	
		try {
		
			return template.query(sql, new Cliente_map());
		
		} catch (Exception e) {
		
			return null;
		
		}
	
	}
	
	public void adicionar_Cliente(Clientes cl){
	
	String sql = "INSERT INTO clientes(ci, nombre, ap, am, direccion, telefono, foto,  empresa) VALUES(?,?,?,?,?,?,?,?)";
	template.update(sql, new Object[]{cl.getCi(), cl.getNombre(), cl.getAp(), cl.getAm(), cl.getDireccion(), cl.getTelefono(), cl.getFoto(), cl.getEmpresa()});
	
	}
	
	public void actualizar_cliente(Clientes cl){
	
	
			String sql = "UPDATE clientes SET nombre = ?, ap = ?, am = ?, direccion = ?, telefono = ?, foto = ?,  empresa = ?, estado=1 WHERE ci = ?";
			
			template.update(sql, new Object[]{cl.getNombre(), cl.getAp(), cl.getAm(), cl.getDireccion(), cl.getTelefono(), cl.getFoto(),  cl.getEmpresa(), cl.getCi()});
		
			
	}
	

	public void eliminar_cliente(Integer ci){
		
	 template.update("update clientes set estado = 0 where ci = ?", ci);
	
    }
	

}