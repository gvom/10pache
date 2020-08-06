package com.sistema.pache.service;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sistema.pache.model.Recibo;
import com.sistema.pache.model.Usuario;
import com.sistema.pache.repository.ReciboRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class ReciboService {

	@Autowired
	private ReciboRepository repository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
    private ServletContext servletContext;
	
	 @Autowired
	 private ResourceLoader resourceLoader;

	@Transactional(propagation = Propagation.NESTED)
	public void salvar(Recibo contrato) {		
		this.repository.save(contrato);
	}

	@Transactional
	public void atualizar(Recibo contrato) {
		this.repository.save(contrato);
	}

	@Transactional
	public void remover(Recibo contrato) {
		this.repository.delete(contrato);
	}

	@Transactional
	public List<Recibo> buscarTodos() {
		return this.repository.buscarTodos();
	}

	@Transactional
	public Recibo buscarPorId(int id) {
		return this.repository.findById(id);
	}
	
	@Transactional
	public List<Recibo> buscarPorReciboEmProcesso() {
		return this.repository.buscarPorReciboEmProcesso();
	}
	
	@Transactional
	public List<Recibo> buscarRecibosAgendadosPorData(Date data) {
		return this.repository.buscarRecibosAgendadosPorData(data);
	}
	
	@Transactional
    public String imprimirRecibo(HttpServletResponse response, int idRecibo) throws IOException, PrinterException, JRException {
    	
    	Date dataAtual = new Date();
    	
    	Recibo recibo = this.buscarPorId(idRecibo);
    	Usuario user = usuarioService.buscarPorId(recibo.getUsuario().getUsuarioId());
    	
    	String nome = recibo.getCliente().getNome();
    	String endereco = recibo.getCliente().getEndereco().getLogradouro() + ", " + recibo.getCliente().getEndereco().getBairro() + ", Nº " + recibo.getCliente().getEndereco().getNumero() ;
    	String enderecoContinuacao = "";
    	String nomeContinuacao = "";
    	
    	if(endereco.length() > 45) {
    		int count = 0;
    		for(int i = 0; i < endereco.length(); i++) {    
                if(endereco.charAt(i) == ' ' && i < 45) {
                	count = i;
                }   
            }
    		endereco = endereco.substring(0, count);
    		enderecoContinuacao = endereco.substring(count);
    	}
    	
    	if(nome.length() > 37) {
    		int count = 0;
    		for(int i = 0; i < nome.length(); i++) {    
                if(nome.charAt(i) == ' ' && i < 37) {
                	count = i;
                }   
            }
    		nome = nome.substring(0, count);
    		nomeContinuacao = nome.substring(count);
    	}
    	
    	Locale locale = new Locale("pt", "BR");
    	DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
    	String dtString = dateFormat.format(dataAtual);
    	
    	String localdata = dtString + "," + user.getEndereco().getCidade() + ", " + user.getEndereco().getEstado() + ".";
    	
        Map<String,Object> params = new HashMap<>();
        params.put("valor", recibo.getValor());
        params.put("nome", nome);
        params.put("rg", recibo.getCliente().getRg());  
        params.put("cpf", recibo.getCliente().getCpf()); 
        params.put("endereco", endereco);
        params.put("enderecoContinuacao", enderecoContinuacao);
        params.put("nomeContinuacao", nomeContinuacao);
        params.put("localdata", localdata);
        
        String path = resourceLoader.getResource("classpath:/relatorios/malaDireta.jrxml").getURI().getPath();
        JasperReport jasperReport = JasperCompileManager.compileReport(path);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params);
        
        String destFileName = servletContext.getInitParameter("arquivos") + File.separator + "galeria";
        File dir = new File(destFileName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Date date = new Date();
        destFileName += File.separator + date.getTime() + ".pdf";
        
//	    response.setContentType("application/pdf");
//	    response.setHeader("Content-disposition", "attachment; filename=recibo.pdf");
//	    final OutputStream outStream = response.getOutputStream();
        
	    JasperExportManager.exportReportToPdfFile(jasperPrint, destFileName);
	    
	    return  File.separator + "imagens" +  File.separator + date.getTime() + ".pdf";
    }
	
	@Transactional
    public boolean removerPdf(String path){
                 
        File arquivo = new File(servletContext.getInitParameter("arquivos") + File.separator + "galeria" + path);
        if (arquivo.delete()) {
            return true;            
        } else {
        	return false;
        }
    }
}