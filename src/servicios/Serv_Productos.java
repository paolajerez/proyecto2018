package servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import modelos.Productos;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class Serv_Productos {
	
	private SimpleJdbcTemplate template;
	
	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
	
		this.template = new SimpleJdbcTemplate(dataSource);
	
	}
	
	private class Producto_map implements ParameterizedRowMapper<Productos>{
		@Override
		public Productos mapRow(ResultSet set, int i) throws SQLException {	
			Productos prod = new Productos();			
			prod.setCodp(set.getString("codp"));			
			prod.setNombre(set.getString("nombre"));			
			prod.setDescripcion(set.getString("descripcion"));		
			prod.setCantidad(set.getInt("cantidad"));
			prod.setPrecio(set.getFloat("precio"));				
			prod.setCodtp(set.getString("codtp"));			
			//prod.setCodtm(set.getString("codtm"));			
			prod.setEstado(set.getInt("estado"));
			
			return prod;
		
		}
	
	}
	
	public Productos prod_por_codp(String codp){
	
		String sql = "SELECT * FROM productos WHERE codp = ?";
	
		return template.queryForObject(sql, new Producto_map(), codp);
	
	}
	
	public List<Productos> listar_productos(){
	
		String sql = "SELECT * FROM productos where estado = 1";
		
		return template.query(sql, new Producto_map());
	
	}
	
	public List<Productos> listar_prod_por_codtp(String codtp){
	
		String sql = "SELECT * FROM productos WHERE codtp = ?";
		
		return template.query(sql, new Producto_map(), codtp);
	
	}
	
	public String generar_cod_prod(){
		
		List<Productos> productos=listar_productos();
		
		int codigo=productos.size() + 1;
		
		for (Productos productos2 : productos ) {
		
			if(codigo==Integer.parseInt(productos2.getCodp().substring((productos2.getCodp().indexOf("-")+1))))codigo++;
		
		}
		
		return "prod-"+codigo;
	
	}
	
	public void adicionar(Productos prod){
		
		String codp = generar_cod_prod();
	
		String sql = "INSERT INTO productos(codp,nombre,descripcion,cantidad,precio,estado,codtp) values(?,?,?,?,?,?,?)";
		
		template.update(sql, new Object[]{codp,prod.getNombre(),prod.getDescripcion(),prod.getCantidad(),prod.getPrecio(),1,prod.getCodtp()});
	
	}
	
	public void modificar(Productos prod){
	
		
		
			String sql = "UPDATE productos SET nombre=?,descripcion=?,cantidad=?,precio=?,estado=?,codtp=? WHERE codp=?";
			
			template.update(sql, new Object[]{prod.getNombre(),prod.getDescripcion(),prod.getCantidad(),prod.getPrecio(),1,prod.getCodtp(),prod.getCodp()});
		
		
	
	}
	
	
	

	public void eliminar(String codp){
	
		template.update("UPDATE productos set estado = 0 WHERE codp = ?", codp);
		
		
	
	}

}