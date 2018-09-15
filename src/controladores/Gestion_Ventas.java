package controladores;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import modelos.Clientes;


import modelos.Detalle_Ventas;

import modelos.Ventas;


import modelos.Genera_fac;

import modelos.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import servicios.Serv_Clientes;
import servicios.Serv_Detalle_Ventas;
import servicios.Serv_Tipo_Producto;
import servicios.Serv_Ventas;
import servicios.Serv_Genera_fac;
import servicios.Serv_Productos;


@Controller
@RequestMapping("/ventas/*")
public class Gestion_Ventas {

	@Autowired
	private Serv_Ventas serv_ventas;
	

	@Autowired
	private Serv_Detalle_Ventas serv_detalle_venta;
	
	@Autowired
	private Serv_Clientes serv_Cliente;
	
	
	@Autowired
	private Serv_Genera_fac serv_genera_factura;
	
	@Autowired
	private Serv_Productos serv_producto;
	
	@Autowired
	private Serv_Tipo_Producto serv_Tipo_Producto;
	
	@RequestMapping("gestion_ventas")
	public String gestion_ventas(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("ventas", serv_ventas.listar());
			return "ventas/gestion_ventas";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	
	int codv=0;
	Genera_fac generado=new Genera_fac();
	Clientes cliente=new Clientes();
	int num_emitido=0;
	ArrayList<Detalle_Ventas> detalles1=new ArrayList<Detalle_Ventas>();
	@RequestMapping("adicionar_venta")
	public String adicionar_venta(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			detalles1.clear();
			generado=new Genera_fac();
			cliente=new Clientes();
			generado=serv_genera_factura.seleccionar_generacion();
			if(generado!=null){
				codv=serv_ventas.generarCodv();
				num_emitido=generado.getNum_inicio()+generado.getNum_emitidos();
				
				model.addAttribute("num_emitido",num_emitido);
				
				model.addAttribute("generado", generado);
				
				model.addAttribute("clientes",serv_Cliente.listar_clientes());
				
				return "ventas/adicionar_venta";
				
			}else{
				
				model.addAttribute("url","ventas/gestion_ventas");
				
				model.addAttribute("msg", "No tiene generador de facturas. Por favor ingrese Generacion de Facturas");
				
				return "principal/mensajes";
			}
			
		}
				else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@RequestMapping("adicionar_detalle")
	public String adicionar_detalle(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			if(generado!=null){
				
				model.addAttribute("detalles1",detalles1);
				
				model.addAttribute("productos", serv_producto.listar_productos());
				
				model.addAttribute("num_emitido",num_emitido);
				
				model.addAttribute("generado", generado);
				
				model.addAttribute("clientes",serv_Cliente.listar_clientes());
				
				return "ventas/adicionar_detalle";
				
			}else{
				
				model.addAttribute("url","ventas/gestion_ventas");
				
				model.addAttribute("msg", "No tiene facturas generadas. Por favor ingrese Generacion de Facturas");
				
				return "principal/mensajes";
			}
			
		}else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}	
	
	@RequestMapping("guardar_detalle")
	public String guardar_detalle(HttpServletRequest request, Model model,String codp,float precio,String descripcion,Integer cantidad){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			if(generado!=null){
				
				
				
				
				Detalle_Ventas detalle=new Detalle_Ventas();
				
				detalle.setCod_det(detalles1.size());
				detalle.setCodp(codp);
				detalle.setCant(cantidad);
				detalle.setPunit(precio);
				detalle.setDescripcion(descripcion);
				detalle.setSubtotal(detalle.getPunit()*detalle.getCant());
				detalles1.add(detalle);
				
				model.addAttribute("detalles1",detalles1);
				model.addAttribute("productos", serv_producto.listar_productos());
				
				
				model.addAttribute("num_emitido",num_emitido);
				
				model.addAttribute("generado", generado);
				
				model.addAttribute("clientes",serv_Cliente.listar_clientes());
				
				
			
				
				return "ventas/adicionar_detalle2";
				
			}else{
				
				model.addAttribute("url","ventas/gestion_ventas");
				
				model.addAttribute("msg", "No tiene facturas generadas. Por favor ingrese Generacion de Facturas");
				
				return "principal/mensajes";
			}
			
		}
		
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("eliminar_detalle")
	public String eliminar_detalle(HttpServletRequest request, Model model,int cod_det){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			try {
				for (Detalle_Ventas det : detalles1) {
					if(det.getCod_det()==cod_det)detalles1.remove(det);
				}
			} catch (Exception e) {
				System.out.println("error "+e.getMessage());
			}
			model.addAttribute("detalles1",detalles1);
			
			model.addAttribute("productos", serv_producto.listar_productos());
			
			
			model.addAttribute("num_emitido",num_emitido);
			
			model.addAttribute("generado", generado);
			
			model.addAttribute("clientes",serv_Cliente.listar_clientes());
			
			return "ventas/adicionar_detalle2";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	@RequestMapping("guardar_venta")
	public String guardar_venta(HttpServletRequest request, Model model,Integer codcli,String nit_ci,String fecha,String cod_control,String descripcion){
	
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		
		if(user!=null){
			
		Iterator<Detalle_Ventas> it=detalles1.iterator();
			
			float monto_total = 0;
			
			
			
			while (it.hasNext()) {
				
				Detalle_Ventas detalle = (Detalle_Ventas) it.next();
				
				monto_total += detalle.getSubtotal();
				
				detalle.setCodv(codv);
			}
			
			
			
			Ventas venta=new Ventas();
			venta.setCodgf(generado.getCodgf());
			venta.setCodv(codv);
			venta.setCodcli(codcli);
			venta.setCodu(user.getCi());
			venta.setNit_ci(nit_ci);
			
			venta.setDescripcion(descripcion);
			
			venta.setFecha(fecha);
		venta.setMonto_total(monto_total);
			venta.setCod_control(cod_control);
			
			
			
			serv_ventas.adicionar(venta);
			for (Detalle_Ventas det : detalles1) {
				det.setCodv(codv);
				
				serv_detalle_venta.adicionar(det);
			}

			model.addAttribute("ventas", serv_ventas.listar());
			return "ventas/gestion_ventas";
		
		}else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	@RequestMapping("ver_venta")
	public String ver_venta(HttpServletRequest request, Model model,int codv){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			if(generado!=null){
				
				Ventas Venta = serv_ventas.obtener_por_codv(codv);
				
				
				generado=serv_genera_factura.obtener_por_Codgf(Venta.getCodgf());
				
				cliente=serv_Cliente.cl_por_ci(Venta.getCodcli());
				
				model.addAttribute("productos", serv_producto.listar_productos());
				
				model.addAttribute("cliente",cliente);
				
				model.addAttribute("generado", generado);
				
				model.addAttribute("factura",Venta);
				
				model.addAttribute("usuario",user);
				
				detalles1.clear();
				if(Venta.getDetalles()!=null){
					detalles1.clear();
					detalles1=(ArrayList<Detalle_Ventas>) Venta.getDetalles();
					
					
					model.addAttribute("detalles1",detalles1);
				}
				
				return "ventas/ver_venta";
				
			}else{
				
				model.addAttribute("url","ventas/gestion_ventas");
				
				model.addAttribute("msg", "No tiene facturas generadas. Por favor ingrese Generacion de Facturas");
				
				return "principal/mensajes";
			}
			
		}
		
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}

}	

