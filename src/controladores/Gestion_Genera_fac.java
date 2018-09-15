package controladores;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import modelos.Genera_fac;
import modelos.Proceso;
import modelos.Rol;
import modelos.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import servicios.Serv_Genera_fac;


@Controller
@RequestMapping("/genera_fac/*")
public class Gestion_Genera_fac {
	
	
	@Autowired
	private Serv_Genera_fac serv_genera_fac;
	
	
	
	@RequestMapping("gestion_genera_fac")
	public String gestion_genera_fac(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("gene", serv_genera_fac.listar());
			return "genera_fac/gestion_genera_fac";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("adicionar_genera_fac")
	public String adicionar_proceso(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			return "genera_fac/adicionar_genera_fac";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_genera_fac")
	@SuppressWarnings("deprecation")
	public String guardar_genera_fac(HttpServletRequest request, Model model, @ModelAttribute("genera_fac") Genera_fac gene){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
				serv_genera_fac.adicionar(gene);
			
				return "genera_fac/gestion_genera_fac";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@RequestMapping("modificar_genera_fac")
	public String modificar_generar_fac(HttpServletRequest request, Model model, Integer codgf){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("gene", serv_genera_fac.obtener_por_Codgf(codgf));
			return "genera_fac/modificar_genera_fac";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@RequestMapping("actualizar_genera_fac")
	@SuppressWarnings("deprecation")
	public String actualizar_genera_fac(HttpServletRequest request, Model model, @ModelAttribute("gene") Genera_fac gene){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
				serv_genera_fac.modificar(gene);
			
			return "redirect:../genera_fac/gestion_genera_fac";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("ver_genera_fac")
	public String ver_genera_fac(HttpServletRequest request, Model model, Integer codgf){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("gene", serv_genera_fac.obtener_por_Codgf(codgf));
			return "genera_fac/ver_genera_fac";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@RequestMapping("eliminar_genera_fac")
	@SuppressWarnings("deprecation")
	public String eliminar_genera_fac(HttpServletRequest request, Model model, Integer codgf){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_genera_fac.borrado_log(codgf);
			return "redirect:../genera_fac/gestion_genera_fac";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	

}
