package com.druo.restapi.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.druo.restapi.models.entity.Usuario;

@Repository("gestorUsuario")
public interface GestorUsuario extends JpaRepository<Usuario, Serializable> 
{
	public abstract Usuario findByUsuario(String nombre);
}
