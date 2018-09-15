package controladores;

import java.sql.Date;
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
import servicios.Serv_Compras;
import servicios.Serv_Detalle_Ventas;
import servicios.Serv_Tipo_Producto;
import servicios.Serv_Usuario;
import servicios.Serv_Ventas;
import servicios.Serv_Genera_fac;
import servicios.Serv_Productos;


@Controller
@RequestMapping("/reportes/*")
public class Gestion_Reportes {

	@Autowired
	private Serv_Ventas serv_ventas;
	
	
	@Autowired
	private Serv_Compras serv_Compras;
	
	
	@Autowired
	private Serv_Usuario serv_usuario;
	

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
	
	@RequestMapping("gestion_reportes")
	public String gestion_reportes(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			return "reportes/gestion_reportes";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@RequestMapping("reporte_ventas")
	public String reporte_ventas(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			
			model.addAttribute("clientes", serv_Cliente.listar_clientes());
			
			model.addAttribute("ventas", serv_ventas.listar());
			
			model.addAttribute("usuarios",serv_usuario.listar());
			
			model.addAttribute("ventas", serv_ventas.listar());
			
			
			return "reportes/reportes_ventas";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("generar_reporte_venta")
	public String generar_reporte_venta(HttpServletRequest request, Model model, String ci,Date fechaini ,Date fechafin ){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			
			
			model.addAttribute("ventas",serv_ventas.ventas_usuario(ci));
			model.addAttribute("rangos",serv_ventas.obtener_por_rango(fechaini, fechafin));
				
			model.addAttribute("fechaini",fechaini);
			
			model.addAttribute("fechafin",fechafin);
			
			
			model.addAttribute("usuario",serv_usuario.obtener_por_Ci(ci));
			
			
			
			
			return "reportes/generar_reporte_venta";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	@RequestMapping("reporte_compras")
	public String reporte_compras(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			
			
			
			model.addAttribute("compras", serv_Compras.listar());
			
			model.addAttribute("usuarios",serv_usuario.listar());
			
		
			
			
			return "reportes/reportes_compras";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@RequestMapping("generar_reporte_compra")
	public String generar_reporte_compra(HttpServletRequest request, Model model, String ci,Date fechaini ,Date fechafin ){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			
			
			model.addAttribute("compras",serv_Compras.compras_usuario(ci));
			model.addAttribute("rangos",serv_Compras.obtener_por_rango(fechaini, fechafin));
				
			model.addAttribute("fechaini",fechaini);
			
			model.addAttribute("fechafin",fechafin);
			
			
			model.addAttribute("usuario",serv_usuario.obtener_por_Ci(ci));
			
			
			
			
			return "reportes/generar_reporte_compra";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	
	
}	

