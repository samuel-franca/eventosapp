package com.spring.eventosapp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.eventosapp.models.Convidado;
import com.spring.eventosapp.models.Evento;
import com.spring.eventosapp.repository.ConvidadoRepository;
import com.spring.eventosapp.repository.EventoRepository;

@Controller
public class EventoController {
	
	@Autowired
	private EventoRepository er;

	@Autowired
	private ConvidadoRepository cr;
	
	//essa função permite que a página seja mostrada ao usuário quando ele entra em /cadastrarEvento
	@RequestMapping(value="/cadastrarEvento", method=RequestMethod.GET) //é um GET, porque ele retorna um formulário
	public String form() { //vai retornar o formulário de cadastro do evento
		return "evento/cadastrarEvento"; //não precisa indicar que é html, basta o nome dele		
	}
	
	//este método define o que deve ser feito ao clicar em Salvar no botão definindo no HTML
	@RequestMapping(value="/cadastrarEvento", method=RequestMethod.POST) //é um POST, porque ele salva no bd
	public String form(@Valid Evento evento, BindingResult result, RedirectAttributes attributes) { //retorna o formulário de cadastro do evento
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/cadastrarEvento";			
		}
		
		er.save(evento); //isto é para salvar (persistir) esse evento no bd
		attributes.addFlashAttribute("mensagem", "Evento cadastrado com sucesso!");
		return "redirect:/cadastrarEvento"; //não precisa indicar que é html, basta o nome dele
	}
	
	//este método retorna a lista de eventos
	@RequestMapping(value = {"/", "/eventos"}) //quando o cliente digitar /eventos, será enviada a requisição
	public ModelAndView listaEventos() {
		ModelAndView mv = new ModelAndView("index"); //é passado para o ModelAndView a página que ele vai renderizar, que é o index.html
		Iterable<Evento> eventos = er.findAll(); //utilizado para buscar todos os eventos no bd. Observe que utilizamos de novo o EventoRepository
		mv.addObject("eventos", eventos); //envio da lista de eventos para a view. O nome daqui, deve ser o mesmo da view para que ele reconheça. E deve-se passar a variável que contém a lista de eventos
		return mv;
	}
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo) {
		ModelAndView mv = new ModelAndView("evento/detalhesEvento"); //detalhesEvento é a página HTML
		Evento evento = er.findByCodigo(codigo); //para utilizar o findByCodigo, precisa-se defini-lo no EventoRepository
		mv.addObject("evento", evento);
		
		Iterable<Convidado> convidados = cr.findByEvento(evento);
		//envio desta lista para a view (evento/detalhesEvento)
		//criou-se dentro de detalhesEvento uma nova div que receberá esses valores
		mv.addObject("convidados", convidados);
		
		return mv;
	}
	
	//método criado deletar um evento
	@RequestMapping("/deletarEvento")
	public String deletarEvento(long codigo) {
		Evento evento = er.findByCodigo(codigo); //buscamos o evento no bd
		er.delete(evento); //e o deletamos
		
		return "redirect:/eventos"; //vai retornar para a lista de eventos
	}
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.POST)
	//@Valid, BindingResult result e RedirectAttributes attributes foram adicionados depois
	//eles são utilizados para fazer a validação
	public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributes) { //@Valid vai validar o convidado
		//antes de salvar no banco, ele vai verificar se tem erros
		if(result.hasErrors()) {
			//para que essa mensagem seja mostrada na view, é necessário criar um fragmento no formulário
			//criou-se então uma nova view, mensagemValidacao
			attributes.addFlashAttribute("mensagem", "Verifique os campos!");
			return "redirect:/{codigo}";
		}
		
		Evento evento = er.findByCodigo(codigo); //busca do evento que está relacionado ao código
		convidado.setEvento(evento);
		cr.save(convidado);
		attributes.addFlashAttribute("mensagem", "Convidado adicionado com sucesso!");
		return "redirect:/{codigo}";
	}
	
	@RequestMapping("/deletarConvidado")
	public String deletarConvidado(String rg) {
		Convidado convidado = cr.findByRg(rg);
		cr.delete(convidado);
		
		Evento evento = convidado.getEvento();
		long codigoLong = evento.getCodigo();
		String codigo = "" + codigoLong;
		return("redirect:/" + codigo);
	}
}