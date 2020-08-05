package com.sistema.pache.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@ComponentScan("com.digital.gerenciacontratos.security")
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
		auth.inMemoryAuthentication()
        .withUser("super").password(encoder().encode("Super#99108-4356")).roles("ADMIN");
//        .and()
//        .withUser("user2").password(encoder().encode("user2")).roles("USER")
//        .and()
//        .withUser("admin").password(encoder().encode("admin")).roles("ADMIN");
//		System.out.println(encoder().encode("senha"));
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.headers()
			.frameOptions()
			.sameOrigin()
			.and()
		.csrf()
			.disable()
        .authorizeRequests()
	        .antMatchers("/admin/**").hasRole("ADMIN")
	        .antMatchers("/anonymous*").anonymous()
	        .antMatchers("/login*").permitAll()
	        .antMatchers("/testeGratis/**").permitAll()
	        .antMatchers("/webpage/**").permitAll()
	        .antMatchers("/webfonts/**").permitAll()
	        .antMatchers("/policasPrivacidade/**").permitAll()
	        .antMatchers("/css/**").permitAll()
			.antMatchers("/passrecovery/**").permitAll()
			.antMatchers("/js/**").permitAll()
			.antMatchers("/fonts/**").permitAll()
			.antMatchers("/imgs/**").permitAll()
			.antMatchers("/temp/**").permitAll()
			.antMatchers("/bootstrap/**").permitAll()	
			.antMatchers("/font-awesome/**").permitAll()	
			.antMatchers("/dist/**").permitAll()	
			.antMatchers("/fav/**").permitAll()	
			.antMatchers("/plugins/**").permitAll()
			.antMatchers("/").permitAll()
			.antMatchers("/arquivos/**").permitAll()
	        .anyRequest().authenticated()
        .and()
        .formLogin()
	        .loginPage("/login").permitAll()
	        .loginProcessingUrl("/verificarlogin")
	        .defaultSuccessUrl("/index", true)
	        .failureUrl("/login?auth=false")
        .and()
		.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.logoutSuccessUrl("/login")				
		.and()
		.rememberMe()
		.and()
		.exceptionHandling()
			.accessDeniedPage("/403")
			.and()
		.sessionManagement()
			.invalidSessionUrl("/login?auth=true")
			.maximumSessions(1)
			.maxSessionsPreventsLogin(false)
			.expiredUrl("/login")
			.sessionRegistry(sessionRegistry());
		
		
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
            .antMatchers("/resources/static/**"); // #3
    }
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
		   
	@Bean
	public SessionRegistry sessionRegistry() {
		SessionRegistry sessionRegistry = new SessionRegistryImpl();
		return sessionRegistry;
	}
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}
}
