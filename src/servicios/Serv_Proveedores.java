package servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;

import modelos.Proveedores;

@Service
public class Serv_Proveedores {
	
	private SimpleJdbcTemplate template;
	
	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
	
		this.template = new SimpleJdbcTemplate(dataSource);
	
	}
	
	private class Proveedor_map implements ParameterizedRowMapper<Proveedores>{
		@Override
		public Proveedores mapRow(ResultSet set, int i) throws SQLException {
	
			Proveedores proveedor = new Proveedores();
			
			proveedor.setCi(set.getInt("ci"));
			
			proveedor.setNombre(set.getString("nombre"));
			
			proveedor.setAp(set.getString("ap"));
			
			proveedor.setAm(set.getString("am"));
			
			proveedor.setEmpresa(set.getString("empresa"));
			
			proveedor.setDireccion(set.getString("direccion"));
			
			proveedor.setEstado(set.getInt("estado"));
			
			proveedor.setFoto(set.getString("foto"));
			
			proveedor.setTelefono(set.getInt("telefono"));
			
			return proveedor;
		
		}
	
	} 
	
	public Proveedores prov_por_ci(Integer ci){
	
		String sql = "SELECT * FROM proveedores WHERE ci = ?";
		
		try {
		
			return template.queryForObject(sql, new Proveedor_map(), ci);
		
		} catch (Exception e) {
		
			return null;
		
		}
	
	}
	
	public List<Proveedores> listar_proveedores(){
	
		String sql = "SELECT * FROM proveedores where estado = 1";
	
		try {
		
			return template.query(sql, new Proveedor_map());
		
		} catch (Exception e) {
		
			return null;
		
		}
	
	}
	
	public void adicionar_proveedor(Proveedores prov){	
		String sql = "INSERT INTO proveedores(ci, nombre, ap, am, direccion, telefono, foto,  empresa) VALUES(?,?,?,?,?,?,?,?)";
		template.update(sql, new Object[]{prov.getCi(), prov.getNombre(), prov.getAp(), prov.getAm(), prov.getDireccion(), prov.getTelefono(), prov.getFoto(),  prov.getEmpresa()});
	
	}
	
	public void modificar_proveedor(Proveedores prov){	
		String sql = "UPDATE proveedores SET nombre = ?, ap = ?, am = ?, direccion = ?, telefono = ?, foto = ?, empresa = ? WHERE ci = ?";
		template.update(sql, new Object[]{prov.getNombre(), prov.getAp(), prov.getAm(), prov.getDireccion(), prov.getTelefono(), prov.getFoto(),prov.getEmpresa(), prov.getCi()});
		
	}
	

	
	public void eliminar_proveedor(Integer ci){	
		template.update("update proveedores set estado = 0 where ci = ?", ci);
		
		
	
	}
	
}