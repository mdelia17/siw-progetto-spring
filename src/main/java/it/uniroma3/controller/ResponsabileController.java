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

import it.uniroma3.controller.validator.ResponsabileValidator;
import it.uniroma3.model.Centro;
import it.uniroma3.model.Responsabile;
import it.uniroma3.service.ResponsabileService;

@Controller
public class ResponsabileController {
	
	@Autowired
	private ResponsabileService responsabileService;
	
	@Autowired
	private ResponsabileValidator responsabileValidator;

	
	/* aggiungi responsabile */
	@RequestMapping("aggiungiResponsabile")
	public String aggiungiResponsabile(Model model) {
		model.addAttribute("responsabile", new Responsabile());
		return "responsabile/responsabileForm";
	}
	
	/* aggiungi responsabile form */
	@RequestMapping(value = "/responsabile", method = RequestMethod.POST)
    public String newReponsabile(@Valid @ModelAttribute("responsabile") Responsabile responsabile, 
    									Model model, BindingResult bindingResult,  HttpSession session) {
        this.responsabileValidator.validate(responsabile, bindingResult);
        
        if (this.responsabileService.alreadyExists(responsabile)) {
            model.addAttribute("exists", "Responsabile already exists");
            return "responsabile/responsabileForm";
        }
        else {
            if (!bindingResult.hasErrors()) {
            	model.addAttribute("responsabile",responsabile);
            	this.responsabileService.save(responsabile);
				return "responsabile/mostraResponsabile";
            }
        }
        return "responsabile/responsabileForm";
    }
	
	/* mostra dettaglio responsabile */
	@RequestMapping(value = "/responsabile/{id}", method = RequestMethod.GET)
	public String getResponsabile(@PathVariable("id") Long id, Model model) {
		model.addAttribute("responsabile", this.responsabileService.findById(id));
		return "responsabile/mostraResponsabile";
	}
	
	/* lista dei responsabili */
	@RequestMapping("/reponsabili")
	public String responsabili(Model model) {
		model.addAttribute("responsabili", this.responsabileService.findAll());
		return "responsabile/listaResponsabili";
	}
}
