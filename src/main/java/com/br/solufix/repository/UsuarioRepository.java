package com.br.solufix.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.solufix.model.Usuario;
import com.br.solufix.queryPersonalizada.UsuarioRepositoryQuery;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryQuery {
	
	public Optional<Usuario> findByEmail(String email);
	
	
	
}
