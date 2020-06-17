package com.sistema.pache.repository.helper;

import java.util.List;

import com.sistema.pache.model.Endereco;

/**
*
* @author Gabriel
*/

public interface IEnderecoRepository {

	public Endereco findById(int id);
	
	public List<Endereco> buscarTodos();
}
