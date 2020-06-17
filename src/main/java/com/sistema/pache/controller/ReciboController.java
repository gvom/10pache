package com.sistema.pache.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
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

import com.google.gson.Gson;
import com.sistema.pache.model.Cliente;
import com.sistema.pache.model.Endereco;
import com.sistema.pache.model.EnderecoTemp;
import com.sistema.pache.model.Recibo;
import com.sistema.pache.model.Usuario;
import com.sistema.pache.service.ClienteService;
import com.sistema.pache.service.EnderecoService;
import com.sistema.pache.service.ReciboService;

@Controller
public class ReciboController {

	@Autowired
	private ReciboService reciboService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EnderecoService enderecoService;
	
	@Autowired
    private HttpSession session;
	
	
	@GetMapping("/recibo/lista")
	public String lista(Model model, @AuthenticationPrincipal User user) {
		List<Recibo> lstRecibo = reciboService.buscarTodos();
		if(lstRecibo == null) {
			lstRecibo = new ArrayList<Recibo>();
		}
		model.addAttribute("lstRecibo", lstRecibo);
		return "admin/cadastro/recibo/lista";
	}
	
	@GetMapping("/recibo/lista/{fragment}")
	public String lista(@PathVariable("fragment") String fragment, Model model, @AuthenticationPrincipal User user) {
		model.addAttribute("lstRecibo", reciboService.buscarTodos());
		return "admin/cadastro/recibo/lista :: " + fragment;
	}
	
	@RequestMapping("/recibo/remove/{id}")
	public String remover(@PathVariable("id") int id) {
		reciboService.remover(reciboService.buscarPorId(id));
		
		return "redirect:/recibo/lista";
	}
	
	@GetMapping("/recibo/novo")
	public String novo(Model model, @AuthenticationPrincipal User user) {
		Recibo recibo = new Recibo();
		model.addAttribute("recibo", recibo);
		model.addAttribute("paginaAnterior", "/recibo/lista");
		return "admin/cadastro/recibo/cadastrar";
	}

	@GetMapping("/recibo/novoAlt")
	public String novoAlt(@AuthenticationPrincipal User user, Model model) {
		model.addAttribute("recibo", new Recibo());
		model.addAttribute("paginaAnterior", "/index");
		return "admin/cadastro/recibo/cadastrar";
	}
	
	@GetMapping("/recibo/atualizar/{id}")
	public String atualizar(@PathVariable("id") int id, @AuthenticationPrincipal User user, Model model) {
		Recibo recibo = reciboService.buscarPorId(id);
		
		model.addAttribute("recibo", recibo);
		return "admin/cadastro/recibo/cadastrar";
	}
	
	@GetMapping("/recibo/atualizar/{id}/{fragment}")
	public String atualizar(@PathVariable("id") int id, @PathVariable("fragment") String fragment,
			@AuthenticationPrincipal User user, Model model) {
		Recibo recibo = reciboService.buscarPorId(id);
		
		model.addAttribute("recibo", recibo);
		return "admin/cadastro/recibo/cadastrar :: " + fragment;
	}
	
	@PostMapping("/recibo/adicionar")
	public String adicionar(@ModelAttribute("recibo") @Valid Recibo recibo,
			@RequestParam("dias") int dias, @AuthenticationPrincipal User user, 
			BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		
		Usuario usr = (Usuario) session.getAttribute("usrLogado");
		
		if(recibo.getStatus() == 2) {
			recibo.setStatus(3);
		}
		Date data = new Date();
		
		if(dias != 0) {
			Calendar c = Calendar.getInstance(); 
			c.setTime(data); 
			c.add(Calendar.DATE, dias);
			data = c.getTime();
		}
				
		 if (recibo.getReciboId() > 0) {
				 Recibo temp = reciboService.buscarPorId(recibo.getReciboId());
				 if((temp.getStatus() == recibo.getStatus() && dias != 0) || (temp.getStatus() != recibo.getStatus())) {
					 recibo.setData(data);
				 }else {
					 recibo.setData(temp.getData());
				 }
				 recibo.setDataCadastro(temp.getDataCadastro());
				 
	            if (result.hasErrors()){               	                              
	                return "redirect:/recibo/novo";
	            }else{
	            	if (recibo.getCliente().getClienteId() > 0) {
	            		Cliente clienteTemp = recibo.getCliente();
	            		clienteService.atualizar(clienteTemp);
	            		if (recibo.getCliente().getEndereco().getIdEndereco() > 0) {
	            			Endereco enderecoTemp = recibo.getCliente().getEndereco();
	            			enderecoService.atualizar(enderecoTemp);
	            		}
	            	}
	            	Recibo recTe = reciboService.buscarPorId(recibo.getReciboId());
	            	recibo.getCliente().setUsuario(recTe.getUsuario());
	            	recibo.setUsuario(recTe.getUsuario());
	                reciboService.atualizar(recibo);
	                redirectAttributes.addFlashAttribute("msg","Recibo alterado com sucesso!");
	            }
	        }else{	            
	            
	            if (result.hasErrors()) {
	            	return "redirect:/recibo/novo";
	            } else {
	            	recibo.getCliente().setUsuario(usr);
	            	recibo.setUsuario(usr);
	            	recibo.setData(data);
	            	recibo.setDataCadastro(new Date());
	            	reciboService.salvar(recibo);
	                redirectAttributes.addFlashAttribute("msg","Recibo cadastrado com sucesso!"); 
	            }
	        }
	        return "redirect:/recibo/atualizar/"+recibo.getReciboId();
	}
	
	@RequestMapping("/recibo/buscarPorCpf")
	public @ResponseBody String buscarPorCpf(@RequestParam("cpf") String cpf) {
		String retorno = "";
		Cliente cliente = clienteService.buscarPorCpf(cpf);
		if(cliente != null) {
			cliente.setEndereco(null);
			Gson gson = new Gson();
			retorno = gson.toJson(cliente);
		}
		
		return retorno;
	}
	
	@RequestMapping("/recibo/buscarEndereco")
	public @ResponseBody String buscarEndereco(@RequestParam("id") int id) {
		String retorno = "";
		Endereco endereco = enderecoService.buscarPorId(clienteService.buscarPorId(id).getEndereco().getIdEndereco());
		if(endereco != null) {
			EnderecoTemp temp = new EnderecoTemp();
			temp.setBairro(endereco.getBairro());
			temp.setCep(endereco.getCep());
			temp.setCidade(endereco.getCidade());
			temp.setComplemento(endereco.getComplemento());
			temp.setEstado(endereco.getEstado());
			temp.setIdEndereco(endereco.getIdEndereco());
			temp.setLogradouro(endereco.getLogradouro());
			temp.setNumero(endereco.getNumero());
			temp.setPais(endereco.getPais());
			
			Gson gson = new Gson();
			retorno = gson.toJson(temp);
		}
		
		return retorno;
	}
	
	@RequestMapping("/recibo/imprimir/{idRecibo}")
    public String imprimirRecibo(HttpServletResponse response, @PathVariable("idRecibo") int idRecibo) throws Exception{
    	reciboService.imprimirRecibo(response, idRecibo);
    	
    	return "redirect:/recibo/lista";
    }
}

