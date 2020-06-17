package com.sistema.pache.repository.helper;

import java.util.List;

import com.sistema.pache.model.Usuario;

/**
*
* @author Gabriel
*/

public interface IUsuarioRepository {

	public Usuario findByLogin(String l);
	
	public Usuario findById(int id);
	
	public List<Usuario> buscarPorParametroLogin(String param);
	
	public void alterarSenha(int id, String senha);
	
	public List<Usuario> buscarTodos();
	
	public List<Usuario> buscarPorParametroCpfEmail(String cpf, String email);
	
	public List<Usuario> buscarPorTesteGratis(short s);
	
}
