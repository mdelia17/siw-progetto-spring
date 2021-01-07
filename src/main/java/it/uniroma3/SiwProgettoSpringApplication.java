package it.uniroma3;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.uniroma3.model.Allievo;
import it.uniroma3.model.Attivita;
import it.uniroma3.model.Centro;
import it.uniroma3.model.Responsabile;
import it.uniroma3.service.AllievoService;
import it.uniroma3.service.CentroService;
import it.uniroma3.service.ResponsabileService;

@SpringBootApplication
public class SiwProgettoSpringApplication {

	@Autowired
	AllievoService allievoService;

	@Autowired
	CentroService centroService;
	
	@Autowired
	ResponsabileService rs;
	
	public static void main(String[] args) {
		SpringApplication.run(SiwProgettoSpringApplication.class, args);
	}
	
	@PostConstruct
	public void init() {
		
		Allievo allievo = new Allievo("Valerio", "Alberino");
		allievo.setCf("MCCDMR96H27H133C");
		allievo.setEmail("valerio.albe@gmail.com");
		allievo.setTelefono("388556644222");
		allievo.setDataNascita(LocalDate.of(1987, 1, 24));
		allievo.setLuogoNascita("Roma");
		allievoService.save(allievo);
		
		for(Allievo a : allievoService.findByCognome("Alberino")) {
			System.out.println(a.getNome());
		}
		
		Centro centro = new Centro();
		centro.setNome("Centro1");
		centro.setEmail("centro@azienda.org");
		centro.setTelefono("061234567");
		centro.setIndirizzo("piazza la bomba e scappa");

		Responsabile resp = new Responsabile();
		Responsabile admin = new Responsabile("admin", "istrator", "admin");
		admin.setRuolo("ADMIN");
		admin.setDataNascita(LocalDate.of(1970, 10, 15));
		rs.save(admin);
		centro.setResponsabile(resp);
		resp.setNome("ajeje");
		resp.setCognome("brazorf");
		resp.setCentro(centro);
		resp.setUsername("ajeje");
		resp.setPassword("brazorf");
		resp.setDataNascita(LocalDate.of(1980, 12, 20));
		rs.save(resp);
		resp.setRuolo(centro.getId().toString());
		rs.save(resp);
		
		System.out.println(resp.getRuolo());
		
		Attivita attivita = new Attivita();
		attivita.setNome("Analisi");
		attivita.setDescrizione("Corso base di Analisi");
		attivita.setDataInizio(LocalDate.of(2000, 1, 20));
		attivita.setOraInizio(LocalTime.of(12, 50));
		attivita.setCentro(centro);
		centro.add(attivita);
		rs.save(resp);
		
	}
}
