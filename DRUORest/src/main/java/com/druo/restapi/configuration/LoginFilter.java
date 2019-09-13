package com.druo.restapi.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;


public class LoginFilter extends AbstractAuthenticationProcessingFilter 
{

	public LoginFilter(String url, AuthenticationManager authManager)
	{
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException 
	{
		InputStream body = request.getInputStream();
		UsuarioJWT user = new ObjectMapper().readValue(body, UsuarioJWT.class);
		
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getUsuario(), user.getContrasena(), Collections.emptyList()));
	}
	
	
	protected void successfullAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException
	{
		/* Si la autenticacion es exitosa, se agrega el token a la respuesta */ 
		JwtUtil.addAuthentication(response, auth.getName());
	}

}
