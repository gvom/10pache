package com.sistema.pache.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.pache.model.Arquivo;
import com.sistema.pache.repository.ArquivoRepository;

@Service
public class ArquivoService {
	
	@Autowired
	private ArquivoRepository repository;

	@Transactional(propagation = Propagation.NESTED)
	public void salvar(Arquivo arquivo) {		
		this.repository.save(arquivo);
	}

	@Transactional
	public void atualizar(Arquivo arquivo) {
		this.repository.save(arquivo);
	}

	@Transactional
	public void remover(Arquivo arquivo) {
		this.repository.delete(arquivo);
	}

	@Transactional
	public List<Arquivo> buscarTodos() {
		return this.repository.buscarTodos();
	}

	@Transactional
	public Arquivo buscarPorId(int id) {
		return this.repository.findById(id);
	}
	
	@Transactional
	public List<Arquivo> buscarPorContrato(int id){
		return this.repository.buscarPorContrato(id);
	}
}