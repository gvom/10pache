/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.pache.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.sistema.pache.model.Usuario;

/**
 *
 * @author Gabriel
 */
public class LoginSistema extends User {

    private Usuario login;

    public LoginSistema(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getLogin(), usuario.getSenha(), authorities);
        this.login = usuario;
    }

    public Usuario getUsuario() {
        return login;
    }

}
