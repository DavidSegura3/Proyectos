package com.druo.restapi.models.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.druo.restapi.models.entity.Usuario;
import com.druo.restapi.repository.GestorUsuario;

@Service()
public class UsuarioServiceImpl implements UserDetailsService
{
	@Autowired
	private GestorUsuario gestorUsuario;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		
		Usuario user = gestorUsuario.findByUsuario(username);
		return new User(user.getUsuario(), user.getContrasena(), user.isActivo(), user.isActivo(), user.isActivo(), user.isActivo(), buildgrante(user.getRol()));
	}
	
	public List<GrantedAuthority> buildgrante(byte rol)
	{
		String [] roles = {"ADMINISTRADOR", "ANALISTA", "COORDINADOR"};
		List<GrantedAuthority> auths = new ArrayList<>();
		
		for (int i = 0; i < rol; i++) 
		{
			auths.add(new SimpleGrantedAuthority(roles[i]));
		}
		
		return auths;
	}
}
