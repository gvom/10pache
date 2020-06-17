package com.sistema.pache.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.sistema.pache.model.Cliente;
import com.sistema.pache.model.Usuario;
import com.sistema.pache.repository.helper.IClienteRepository;

public class ClienteRepositoryImpl implements IClienteRepository {

    @PersistenceContext
    private EntityManager manager;
    
    @Autowired
    private HttpSession session;
    
    @Override
    public Cliente findById(int id) {
    	Usuario usr = (Usuario) session.getAttribute("usrLogado");
    	
    	String term = "";
    	if(usr.getTipoUsuario() != 0) {
    		term = "and u.usuarioId = :usuarioId";
    		return this.manager.createQuery("from " + Cliente.class.getName() + " gu join fetch gu.usuario u where"
        			+ " gu.clienteId = :id " + term, Cliente.class)
        			.setParameter("id", id)
        			.setParameter("usuarioId", usr.getUsuarioId())
        			.getSingleResult();
    	}else {
    		return this.manager.createQuery("from " + Cliente.class.getName() + " gu join fetch gu.usuario u where"
        			+ " gu.clienteId = :id " + term, Cliente.class)
        			.setParameter("id", id)
        			.getSingleResult();
    	}
    }

    @Override
    public List<Cliente> buscarTodos() {
    	Usuario usr = (Usuario) session.getAttribute("usrLogado");
    	
    	String term = "";
    	if(usr.getTipoUsuario() != 0) {
    		term = " where u.usuarioId = :usuarioId";
    		return this.manager.createQuery("from " + Cliente.class.getName() + " gu join fetch gu.usuario u "
            		+ "" + term , Cliente.class)
            		.setParameter("usuarioId", usr.getUsuarioId())
                    .getResultList();
    	}else {
    		return this.manager.createQuery("from " + Cliente.class.getName() + " gu join fetch gu.usuario u "
            		+ "" + term , Cliente.class)
                    .getResultList();
    	}
    }

    @Override
    public Cliente buscarPorCpf(String cpf) {
    	Usuario usr = (Usuario) session.getAttribute("usrLogado");
    	
    	String term = "";
    	if(usr.getTipoUsuario() != 0) {
    		term = "and u.usuarioId = :usuarioId";
    		return this.manager.createQuery("from " + Cliente.class.getName() + " gu join fetch gu.usuario u "
        			+ "where gu.cpf = :cpf " + term, Cliente.class)
        			.setParameter("cpf", cpf)
        			.setParameter("usuarioId", usr.getUsuarioId())
        			.getSingleResult();
    	}else {
    		return this.manager.createQuery("from " + Cliente.class.getName() + " gu join fetch gu.usuario u "
        			+ "where gu.cpf = :cpf " + term, Cliente.class)
        			.setParameter("cpf", cpf)
        			.getSingleResult();
    	}
    }
}
