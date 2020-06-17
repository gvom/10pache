package com.sistema.pache.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

/**
*
* @author Gabriel
*/

@Entity
public class Arquivo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int arquivoId;
//	@ManyToOne
//    @JoinColumn(name = "contratoId")
//    private Contrato contrato;
	private String path;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date dataUploud;
	private String nomeArquivo;
	
	public int getArquivoId() {
		return arquivoId;
	}
	public void setArquivoId(int arquivoId) {
		this.arquivoId = arquivoId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Date getDataUploud() {
		return dataUploud;
	}
	public void setDataUploud(Date dataUploud) {
		this.dataUploud = dataUploud;
	}
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
}
