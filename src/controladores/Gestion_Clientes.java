package controladores;

//import herramienta.FirtsInCapitals;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import modelos.Clientes;
import modelos.Usuario;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import servicios.Serv_Clientes;

@Controller
@RequestMapping("/clientes/*")
@SuppressWarnings({"rawtypes", "deprecation"})
public class Gestion_Clientes {

	private Serv_Clientes serv_Clientes;
	
	@Autowired
	public void setServ_Clientes(Serv_Clientes serv_Clientes) {		
		this.serv_Clientes = serv_Clientes;
	}
	
	
	
	@RequestMapping("gestion_clientes")
	public String gestion_clientes(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		
		if (user != null) {		
			model.addAttribute("usuario", user);			
			model.addAttribute("clientes", serv_Clientes.listar_clientes());				
		return "clientes/gestion_clientes";
		
		}else {
			
			model.addAttribute("error", "Verifique sus datos o no es usuario del sistema..!");
			
			return "principal/inicio";
		
		}
	
	}
	
	
	
	@RequestMapping("adicionar_cliente")
	public String adicionar_cliente(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			return "clientes/adicionar_cliente";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@RequestMapping("guardar_cliente")
	@SuppressWarnings("deprecation")
	public String guardar_cliente(HttpServletRequest request, Model model, @ModelAttribute("cli") Clientes cli, @RequestParam("file") MultipartFile file){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			try {
				if(!file.isEmpty()){
					FileOutputStream out = new FileOutputStream(request.getRealPath("us_fotos")+"/"+file.getOriginalFilename());
					out.write(file.getBytes()); out.close();
					cli.setFoto(file.getOriginalFilename().toString());
				}
				serv_Clientes.adicionar_Cliente(cli);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "redirect:../clientes/gestion_clientes";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}

	
	@RequestMapping("modificar_cliente")
	public String modificar_cliente(HttpServletRequest request, Model model, Integer ci){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("cl", serv_Clientes.cl_por_ci(ci));
			return "clientes/modificar_cliente";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@RequestMapping("actualizar_cliente")
	@SuppressWarnings("deprecation")
	public String actualizar_cliente(HttpServletRequest request, Model model, @ModelAttribute("cli") Clientes cli, @RequestParam("file") MultipartFile file){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			try {
				if(!file.isEmpty()){
					new File(request.getRealPath("us_fotos")+"/"+cli.getFoto()).delete();
					FileOutputStream out = new FileOutputStream(request.getRealPath("us_fotos")+"/"+file.getOriginalFilename());
					out.write(file.getBytes()); out.close();
					cli.setFoto(file.getOriginalFilename().toString());
				}
				serv_Clientes.actualizar_cliente(cli);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "redirect:../clientes/gestion_clientes";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("eliminar_cliente")
	public String eliminar_cliente(HttpServletRequest request, Model model, Integer ci){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Clientes.eliminar_cliente(ci);
			return "redirect:../clientes/gestion_clientes";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@RequestMapping("ver_cliente")
	public String ver_cliente(HttpServletRequest request, Model model, Integer ci){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("cliente", serv_Clientes.cl_por_ci(ci));
			return "clientes/ver_cliente";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	

}