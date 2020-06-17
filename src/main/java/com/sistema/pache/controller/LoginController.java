package com.sistema.pache.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sistema.pache.model.Usuario;

public class LoginController {

	@RequestMapping("/login")
    public String loginForm(Model model, @RequestParam(value="auth", required = false) String auth) {                 
		
		if(auth.equals("true")) {
            model.addAttribute("msgSession", "true");
    	}else {
            model.addAttribute("msgSession", "false");
    	}
		
    	model.addAttribute("usuario", new Usuario());
    	model.addAttribute("auth", auth);
    	
        return "login";
    }   
	
	@RequestMapping("/403")
	public String semPermissao(){
		return "403";
	}
}
