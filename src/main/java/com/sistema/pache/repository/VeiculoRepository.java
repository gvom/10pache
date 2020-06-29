package com.sistema.pache.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.pache.model.Veiculo;
import com.sistema.pache.repository.helper.IVeiculoRepository;

/**
*
* @author Gabriel
*/

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Integer>, IVeiculoRepository{

}
