package com.druo.restapi.models.services;

import java.util.List;

import com.druo.restapi.models.entity.Cliente;

public interface IClienteService 
{
	public Cliente findById(long id);
	public Cliente save(Cliente cliente);
	public List<Cliente> findAll();
}
