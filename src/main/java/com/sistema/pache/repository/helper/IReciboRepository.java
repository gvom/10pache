package com.sistema.pache.repository.helper;

import java.util.Date;
import java.util.List;

import com.sistema.pache.model.Recibo;

/**
*
* @author Gabriel
*/

public interface IReciboRepository {

	public Recibo findById(int id);
	
	public List<Recibo> buscarTodos();

	public List<Recibo> buscarPorDataStatus(Date data);
}
