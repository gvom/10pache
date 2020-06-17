package com.sistema.pache.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sistema.pache.model.Contato;
import com.sistema.pache.repository.helper.IContatoRepository;

public class ContatoRepositoryImpl implements IContatoRepository {

    @PersistenceContext
    private EntityManager manager;
    
    @Override
    public Contato findById(int id) {
		return this.manager.createQuery("from " + Contato.class.getName() + " gu where"
    			+ " gu.contatoId = :id ", Contato.class)
    			.setParameter("id", id)
    			.getSingleResult();
    }

    @Override
    public List<Contato> buscarTodos() {
		return this.manager.createQuery("from " + Contato.class.getName(), Contato.class)
                .getResultList();
    	
    }

	@Override
	public List<Contato> buscarPorNome(String nome) {
		return this.manager.createQuery("from " + Contato.class.getName() + " gu where gu.nome = :nome ", Contato.class)
    			.setParameter("nome", nome)
    			.getResultList();
	}

	@Override
	public List<Contato> buscarPorEmail(String email) {
		return this.manager.createQuery("from " + Contato.class.getName() + " gu where gu.email = :email ", Contato.class)
    			.setParameter("email", email)
    			.getResultList();
	}
}