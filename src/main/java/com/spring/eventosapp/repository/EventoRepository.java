package com.spring.eventosapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.eventosapp.models.Evento;

//nessa Interface é necessário apenas instanciá-la. Para que possamos utilizar métodos já prontos como findById, save, delete etc
//nós utilizamos, por exemplo, o save em EventoController
//basicamente nós só a criamos e extendemos o JpaRepository

public interface EventoRepository extends JpaRepository<Evento, String>{

	Evento findByCodigo(long codigo);
	
}