package it.uniroma3.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.model.Centro;
import it.uniroma3.model.Responsabile;

public interface ResponsabileRepository extends CrudRepository<Responsabile, Long> {
	
	public List<Responsabile> findByCentro(Centro centro);

	public List<Responsabile> findByCognome(String cognome);

	public Responsabile findByUsername(String username);
	
}
