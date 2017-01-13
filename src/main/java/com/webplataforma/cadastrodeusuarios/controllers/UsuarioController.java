package com.webplataforma.cadastrodeusuarios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.webplataforma.cadastrodeusuarios.domain.Usuario;
import com.webplataforma.cadastrodeusuarios.domain.UsuarioRepository;

@Controller
@RequestMapping(value="/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository repository;

	@RequestMapping(value="", method=RequestMethod.GET)
	public String listaUsuarios(Model model){
		model.addAttribute("usuarios", repository.findAll());
		
		return "usuarios/lista";
	}
	
	@RequestMapping(value="/novo", method=RequestMethod.GET)
	public String cadastrar(){
		return "usuarios/novo";
	}
	
	@RequestMapping(value="/criar", method=RequestMethod.POST)
	public ModelAndView criar(@RequestParam("nome") String nome, 
							  @RequestParam("email") String email,
							  @RequestParam("senha") String senha){
		Usuario usuario = new Usuario(nome, email, senha);
		repository.save(usuario);
		
		return new ModelAndView("redirect:/usuarios");
	}
	
	@RequestMapping(value="/{id}/editar", method=RequestMethod.GET)
	public String editar(@PathVariable long id, Model model){
		model.addAttribute("usuario", repository.findOne(id));
		
		return "usuarios/editar";
	}
	
	@RequestMapping(value="/atualizar", method=RequestMethod.POST)
	public ModelAndView atualizar(@RequestParam("usuario_id") long id,
			  @RequestParam("nome") String nome, 
			  @RequestParam("email") String email,
			  @RequestParam("senha") String senha){
	
		Usuario usuario = repository.findOne(id);
		usuario.setNome(nome);
		usuario.setEmail(email);
		usuario.setSenha(senha);
		repository.save(usuario);
		
		return new ModelAndView("redirect:/usuarios");
	}
	
	@RequestMapping(value="/{id}/excluir", method=RequestMethod.GET)
	public ModelAndView excluir(@PathVariable long id){
		repository.delete(id);
		
		return new ModelAndView("redirect:/usuarios");
	}
	
	
}
