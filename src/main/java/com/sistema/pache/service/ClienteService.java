package com.sistema.pache.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.pache.model.Cliente;
import com.sistema.pache.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
    private HistoricoService historicoService;

	@Transactional(propagation = Propagation.NESTED)
	public void salvar(Cliente cliente) {		
		this.repository.save(cliente);
	}

	@Transactional
	public void atualizar(Cliente cliente) {
		this.repository.save(cliente);
	}

	@Transactional
	public void remover(Cliente cliente) {
		historicoService.criarHistorico(cliente.getClass().getName(), cliente.getClienteId(), cliente, true);
		this.repository.delete(cliente);
	}

	@Transactional
	public List<Cliente> buscarTodos() {
		return this.repository.buscarTodos();
	}

	@Transactional
	public Cliente buscarPorId(int id) {
		return this.repository.findById(id);
	}
	
	@Transactional
	public Cliente buscarPorCpf(String cpf) {
		return this.repository.buscarPorCpf(cpf);
	}
	
	@Transactional
	public Cliente buscarPorCpfIdentidade(String cpf, String rg) {
		return this.buscarPorCpfIdentidade(cpf, rg);
	}
}
