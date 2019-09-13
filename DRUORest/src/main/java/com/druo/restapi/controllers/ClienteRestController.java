package com.druo.restapi.controllers;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.druo.restapi.models.entity.Cliente;
import com.druo.restapi.models.services.IClienteService;

import net.sf.json.JSONObject;



@RestController
@RequestMapping("/restapi")
public class ClienteRestController 
{
	@Autowired
	private IClienteService clienteService;
	
	/**
	 * Petición para crear un cliente en la bd.
	 * @param cliente Objeto con la información del cliente a crear.
	 * @return Retorna un un mensaje y un código HttpStatus dependiendo la respuesta.
	 * @throws DataAccessException/Exception En caso de que falle creando un cliente, 
	 * bien sea por la conexión de la bd o el servidor.
	 * @author David Segura - 10/09/2019.
	 */
	@PostMapping("cliente")
	public ResponseEntity<?> crearCliente(@RequestBody Cliente cliente)
	{
		JSONObject respuesta = new JSONObject();
		try
		{
			cliente = clienteService.save(cliente);
			return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
		}
		catch(DataAccessException e)
		{
			respuesta.put("error", "Error en la base de datos.\n");
			respuesta.put("causa", e.getMessage() + "\n" + e.getMostSpecificCause());
			respuesta.put("acción", "Por favor valide los datos de la base de datos y vuelva a intentarlo.\n");
			return new ResponseEntity<JSONObject>(respuesta, HttpStatus.CONFLICT);
		}
		catch(Exception e)
		{
			respuesta.put("error", "Problemas con servidor.\n");
			respuesta.put("causa", e.getMessage() + "\n" + e.getCause());
			respuesta.put("acción", "Por favor contactase con el administrador del recurso.\n");
			return new ResponseEntity<JSONObject>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/**
	 * Petición para obtener una lista de clientes.
	 * @return Retorna un un mensaje y un código HttpStatus dependiendo la respuesta.
	 * @throws DataAccessException/Exception En caso de que falle creando un cliente, 
	 * bien sea por la conexión de la bd o el servidor.
	 * @author David Segura - 10/09/2019.
	 */
	@GetMapping("/clientes")
	public ResponseEntity<?> listaClientes()
	{
		List<Cliente> listaClientes = null;
		JSONObject respuesta = new JSONObject();
		try
		{
			listaClientes = clienteService.findAll();
			if(listaClientes.isEmpty() || listaClientes == null)
			{
				respuesta.put("respuesta", "La lista de clientes puede estar nula o vacía.\n");
				return new ResponseEntity<JSONObject>(respuesta, HttpStatus.NOT_FOUND);
			}
			else
				return new ResponseEntity<List<Cliente>>(listaClientes, HttpStatus.OK);
			
		}
		catch(DataAccessException e)
		{
			respuesta.put("error", "Error en la base de datos.\n");
			respuesta.put("causa", e.getMessage() + "\n" + e.getMostSpecificCause());
			respuesta.put("acción", "Por favor valide los datos de la base de datos y vuelva a intentarlo.\n");
			return new ResponseEntity<JSONObject>(respuesta, HttpStatus.CONFLICT);
		}
		catch(Exception e)
		{
			respuesta.put("error", "Problemas con servidor.\n");
			respuesta.put("causa", e.getMessage() + "\n" + e.getCause());
			respuesta.put("acción", "Por favor contactase con el administrador del recurso.\n");
			return new ResponseEntity<JSONObject>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	/** 
	 * Petición para obtener un cliente por ID.
	 * @param id Id del cliente a buscar.
	 * @return Retorna un mensaje y un código HttpStatus dependiendo la respuesta.
	 * @throws DataAccessException/Exception En caso de que falle creando un cliente, 
	 * bien sea por la conexión de la bd o el servidor.
	 * @author David Segura - 10/09/2019.
	 */
	@GetMapping("/cliente/{id}")
	public ResponseEntity<?> buscarClienteId(@PathVariable long id)
	{
		Cliente cliente = null;
		JSONObject respuesta = new JSONObject();
		try
		{
			cliente = clienteService.findById(id);
			if(cliente == null)
			{
				respuesta.put("respuesta", "El cliente " + id + " no existe en la base de datos.\n");
				return new ResponseEntity<JSONObject>(respuesta, HttpStatus.NOT_FOUND);
			}
			else
				return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
		}
		catch(DataAccessException e)
		{
			respuesta.put("error", "Error en la base de datos.\n");
			respuesta.put("causa", e.getMessage() + "\n" + e.getMostSpecificCause());
			respuesta.put("acción", "Por favor valide los datos de la base de datos y vuelva a intentarlo.\n");
			return new ResponseEntity<JSONObject>(respuesta, HttpStatus.CONFLICT);
		}
		catch(Exception e)
		{
			respuesta.put("error", "Problemas con servidor.\n");
			respuesta.put("causa", e.getMessage() + "\n" + e.getCause());
			respuesta.put("acción", "Por favor contactase con el administrador del recurso.\n");
			return new ResponseEntity<JSONObject>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
