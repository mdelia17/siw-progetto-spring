package it.uniroma3.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.model.Allievo;
import it.uniroma3.model.Attivita;
import it.uniroma3.repository.AttivitaRepository;

@Transactional
@Service
public class AttivitaService {

	@Autowired
	private AttivitaRepository attivitaRepository;

	public Attivita save(Attivita a) {
		return this.attivitaRepository.save(a);
	}

	public List<Attivita> findAll() {
		return (List<Attivita>) this.attivitaRepository.findAll();
	}

	public List<Attivita> findByNome(String nome) {
		return this.attivitaRepository.findByNome(nome);	
	}

	public Attivita findById(Long id) {
		Optional<Attivita> attivita = this.attivitaRepository.findById(id);
		if(attivita.isPresent())
			return attivita.get();
		return null;
	}

	public boolean alreadyExists(Attivita a) {
		String nome = a.getNome();
		LocalDate dataInizio = a.getDataInizio();
		LocalTime oraInizio = a.getOraInizio();
		List<Attivita> findByNomeAndDataInizioAndOraInizio = this.attivitaRepository.findByNomeAndDataInizioAndOraInizio(nome, dataInizio, oraInizio);
		return findByNomeAndDataInizioAndOraInizio.size()>0;
	}
	
	public boolean allievoGiaIscritto(List<Allievo> allievi, String cf) {
		for (Allievo a : allievi) {
			if (a.getCf().equals(cf))	return true;
		}
		return false;
	}

}
