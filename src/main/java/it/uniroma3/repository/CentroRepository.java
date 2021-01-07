package it.uniroma3.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.model.Centro;

public interface CentroRepository extends CrudRepository<Centro, Long> {
	
	public Optional<Centro> findByNome(String nome);

}
