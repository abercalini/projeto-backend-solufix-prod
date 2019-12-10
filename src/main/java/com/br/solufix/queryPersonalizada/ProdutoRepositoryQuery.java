package com.br.solufix.queryPersonalizada;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.br.solufix.filter.ProdutoFilter;
import com.br.solufix.model.Produto;

public interface ProdutoRepositoryQuery {
	
	Page<Produto> listarTodos(ProdutoFilter produtoFilter, Pageable pageable);
	
	List<Produto> listarTodosAndroid(String nome);
}
