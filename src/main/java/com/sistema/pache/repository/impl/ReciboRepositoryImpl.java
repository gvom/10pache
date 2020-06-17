package com.sistema.pache.repository.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.sistema.pache.model.Recibo;
import com.sistema.pache.model.Usuario;
import com.sistema.pache.repository.helper.IReciboRepository;

public class ReciboRepositoryImpl implements IReciboRepository {

    @PersistenceContext
    private EntityManager manager;
    
    @Autowired
    private HttpSession session;
    
    @Override
    public Recibo findById(int id) {
    	Usuario usr = (Usuario) session.getAttribute("usrLogado");
    	
    	String term = "";
    	if(usr.getTipoUsuario() != 0) {
    		term = "and u.usuarioId = :usuarioId";
    		return this.manager.createQuery("from " + Recibo.class.getName() + " gu join fetch gu.usuario u "
        			+ "where gu.reciboId = :id " + term, Recibo.class)
        			.setParameter("id", id)
        			.setParameter("usuarioId", usr.getUsuarioId())
        			.getSingleResult();
    	}else {
    		return this.manager.createQuery("from " + Recibo.class.getName() + " gu join fetch gu.usuario u "
        			+ "where gu.reciboId = :id " + term, Recibo.class)
        			.setParameter("id", id)
        			.getSingleResult();
    	} 
    }

    @Override
    public List<Recibo> buscarTodos() {
    	Usuario usr = (Usuario) session.getAttribute("usrLogado");
    	
    	String term = "";
    	if(usr.getTipoUsuario() != 0) {
    		term = " where u.usuarioId = :usuarioId";
    		return this.manager.createQuery("from " + Recibo.class.getName()  + " gu join fetch gu.usuario u "
            		+ "" + term, Recibo.class)
            		.setParameter("usuarioId", usr.getUsuarioId())
                    .getResultList();
    	}else {
    		return this.manager.createQuery("from " + Recibo.class.getName()  + " gu join fetch gu.usuario u "
            		+ "" + term, Recibo.class)
                    .getResultList();
    	}
    }

	@Override
	public List<Recibo> buscarPorDataStatus(Date data) {
		Usuario usr = (Usuario) session.getAttribute("usrLogado");
		
		String term = "";
    	if(usr.getTipoUsuario() != 0) {
    		term = "and u.usuarioId = :usuarioId";
    		return this.manager.createQuery("from " + Recibo.class.getName() + " gu join fetch gu.usuario u "
    				+ "where gu.status != 5 and (gu.data < :data or gu.data is null) " + term + " order by gu.data", Recibo.class)
    				.setParameter("data", data)
    				.setParameter("usuarioId", usr.getUsuarioId())
    				.getResultList();
    	}else {
    		return this.manager.createQuery("from " + Recibo.class.getName() + " gu join fetch gu.usuario u "
    				+ "where gu.status != 5 and (gu.data < :data or gu.data is null) " + term + " order by gu.data", Recibo.class)
    				.setParameter("data", data)
    				.getResultList();
    	}
	}

}
