package com.sistema.pache.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.pache.model.Veiculo;
import com.sistema.pache.repository.VeiculoRepository;


@Service
public class VeiculoService {

	@Autowired
	private VeiculoRepository repository;

	@Transactional(propagation = Propagation.NESTED)
	public void salvar(Veiculo veiculo) {		
		this.repository.save(veiculo);
	}

	@Transactional
	public void atualizar(Veiculo veiculo) {
		this.repository.save(veiculo);
	}

	@Transactional
	public void remover(Veiculo veiculo) {
		this.repository.delete(veiculo);
	}

	@Transactional
	public List<Veiculo> buscarTodos() {
		return this.repository.buscarTodos();
	}

	@Transactional
	public Veiculo buscarPorId(int id) {
		return this.repository.findById(id);
	}
	
	@Transactional
	public Veiculo buscarPorChassi(String chassi) {
		return this.repository.buscarPorChassi(chassi);
	}
	
	@Transactional
	public Veiculo buscarUltimoCadastro() {
		return this.repository.buscarUltimoCadastro();
	}
}
