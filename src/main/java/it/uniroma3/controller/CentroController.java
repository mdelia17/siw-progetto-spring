package it.uniroma3.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.controller.validator.CentroValidator;
import it.uniroma3.model.Centro;
import it.uniroma3.service.CentroService;

@Controller
public class CentroController {
	
	@Autowired
	private CentroService centroService;
	
	@Autowired
	private CentroValidator validator;
		
	/* aggiungi centro */
	@RequestMapping("aggiungiCentro")
	public String aggiungiCentro(Model model) {
		model.addAttribute("centro", new Centro());
		return "centro/centroForm";
	}
	
	/* aggiungi centro form */
	@RequestMapping(value = "/centro", method = RequestMethod.POST)
    public String newCentro(@Valid @ModelAttribute("centro") Centro centro, 
    									Model model, BindingResult bindingResult,  HttpSession session) {
        this.validator.validate(centro, bindingResult);
        
        if (this.centroService.alreadyExists(centro)) {
            model.addAttribute("exists", "Centro already exists");
            return "centro/centroForm";
        }
        else {
            if (!bindingResult.hasErrors()) {
				session.setAttribute("centro", centro);
				return "centro/centroConferma";
            }
        }
        return "centro/centroForm";
    }
	
	/*conferma iscrizione centro*/
	@RequestMapping(value = "/confermaCentro", method = RequestMethod.POST)
	public String confermaAllievo(@ModelAttribute("centro") Centro centro, Model model, HttpSession session) {
		Centro c = (Centro)session.getAttribute("centro");
		centroService.save(c);
		model.addAttribute("centro", c);
		return "redirect:/";
	}
	
	/* mostra dettaglio centro */
	@RequestMapping(value = "/centro/{id}", method = RequestMethod.GET)
	public String getCentro(@PathVariable("id") Long id, Model model) {
		model.addAttribute("centro", this.centroService.findById(id));
		return "centro/mostraCentro";
	}
	
	/* lista dei centri */
	@RequestMapping("/centri")
	public String centri(Model model) {
		model.addAttribute("centri", this.centroService.findAll());
		return "centro/listaCentri";
	}
	
}
