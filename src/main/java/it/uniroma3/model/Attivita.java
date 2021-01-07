package it.uniroma3.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Attivita {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(nullable=false)
	private String nome;

	@Column(length=2000)
	private String descrizione;
	
	@DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
	private LocalDate dataInizio;

	@DateTimeFormat(iso= DateTimeFormat.ISO.TIME)
	private LocalTime oraInizio;
	
	@ManyToOne
	private Centro centro;
	
	@ManyToMany
	private List<Allievo> allievi;
	
	public Attivita() {
		this.allievi = new LinkedList<>();
	}

	
	//Getter e Setter
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDate getDataInizio() {
		return dataInizio;
	}


	public void setDataInizio(LocalDate dataInizio) {
		this.dataInizio = dataInizio;
	}


	public LocalTime getOraInizio() {
		return oraInizio;
	}

	public void setOraInizio(LocalTime oraInizio) {
		this.oraInizio = oraInizio;
	}

	public Centro getCentro() {
		return centro;
	}


	public void setCentro(Centro centro) {
		this.centro = centro;
	}

	public List<Allievo> getAllievi() {
		return allievi;
	}


	public void setAllievi(List<Allievo> allievi) {
		this.allievi = allievi;
	}


	public void add(Allievo a) {
		this.allievi.add(a);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataInizio == null) ? 0 : dataInizio.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((oraInizio == null) ? 0 : oraInizio.hashCode());
		return result;
	}


	//Equals e HashCode
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attivita other = (Attivita) obj;
		if (dataInizio == null) {
			if (other.dataInizio != null)
				return false;
		} else if (!dataInizio.equals(other.dataInizio))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (oraInizio == null) {
			if (other.oraInizio != null)
				return false;
		} else if (!oraInizio.equals(other.oraInizio))
			return false;
		return true;
	}
	
	

}
