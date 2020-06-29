package com.sistema.pache.util;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;

import com.sistema.pache.model.Recibo;
import com.sistema.pache.model.Usuario;
import com.sistema.pache.service.ReciboService;

public class Sendmail
{
	@Autowired
    private HttpSession session;
	
	@Autowired
	private ReciboService reciboService;
	
    public void sendmail()
    {
    	Usuario usr = (Usuario) session.getAttribute("usrLogado");
    	
    	if(!usr.getEmail().equals("")) {
	        HtmlEmail email = new HtmlEmail();
	        email.setSSLOnConnect(true);
	        // Aqui está o nome do servidor de envio
	        email.setHostName("smtp.office365.com");
	        email.setAuthenticator(new DefaultAuthenticator("nao-responder@10pache.com.br", "senha"));
	        email.setSSLOnConnect(false);
	        email.setTLS(true);
	        email.setSmtpPort(587);
	        try
	        {
	            email.setFrom("nao-responder@10pache.com.br", "Agenda do Dia Seguinte - 10PACHE");
	            email.setDebug(false);
	            email.setSubject("Agenda do Dia Seguinte - 10PACHE");
	            email.setCharset("utf-8");
	            StringBuilder builder = new StringBuilder();
	            List<Recibo> lstDia = reciboService.buscarRecibosAgendadosPorData(new Date());
	            if(lstDia.size() > 0) {
	            	String corpoEmail = 
	            			"<p style=\"text-align: center;\">Agenda do Dia Seguinte - 10PACHE</p>\r\n" + 
		            		"<p style=\"text-align: left;\">Ol&aacute;,</p>\r\n" + 
		            		"<p style=\"text-align: left;\">Segue abaixo sua agenda de tarefas agendadas para amanh&atilde; at&eacute; o momento:</p>\r\n" + 
		            		"<ul>\r\n"; 
		            for(Recibo i : lstDia) {
		            	String marcacao = "";
		            	if(i.getStatus() == 1) {
		            		marcacao = "Visturia Agendada";
		            	}else {
		            		marcacao = "Entrega Agendada";
		            	}
		            	corpoEmail += "<li style=\"text-align: left;\">" + marcacao + " - Veiculo de placa: " + i.getPlaca() + " do Cliente: " + i.getCliente().getNome() + " de Telefone: " + i.getCliente().getTelefone() + "</li>\r\n";
		            }
	            	
		            corpoEmail += "</ul>\r\n" + 
		            		"<p>Tenha uma ótima noite!</p>\r\n" + 
		            		"<p>Equipe 10PACHE.</p>";
	            	
		            builder.append(corpoEmail);
	            }else {
	            	builder.append(
	            			"<p style=\"text-align: center;\">Agenda do Dia Seguinte - 10PACHE</p>\r\n" + 
	            			"<p style=\"text-align: left;\">Ol&aacute;,</p>\r\n" + 
	            			"<p style=\"text-align: left;\">Voc&ecirc; n&atilde;o possui tarefas agendadas para amanh&atilde; at&eacute; o momento.</p>\r\n" + 
	            			"<p>Tenha uma ótima noite!</p>\r\n" + 
	            			"<p>Equipe 10PACHE.</p>"
		            );
	            }
	            email.setHtmlMsg(builder.toString());
	            email.addTo(usr.getEmail());
	            email.send();
	        }
	        catch(EmailException e)
	        {
	            //e.printStackTrace();
	            //System.out.println(e.getMessage());
	        }
    	}
    }
}