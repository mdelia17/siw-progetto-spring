package it.uniroma3.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.model.Centro;
import it.uniroma3.model.Responsabile;
import it.uniroma3.repository.ResponsabileRepository;

@Transactional
@Service
public class ResponsabileService {
	
	@Autowired
	private ResponsabileRepository responsabileRepository;
	
	public Responsabile save(Responsabile responsabile) {
		return this.responsabileRepository.save(responsabile);
	}
	
	public List<Responsabile> findByCentro(Centro centro) {
		return this.responsabileRepository.findByCentro(centro);
	}
	
	public List<Responsabile> findByCognome(String citta) {
		return this.responsabileRepository.findByCognome(citta);
	}
	
	public Responsabile findByUsername(String username) {
		return this.responsabileRepository.findByUsername(username);
	}
	
	public List<Responsabile> findAll() {
		return (List<Responsabile>) this.responsabileRepository.findAll();
	}
	
	public Responsabile findById(Long id) {
		Optional<Responsabile> responsabile = this.responsabileRepository.findById(id);
		if(responsabile.isPresent())
			return responsabile.get();
		return null;
	}

	public boolean alreadyExists(Responsabile responsabile) {
		return this.responsabileRepository.findByUsername(responsabile.getUsername()) != null;
	}

}