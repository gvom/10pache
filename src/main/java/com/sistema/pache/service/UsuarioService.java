package com.sistema.pache.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.pache.model.Usuario;
import com.sistema.pache.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
    private HistoricoService historicoService;

	@Transactional(propagation = Propagation.NESTED)
	public void salvar(Usuario usuario) {		
		this.repository.save(usuario);
	}

	@Transactional
	public void atualizar(Usuario usuario) {
		historicoService.criarHistorico(usuario.getClass().getName(), usuario.getUsuarioId(), usuario, false);
		this.repository.save(usuario);
	}

	@Transactional
	public void remover(Usuario usuario) {
		historicoService.criarHistorico(usuario.getClass().getName(), usuario.getUsuarioId(), usuario, true);
		this.repository.delete(usuario);
	}

	@Transactional
	public List<Usuario> buscarTodos() {
		return this.repository.buscarTodos();
	}

	@Transactional
	public Usuario buscarPorId(int id) {
		return this.repository.findById(id);
	}
	
	@Transactional
	public List<Usuario> buscarPorTesteGratis(short s) {
		return this.repository.buscarPorTesteGratis(s);
	}
	
	@Transactional
    public String alterarSenha(String senhaAntiga, String novaSenha,
             String confirme, int idUsuario){
        
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();        	
        String retorno = "";
        Usuario usuario = this.repository.findById(idUsuario);              
        novaSenha = encoder.encode(novaSenha);       
        if(encoder.matches(senhaAntiga, usuario.getSenha())){
            usuario.setSenha(novaSenha);
            this.repository.save(usuario);
            retorno += "true";
        }else{
            retorno += "false";
        }
        return retorno;
    } 
	
	@Transactional
    public String alterarSenhaAlt(String novaSenha,
             String confirme, int idUsuario){
        
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();        	
        String retorno = "";
        Usuario usuario = this.repository.findById(idUsuario);              
        novaSenha = encoder.encode(novaSenha);       
        usuario.setSenha(novaSenha);
        this.repository.save(usuario);
        retorno += "true";
        return retorno;
    } 
	
	public Usuario criptografaSenha(Usuario usuario){
    	
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();       
        usuario.setSenha(encoder.encode(usuario.getSenha()));
              
        return usuario;
    }
	
	@Transactional
	public List<Usuario> verificaLogin(String login) {
		return this.repository.buscarPorParametroLogin(login);
	}
	
	@Transactional
	public List<Usuario> buscarPorParametroCpfEmail(String cpf, String email){
		return this.repository.buscarPorParametroCpfEmail(cpf, email);
	}

}
