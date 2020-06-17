package com.sistema.pache.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sistema.pache.model.Arquivo;
import com.sistema.pache.repository.helper.IArquivoRepository;

public class ArquivoRepositoryImpl implements IArquivoRepository {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Arquivo findById(int id) {
		try {
			return this.manager.createQuery("from " + Arquivo.class.getName() + " where arquivoId = :id", Arquivo.class)
					.setParameter("id", id).getSingleResult();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

	@Override
	public List<Arquivo> buscarTodos() {
		return this.manager.createQuery("from " + Arquivo.class.getName(), Arquivo.class).getResultList();
	}
	
	@Override
	public List<Arquivo> buscarPorContrato(int id) {
		return this.manager.createQuery("from " + Arquivo.class.getName() + " gu join fetch gu.contrato c  where c.contratoId = :id", Arquivo.class)
				.setParameter("id", id).getResultList();
	}

}
