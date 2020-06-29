package com.sistema.pache.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.sistema.pache.model.Veiculo;
import com.sistema.pache.model.Usuario;
import com.sistema.pache.repository.helper.IVeiculoRepository;

public class VeiculoRepositoryImpl implements IVeiculoRepository {

    @PersistenceContext
    private EntityManager manager;
    
    @Autowired
    private HttpSession session;

    @Override
    public Veiculo findById(int id) {
    	Usuario usr = (Usuario) session.getAttribute("usrLogado");
    	try {
	    	String term = "";
	    	if(usr.getTipoUsuario() != 0) {
	    		term = "and u.usuarioId = :usuarioId";
	    		return this.manager.createQuery("from " + Veiculo.class.getName() + " gu join fetch gu.usuario u "
	        			+ "where gu.veiculoId = :id " + term, Veiculo.class)
	        			.setParameter("id", id)
	        			.setParameter("usuarioId", usr.getUsuarioId())
	        			.getSingleResult();
	    	}else {
	    		return this.manager.createQuery("from " + Veiculo.class.getName() + " gu join fetch gu.usuario u "
	        			+ "where gu.veiculoId = :id " + term, Veiculo.class)
	        			.setParameter("id", id)
	        			.getSingleResult();
	    	}
    	} catch (Exception e) {
			return null;
		}
    }

    @Override
    public List<Veiculo> buscarTodos() {
    	Usuario usr = (Usuario) session.getAttribute("usrLogado");
    	
    	String term = "";
    	if(usr.getTipoUsuario() == 0) {
    		return this.manager.createQuery("from " + Veiculo.class.getName()  + " gu join fetch gu.usuario u "
            		+ "" + term, Veiculo.class)
                    .getResultList();
    	}else {
    		return new ArrayList<Veiculo>();
    	}
    }

	@Override
	public Veiculo buscarPorChassi(String chassi) {
		try {
			return this.manager.createQuery("from " + Veiculo.class.getName()  + " gu join fetch gu.usuario u "
	        		+ "where gu.chassi = :chassi", Veiculo.class)
					.setParameter("chassi", chassi)
	                .getSingleResult();
		} catch (Exception e) {
			return null;
		}
    	
	}
	
	@Override
	public Veiculo buscarUltimoCadastro() {
		try {
			return this.manager.createQuery("from " + Veiculo.class.getName()  + " gu join fetch gu.usuario u "
	        		+ "order by gu.veiculoId DESC", Veiculo.class)
					.setMaxResults(1)
	                .getSingleResult();
		} catch (Exception e) {
			return null;
		}
    	
	}
}
