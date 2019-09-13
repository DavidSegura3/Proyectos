package com.druo.restapi.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.druo.restapi.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long> 
{
	
}
