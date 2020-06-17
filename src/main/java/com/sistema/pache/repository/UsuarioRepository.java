/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.pache.repository;

import com.sistema.pache.model.Usuario;
import com.sistema.pache.repository.helper.IUsuarioRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Léo
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>, IUsuarioRepository {

}
