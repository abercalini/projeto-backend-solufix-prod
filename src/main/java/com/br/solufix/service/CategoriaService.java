package com.br.solufix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.br.solufix.model.Categoria;
import com.br.solufix.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public ResponseEntity<Categoria> verificarCodigo(Long codigo) {
		Categoria categoriaRetornada = categoriaRepository.findOne(codigo);
		if (categoriaRetornada != null) {
			return ResponseEntity.status(HttpStatus.OK).body(categoriaRetornada);
		} else {
			 throw new NullPointerException();
		}
	}
	
	public Categoria atualizarCategoria(Long codigo) {
		Categoria categoriaRetornada = categoriaRepository.findOne(codigo);
		if (categoriaRetornada != null) {
			return categoriaRetornada;
		} else {
			throw new NullPointerException();
		}
	}
	
}
