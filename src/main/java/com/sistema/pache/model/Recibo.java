package com.sistema.pache.model;

import java.io.Serializable;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ManyToAny;
import org.springframework.format.annotation.DateTimeFormat;

/**
*
* @author Gabriel
*/

@Entity
public class Recibo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int reciboId;
	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "clienteId")
    private Cliente cliente;
	@DateTimeFormat(pattern="yyyy-MM-dd'T'hh:mm")
    @Temporal(TemporalType.TIMESTAMP)
	private Date dataVisturia;
	@DateTimeFormat(pattern="yyyy-MM-dd'T'hh:mm")
    @Temporal(TemporalType.TIMESTAMP)
	private Date dataRecibo;
	@DateTimeFormat(pattern="yyyy-MM-dd'T'hh:mm")
    @Temporal(TemporalType.TIMESTAMP)
	private Date dataEntrega;
	@DateTimeFormat(pattern="dd/MM/yyyy")
    @Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;
	private String dataEntregaString;
	private String dataVisturiaString;
	private String localEmissao;
	private String valor;
	private int via;
	private String renavam;
	private String rntrc;
	private String anterior;
	private String placa;
	private String chassi;
	private String antUf;
	private String combustivel;
	private String especie;
	private String marca;
	private int anoFab;
	private int anoMod;
	private String capPotCil;
	private String categoria;
	private String cor;
	private String observacao;
	private int status = 0;
	@ManyToOne
	@JoinColumn(name = "usuarioId")
	private Usuario usuario;
	
	public int diferencaDias(Date date) {
		int dias = 0;
		if(date.after(this.dataRecibo)) {
			LocalDate dateBefore = this.dataRecibo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate dateAfter = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			dias = (int) ChronoUnit.DAYS.between(dateBefore, dateAfter);
		}else {
			LocalDate dateBefore = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate dateAfter = this.dataRecibo.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			dias = (int) ChronoUnit.DAYS.between(dateBefore, dateAfter);
		}
		return dias;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public int getReciboId() {
		return reciboId;
	}
	public void setReciboId(int reciboId) {
		this.reciboId = reciboId;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public String getLocalEmissao() {
		return localEmissao;
	}
	public void setLocalEmissao(String localEmissao) {
		this.localEmissao = localEmissao;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public int getVia() {
		return via;
	}
	public void setVia(int via) {
		this.via = via;
	}
	public String getRenavam() {
		return renavam;
	}
	public void setRenavam(String renavam) {
		this.renavam = renavam;
	}
	public String getRntrc() {
		return rntrc;
	}
	public void setRntrc(String rntrc) {
		this.rntrc = rntrc;
	}
	public String getAnterior() {
		return anterior;
	}
	public void setAnterior(String anterior) {
		this.anterior = anterior;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getChassi() {
		return chassi;
	}
	public void setChassi(String chassi) {
		this.chassi = chassi;
	}
	public String getAntUf() {
		return antUf;
	}
	public void setAntUf(String antUf) {
		this.antUf = antUf;
	}
	public String getCombustivel() {
		return combustivel;
	}
	public void setCombustivel(String combustivel) {
		this.combustivel = combustivel;
	}
	public String getEspecie() {
		return especie;
	}
	public void setEspecie(String especie) {
		this.especie = especie;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public int getAnoFab() {
		return anoFab;
	}
	public void setAnoFab(int anoFab) {
		this.anoFab = anoFab;
	}
	public int getAnoMod() {
		return anoMod;
	}
	public void setAnoMod(int anoMod) {
		this.anoMod = anoMod;
	}
	public String getCapPotCil() {
		return capPotCil;
	}
	public void setCapPotCil(String capPotCil) {
		this.capPotCil = capPotCil;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getDataVisturia() {
		return dataVisturia;
	}
	public void setDataVisturia(Date dataVisturia) {
		this.dataVisturia = dataVisturia;
	}
	public Date getDataRecibo() {
		return dataRecibo;
	}
	public void setDataRecibo(Date dataRecibo) {
		this.dataRecibo = dataRecibo;
	}
	public Date getDataEntrega() {
		return dataEntrega;
	}
	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public String getDataEntregaString() {
		return dataEntregaString;
	}

	public void setDataEntregaString(String dataEntregaString) {
		this.dataEntregaString = dataEntregaString;
	}

	public String getDataVisturiaString() {
		return dataVisturiaString;
	}

	public void setDataVisturiaString(String dataVisturiaString) {
		this.dataVisturiaString = dataVisturiaString;
	}
	
}
