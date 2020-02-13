package com.spring.eventosapp.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="tb_convidado")
public class Convidado {
	
	@Id
	@NotBlank(message = "Obrigatório.") //para indicar que não pode ser deixado em branco
	private String rg;
	
	@NotBlank(message = "Obrigatório.")
	private String nomeConvidado;
	
	@ManyToOne //muitos convidados para um só evento
	private Evento evento;

	
	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getNomeConvidado() {
		return nomeConvidado;
	}

	public void setNomeConvidado(String nomeConvidado) {
		this.nomeConvidado = nomeConvidado;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

}
