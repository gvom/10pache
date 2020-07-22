package com.sistema.pache.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.pache.model.Endereco;
import com.sistema.pache.repository.EnderecoRepository;

@Service
public class EnderecoService {

	@Autowired
	private EnderecoRepository repository;
	
	@Autowired
    private HistoricoService historicoService;

	@Transactional(propagation = Propagation.NESTED)
	public void salvar(Endereco endereco) {		
		this.repository.save(endereco);
	}

	@Transactional
	public void atualizar(Endereco endereco) {
		historicoService.criarHistorico(endereco.getClass().getName(), endereco.getIdEndereco(), endereco, false);
		this.repository.save(endereco);
	}

	@Transactional
	public void remover(Endereco endereco) {
		historicoService.criarHistorico(endereco.getClass().getName(), endereco.getIdEndereco(), endereco, true);
		this.repository.delete(endereco);
	}

	@Transactional
	public List<Endereco> buscarTodos() {
		return this.repository.buscarTodos();
	}

	@Transactional
	public Endereco buscarPorId(int id) {
		return this.repository.findById(id);
	}
}
