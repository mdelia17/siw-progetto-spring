package it.uniroma3.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.model.Attivita;

public interface AttivitaRepository extends CrudRepository<Attivita, Long>{
	
	public List<Attivita> findByNome(String nome);

	public List<Attivita> findByNomeAndDataInizioAndOraInizio(String nome, LocalDate dataInizio, LocalTime oraInizio);
	
}

