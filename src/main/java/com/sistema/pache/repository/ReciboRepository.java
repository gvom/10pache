package com.sistema.pache.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.pache.model.Recibo;
import com.sistema.pache.repository.helper.IReciboRepository;

/**
*
* @author Gabriel
*/

@Repository
public interface ReciboRepository extends JpaRepository<Recibo, Integer>, IReciboRepository{

}
