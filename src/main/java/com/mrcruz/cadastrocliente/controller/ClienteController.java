package com.mrcruz.cadastrocliente.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mrcruz.cadastrocliente.model.Cliente;
import com.mrcruz.cadastrocliente.repository.ClienteRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/clientes")
@Api(value="API Rest de Clientes")
@CrossOrigin(origins = "*")
public class ClienteController {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@ApiOperation(value="Retorna lista de clientes")
	@GetMapping
	public List<Cliente> listar(){
		return clienteRepository.findAll();
	}
	
	@ApiOperation(value="Retorna um cliente pelo ID")
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscar(@PathVariable("id") Long id){
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if(cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@ApiOperation(value="Cria um cliente e retorna o mesmo")
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Cliente adicionar(@Valid @RequestBody Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	
	@ApiOperation(value="Atualiza um cliente pelo ID e retorna o mesmo")
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> atualizar(@PathVariable("id") Long id, @Valid @RequestBody Cliente cliente){
		if(!clienteRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		cliente.setId(id);
		cliente = clienteRepository.save(cliente);
		
		return ResponseEntity.ok(cliente);
	} 
	
	@ApiOperation(value="Deleta um cliente pelo ID")
	@DeleteMapping("/{id}")
	public ResponseEntity excluir(@PathVariable("id") Long id) {
		if(!clienteRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		clienteRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}

}
