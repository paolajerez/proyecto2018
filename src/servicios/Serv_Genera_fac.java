package servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import modelos.Clientes;
import modelos.Genera_fac;


import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import tools.Date_Hour;
import tools.FirstInCapitals;



import coneccion.DB;

@Service
@SuppressWarnings("deprecation")

public class Serv_Genera_fac extends DB{
	
	
	private Date_Hour dh2 = new Date_Hour();
	private FirstInCapitals fic2 = new FirstInCapitals();
	private class to_Object implements ParameterizedRowMapper<Genera_fac>{
		@Override
		public Genera_fac mapRow(ResultSet rs, int rows) throws SQLException {
			
			Genera_fac gene = new Genera_fac();
			
			gene.setCodgf(rs.getInt("codgf"));
			gene.setAutorizacion(rs.getString("autorizacion"));
			gene.setNit(rs.getString("nit"));
			gene.setNum_inicio(rs.getInt("num_inicio"));
			gene.setNum_fin(rs.getInt("num_fin"));			
			gene.setFecha_emision(rs.getDate("fecha_emision").toString());
			gene.setLlave_dosificacion(rs.getString("llave_dosificacion"));
			gene.setNum_emitidos(rs.getInt("num_emitidos"));
			gene.setEstado(rs.getInt("estado"));
			
			return gene;
		}
	  }
	
	public Genera_fac obtener_por_Codgf(Integer codgf){
		String sql = "SELECT * FROM genera_fac WHERE codgf=?;";
		return db.queryForObject(sql, new to_Object(), codgf);
	}
	
	public List<Genera_fac> listar(){
		String sql = "SELECT * FROM genera_fac;";
		return db.query(sql, new to_Object());
	}
	
	public void adicionar(Genera_fac genf){
		
		String sql = "INSERT INTO genera_fac(codgf,autorizacion,nit, num_inicio, num_fin, fecha_emision,llave_dosificacion, num_emitidos,estado) VALUES(?,?,?,?,?,?,?,?,?);";
		db.update(sql, new Object[]{genf.getCodgf(), genf.getAutorizacion(), genf.getNit(), genf.getNum_inicio(), genf.getNum_fin(), dh2.y_m_d(genf.getFecha_emision()), genf.getLlave_dosificacion(), genf.getNum_emitidos(), genf.getEstado()});
	}
	
	
	public void modificar(Genera_fac genf){
		
		String sql = "UPDATE genera_fac SET  autorizacion=?, nit=?, num_inicio=?, num_fin=?,  fecha_emision=?, llave_dosificacion=?, num_emitidos=?,estado=1 WHERE codgf=?;";
		db.update(sql, new Object[]{genf.getAutorizacion(), genf.getNit(), genf.getNum_inicio(),genf.getNum_fin(), dh2.y_m_d(genf.getFecha_emision()), genf.getLlave_dosificacion(), genf.getNum_emitidos(),  genf.getCodgf()});
	}
	
	public void borrado_log(Integer codgf){
		
		String sql = "UPDATE genera_fac SET estado=0 WHERE codgf=?;";
		db.update(sql, codgf);
	}
	
	
	
	public Integer generarCodgf(){
		Integer num=listar().size()+1;
		List<Genera_fac> lista=listar();
		for (Genera_fac genera : lista) {
			if(genera.getCodgf()==num)num++;
		}
		return num;
	}
	
	
	public Genera_fac seleccionar_generacion(){
		String sql="select * from genera_fac where CURRENT_DATE<=fecha_emision AND num_fin>(num_emitidos+num_inicio)";
		List<Genera_fac> lista=db.query(sql, new to_Object());
		if(lista.size()>0){
			Genera_fac gen=new Genera_fac();
		for (Genera_fac genera_Fac : lista) {
			gen=genera_Fac;
			break;
		}
		return gen;
		}else{
			return null;
		}
	}

	
	
	
	

}
