package com.druo.restapi.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.druo.restapi.models.services.UsuarioServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter 
{

	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception 
	{
		auth.userDetailsService(usuarioService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception 
	{
		http.csrf().disable().authorizeRequests().antMatchers("/login").permitAll() //Permitimos el acceso a /login a cualquiera
		.anyRequest().authenticated() //Cualquier otra peticion requiere autenticacion
		.and() 
		//Las peticiones /login pasaran previamente por este filtro
		.addFilterBefore(new LoginFilter("/login", authenticationManager()), 
				UsernamePasswordAuthenticationFilter.class)
		//Las demas peticiones pasarán por este filtro para validar el token
		.addFilterBefore(new JwtFilter(),
				UsernamePasswordAuthenticationFilter.class);
	}
	
}
