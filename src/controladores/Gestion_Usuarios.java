package controladores;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import modelos.Datos;
import modelos.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import servicios.Serv_Datos;
import servicios.Serv_Rol;
import servicios.Serv_Usuario;

@Controller
@RequestMapping("/usuarios/*")
public class Gestion_Usuarios {
	
	@Autowired
	private Serv_Usuario serv_Usuario;
	@Autowired
	private Serv_Datos serv_Datos;
	@Autowired
	private Serv_Rol serv_Rol;
	
	@RequestMapping("gestion_usuarios")
	public String gestion_usuarios(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("usuarios", serv_Usuario.listar());
			return "usuarios/gestion_usuarios";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("adicionar_usuario")
	public String adicionar_usuario(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			return "usuarios/adicionar_usuario";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_usuario")
	@SuppressWarnings("deprecation")
	public String guardar_usuario(HttpServletRequest request, Model model, @ModelAttribute("us") Usuario us, @RequestParam("file") MultipartFile file){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			try {
				if(!file.isEmpty()){
					FileOutputStream out = new FileOutputStream(request.getRealPath("us_fotos")+"/"+file.getOriginalFilename());
					out.write(file.getBytes()); out.close();
					us.setFoto(file.getOriginalFilename().toString());
				}
				serv_Usuario.adicionar(us);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "redirect:../usuarios/gestion_usuarios";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("modificar_usuario")
	public String modificar_usuario(HttpServletRequest request, Model model, String ci){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("us", serv_Usuario.obtener_por_Ci(ci));
			return "usuarios/modificar_usuario";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("actualizar_usuario")
	@SuppressWarnings("deprecation")
	public String actualizar_usuario(HttpServletRequest request, Model model, @ModelAttribute("us") Usuario us, @RequestParam("file") MultipartFile file){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			try {
				if(!file.isEmpty()){
					new File(request.getRealPath("us_fotos")+"/"+us.getFoto()).delete();
					FileOutputStream out = new FileOutputStream(request.getRealPath("us_fotos")+"/"+file.getOriginalFilename());
					out.write(file.getBytes()); out.close();
					us.setFoto(file.getOriginalFilename().toString());
				}
				serv_Usuario.modificar(us);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "redirect:../usuarios/gestion_usuarios";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("eliminar_usuario")
	public String eliminar_usuario(HttpServletRequest request, Model model, String ci){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Usuario.borrado_log(ci);
			return "redirect:../usuarios/gestion_usuarios";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("ver_usuario")
	public String ver_usuario(HttpServletRequest request, Model model, String ci){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("us", serv_Usuario.obtener_por_Ci(ci));
			return "usuarios/ver_usuario";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("asignar_roles")
	public String asignar_roles(HttpServletRequest request, Model model, String ci){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("us", serv_Usuario.obtener_por_Ci(ci));
			model.addAttribute("roles", serv_Rol.roles_sin_asignar_a_Usuario(ci));
			return "usuarios/asignar_roles";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_roles")
	public String guardar_roles(HttpServletRequest request, Model model, String ci, Integer[] codr){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Usuario.eliminar_usurol(ci);
			if(codr!=null){
				for(int i: codr) serv_Usuario.asignar_roles_a_Usuario(ci, i);
			}
			return "redirect:../usuarios/gestion_usuarios";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("adicionar_datos")
	public String adicionar_datos(HttpServletRequest request, Model model, String ci){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("us", serv_Usuario.obtener_por_Ci(ci));
			return "usuarios/adicionar_datos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_datos")
	public String guardar_datos(HttpServletRequest request, Model model, @ModelAttribute("dt") Datos dt){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Datos.adicionar(dt);
			return "redirect:../usuarios/gestion_usuarios";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("modificar_datos")
	public String modificar_datos(HttpServletRequest request, Model model, String ci){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("dt", serv_Datos.obteber_por_Ci(ci));
			return "usuarios/modificar_datos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("actualizar_datos")
	public String actualizar_datos(HttpServletRequest request, Model model, @ModelAttribute("dt") Datos dt){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Datos.modificar(dt);
			return "redirect:../usuarios/gestion_usuarios";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
}