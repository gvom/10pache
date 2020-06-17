package com.sistema.pache.controller;

import java.util.ArrayList;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistema.pache.model.Cliente;
import com.sistema.pache.model.Usuario;
import com.sistema.pache.service.ClienteService;

@Controller
public class ClienteController {

	@Autowired
	ClienteService clienteService;
	
	@Autowired
    private HttpSession session;
	
	
	@GetMapping("/cliente/lista")
	public String lista(Model model, @AuthenticationPrincipal User user) {
		List<Cliente> lstCliente = clienteService.buscarTodos();
		if(lstCliente == null) {
			lstCliente = new ArrayList<Cliente>();
		}
		model.addAttribute("lstCliente", lstCliente);
		return "admin/cadastro/cliente/lista";
	}
	
	@GetMapping("/cliente/lista/{fragment}")
	public String lista(@PathVariable("fragment") String fragment, Model model, @AuthenticationPrincipal User user) {
		List<Cliente> lstCliente = clienteService.buscarTodos();
		if(lstCliente == null) {
			lstCliente = new ArrayList<Cliente>();
		}
		model.addAttribute("lstCliente", lstCliente);
		return "admin/cadastro/cliente/lista :: " + fragment;
	}
	
	@RequestMapping("/cliente/remove/{id}")
	public String remover(@PathVariable("id") int id) {
		clienteService.remover(clienteService.buscarPorId(id));
		
		return "redirect:/cliente/lista";
	}
	
	@GetMapping("/cliente/novo")
	public String novo(Model model, @AuthenticationPrincipal User user) {
		Cliente cliente = new Cliente();
		model.addAttribute("cliente", cliente);
		return "admin/cadastro/cliente/cadastrar";
	}

	@GetMapping("/cliente/novo/{fragment}")
	public String novo(@PathVariable("fragment") String fragment, @AuthenticationPrincipal User user, Model model) {
		model.addAttribute("cliente", new Cliente());
		return "admin/cadastro/cliente/cadastrar :: " + fragment;
	}
	
	@GetMapping("/cliente/atualizar/{id}")
	public String atualizar(@PathVariable("id") int id, @AuthenticationPrincipal User user, Model model) {
		Cliente cliente = clienteService.buscarPorId(id);
		
		model.addAttribute("cliente", cliente);
		return "admin/cadastro/cliente/cadastrar";
	}
	
	@GetMapping("/cliente/atualizar/{id}/{fragment}")
	public String atualizar(@PathVariable("id") int id, @PathVariable("fragment") String fragment,
			@AuthenticationPrincipal User user, Model model) {
		Cliente cliente = clienteService.buscarPorId(id);
		
		model.addAttribute("cliente", cliente);
		return "admin/cadastro/cliente/cadastrar :: " + fragment;
	}
	
	@PostMapping("/cliente/adicionar")
	public String adicionar(@ModelAttribute("cliente") @Valid Cliente cliente, @AuthenticationPrincipal User user, 
			BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		
		Usuario usr = (Usuario) session.getAttribute("usrLogado");
    	cliente.setUsuario(usr);
		
		 if (cliente.getClienteId() > 0) {
	            if (result.hasErrors()){               	                              
	                return "redirect:/cliente/novo";
	            }else{
	                clienteService.atualizar(cliente);
	                redirectAttributes.addFlashAttribute("msg","Cliente alterado com sucesso!");
	            }
	        }else{	            
	            
	            if (result.hasErrors()) {
	            	return "redirect:/cliente/novo";
	            } else {
	            	clienteService.salvar(cliente);
	                redirectAttributes.addFlashAttribute("msg","Cliente cadastrado com sucesso!"); 
	            }
	        }
	        return "redirect:/cliente/lista";
	}
}
