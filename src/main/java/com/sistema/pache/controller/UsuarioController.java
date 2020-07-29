package com.sistema.pache.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

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

import com.sistema.pache.model.Usuario;
import com.sistema.pache.service.UsuarioService;
import com.sistema.pache.util.Criptografia;
import com.sistema.pache.util.SessionUtils;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
    private HttpSession session;
	
	@Autowired
	private SessionUtils sessionUtils;
	
	@GetMapping("/usuario/leads")
	public String leads(Model model, @AuthenticationPrincipal User user) {
		List<Usuario> lstUsuario = usuarioService.buscarPorTesteGratis((short)0);
		if(lstUsuario == null) {
			lstUsuario = new ArrayList<Usuario>();
		}
		model.addAttribute("lstUsuario", lstUsuario);
		return "admin/usuario/leads";
	}
	
	@GetMapping("/usuario/lista")
	public String lista(Model model, @AuthenticationPrincipal User user) {
		List<Usuario> lstUsuario = usuarioService.buscarTodos();
		if(lstUsuario == null) {
			lstUsuario = new ArrayList<Usuario>();
		}
		model.addAttribute("lstUsuario", lstUsuario);
		return "admin/usuario/lista";
	}
	
	@GetMapping("/usuario/lista/{fragment}")
	public String lista(@PathVariable("fragment") String fragment, Model model, @AuthenticationPrincipal User user) {
		model.addAttribute("lstUsuario", usuarioService.buscarTodos());
		return "admin/usuario/lista :: " + fragment;
	}
	
	@RequestMapping("/usuario/remove/{id}")
	public String remover(@PathVariable("id") int id) {
		usuarioService.remover(usuarioService.buscarPorId(id));
		
		return "redirect:/usuario/lista";
	}
	
	@GetMapping("/usuario/novo")
	public String novo(Model model, @AuthenticationPrincipal User user) {
		Usuario usuario = new Usuario();
		usuario.setSenha("");
		model.addAttribute("usuario", usuario);
		return "admin/usuario/cadastrar";
	}

	@GetMapping("/usuario/novo/{fragment}")
	public String novo(@PathVariable("fragment") String fragment, @AuthenticationPrincipal User user, Model model) {
		model.addAttribute("usuario", new Usuario());
		return "admin/usuario/cadastrar :: " + fragment;
	}
	
	@GetMapping("/usuario/atualizar/{id}")
	public String atualizar(@PathVariable("id") int id, @AuthenticationPrincipal User user, Model model) {
		Usuario usuario = usuarioService.buscarPorId(id);
		
		model.addAttribute("usuario", usuario);
		return "admin/usuario/cadastrar";
	}
	
	@GetMapping("/usuario/atualizar/{id}/{fragment}")
	public String atualizar(@PathVariable("id") int id, @PathVariable("fragment") String fragment,
			@AuthenticationPrincipal User user, Model model) {
		Usuario usuario = usuarioService.buscarPorId(id);
		
		model.addAttribute("usuario", usuario);
		return "admin/usuario/cadastrar :: " + fragment;
	}
	
	@PostMapping("/usuario/adicionar")
	public String adicionar(@ModelAttribute("usuario") @Valid Usuario usuario, @AuthenticationPrincipal User user, 
			BindingResult result, Model model, RedirectAttributes redirectAttributes) {
				
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		 if (usuario.getUsuarioId() > 0) {
	            if (result.hasErrors()){               	                              
	                return "redirect:/usuario/novo";
	            }else{
	            	Usuario usrBuscaSenha = usuarioService.buscarPorId(usuario.getUsuarioId());
		            usuario.setSenha(usrBuscaSenha.getSenha());
		            try {
		                usuario.setPasswordHash(Criptografia.getMD5Code(usuario.getLogin()));
		            } catch (NoSuchAlgorithmException ex) {
		                Logger.getLogger(UsuarioController.class.getSimpleName()).log(Level.SEVERE, null, ex);
		            }           
		
		            
		            usuarioService.atualizar(usuario);
	                redirectAttributes.addFlashAttribute("msg","Usuario alterado com sucesso!");
	            }
	        }else{	            
	            
	            if (result.hasErrors()) {
	            	return "redirect:/usuario/novo";
	            } else { 
	            	usuario.setTeste((short)1);
	            	usuario.setDtCadastro(new Date());
	            	usuario = usuarioService.criptografaSenha(usuario);
			        try {
			            usuario.setPasswordHash(Criptografia.getMD5Code(usuario.getLogin()));
			        } catch (NoSuchAlgorithmException ex) {
			            Logger.getLogger(UsuarioController.class.getSimpleName()).log(Level.SEVERE, null, ex);
			        }
			        usuarioService.salvar(usuario);
	                redirectAttributes.addFlashAttribute("msg","Usuario cadastrado com sucesso!"); 
	            }
	        }
	        
	        return "redirect:/usuario/lista";
	}
	
	@PostMapping("/usuario/alterarSenha")
    public String alterarSenha(@RequestParam("senhaAntiga") String senhaAntiga, @RequestParam("idUserAlt") int idUserAlt,
            @RequestParam("novaSenha") String novaSenha, @RequestParam("confirme") String confirme, RedirectAttributes redirectAttributes){
		if(idUserAlt == 0) {
			Usuario usr = (Usuario) session.getAttribute("usrLogado");
	        String conf = usuarioService.alterarSenha(senhaAntiga, novaSenha, confirme, usr.getUsuarioId());
		}else {
			Usuario userAlt = usuarioService.buscarPorId(idUserAlt);
			String conf = usuarioService.alterarSenhaAlt(novaSenha, confirme, userAlt.getUsuarioId());
		}
		
        return "redirect:/index";
    }
	
	@RequestMapping("/usuario/confSn")
    public @ResponseBody String alterarSenha(@RequestParam("sn") String senha){
		Usuario usr = (Usuario) session.getAttribute("usrLogado");

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();        	
		String retorno = "";
        Usuario usuario = usuarioService.buscarPorId(usr.getUsuarioId());         
        if(encoder.matches(senha, usuario.getSenha())){
            retorno = "true";
        }else{
            retorno = "false";
        }
        
        return retorno;
        
    }
	
	@RequestMapping("/usuario/invalidar/{login}")
    public @ResponseBody void invalidar(@PathVariable("login") String login){
		System.out.println(login);
		sessionUtils.expireUserSessions(login);
    }
	
	@RequestMapping("/usuario/verificaLogin")
    public @ResponseBody String verificaLogin(@RequestParam("login") String login){
		int tam = usuarioService.verificaLogin(login).size();
		if(tam > 0){
            return "true";            
        }else{
            return "false";
        }
    }
	
	@RequestMapping("/testeGratis/gerarUsuarioTeste")
    public @ResponseBody String gerarUsuarioTeste(@RequestParam("cpf") String cpf, @RequestParam("email") String email,
    		@RequestParam("nome") String nome, @RequestParam("telefone") String telefone, @RequestParam("plano") String plano){
		
		int tam = usuarioService.buscarPorParametroCpfEmail(cpf, email).size();
		
		if(tam == 0) {
			Usuario usuario = new Usuario();
			UUID uuid = UUID.randomUUID();  
			String myRandom = uuid.toString(); 
			String senha = myRandom.substring(0,6);
			usuario.setSenha(senha);
			usuario = usuarioService.criptografaSenha(usuario);
			usuario.setNome(nome);
			usuario.setTelefone(telefone);
			usuario.setLogin(email);
			usuario.setEmail(email);
			usuario.setTeste((short)0);
			usuario.setPlano(plano);
			usuario.setDtCadastro(new Date());
	        try {
	            usuario.setPasswordHash(Criptografia.getMD5Code(usuario.getLogin()));
	        } catch (NoSuchAlgorithmException ex) {
	            Logger.getLogger(UsuarioController.class.getSimpleName()).log(Level.SEVERE, null, ex);
	        }
	        usuarioService.salvar(usuario);
	        
	        return senha;
			
		}else {
			return "";
		}
    }
	
	@GetMapping("/testeGratis/novo")
	public String novo() {
		return "website/testeGratis";
	}
	
	@RequestMapping("/usuario/confirmarLead/{id}")
    public @ResponseBody void confirmarLead(@PathVariable("id") int id) {
		Usuario user = usuarioService.buscarPorId(id);
		user.setComfirmeLead((short)1);
		usuarioService.atualizar(user);
	}
}
