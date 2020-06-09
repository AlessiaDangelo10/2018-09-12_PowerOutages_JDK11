package it.polito.tdp.poweroutages.model;

public class Adiacenza implements Comparable<Adiacenza>{
	
	private Nerc n1;
	private Nerc n2;
	private Double peso;
	
	public Adiacenza(Nerc n1, Nerc n2, Double peso) {
		super();
		this.n1 = n1;
		this.n2 = n2;
		this.peso = peso;
	}

	public Nerc getN1() {
		return n1;
	}

	public void setN1(Nerc n1) {
		this.n1 = n1;
	}

	public Nerc getN2() {
		return n2;
	}

	public void setN2(Nerc n2) {
		this.n2 = n2;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((n1 == null) ? 0 : n1.hashCode());
		result = prime * result + ((n2 == null) ? 0 : n2.hashCode());
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
		Adiacenza other = (Adiacenza) obj;
		if (n1 == null) {
			if (other.n1 != null)
				return false;
		} else if (!n1.equals(other.n1))
			return false;
		if (n2 == null) {
			if (other.n2 != null)
				return false;
		} else if (!n2.equals(other.n2))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return  n1 + " - " + n2 + "   peso= " + peso;
	}

	@Override
	public int compareTo(Adiacenza other) {
		return -this.peso.compareTo(other.peso);
	}
	
	
	
}
