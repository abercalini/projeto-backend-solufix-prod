package com.br.solufix.queryPersonalizada;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.br.solufix.filter.UsuarioFilter;
import com.br.solufix.model.Usuario;

public interface UsuarioRepositoryQuery {
	
	public Page<Usuario> listarTodos(UsuarioFilter usuarioFilter, Pageable pageable);

}
