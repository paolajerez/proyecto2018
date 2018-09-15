package controladores;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import modelos.Rol;
import modelos.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import servicios.Serv_Proceso;
import servicios.Serv_Rol;

@Controller
@RequestMapping("/roles/*")
public class Gestion_Roles {
	
	@Autowired
	private Serv_Rol serv_Rol;
	@Autowired
	private Serv_Proceso serv_Proceso;
	
	@RequestMapping("gestion_roles")
	public String gestion_roles(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("roles", serv_Rol.listar());
			return "roles/gestion_roles";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("adicionar_rol")
	public String adicionar_rol(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			return "roles/adicionar_rol";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_rol")
	@SuppressWarnings("deprecation")
	public String guardar_rol(HttpServletRequest request, Model model, @ModelAttribute("rol") Rol rol, @RequestParam("file") MultipartFile file){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			try {
				if(!file.isEmpty()){
					System.out.println("Ruta: "+request.getRealPath("rol_logos")+"/"+file.getOriginalFilename());
					FileOutputStream out = new FileOutputStream(request.getRealPath("rol_logos")+"/"+file.getOriginalFilename());
					out.write(file.getBytes()); out.close();
					rol.setLogo(file.getOriginalFilename().toString());
				}
				serv_Rol.adicionar(rol);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "redirect:../roles/gestion_roles";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("modificar_rol")
	public String modificar_rol(HttpServletRequest request, Model model, Integer codr){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("rol", serv_Rol.obtener_por_Codr(codr));
			return "roles/modificar_rol";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("actualizar_rol")
	@SuppressWarnings("deprecation")
	public String actualizar_rol(HttpServletRequest request, Model model, @ModelAttribute("rol") Rol rol, @RequestParam("file") MultipartFile file){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			try {
				if(!file.isEmpty()){
					new File(request.getRealPath("rol_logos")+"/"+rol.getLogo()).delete();
					System.out.println("Ruta: "+request.getRealPath("rol_logos")+"/"+file.getOriginalFilename());
					FileOutputStream out = new FileOutputStream(request.getRealPath("rol_logos")+"/"+file.getOriginalFilename());
					out.write(file.getBytes()); out.close();
					rol.setLogo(file.getOriginalFilename().toString());
				}
				serv_Rol.modificar(rol);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "redirect:../roles/gestion_roles";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("eliminar_rol")
	@SuppressWarnings("deprecation")
	public String eliminar_rol(HttpServletRequest request, Model model, Integer codr){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			new File(request.getRealPath("rol_logos")+"/"+serv_Rol.obtener_por_Codr(codr).getLogo()).delete();
			serv_Rol.eliminar(codr);
			return "redirect:../roles/gestion_roles";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("ver_rol")
	public String ver_rol(HttpServletRequest request, Model model, Integer codr){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("rol", serv_Rol.obtener_por_Codr(codr));
			return "roles/ver_rol";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("asignar_procesos")
	public String asignar_procesos(HttpServletRequest request, Model model, Integer codr){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("rol", serv_Rol.obtener_por_Codr(codr));
			model.addAttribute("procesos", serv_Proceso.procesos_sin_asignar_a_Rol(codr));
			return "roles/asignar_procesos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_procesos")
	public String guardar_procesos(HttpServletRequest request, Model model, Integer codr, Integer[] codp){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Rol.eliminar_de_rolpro(codr);
			if(codp!=null){
				for(int i: codp) serv_Rol.asignar_procesos_a_Rol(codr, i);
			}
			return "redirect:../roles/gestion_roles";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
}