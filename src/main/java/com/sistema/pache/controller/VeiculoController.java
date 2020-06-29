package com.sistema.pache.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.google.gson.Gson;
import com.sistema.pache.model.Cliente;
import com.sistema.pache.model.Endereco;
import com.sistema.pache.model.Veiculo;
import com.sistema.pache.model.Usuario;
import com.sistema.pache.service.VeiculoService;

@Controller
public class VeiculoController {

	@Autowired
	private VeiculoService veiculoService;
	
	@Autowired
    private HttpSession session;
	
	@GetMapping("/veiculo/lista")
	public String lista(Model model, @AuthenticationPrincipal User user) {
		List<Veiculo> lstVeiculo = veiculoService.buscarTodos();
		if(lstVeiculo == null) {
			lstVeiculo = new ArrayList<Veiculo>();
		}
		model.addAttribute("lstVeiculo", lstVeiculo);
		return "admin/cadastro/veiculo/lista";
	}
	
	@GetMapping("/veiculo/lista/{fragment}")
	public String lista(@PathVariable("fragment") String fragment, Model model, @AuthenticationPrincipal User user) {
		model.addAttribute("lstVeiculo", veiculoService.buscarTodos());
		return "admin/cadastro/veiculo/lista :: " + fragment;
	}
	
	@RequestMapping("/veiculo/remove/{id}")
	public String remover(@PathVariable("id") int id) {
		veiculoService.remover(veiculoService.buscarPorId(id));
		
		return "redirect:/veiculo/lista";
	}
	
	@GetMapping("/veiculo/novo")
	public String novo(Model model, @AuthenticationPrincipal User user) {
		Veiculo veiculo = new Veiculo();
		model.addAttribute("veiculo", veiculo);
		model.addAttribute("paginaAnterior", "/veiculo/lista");
		return "admin/cadastro/veiculo/cadastrar";
	}

	@GetMapping("/veiculo/novoAlt")
	public String novoAlt(@AuthenticationPrincipal User user, Model model) {
		model.addAttribute("veiculo", new Veiculo());
		model.addAttribute("paginaAnterior", "/index");
		return "admin/cadastro/veiculo/cadastrar";
	}
	
	@GetMapping("/veiculo/atualizar/{id}")
	public String atualizar(@PathVariable("id") int id, @AuthenticationPrincipal User user, Model model) {
		Veiculo veiculo = veiculoService.buscarPorId(id);
		
		model.addAttribute("veiculo", veiculo);
		return "admin/cadastro/veiculo/cadastrar";
	}
	
	@GetMapping("/veiculo/atualizar/{id}/{fragment}")
	public String atualizar(@PathVariable("id") int id, @PathVariable("fragment") String fragment,
			@AuthenticationPrincipal User user, Model model) {
		Veiculo veiculo = veiculoService.buscarPorId(id);
		
		model.addAttribute("veiculo", veiculo);
		return "admin/cadastro/veiculo/cadastrar :: " + fragment;
	}
	
	@PostMapping("/veiculo/adicionar")
	public String adicionar(@ModelAttribute("veiculo") @Valid Veiculo veiculo, @AuthenticationPrincipal User user, 
			BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		
		Usuario usr = (Usuario) session.getAttribute("usrLogado");
		
		 if (veiculo.getVeiculoId() > 0) {
				 Veiculo temp = veiculoService.buscarPorId(veiculo.getVeiculoId());
				 
				 veiculo.setDataCadastro(temp.getDataCadastro());
				 
	            if (result.hasErrors()){               	                              
	                return "redirect:/veiculo/novo";
	            }else{
	            	Veiculo recTe = veiculoService.buscarPorId(veiculo.getVeiculoId());
	            	veiculo.setUsuario(recTe.getUsuario());
	                veiculoService.atualizar(veiculo);
	                redirectAttributes.addFlashAttribute("msg","Veiculo alterado com sucesso!");
	            }
	        }else{	            
	            
	            if (result.hasErrors()) {
	            	return "redirect:/veiculo/novo";
	            } else {
	            	veiculo.setUsuario(usr);
	            	veiculo.setDataCadastro(new Date());
	            	veiculoService.salvar(veiculo);
	                redirectAttributes.addFlashAttribute("msg","Veiculo cadastrado com sucesso!"); 
	            }
	        }
	        return "redirect:/veiculo/lista";
	}
	
	@RequestMapping("/veiculo/buscarPorChassi")
	public @ResponseBody String buscarPorChassi(@RequestParam("chassi") String chassi) {
		String retorno = "";
		Veiculo veiculo = veiculoService.buscarPorChassi(chassi);
		if(veiculo != null) {
			Gson gson = new Gson();
			veiculo.setUsuario(null);
			retorno = gson.toJson(veiculo);
		}
		
		return retorno;
	}
}
