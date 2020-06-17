package com.sistema.pache.repository.helper;

import java.util.List;

import com.sistema.pache.model.Arquivo;

/**
*
* @author Gabriel
*/

public interface IArquivoRepository {

	public Arquivo findById(int id);
	
	public List<Arquivo> buscarTodos();
	
	public List<Arquivo> buscarPorContrato(int id);
}
