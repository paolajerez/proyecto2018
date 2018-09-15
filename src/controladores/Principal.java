package controladores;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modelos.Datos;
import modelos.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import servicios.Serv_Usuario;

@Controller
@RequestMapping("/principal/*")
public class Principal {
	
	@Autowired
	private Serv_Usuario serv_Usuario;
	
	@RequestMapping("login")
	public String login(Model model){
		return "principal/login";
	}
	
	@RequestMapping("validar")
	public String validar(HttpServletRequest request, Model model, @ModelAttribute("dt") Datos dt){
		try {
			Usuario user = serv_Usuario.iniciar_sesion(dt);
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			model.addAttribute("msg", "Bienvenido "+user.toString());
			model.addAttribute("url", "principal/post_validar");
			return "principal/mensajes";
		} catch (Exception e) {
			model.addAttribute("error", "Verifique sus datos");
			return "principal/login";
		}
	}
	
	@RequestMapping("post_validar")
	public String post_validar(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("user", user);
			model.addAttribute("roles", serv_Usuario.obtener_por_Ci(user.getCi()).getRoles());
			return "principal/post_validar";
		}
		else{
			model.addAttribute("msg", "Debe inicar sesión..!");
			model.addAttribute("url", "principal/login");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("salir")
	public String salir(HttpServletRequest request, HttpServletResponse response){
		try {
			Cookie[] cookie = request.getCookies();
			for(Cookie i: cookie){
				i.setPath(request.getContextPath());
		    	i.setMaxAge(0);
		    	i.setValue(null);
		    	response.addCookie(i);
			}
		} catch (Exception e) {}
		HttpSession session = request.getSession(false);
		if(session != null) session.invalidate();
		return "redirect:../principal/login";
	} 
}