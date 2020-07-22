package com.sistema.pache.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
*
* @author Gabriel
*/

@Entity
public class Historico implements Serializable{

private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int historicoId;
	private String tabela;
	private int id;
	@DateTimeFormat(pattern="dd/MM/yyyy'T'hh:mm")
    @Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;
	private String ultimoValor;
	private boolean deletado = false;
	private boolean atualizado = false;
	@ManyToOne
	@JoinColumn(name = "usuarioId")
	private Usuario usuario;
	
	public Historico(String tabela, int id, String ultimoValor, boolean deletado, boolean atualizado, Usuario usuario) {
		super();
		this.tabela = tabela;
		this.id = id;
		this.dataCadastro = new Date();
		this.ultimoValor = ultimoValor;
		this.deletado = deletado;
		this.atualizado = atualizado;
		this.usuario = usuario;
	}
	public int getHistoricoId() {
		return historicoId;
	}
	public void setHistoricoId(int historicoId) {
		this.historicoId = historicoId;
	}
	public String getTabela() {
		return tabela;
	}
	public void setTabela(String tabela) {
		this.tabela = tabela;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public String getUltimoValor() {
		return ultimoValor;
	}
	public void setUltimoValor(String ultimoValor) {
		this.ultimoValor = ultimoValor;
	}
	public JsonObject getUltimoValorJson() {
		return new Gson().fromJson(ultimoValor, JsonObject.class);
	}
	public void setUltimoValorJson(JsonObject ultimoValorJson) {
		this.ultimoValor = ultimoValorJson.toString();
	}
	public boolean isDeletado() {
		return deletado;
	}
	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}
	public boolean isAtualizado() {
		return atualizado;
	}
	public void setAtualizado(boolean atualizado) {
		this.atualizado = atualizado;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
}
