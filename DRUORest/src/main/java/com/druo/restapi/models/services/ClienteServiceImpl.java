package com.druo.restapi.models.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.druo.restapi.models.dao.IClienteDao;
import com.druo.restapi.models.entity.Cliente;

@Service
public class ClienteServiceImpl implements IClienteService
{

	@Autowired
	private IClienteDao clienteDao;
	
	
	@Override
	public Cliente save(Cliente cliente) 
	{
		return clienteDao.save(cliente);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() 
	{
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	public Cliente findById(long id) 
	{
		Cliente cliente = clienteDao.findById(id).get();
		return cliente;
	}

}