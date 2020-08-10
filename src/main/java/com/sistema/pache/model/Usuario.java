/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sistema.pache.model;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.springframework.format.annotation.DateTimeFormat;

/**
*
* @author Gabriel
*/

@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int usuarioId;
    private String login;
    private String senha;
    private String nome;
	private String passwordHash;
	private int tipoUsuario = 1;
	private int status = 0;
	@OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "idEndereco")
    private Endereco endereco;
	private short teste;
	@DateTimeFormat(pattern="dd/MM/yyyy")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	Date dtCadastro;
	private String cpf;
	private String email;
	private String telefone;
	private short comfirmeLead;
	private String plano;

    public Date getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(int tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public short getTeste() {
		return teste;
	}

	public void setTeste(short teste) {
		this.teste = teste;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public short getComfirmeLead() {
		return comfirmeLead;
	}

	public void setComfirmeLead(short comfirmeLead) {
		this.comfirmeLead = comfirmeLead;
	}

	public String getPlano() {
		return plano;
	}

	public void setPlano(String plano) {
		this.plano = plano;
	}
	
	public int getDias() {
		Date dt = this.dtCadastro;
    	Calendar c = Calendar.getInstance(); 
    	c.setTime(dt); 
    	c.add(Calendar.DATE, 7);
    	dt = c.getTime();
    	Date now = new Date();
    	LocalDate prox = Instant.ofEpochMilli(dt.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    	LocalDate ago = Instant.ofEpochMilli(now.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    	Period period = Period.between(ago, prox);
    	
		return period.getDays();
	}

}
