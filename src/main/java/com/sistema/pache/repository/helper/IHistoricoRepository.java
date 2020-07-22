package com.sistema.pache.repository.helper;

import java.util.Date;
import java.util.List;

import com.sistema.pache.model.Historico;

/**
*
* @author Gabriel
*/
public interface IHistoricoRepository {

	public Historico findById(int id);
	
	public List<Historico> buscarTodos();
}
