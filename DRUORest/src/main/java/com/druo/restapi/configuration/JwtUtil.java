package com.druo.restapi.configuration;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil 
{
	/* Método para crear jwt y enviarlo al cliente en el header de la respuesta */
	static void addAuthentication(HttpServletResponse response, String username)
	{
		
		String token = Jwts.builder().setSubject(username)
				
				
				/* Hash con el que se firma la clave */ 
				.signWith(SignatureAlgorithm.HS512, "dru@").compact();
		
		/* Se agrega el encabezado del Token */
		response.addHeader("Authorization", "Bearer " + token);
	}
	
	static Authentication getAuthentication(HttpServletRequest request)
	{
		/* Obtenemos el token que viene en el encabezado de la peticion*/
		String token = request.getHeader("Authorization");
		
		/* Si existe algún token se valida*/
		if(token != null)
		{
			String user = Jwts.parser().setSigningKey("dru@").parseClaimsJws(token.replace("Bearer", ""))// este metodo es quien valida
					.getBody().getSubject();
			
			/* Las peticiones que no sea /login no requieren una autenticacion por username y password, por eso mismo se devuelve un UsernamePasswordAuthenticationToken sin password */
			return user != null ? new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()): null;
		}
		return null;
	}

}
