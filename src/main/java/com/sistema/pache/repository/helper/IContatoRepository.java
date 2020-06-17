package com.sistema.pache.repository.helper;

import java.util.List;

import com.sistema.pache.model.Contato;

/**
*
* @author Gabriel
*/
public interface IContatoRepository {
	
	public Contato findById(int id);
	
	public List<Contato> buscarTodos();

	public List<Contato> buscarPorNome(String nome);
	
	public List<Contato> buscarPorEmail(String email);
}
