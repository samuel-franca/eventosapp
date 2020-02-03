package com.spring.eventosapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	//depois do localhost:8080, nós temos a /, que é a raiz do projeto
	//esse GetMapping vai acessar a raiz do projeto
	@GetMapping("/")
	public String home() {
		return "home";
	}

}