package com.spring.eventosapp.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name="tb_evento")
public class Evento {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) //para gerar o Id automaticamente
	private long codigo; //toda entidade tem que ter um Id
	
	@NotBlank(message = "Obrigatório.")
	private String nome;
	
	@NotBlank(message = "Obrigatório.")
	private String local;
	
	@NotNull(message = "Obrigatório.")
	//columnDefinition serve para definir o tipo de dado que teremos no BD
	@Column(columnDefinition = "DATE")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate data;
	
	@NotBlank(message = "Obrigatório.")
	private String horario;

	//quando iríamos deletar um evento que tinha convidados, dava erro
	//para contornar isto, foi necessário adicionar estes 3 parâmetros no @OneToMany
	//Obs.: somente os três em conjunto deram certo
	@OneToMany(mappedBy="evento", cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Convidado> convidado;
	
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
}
