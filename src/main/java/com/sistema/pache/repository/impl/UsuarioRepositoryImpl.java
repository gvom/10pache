/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.pache.repository.impl;

import com.sistema.pache.model.Usuario;
import com.sistema.pache.repository.helper.IUsuarioRepository;

import javax.persistence.PersistenceContext;
import java.util.List;
import javax.persistence.EntityManager;

public class UsuarioRepositoryImpl implements IUsuarioRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Usuario findByLogin(String l) {
        try {
            return this.manager.createQuery("from " + Usuario.class.getName() + " u where u.login = :login", Usuario.class)
                    .setParameter("login", l)
                    .getSingleResult();
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }

    }

    @Override
    public Usuario findById(int id) {
    	return this.manager.createQuery("from " + Usuario.class.getName() + " gu where gu.usuarioId = :id", Usuario.class)
    			.setParameter("id", id).getSingleResult();
        
    }

    @Override
    public List<Usuario> buscarPorParametroLogin(String param) {
        return this.manager.createQuery("from " + Usuario.class.getName() + " gu where gu.login like :param", Usuario.class)
                .setParameter("param", "%" + param)
                .getResultList();
    }

    @Override
    public void alterarSenha(int user_id, String senha) {

        this.manager.createNativeQuery("SET @salt = UNHEX(SHA2(uuid(), 256));")
                .executeUpdate();

        this.manager.createNativeQuery("UPDATE "+ Usuario.class.getName() +" SET senha = @salt, password_hash = UNHEX(SHA2(CONCAT(?, HEX(@salt)), 256)) WHERE (usuario_id = ?);")
                .setParameter(1, senha)
                .setParameter(2, user_id)
                .executeUpdate();

    }

    @Override
    public List<Usuario> buscarTodos() {
        return this.manager.createQuery("from " + Usuario.class.getName(), Usuario.class)
                .getResultList();
    }

	@Override
	public List<Usuario> buscarPorParametroCpfEmail(String cpf, String email) {
		return this.manager.createQuery("from " + Usuario.class.getName() + " gu where gu.cpf = :cpf or gu.email = :email", Usuario.class)
                .setParameter("cpf", cpf)
                .setParameter("email", email)
                .getResultList();
	}

	@Override
	public List<Usuario> buscarPorTesteGratis(short s) {
		return this.manager.createQuery("from " + Usuario.class.getName() + " gu where gu.teste = :s", Usuario.class)
    			.setParameter("s", s).getResultList();
	}

}
