package com.sistema.pache.repository.helper;

/**
*
* @author Gabriel
*/


import java.util.List;

import com.sistema.pache.model.Veiculo;

public interface IVeiculoRepository {

	public Veiculo findById(int id);
	
	public List<Veiculo> buscarTodos();
	
	public Veiculo buscarPorChassi(String chassi);

	public Veiculo buscarUltimoCadastro();
}
