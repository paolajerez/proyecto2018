package servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;


import modelos.Tipo_Productos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class Serv_Tipo_Producto {
	
	private SimpleJdbcTemplate template;
	
	private Serv_Productos serv_Productos;
	
	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
	
		this.template = new SimpleJdbcTemplate(dataSource);
	
	}
	
	@Autowired
	public void setServ_Productos(Serv_Productos serv_Productos) {
	
		this.serv_Productos = serv_Productos;
	
	}
	
	private class Tipo_Prod_map implements ParameterizedRowMapper<Tipo_Productos>{
		@Override
		public Tipo_Productos mapRow(ResultSet set, int i)throws SQLException {
	
			Tipo_Productos tipo_prod = new Tipo_Productos();
			
			tipo_prod.setCodtp(set.getString("codtp"));
			
			tipo_prod.setNombre(set.getString("nombre"));
			
			tipo_prod.setEstado(set.getInt("estado"));
			
			try {
			
				tipo_prod.setProductos(serv_Productos.listar_prod_por_codtp(set.getString("codtp")));
			
			} catch (Exception e) {
			
				tipo_prod.setProductos(null);
			
			}
		
			return tipo_prod;
		
		}
	
	}
	
	public Tipo_Productos tipo_prod_por_codtp(String codtp){
	
		String sql = "SELECT * FROM tipo_producto WHERE codtp = ?";
		
		return template.queryForObject(sql, new Tipo_Prod_map(), codtp);
	
	}
	
	public List<Tipo_Productos> listar_productos(){
	
		String sql = "SELECT * FROM tipo_producto  where estado = 1";
	
		return template.query(sql, new Tipo_Prod_map());
	
	}
	
	public String generar_cod_tprod(){
	
		List<Tipo_Productos> Tproductos=listar_productos();
		
		int codigo=Tproductos.size() + 1;
		
		for (Tipo_Productos Tproductos2 : Tproductos ) {
		
			if(codigo==Integer.parseInt(Tproductos2.getCodtp().substring((Tproductos2.getCodtp().indexOf("-")+1))))codigo++;
		
		}
		
		return "tprod-"+codigo;
	
	}
	
	public void insertar_tipo_prod(String nombre){
		
		String codtp = generar_cod_tprod();
	
		String sql = "INSERT INTO tipo_producto(codtp,nombre,estado) VALUES(?,?,?)";
	
		template.update(sql, new Object[]{codtp,nombre,1});
	
	}
	
	public void actualizar_tipo_prod(String codtp, String nombre){
	
		String sql = "UPDATE tipo_producto SET nombre = ? WHERE codtp = ?";
		
		template.update(sql, nombre, codtp);
	
	}
	
	public boolean existeRelacion(String codtp){
		String sql=	"select count(*) from productos where codtp = ?";
		
		int contador=template.queryForInt(sql, codtp);
		
		if(contador > 0)return true;
		
		return false;
	}
	
	public void eliminar_tipo_prod(String codtp){

		if(!existeRelacion(codtp))template.update("DELETE FROM tipo_producto WHERE codtp = ?", codtp);
		
		else {
			
			String sql ="UPDATE tipo_producto "+
						
						"set estado=0 "+
						
						"WHERE tipo_producto.codtp = ?";
			
			template.update(sql, codtp);
			
			sql = 	"UPDATE productos"+
					
					"set estado=0 "+
					
					"WHERE productos.codtp = ?";
			
			template.update(sql, codtp);
		}
	
	}

}