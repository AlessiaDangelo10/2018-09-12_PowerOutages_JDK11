package it.polito.tdp.poweroutages.model;

public class Nerc implements Comparable<Nerc>{
	private int id;
	private String value;
	private boolean libero;
	private int bonus;

	public Nerc(int id, String value) {
		this.id = id;
		this.value = value;
		this.libero = true;
		this.bonus = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean isLibero() {
		return libero;
	}

	public void setLibero(boolean libero) {
		this.libero = libero;
	}
	
	public void increaseBonus(int b) {
		this.bonus += b;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Nerc other = (Nerc) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(value);
		return builder.toString();
	}

	@Override
	public int compareTo(Nerc other) {
		return -(this.bonus-other.bonus);
	}

	public int getBonus() {
		// TODO Auto-generated method stub
		return this.bonus;
	}

	public void resetBonus() {
		this.bonus = 0;
	}
}
