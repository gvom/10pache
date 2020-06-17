package com.sistema.pache.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.pache.model.Arquivo;
import com.sistema.pache.repository.helper.IArquivoRepository;

/**
*
* @author Gabriel
*/

@Repository
public interface ArquivoRepository extends JpaRepository<Arquivo, Integer>, IArquivoRepository{

}
