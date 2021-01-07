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

import it.uniroma3.controller.validator.AllievoValidator;
import it.uniroma3.model.Allievo;
import it.uniroma3.model.Attivita;
import it.uniroma3.service.AllievoService;
import it.uniroma3.service.AttivitaService;
import it.uniroma3.service.CentroService;

@Controller
public class AllievoController {

	@Autowired
	private AllievoService allievoService;

	@Autowired
	private AllievoValidator validator;

	@Autowired
	private CentroService centroService;

	@Autowired
	private AttivitaService attivitaService;

	/* inserimento allievo */
	@RequestMapping("/registraAllievo")
	public String aggiungiAllievo(Model model) {
		model.addAttribute("allievo", new Allievo());
		return "allievo/allievoForm";
	}

	/* form inserimento allievo */
	@RequestMapping(value = "/allievo", method = RequestMethod.POST)
	public String newAllievo(@Valid @ModelAttribute("allievo") Allievo allievo, 
			Model model, BindingResult bindingResult, HttpSession session) {
		this.validator.validate(allievo, bindingResult);

		if (this.allievoService.alreadyExists(allievo)) {
			model.addAttribute("exists", "L'allievo esiste già!");
			return "allievo/allievoForm";
		}
		else {
			if (!bindingResult.hasErrors()) {
				//this.allievoService.save(allievo);
				//model.addAttribute("allievi", this.allievoService.findAll());
				session.setAttribute("allievo", allievo);
				return "allievo/allievoConferma";
				//return "allievo/listaAllievi";
			}
		}
		return "allievoForm";
	}

	/*conferma iscrizione allievo*/
	@RequestMapping(value = "/confermaAllievo", method = RequestMethod.POST)
	public String confermaAllievo(@ModelAttribute("allievo") Allievo allievo, Model model, HttpSession session) {
		Allievo a = (Allievo)session.getAttribute("allievo");
		allievoService.save(a);
		model.addAttribute("allievo", this.allievoService.findById(a.getId()));
		return "allievo/mostraAllievo";
	}

	/* lista allievi*/
	@RequestMapping("/allievi")
	public String allievi(Model model) {
		model.addAttribute("allievi", this.allievoService.findAll());
		return "allievo/listaAllievi";
	}

	/* mostra dettaglio allievo */
	@RequestMapping(value = "/allievo/{id}", method = RequestMethod.GET)
	public String getAllievo(@PathVariable("id") Long id, Model model) {
		model.addAttribute("allievo", this.allievoService.findById(id));
		return "allievo/mostraAllievo";
	}

	/* iscrivi allievo ad una attività */
	@RequestMapping("/centro/{centroid}/attivita/{attivitaid}/iscriviAllievo")
	public String iscriviAllievo(@PathVariable("centroid") Long centroId, @PathVariable("attivitaid") Long attivitaId, Model model) {
		model.addAttribute("centroid",centroId);
		model.addAttribute("attivitaid",attivitaId);
		model.addAttribute("centro",this.centroService.findById(centroId));
		model.addAttribute("attivita",this.attivitaService.findById(attivitaId));
		model.addAttribute("allievo", new Allievo());
		return "allievo/iscriviAllievo";
	}

	/* verifica cf allievo per iscrizione attività*/
	@RequestMapping(value = "/centro/{centroid}/attivita/{attivitaid}/verificaCf", method = RequestMethod.POST)
	public String verificaCf(@PathVariable("centroid") Long centroId, @PathVariable("attivitaid") Long attivitaId,
			@ModelAttribute("allievo") Allievo allievo, Model model) {

		// l'allievo non esiste
		if (this.allievoService.findByCf(allievo.getCf())== null)	{
			model.addAttribute("notexists", "L'allievo non esiste!");
			return "allievo/iscriviAllievo";
		}
		Attivita attivita = this.attivitaService.findById(attivitaId);

		// l'allievo è già iscritto
		if (this.attivitaService.allievoGiaIscritto(attivita.getAllievi(),allievo.getCf())) {
			model.addAttribute("yetsubscribed", "L'allievo è già iscritto!");
			return "allievo/iscriviAllievo";
		}

		// l'allievo non è iscritto ed esiste
		Allievo a = this.allievoService.findByCf(allievo.getCf());
		a.add(attivita);
		attivita.add(a);
		this.allievoService.save(a);
		this.attivitaService.save(attivita);
		model.addAttribute("centroid", centroId);
		model.addAttribute("attivitaid", attivitaId);
		model.addAttribute("attivita", attivita);
		model.addAttribute("centro", this.centroService.findById(centroId));
		model.addAttribute("allievo", a);
		return "allievo/confermaIscrizioneAllievo";
	}

	/* lista allievi di una attivita*/
	@RequestMapping("/centro/{centroid}/attivita/{attivitaid}/allievi")
	public String allieviAttivita(@PathVariable("centroid") Long centroId,
			@PathVariable("attivitaid") Long attivitaId, Model model) {
		model.addAttribute("attivitaid", attivitaId);
		Attivita attivita = this.attivitaService.findById(attivitaId);
		model.addAttribute("attivita", attivita);
		model.addAttribute("allievi", attivita.getAllievi());
		return "allievo/listaAllievi";
	}
	
	/* ricerca di uno allievo */
	@RequestMapping(value = ("/allievo/ricerca"), method = RequestMethod.GET)
	public String initRicercaAllievo(Model model) {
		List<Allievo> allievi = new ArrayList<>();
		model.addAttribute("allievi",allievi);
		model.addAttribute("allievo",new Allievo());
		return "allievo/ricercaAllievo";
	}
	
	@RequestMapping(value = ("/allievo/ricerca"), method = RequestMethod.POST)
	public String aggiornaRicercaAllievo(Allievo allievo, Model model) {
		List<Allievo> allievi = new ArrayList<Allievo>();
		if(!allievo.getNome().equals("") && !allievo.getCognome().equals("")) {
			allievi.addAll(this.allievoService.findByNomeAndCognome(allievo.getNome(),allievo.getCognome()));
		} else {
			if(!allievo.getNome().equals("")) {	
				allievi.addAll(this.allievoService.findByNome(allievo.getNome()));
			} else if(!allievo.getCognome().equals("")) {
				allievi.addAll(this.allievoService.findByCognome(allievo.getCognome()));
			}
		}
		model.addAttribute("allievo",allievo);
		model.addAttribute("allievi",allievi);
		return "allievo/ricercaAllievo";
}
}

