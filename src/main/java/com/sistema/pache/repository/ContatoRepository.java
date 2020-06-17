package com.sistema.pache.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.pache.model.Contato;
import com.sistema.pache.repository.helper.IContatoRepository;

/**
*
* @author Gabriel
*/

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Integer>, IContatoRepository{

}