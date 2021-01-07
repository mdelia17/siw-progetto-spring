package it.uniroma3.controller;

import java.util.ArrayList;
import java.util.List;

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

import it.uniroma3.controller.validator.AttivitaValidator;
import it.uniroma3.model.Attivita;
import it.uniroma3.model.Centro;
import it.uniroma3.service.AttivitaService;
import it.uniroma3.service.CentroService;

@Controller
public class AttivitaController {
	
	@Autowired
	private AttivitaService attivitaService;
	
	@Autowired
	private AttivitaValidator validator;
	
	@Autowired
	private CentroService centroService;
	
	/* creazione nuova attività*/
	@RequestMapping(value = "/centro/{centroid}/aggiungiAttivita", method = RequestMethod.GET)
	public String aggiungiAttivita(@PathVariable("centroid") Long centroId, Model model, HttpSession session) {
		model.addAttribute("attivita", new Attivita());
		model.addAttribute("centroid", centroId);
		return "attivita/attivitaForm";
	}
	
	/* creazione nuova attivita parte form */
	@RequestMapping(value = "/centro/{centroid}/attivita", method = RequestMethod.POST)
    public String newAttivita(@PathVariable("centroid") Long centroId, @Valid @ModelAttribute("attivita") Attivita attivita, 
    									Model model, BindingResult bindingResult, HttpSession session) {
        this.validator.validate(attivita, bindingResult);
        
        if (this.attivitaService.alreadyExists(attivita)) {
            model.addAttribute("exists", "Attivita already exists");
            return "attivita/attivitaForm";
        }
        else {
            if (!bindingResult.hasErrors()) {
               // this.attivitaService.save(attivita);
               //model.addAttribute("attivitas", this.attivitaService.findAll());
            	session.setAttribute("centroid", centroId);
            	session.setAttribute("attivita", attivita);
            	return "attivita/attivitaConferma";
               //return "attivita/listaAttivita";
            }
        }
        return "attivita/attivitaForm";
    }
	
	/*conferma creazione attivita*/
	@RequestMapping(value = "/centro/{centroid}/confermaAttivita", method = RequestMethod.POST)
	public String confermaAttivita(@PathVariable("centroid") Long centroId, @ModelAttribute("attivita") Attivita attivita, Model model, HttpSession session) {
		Attivita a = (Attivita)session.getAttribute("attivita");
		Centro c = this.centroService.findById(centroId);
		a.setCentro(c);
		c.add(a);
		this.centroService.save(c);
		//this.attivitaService.save(a);
		model.addAttribute("attivitas", this.attivitaService.findAll());
		return "attivita/listaAttivita";
	}
	
	/* lista attivita */
	@RequestMapping("/attivitas")
	public String attivitas(Model model) {
		model.addAttribute("attivitas", this.attivitaService.findAll());
		return "attivita/listaAttivita";
	}
	
	/* lista attivita di un centro*/
	@RequestMapping("/centro/{centroid}/attivitas")
	public String attivitas(@PathVariable("centroid") Long centroId, Model model) {
		List<Attivita> attivitas = new ArrayList<>();
		Centro centro = this.centroService.findById(centroId);
		attivitas.addAll(centro.getAttivita());
		model.addAttribute("attivitas", attivitas);
		model.addAttribute("centro" , this.centroService.findById(centroId));
		model.addAttribute("centroid", centroId);
		return "attivita/listaAttivita";
	}
	
	/* mostra dettaglio attivita */
	@RequestMapping(value = "/centro/{centroid}/attivita/{id}", method = RequestMethod.GET)
	public String getAttivita(@PathVariable("centroid") Long centroId, @PathVariable("id") Long id, Model model) {
		model.addAttribute("attivita", this.attivitaService.findById(id));
		model.addAttribute("centroid", centroId);
		model.addAttribute("attivitaid", id);
		return "attivita/mostraAttivita";
	}
	
	
	
	

}
