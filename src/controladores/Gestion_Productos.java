package controladores;



import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import modelos.Productos;
import modelos.Usuario;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import servicios.Serv_Productos;

import servicios.Serv_Tipo_Producto;

@Controller
@RequestMapping("/productos/*")
@SuppressWarnings({"unused", "rawtypes", "deprecation"})
public class Gestion_Productos {
	
	private Serv_Productos serv_Productos;
	
	private Serv_Tipo_Producto serv_Tipo_Producto;
	

	@Autowired
	public void setServ_Productos(Serv_Productos serv_Productos) {
		
		this.serv_Productos = serv_Productos;
	}
	
	@Autowired
	public void setServ_Tipo_Producto(Serv_Tipo_Producto serv_Tipo_Producto) {
		
		this.serv_Tipo_Producto = serv_Tipo_Producto;
	
	}
	
	
	
	@RequestMapping("gestion_productos")
	public String gestion_productos(HttpServletRequest request, Model model){
		
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
										
			model.addAttribute("productos", serv_Productos.listar_productos());
			
			return "productos/gestion_productos";
		
		}else {
			
			model.addAttribute("error", "Verifique sus datos o no es usuario del sistema..!");
			
			return "principal/inicio";
		
		}
	
	}
	
	
	
	
	
	@RequestMapping("adicionar_producto")
	public String adicionar_producto(HttpServletRequest request, Model model){
		
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
						
			model.addAttribute("tipo_prod", serv_Tipo_Producto.listar_productos());			
			//model.addAttribute("tipo_mon", serv_Tipo_Moneda.listar_Monedas());
			
			return "productos/adicionar_producto";
		
		}else {
			
			model.addAttribute("error", "Verifique sus datos o no es usuario del sistema..!");			
			return "principal/inicio";
		
		}
	
	}
	
	@RequestMapping("guardar_producto")
	public String guardar_producto(HttpServletRequest request, Model model , @ModelAttribute("pro") Productos pro){
		
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
			
			
			model.addAttribute("productos", serv_Productos.listar_productos());			
			
			serv_Productos.adicionar(pro);
			
			return "productos/gestion_productos";
			
			
		
		}else {
			
			model.addAttribute("error", "Verifique sus datos o no es usuario del sistema..!");
			
			return "principal/inicio";
		
		}
	
	}
	
	@RequestMapping("modificar_producto")
	public String modificar_producto(HttpServletRequest request, Model model, String codp){
		
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			Productos producto = serv_Productos.prod_por_codp(codp);
			
			
			
						
			model.addAttribute("prod", producto);			
			model.addAttribute("tipo_prod", serv_Tipo_Producto.listar_productos());			
			
			
			
			return "productos/modificar_producto";
		
		}else {
			
			model.addAttribute("error", "Verifique sus datos o no es usuario del sistema..!");
			
			return "principal/inicio";
		
		}
	
	}
	
	@RequestMapping("actualizar_producto")
	public String actualizar_producto(HttpServletRequest request, Model model,@ModelAttribute("pro") Productos pro){
		
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
				
			model.addAttribute("productos", serv_Productos.listar_productos());
		
			serv_Productos.modificar(pro);
				
				
		
			
			return "productos/gestion_productos";
		
		}else {
			
			model.addAttribute("error", "Verifique sus datos o no es usuario del sistema..!");
			
			return "principal/inicio";
		
		}
	
	}
	
	@RequestMapping("eliminar_producto")
	public String eliminar_producto(HttpServletRequest request, Model model, String codp){
		
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
			serv_Productos.eliminar(codp);
			
			
			
			
			model.addAttribute("productos", serv_Productos.listar_productos());
			
			return "productos/gestion_productos";
		
		}else {
			
			model.addAttribute("error", "Verifique sus datos o no es usuario del sistema..!");
			
			return "principal/inicio";
		
		}
	
	}
	
	@RequestMapping("ver_producto")
	public String ver_producto(HttpServletRequest request, Model model, String codp){
		
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
			
			
			Productos producto = serv_Productos.prod_por_codp(codp);
			
			//model.addAttribute("tipo_mon", serv_Tipo_Moneda.Tipo_Moneda_por_codtm(producto.getCodtm()));
			
			model.addAttribute("tipo_prod", serv_Tipo_Producto.tipo_prod_por_codtp(producto.getCodtp()));
			
			model.addAttribute("prod", serv_Productos.prod_por_codp(codp));
			
			return "productos/ver_producto";
		
		}else {
			
			model.addAttribute("error", "Verifique sus datos o no es usuario del sistema..!");
			
			return "principal/inicio";
		
		}
	
	}

}