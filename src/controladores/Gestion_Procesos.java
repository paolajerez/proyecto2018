package controladores;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import modelos.Proceso;
import modelos.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import servicios.Serv_Proceso;

@Controller
@RequestMapping("/procesos/*")
public class Gestion_Procesos {
	
	@Autowired
	private Serv_Proceso serv_Proceso;
	
	@RequestMapping("gestion_procesos")
	public String gestion_procesos(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("procesos", serv_Proceso.listar());
			return "procesos/gestion_procesos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("adicionar_proceso")
	public String adicionar_proceso(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			return "procesos/adicionar_proceso";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_proceso")
	@SuppressWarnings("deprecation")
	public String guardar_proceso(HttpServletRequest request, Model model, @ModelAttribute("pr") Proceso pr, @RequestParam("file") MultipartFile file){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			try {
				if(!file.isEmpty()){
					System.out.println("Ruta: "+request.getRealPath("pro_logos")+"/"+file.getOriginalFilename());
					FileOutputStream out = new FileOutputStream(request.getRealPath("pro_logos")+"/"+file.getOriginalFilename());
					out.write(file.getBytes()); out.close();
					pr.setLogo(file.getOriginalFilename().toString());
				}
				serv_Proceso.adicionar(pr);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "redirect:../procesos/gestion_procesos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("modificar_proceso")
	public String modificar_proceso(HttpServletRequest request, Model model, Integer codp){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("pr", serv_Proceso.obtener_por_Codp(codp));
			return "procesos/modificar_proceso";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("actualizar_proceso")
	@SuppressWarnings("deprecation")
	public String actualizar_proceso(HttpServletRequest request, Model model, @ModelAttribute("pr") Proceso pr, @RequestParam("file") MultipartFile file){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			try {
				if(!file.isEmpty()){
					new File(request.getRealPath("pro_logos")+"/"+pr.getLogo()).delete();
					System.out.println("Ruta: "+request.getRealPath("pro_logos")+"/"+file.getOriginalFilename());
					FileOutputStream out = new FileOutputStream(request.getRealPath("pro_logos")+"/"+file.getOriginalFilename());
					out.write(file.getBytes()); out.close();
					pr.setLogo(file.getOriginalFilename().toString());
				}
				serv_Proceso.modificar(pr);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "redirect:../procesos/gestion_procesos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("eliminar_proceso")
	@SuppressWarnings("deprecation")
	public String eliminar_proceso(HttpServletRequest request, Model model, Integer codp){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			new File(request.getRealPath("pro_logos")+"/"+serv_Proceso.obtener_por_Codp(codp).getLogo()).delete();
			serv_Proceso.eliminar(codp);
			return "redirect:../procesos/gestion_procesos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("ver_proceso")
	public String ver_proceso(HttpServletRequest request, Model model, Integer codp){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("pr", serv_Proceso.obtener_por_Codp(codp));
			return "procesos/ver_proceso";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
}