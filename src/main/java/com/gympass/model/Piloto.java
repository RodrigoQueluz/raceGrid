package com.gympass.model;

public class Piloto {

	private Integer id;
	private String name;
	private Volta melhorVolta;
	private Double velocidadeTotal = 0D;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Volta getMelhorVolta() {
		return melhorVolta;
	}

	public void setMelhorVolta(Volta melhorVolta) {
		this.melhorVolta = melhorVolta;
	}

	public Double getVelocidadeTotal() {
		return velocidadeTotal;
	}

	public void setVelocidadeTotal(Double velocidadeTotal) {
		this.velocidadeTotal = velocidadeTotal;
	}

	@Override
	public String toString() {
		return "Piloto: " + name + " [" + id + "]";
	}

	@Override
	public int hashCode() {
		return this.id;
	}

	@Override
	public boolean equals(Object piloto) {
		return piloto != null && piloto instanceof Piloto && this == ((Piloto) piloto); 
	}

}