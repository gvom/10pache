package com.sistema.pache.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.pache.model.Arquivo;
import com.sistema.pache.model.Cliente;
import com.sistema.pache.repository.helper.IArquivoRepository;
import com.sistema.pache.repository.helper.IClienteRepository;

/**
*
* @author Gabriel
*/

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>, IClienteRepository{

}
