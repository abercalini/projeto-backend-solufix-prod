package com.br.solufix.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.solufix.model.Contato;
import com.br.solufix.repository.ContatoRepository;

@RestController
@RequestMapping("/contato/aplicativo")
public class ContatoResource {
	
	@Autowired
	private ContatoRepository contatoRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public boolean adicionarContato(@RequestBody Contato contato) {
		contatoRepository.save(contato);
		System.out.println(contato);
		return true;
	}
	
	
	
}
