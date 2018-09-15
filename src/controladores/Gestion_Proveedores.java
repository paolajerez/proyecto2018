package controladores;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import modelos.Clientes;
import modelos.Proveedores;
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

import servicios.Serv_Proveedores;

@Controller
@RequestMapping("/proveedores/*")
@SuppressWarnings({"rawtypes", "deprecation"})
public class Gestion_Proveedores {

	private Serv_Proveedores serv_proveedores;
	
	@Autowired
	public void setServ_proveedores(Serv_Proveedores serv_proveedores) {
		
		this.serv_proveedores = serv_proveedores;
	
	}

	@RequestMapping("gestion_proveedores")
	public String gestion_proveedores(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		
		if (user != null) {
		
			model.addAttribute("usuario", user);
			
			model.addAttribute("proveedores", serv_proveedores.listar_proveedores());
			
			
			
			return "proveedores/gestion_proveedores";
		
		}else {
			
			model.addAttribute("error", "Verifique sus datos o no es usuario del sistema..!");
			
			return "principal/inicio";
		
		}
	
	}
	
	@RequestMapping("adicionar_proveedor")
	public String adicionar_proveedor(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			return "proveedores/adicionar_proveedor";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@RequestMapping("guardar_proveedor")
	@SuppressWarnings("deprecation")
	public String guardar_proveedor(HttpServletRequest request, Model model, @ModelAttribute("pro") Proveedores pro, @RequestParam("file") MultipartFile file){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			try {
				if(!file.isEmpty()){
					FileOutputStream out = new FileOutputStream(request.getRealPath("us_fotos")+"/"+file.getOriginalFilename());
					out.write(file.getBytes()); out.close();
					pro.setFoto(file.getOriginalFilename().toString());
				}
				serv_proveedores.adicionar_proveedor(pro);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "redirect:../proveedores/gestion_proveedores";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	
	
	@RequestMapping("modificar_proveedor")
	public String modificar_proveedor(HttpServletRequest request, Model model, Integer ci){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("prov", serv_proveedores.prov_por_ci(ci));
			return "proveedores/modificar_proveedor";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@RequestMapping("actualizar_proveedor")
	@SuppressWarnings("deprecation")
	public String actualizar_proveedor(HttpServletRequest request, Model model, @ModelAttribute("pro") Proveedores pro, @RequestParam("file") MultipartFile file){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			try {
				if(!file.isEmpty()){
					new File(request.getRealPath("us_fotos")+"/"+pro.getFoto()).delete();
					FileOutputStream out = new FileOutputStream(request.getRealPath("us_fotos")+"/"+file.getOriginalFilename());
					out.write(file.getBytes()); out.close();
					pro.setFoto(file.getOriginalFilename().toString());
				}
				serv_proveedores.modificar_proveedor(pro);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "redirect:../proveedores/gestion_proveedores";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("eliminar_proveedor")
	public String eliminar_proveedor(HttpServletRequest request, Model model, Integer ci){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_proveedores.eliminar_proveedor(ci);
			return "redirect:../proveedores/gestion_proveedores";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@RequestMapping("ver_proveedor")
	public String ver_proveedor(HttpServletRequest request, Model model, Integer ci){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("proveedor", serv_proveedores.prov_por_ci(ci));
			return "proveedores/ver_proveedor";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	

}