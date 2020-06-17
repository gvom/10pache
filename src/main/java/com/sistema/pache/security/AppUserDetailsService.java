package com.sistema.pache.security;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sistema.pache.model.Usuario;
import com.sistema.pache.repository.UsuarioRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository logins;
    
    @Autowired
	private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String l) throws UsernameNotFoundException {
        Usuario usuario = logins.findByLogin(l);

        if(usuario.getTeste() == 0 && usuario.getStatus() != 1) { 
        	Date dt = usuario.getDtCadastro();
        	Calendar c = Calendar.getInstance(); 
        	c.setTime(dt); 
        	c.add(Calendar.DATE, 7);
        	dt = c.getTime();
        	Date now = new Date();
        	if(dt.after(now)) {
        		usuario.setStatus(1);
        	}
        }
  		if (usuario == null || usuario.getStatus() == 1) {
  			throw new UsernameNotFoundException(l);
  		}
  		if(l.equals("super")) {
  			usuario.setTipoUsuario(0);
  		}
  		request.getSession().setAttribute("usrLogado", usuario);
  	 		
  		return new LoginSistema(usuario, getPermissoes(usuario));
    }
    
    public static void main(String[] args){
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		//System.out.println("senha:" + b.encode("123"));
	}

    private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        //Editar aqui para implementar permissões de acesso!
        List<String> permissoes = new ArrayList<>();
        permissoes.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.toUpperCase())));
        return authorities;
    }

}
