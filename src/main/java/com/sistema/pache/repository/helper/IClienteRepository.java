package com.sistema.pache.repository.helper;

import java.util.List;

import com.sistema.pache.model.Cliente;

/**
*
* @author Gabriel
*/

public interface IClienteRepository {

	public Cliente findById(int id);
	
	public List<Cliente> buscarTodos();

	public Cliente buscarPorCpf(String cpf);
	
	public Cliente buscarPorCpfIdentidade(String cpf, String rg);
}
