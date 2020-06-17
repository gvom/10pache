package com.sistema.pache.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

/**
*
* @author Gabriel
*/

@Entity
public class Contato implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int contatoId;
	private String nome;
	private String email;
	@Column(columnDefinition = "TEXT", length = 600)
	private String mensagem;
	@DateTimeFormat(pattern="dd/MM/yyyy")
    @Temporal(TemporalType.TIMESTAMP)
	private Date dtMensagem;
	private boolean lida = false;	
	
	public int getContatoId() {
		return contatoId;
	}
	public Date getDtMensagem() {
		return dtMensagem;
	}
	public void setDtMensagem(Date dtMensagem) {
		this.dtMensagem = dtMensagem;
	}
	public void setContatoId(int contatoId) {
		this.contatoId = contatoId;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public boolean isLida() {
		return lida;
	}
	public void setLida(boolean lida) {
		this.lida = lida;
	}

}
