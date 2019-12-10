package com.br.solufix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.solufix.model.Produto;
import com.br.solufix.queryPersonalizada.ProdutoRepositoryQuery;

public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQuery {

}
