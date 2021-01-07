package it.uniroma3.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.model.Allievo;
import it.uniroma3.repository.AllievoRepository;

@Transactional
@Service
public class AllievoService {

	@Autowired
	private AllievoRepository allievoRepository;
	
	public void add(final Allievo a) {
		this.allievoRepository.save(a);
	}

	public void update(Allievo a) {
		this.allievoRepository.save(a);
	}

	public void delete(Allievo a) {
		this.allievoRepository.delete(a);		
}
	
	public Allievo save(Allievo a) {
		return this.allievoRepository.save(a);
	}
	
	public List<Allievo> findByNome(String nome) {
		return (List<Allievo>) this.allievoRepository.findByNome(nome);
	}
	
	public List<Allievo> findByCognome(String cognome) {
		return this.allievoRepository.findByCognome(cognome);
	}
	
	public List<Allievo> findByNomeAndCognome(String nome, String cognome) {
		return (List<Allievo>) this.allievoRepository.findByNomeAndCognome(nome,cognome);
	}
	
	public List<Allievo> findAll() {
		return (List<Allievo>) this.allievoRepository.findAll();
	}

	public Allievo findById(Long id) {
		Optional<Allievo> allievo = this.allievoRepository.findById(id);
		if(allievo.isPresent())
			return allievo.get();
		return null;
	}
	
	public boolean alreadyExists(Allievo a) {
		return findByCf(a.getCf()) != null;
	}

	public Allievo findByCf(String cf) {
		return this.allievoRepository.findByCf(cf);
	}
}

