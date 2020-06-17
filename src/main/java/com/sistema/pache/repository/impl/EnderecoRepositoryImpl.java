package com.sistema.pache.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.sistema.pache.model.Endereco;
import com.sistema.pache.repository.helper.IEnderecoRepository;

public class EnderecoRepositoryImpl implements IEnderecoRepository {

    @PersistenceContext
    private EntityManager manager;
    @Override
    public Endereco findById(int id) {
    	return this.manager.createQuery("from " + Endereco.class.getName() + " gu where gu.idEndereco = :id", Endereco.class)
    			.setParameter("id", id).getSingleResult();
        
    }

    @Override
    public List<Endereco> buscarTodos() {
        return this.manager.createQuery("from " + Endereco.class.getName(), Endereco.class)
                .getResultList();
    }


}
