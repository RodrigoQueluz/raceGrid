package com.gympass.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Volta {
	
	private Piloto piloto;
	private Integer nroVolta;
	private Date horaVolta;
	private Date tempoVolte;
	private Double velocidadeVolta;
	
	public Piloto getPiloto() {
		return piloto;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}

	public Integer getNroVolta() {
		return nroVolta;
	}

	public void setNroVolta(Integer nroVolta) {
		this.nroVolta = nroVolta;
	}

	public Date getHoraVolta() {
		return horaVolta;
	}

	public void setHoraVolta(Date horaVolta) {
		this.horaVolta = horaVolta;
	}

	public Date getTempoVolte() {
		return tempoVolte;
	}

	public void setTempoVolte(Date tempoVolte) {
		this.tempoVolte = tempoVolte;
	}

	public Double getVelocidadeVolta() {
		return velocidadeVolta;
	}

	public void setVelocidadeVolta(Double velocidadeVolta) {
		this.velocidadeVolta = velocidadeVolta;
	}

	@Override
	public String toString() {
		DateFormat formatoVolta = new SimpleDateFormat("mm:ss.SSS");

		return ("Volta de numero " + nroVolta + ", " + piloto + ", Tempo Volta: " + formatoVolta.format(tempoVolte) +  ", Velocidade: " + velocidadeVolta);
	}

}