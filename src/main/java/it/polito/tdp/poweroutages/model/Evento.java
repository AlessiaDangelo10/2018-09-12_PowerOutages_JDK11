package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class Evento implements Comparable<Evento>{
	
	public enum EventType {
		INIZIO, FINE,
	}
	
	private Nerc nerc;
	private EventType type;
	private LocalDateTime time;
	private int numGiorni;
	
	public Evento(Nerc nerc, EventType type, LocalDateTime time, int numGiorni) {
		super();
		this.nerc = nerc;
		this.type = type;
		this.time = time;
		this.numGiorni = numGiorni;
	}

	public int getNumGiorni() {
		return numGiorni;
	}

	public void setNumGiorni(int numGiorni) {
		this.numGiorni = numGiorni;
	}

	public Nerc getNerc() {
		return nerc;
	}

	public EventType getType() {
		return type;
	}

	public LocalDateTime getTime() {
		return time;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nerc == null) ? 0 : nerc.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Evento other = (Evento) obj;
		if (nerc == null) {
			if (other.nerc != null)
				return false;
		} else if (!nerc.equals(other.nerc))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Evento [nerc=" + nerc + ", type=" + type + ", time=" + time + "]";
	}

	@Override
	public int compareTo(Evento other) {
		return this.time.compareTo(other.time);
	}

}
