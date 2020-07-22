package com.sistema.pache.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.sistema.pache.model.Historico;
import com.sistema.pache.model.Usuario;
import com.sistema.pache.repository.helper.IHistoricoRepository;
import com.sistema.pache.repository.helper.IReciboRepository;

public class HistoricoRepositoryImpl implements IHistoricoRepository{

	@PersistenceContext
    private EntityManager manager;
    
    @Autowired
    private HttpSession session;
    
    @Override
    public Historico findById(int id) {
    	Usuario usr = (Usuario) session.getAttribute("usrLogado");
    	
    	String term = "";
    	if(usr.getTipoUsuario() != 0) {
    		term = "and u.usuarioId = :usuarioId";
    		return this.manager.createQuery("from " + Historico.class.getName() + " gu join fetch gu.usuario u "
        			+ "where gu.historicoId = :id " + term, Historico.class)
        			.setParameter("id", id)
        			.setParameter("usuarioId", usr.getUsuarioId())
        			.getSingleResult();
    	}else {
    		return this.manager.createQuery("from " + Historico.class.getName() + " gu join fetch gu.usuario u "
        			+ "where gu.historicoId = :id " + term, Historico.class)
        			.setParameter("id", id)
        			.getSingleResult();
    	} 
    }

    @Override
    public List<Historico> buscarTodos() {
    	Usuario usr = (Usuario) session.getAttribute("usrLogado");
    	
    	String term = "";
    	if(usr.getTipoUsuario() != 0) {
    		term = " where u.usuarioId = :usuarioId";
    		return this.manager.createQuery("from " + Historico.class.getName()  + " gu join fetch gu.usuario u "
            		+ "" + term, Historico.class)
            		.setParameter("usuarioId", usr.getUsuarioId())
                    .getResultList();
    	}else {
    		return this.manager.createQuery("from " + Historico.class.getName()  + " gu join fetch gu.usuario u "
            		+ "" + term, Historico.class)
                    .getResultList();
    	}
    }
}
