package com.sistema.pache.controller;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sistema.pache.model.Usuario;
import com.sistema.pache.service.ReciboService;

@Controller
public class IndexController {
	
	@Autowired
	private ReciboService reciboService;
	
	@Autowired
    private HttpSession session;
	
	@Autowired
	private HttpServletRequest request;

    @RequestMapping("/index")
    public String index(Model model, @AuthenticationPrincipal User user) {
    	Usuario usr = (Usuario) session.getAttribute("usrLogado");
    	if(usr == null) {
    		Usuario usuario = new Usuario();
    		usuario.setTipoUsuario(0);
    		request.getSession().setAttribute("usrLogado", usuario);
    	}
    	
    	Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -7);
		Date dataVencimento = calendar.getTime();
    	
		model.addAttribute("dataVencimento", dataVencimento);
		model.addAttribute("dataVencimentoReal", new Date());
    	model.addAttribute("lstRecibos", reciboService.buscarPorReciboEmProcesso());
    	model.addAttribute("lstDiaria", reciboService.buscarRecibosAgendadosPorData(new Date()));
    	
    	return "admin/dashboard/dashboard";
    }

    @RequestMapping("/login")
    public String login(@AuthenticationPrincipal User user) {
        return "admin/login";
    }
    
    @RequestMapping("/")
    public String webpage() {
        return "website/singlepage";
    }
    
    @RequestMapping("/verificarlogin")
    public String verificarlogin(Model model, @AuthenticationPrincipal User user) {
        if (user != null) {
        	Calendar calendar = Calendar.getInstance();
    		calendar.add(Calendar.DAY_OF_MONTH, -7);
    		Date dataVencimento = calendar.getTime();
        	
    		model.addAttribute("dataVencimento", dataVencimento);
    		model.addAttribute("dataVencimentoReal", new Date());
        	model.addAttribute("lstRecibos", reciboService.buscarPorReciboEmProcesso());
        	model.addAttribute("lstDiaria", reciboService.buscarRecibosAgendadosPorData(new Date()));
            return "redirect:dashboard/dashboard";
        }
        return "admin/login";
    }
}