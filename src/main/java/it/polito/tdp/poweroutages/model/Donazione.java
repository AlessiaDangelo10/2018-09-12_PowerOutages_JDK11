package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class Donazione {

	private Nerc donatore;
	private Nerc ricevente;
	private LocalDateTime inizio;
	private LocalDateTime fine;
	
	public Donazione(Nerc donatore, Nerc ricevente, LocalDateTime data, LocalDateTime fine) {
		super();
		this.donatore = donatore;
		this.ricevente = ricevente;
		this.inizio = data;
		this.fine = fine;
	}

	public Nerc getDonatore() {
		return donatore;
	}

	public void setDonatore(Nerc donatore) {
		this.donatore = donatore;
	}

	public Nerc getRicevente() {
		return ricevente;
	}

	public void setRicevente(Nerc ricevente) {
		this.ricevente = ricevente;
	}

	public LocalDateTime getInizio() {
		return inizio;
	}

	public void setInizio(LocalDateTime inizio) {
		this.inizio = inizio;
	}

	public LocalDateTime getFine() {
		return fine;
	}

	public void setFine(LocalDateTime fine) {
		this.fine = fine;
	}

	@Override
	public String toString() {
		return "Donazione [donatore=" + donatore + ", ricevente=" + ricevente + ", inizio=" + inizio + ", fine=" + fine
				+ "]";
	}

	
}
