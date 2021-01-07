package it.uniroma3.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.model.Allievo;

public interface AllievoRepository extends CrudRepository<Allievo, Long> {

	public Allievo findByCf(String cf);
	
	public List<Allievo> findByCognome(String cognome);

	public List<Allievo> findByNome(String nome);
	
	public List<Allievo> findByNomeAndCognome(String nome, String cognome);
	
}
