package com.sistema.pache.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistema.pache.model.Contato;
import com.sistema.pache.model.Usuario;
import com.sistema.pache.service.ContatoService;

@Controller
public class ContatoController {

	@Autowired
	ContatoService contatoService;
	
	@Autowired
    private HttpSession session;
	
	
	@GetMapping("/contato/lista")
	public String lista(Model model, @AuthenticationPrincipal User user) {
		List<Contato> lstContato = contatoService.buscarTodos();
		if(lstContato == null) {
			lstContato = new ArrayList<Contato>();
		}
		model.addAttribute("lstContato", lstContato);
		return "admin/cadastro/contato/lista";
	}
	
	@GetMapping("/contato/lista/{fragment}")
	public String lista(@PathVariable("fragment") String fragment, Model model, @AuthenticationPrincipal User user) {
		List<Contato> lstContato = contatoService.buscarTodos();
		if(lstContato == null) {
			lstContato = new ArrayList<Contato>();
		}
		model.addAttribute("lstContato", lstContato);
		return "admin/cadastro/contato/lista :: " + fragment;
	}
	
	@RequestMapping("/contato/remove/{id}")
	public String remover(@PathVariable("id") int id) {
		contatoService.remover(contatoService.buscarPorId(id));
		
		return "redirect:/contato/lista";
	}
	
	@PostMapping("/testeGratis/adicionarContato")
	public @ResponseBody void adicionar(@RequestParam("nome") String nome, @RequestParam("email") String email,
			@RequestParam("mensagem") String mensagem, RedirectAttributes redirectAttributes) {
		Contato contato = new Contato();
		contato.setNome(nome);
		contato.setEmail(email);
		contato.setMensagem(mensagem);
		contato.setDtMensagem(new Date());
    	contatoService.salvar(contato);
	}
	
	@RequestMapping("/contato/confirmarLeitura")
	public @ResponseBody void confirmarLeitura(@RequestParam("id") int id) {
		Contato contato = contatoService.buscarPorId(id);
		contato.setLida(true);
		contatoService.atualizar(contato);
	}
}
