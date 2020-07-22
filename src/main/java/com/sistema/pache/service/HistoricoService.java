package com.sistema.pache.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.sistema.pache.model.Historico;
import com.sistema.pache.model.Usuario;
import com.sistema.pache.repository.HistoricoRepository;

@Service
public class HistoricoService {

	@Autowired
	private HistoricoRepository repository;
	
	@Autowired
    private HttpSession session;

	@Transactional(propagation = Propagation.NESTED)
	public void salvar(Historico historico) {		
		this.repository.save(historico);
	}

	@Transactional
	public void atualizar(Historico historico) {
		this.repository.save(historico);
	}

	@Transactional
	public void remover(Historico historico) {
		this.repository.delete(historico);
	}

	@Transactional
	public List<Historico> buscarTodos() {
		return this.repository.buscarTodos();
	}

	@Transactional
	public Historico buscarPorId(int id) {
		return this.repository.findById(id);
	}
	
	@Transactional(propagation = Propagation.NESTED)
	public void criarHistorico(String tabela, int id, Object object, boolean deletado) {
		boolean atualizado = false;
		if(!deletado) {
			atualizado = true;
		}
		Gson gson = new Gson();
		String ultimoValor = gson.toJson(object);
		Usuario usuario = (Usuario) session.getAttribute("usrLogado");
		Historico historico = new Historico(tabela, id, ultimoValor, deletado, atualizado, usuario);
		this.repository.save(historico);
	}
}
