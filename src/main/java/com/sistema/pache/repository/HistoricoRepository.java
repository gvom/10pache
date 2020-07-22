package com.sistema.pache.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.pache.model.Historico;
import com.sistema.pache.repository.helper.IHistoricoRepository;

/**
*
* @author Gabriel
*/

@Repository
public interface HistoricoRepository extends JpaRepository<Historico, Integer>, IHistoricoRepository{

}
