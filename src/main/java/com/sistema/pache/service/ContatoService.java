package com.sistema.pache.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.pache.model.Contato;
import com.sistema.pache.repository.ContatoRepository;

@Service
public class ContatoService {

	@Autowired
	private ContatoRepository repository;

	@Transactional(propagation = Propagation.NESTED)
	public void salvar(Contato contrato) {		
		this.repository.save(contrato);
	}

	@Transactional
	public void atualizar(Contato contrato) {
		this.repository.save(contrato);
	}

	@Transactional
	public void remover(Contato contrato) {
		this.repository.delete(contrato);
	}

	@Transactional
	public List<Contato> buscarTodos() {
		return this.repository.buscarTodos();
	}

	@Transactional
	public Contato buscarPorId(int id) {
		return this.repository.findById(id);
	}
	
	@Transactional
	public List<Contato> buscarPorNome(String nome) {
		return this.repository.buscarPorNome(nome);
	}
	
	@Transactional
	public List<Contato> buscarPorEmail(String email) {
		return this.repository.buscarPorEmail(email);
	}
}
