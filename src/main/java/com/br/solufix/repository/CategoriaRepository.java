package com.br.solufix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.solufix.model.Categoria;
import com.br.solufix.queryPersonalizada.CategoriaRepositoryQuey;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>, CategoriaRepositoryQuey {

}
