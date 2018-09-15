package controladores;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import modelos.Clientes;
import modelos.Compras;
import modelos.Detalle_Compras;



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
import servicios.Serv_Detalle_Compras;
import servicios.Serv_Detalle_Ventas;
import servicios.Serv_Proveedores;
import servicios.Serv_Tipo_Producto;
import servicios.Serv_Ventas;
import servicios.Serv_Genera_fac;
import servicios.Serv_Productos;


@Controller
@RequestMapping("/compras/*")
public class Gestion_Compras {

	

	@Autowired
	private Serv_Compras serv_Compras;
	
	
	
	
	@Autowired
	private Serv_Detalle_Compras serv_Detalle_Compras;
	
	
	@Autowired
	private Serv_Proveedores serv_Proveedores;
	
	
	
	
	@Autowired
	private Serv_Productos serv_producto;
	
	@Autowired
	private Serv_Tipo_Producto serv_Tipo_Producto;
	
	@RequestMapping("gestion_compras")
	public String gestion_compras(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("compras", serv_Compras.listar());
			
			model.addAttribute("proveedores", serv_Proveedores.listar_proveedores());
			
			return "compras/gestion_compras";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	int codc=0;
	
	//int num_de_compra=0;
	ArrayList<Detalle_Compras> detalles1=new ArrayList<Detalle_Compras>();
	@RequestMapping("adicionar_compra")
	public String adicionar_compra(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			detalles1.clear();
			
			
			
		
				codc=serv_Compras.generarCodc();
				
				model.addAttribute("codc",codc);
				
				
				model.addAttribute("proveedores",serv_Proveedores.listar_proveedores());
					
				
				
				return "compras/adicionar_compra";
				
			
			
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
			
			
				
				model.addAttribute("detalles1",detalles1);
				
				model.addAttribute("productos", serv_producto.listar_productos());
				
				model.addAttribute("codc",codc);
				
				model.addAttribute("proveedores",serv_Proveedores.listar_proveedores());
				
				return "compras/adicionar_detalle";
				
			
			
		}else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}	

	
	
	
	
	
	@RequestMapping("guardar_detalle")
	public String guardar_detalle(HttpServletRequest request, Model model,String codp,float precio,String descripcion,Integer cantidad){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
				
				
				
				
				Detalle_Compras detalle=new Detalle_Compras();
				
				detalle.setCod_det(detalles1.size());
				detalle.setCodp(codp);
				detalle.setCant(cantidad);
				detalle.setPunit(precio);
				detalle.setDescripcion(descripcion);
				detalle.setSubtotal(detalle.getPunit()*detalle.getCant());
				detalles1.add(detalle);
				
				model.addAttribute("detalles1",detalles1);
				model.addAttribute("productos", serv_producto.listar_productos());
				
				
				model.addAttribute("codc",codc);
				
				model.addAttribute("proveedores",serv_Proveedores.listar_proveedores());
				
				
				
				
				
			
				
				return "compras/adicionar_detalle2";
				
			
			
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
				for (Detalle_Compras det : detalles1) {
					if(det.getCod_det()==cod_det)detalles1.remove(det);
				}
			} catch (Exception e) {
				System.out.println("error "+e.getMessage());
			}
			model.addAttribute("detalles1",detalles1);
			
			model.addAttribute("productos", serv_producto.listar_productos());
			
			model.addAttribute("codc",codc);
			
			model.addAttribute("proveedores",serv_Proveedores.listar_proveedores());
			
			
			
			
			
			return "compras/adicionar_detalle2";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	@RequestMapping("guardar_compra")
	public String guardar_compra(HttpServletRequest request, Model model,Integer codpro,String fecha,String descripcion){
	
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		
		if(user!=null){
			
		Iterator<Detalle_Compras> it=detalles1.iterator();
			
			float monto_total = 0;
			
			
			
			while (it.hasNext()) {
				
				Detalle_Compras detalle = (Detalle_Compras) it.next();
				
				monto_total += detalle.getSubtotal();
				
				detalle.setCodc(codc);
			}
			
			
			
			
			Compras compra=new Compras();
			
			
			compra.setCodc(codc);
			compra.setCodpro((codpro));
			compra.setCodu(user.getCi());
			compra.setDescripcion(descripcion);
			compra.setFecha(fecha);
			compra.setMonto_total(monto_total);
			
			
			
			
			serv_Compras.adicionar(compra);
			for (Detalle_Compras det : detalles1) {
				det.setCodc(codc);
				
				serv_Detalle_Compras.adicionar(det);
			}

			
			
			model.addAttribute("proveedores",serv_Proveedores.listar_proveedores());
			
			
			model.addAttribute("compras", serv_Compras.listar());
			return "compras/gestion_compras";
		
		}else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}

}