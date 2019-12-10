package com.br.solufix.queryPersonalizada;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.br.solufix.filter.CategoriaFilter;
import com.br.solufix.model.Categoria;

public interface CategoriaRepositoryQuey {
	
	public Page<Categoria> listarTodos(CategoriaFilter categoriaFilter, Pageable pageable);
	
}
