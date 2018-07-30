package com.gympass.model;

import java.util.Date;

public class ResultadoPiloto {

	private Piloto piloto;
	private Double velocidadeTotal;
	private Double velocidadeMedia;
	private Integer nroVoltas;
	private Integer classificacaoFinal;
	private Volta melhorVolta;
	private Date horaUltimaVolta;
	
	public Date getHoraUltimaVolta() {
		return horaUltimaVolta;
	}
	public void setHoraUltimaVolta(Date horaUltimaVolta) {
		this.horaUltimaVolta = horaUltimaVolta;
	}
	public Piloto getPiloto() {
		return piloto;
	}
	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}
	public Double getVelocidadeTotal() {
		return velocidadeTotal;
	}
	public void setVelocidadeTotal(Double velocidadeTotal) {
		this.velocidadeTotal = velocidadeTotal;
	}
	public Double getVelocidadeMedia() {
		return velocidadeMedia;
	}
	public void setVelocidadeMedia(Double velocidadeMedia) {
		this.velocidadeMedia = velocidadeMedia;
	}
	public Integer getNroVoltas() {
		return nroVoltas;
	}
	public void setNroVoltas(Integer nroVoltas) {
		this.nroVoltas = nroVoltas;
	}
	public Integer getClassificacaoFinal() {
		return classificacaoFinal;
	}
	public void setClassificacaoFinal(Integer classificacaoFinal) {
		this.classificacaoFinal = classificacaoFinal;
	}
	

	public Volta getMelhorVolta() {
		return melhorVolta;
	}
	public void setMelhorVolta(Volta melhorVolta) {
		this.melhorVolta = melhorVolta;
	}
	@Override
	public int hashCode() {
		return this.getPiloto().getId();
	}

	@Override
	public boolean equals(Object resultadoPiloto) {
		return resultadoPiloto != null && resultadoPiloto instanceof ResultadoPiloto && this.getPiloto().getId() == ((ResultadoPiloto) resultadoPiloto).getPiloto().getId(); 
	}

}