package com.sistema.pache.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.sistema.pache.model.Veiculo;
import com.sistema.pache.service.ClienteService;
import com.sistema.pache.service.EnderecoService;
import com.sistema.pache.service.ReciboService;
import com.sistema.pache.service.VeiculoService;

@Controller
public class ReciboController {

	@Autowired
	private ReciboService reciboService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private VeiculoService veiculoService;
	
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
	public String adicionar(@ModelAttribute("recibo") @Valid Recibo recibo, @AuthenticationPrincipal User user, 
			BindingResult result, Model model, RedirectAttributes redirectAttributes) {
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		
		Usuario usr = (Usuario) session.getAttribute("usrLogado");
				
		String dataEntregaString = recibo.getDataEntregaString();
		String dataVisturiaString = recibo.getDataVisturiaString();
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
		
		if(!dataEntregaString.equals("")) {
			try {
				Date date = (Date)formatter.parse(dataEntregaString);
				recibo.setDataEntrega(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		if(!dataVisturiaString.equals("")) {
			try {
				Date date = (Date)formatter.parse(dataVisturiaString);
				recibo.setDataVisturia(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		 if (recibo.getReciboId() > 0) {
				 Recibo temp = reciboService.buscarPorId(recibo.getReciboId());
				 
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
	            	recibo.setVeiculoId(salvarVeiculo(recibo, usr));
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
	            	recibo.setVeiculoId(salvarVeiculo(recibo, usr));
	            	recibo.getCliente().setUsuario(usr);
	            	recibo.setUsuario(usr);
	            	recibo.setDataCadastro(new Date());
	            	reciboService.salvar(recibo);
	                redirectAttributes.addFlashAttribute("msg","Recibo cadastrado com sucesso!"); 
	            }
	        }
	        return "redirect:/recibo/lista";
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
	
	@RequestMapping("/recibo/imprimir")
    public @ResponseBody String imprimirRecibo(HttpServletResponse response, @RequestParam("idRecibo") int idRecibo) throws Exception{
		return reciboService.imprimirRecibo(response, idRecibo);
    }
	
	@RequestMapping("/recibo/removerPdf")
    public @ResponseBody void removerPdf(@RequestParam("arquivo") String arquivo) throws Exception{
		boolean verificar = reciboService.removerPdf(arquivo.split("imagens")[1]);
		if(!verificar) {
			System.out.println("ERROR:::: Não foi possivel deletar arquivo: "+arquivo+" do servidor!");
		}
    }
	
	public int salvarVeiculo(Recibo recibo, Usuario usr) {
		Veiculo veiculo = new Veiculo();
    	veiculo.setVeiculoId(recibo.getVeiculoId());
    	Veiculo temp = veiculoService.buscarPorId(veiculo.getVeiculoId());
    	if(veiculo.getVeiculoId() > 0 && temp != null) {
    		int id = veiculo.getVeiculoId();
    		veiculo.setDataUltimoRegistro(new Date());
    		veiculo.setAntUf(recibo.getAntUf());
    		veiculo.setCapPotCil(recibo.getCapPotCil());
        	veiculo.setCor(recibo.getCor());
        	veiculo.setPlaca(recibo.getPlaca());
        	veiculoService.atualizar(veiculo);
        	return id;
    	}else {
    		veiculo.setAnoFab(recibo.getAnoFab());
    		veiculo.setMarca(recibo.getMarca());
    		veiculo.setUsuario(usr);
    		veiculo.setDataCadastro(new Date());
    		veiculo.setCombustivel(recibo.getCombustivel());
    		veiculo.setCategoria(recibo.getCategoria());
        	veiculo.setChassi(recibo.getChassi());
        	veiculo.setEspecie(recibo.getEspecie());
        	veiculo.setAnoMod(recibo.getAnoMod());
        	veiculo.setCapPotCil(recibo.getCapPotCil());
        	veiculo.setCor(recibo.getCor());
        	veiculo.setPlaca(recibo.getPlaca());
        	veiculoService.salvar(veiculo);
        	return veiculoService.buscarUltimoCadastro().getVeiculoId();
    	}
	}
}

