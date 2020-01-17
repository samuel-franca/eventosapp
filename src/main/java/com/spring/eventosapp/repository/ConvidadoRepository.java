package com.spring.eventosapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.eventosapp.models.Convidado;
import com.spring.eventosapp.models.Evento;

public interface ConvidadoRepository extends JpaRepository<Convidado, String>{
	
	//findBy(AlgumaClasse) é uma convenção de assinatura
	//ou seja, o Spring faz a consulta de acordo com o nome do atributo que passar no findBy
	Iterable<Convidado> findByEvento(Evento evento);

	Convidado findByRg(String rg);

}
