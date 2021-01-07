package it.uniroma3.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AziendaController {
	
	@RequestMapping("/")
	public String home() {
		String ruolo = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
		if(ruolo.equals("[ADMIN]")) {
			return "indexAdmin";
		}
		return "redirect:/centro/"+ruolo.substring(1, ruolo.length()-1);
	}
	
}
