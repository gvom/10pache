package com.sistema.pache.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.pache.model.Endereco;
import com.sistema.pache.repository.helper.IEnderecoRepository;

/**
*
* @author Gabriel
*/

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer>, IEnderecoRepository{

}
