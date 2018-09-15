package controladores;

import javax.servlet.http.HttpServletRequest;

import modelos.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import servicios.Serv_Tipo_Producto;

@Controller
@RequestMapping("/tipo_productos/*")
public class Gestion_Tipos_Productos {
	
	private Serv_Tipo_Producto serv_Tipo_Producto;
	
	@Autowired
	public void setServ_Tipo_Producto(Serv_Tipo_Producto serv_Tipo_Producto) {
		
		this.serv_Tipo_Producto = serv_Tipo_Producto;
	
	}
	
	@RequestMapping("gestion_tipo_productos")
	public String gestion_tipo_productos(HttpServletRequest request, Model model){
		
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
			model.addAttribute("usuario", user);
			
			
			
			model.addAttribute("tipos", serv_Tipo_Producto.listar_productos());
			
			return "tipo_productos/gestion_tipo_productos";
		
		}else{
			
			model.addAttribute("error", "Verifique sus datos o no es usuario del sistema..!");
			
			return "principal/inicio";
		
		}
	
	}
	
	@RequestMapping("adicionar_tipo_producto")
	public String adicionar_tipo_producto(HttpServletRequest request, Model model){
		
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
			model.addAttribute("usuario", user);
			
			return "tipo_productos/adicionar_tipo_producto";
		
		}else{
			
			model.addAttribute("error", "Verifique sus datos o no es usuario del sistema..!");
			
			return "principal/inicio";
		
		}
	
	}
	
	@RequestMapping("guardar_tipo")
	public String guardar_tipo(HttpServletRequest request, Model model, String codtp, String nombre){
		
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
			model.addAttribute("usuario", user);
			
			serv_Tipo_Producto.insertar_tipo_prod(nombre);
				
			model.addAttribute("mensaje", "Se guardó correctamente..!");
				
			model.addAttribute("url", "tipo_productos/gestion_tipo_productos");
			
			
			return "tipo_productos/gestion_tipo_productos";
			
		}
		else{
			
			model.addAttribute("error", "Verifique sus datos o no es usuario del sistema..!");
			
			return "principal/inicio";
		
		}
	
	}
	
	@RequestMapping("modificar_tipo_producto")
	public String modificar_tipo_producto(HttpServletRequest request, Model model, String codtp){
		
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
			model.addAttribute("usuario", user);
			
			model.addAttribute("tipo", serv_Tipo_Producto.tipo_prod_por_codtp(codtp));
			
			return "tipo_productos/modificar_tipo_producto";
		
		}else{
			
			model.addAttribute("error", "Verifique sus datos o no es usuario del sistema..!");
			
			return "principal/inicio";
		
		}
	
	}
	
	@RequestMapping("actualizar_tipo_producto")
	public String actualizar_tipo_producto(HttpServletRequest request, Model model, String codtp, String nombre){
		
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
			model.addAttribute("usuario", user);
			
			
			
				serv_Tipo_Producto.actualizar_tipo_prod(codtp, nombre);
				
				return "tipo_productos/gestion_tipo_productos";
			
			
			
		
		
		}else{
		
			model.addAttribute("error", "Verifique sus datos o no es usuario del sistema..!");
			
			return "principal/inicio";
		
		}
	
	}
	
	@RequestMapping("eliminar_tipo_producto")
	public String eliminar_tipo_producto(HttpServletRequest request, Model model, String codtp){
	
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			serv_Tipo_Producto.eliminar_tipo_prod(codtp);
			
			
			
			model.addAttribute("tipos", serv_Tipo_Producto.listar_productos());
			
			return "tipo_productos/gestion_tipo_productos";
		
		}else{
		
			model.addAttribute("error", "Verifique sus datos o no es usuario del sistema..!");
			
			return "principal/inicio";
		
		}
	
	}
	
	@RequestMapping("ver_tipo_producto")
	public String ver_tipo_producto(HttpServletRequest request, Model model, String codtp){
	
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
			
			
			model.addAttribute("tipo", serv_Tipo_Producto.tipo_prod_por_codtp(codtp));
			
			return "tipo_productos/ver_tipo_producto";
		
		}else{
		
			model.addAttribute("error", "Verifique sus datos o no es usuario del sistema..!");
			
			return "principal/inicio";
		
		}
	
	}

}