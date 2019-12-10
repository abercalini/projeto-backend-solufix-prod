package com.br.solufix.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.solufix.model.Contato;

public interface ContatoRepository extends JpaRepository<Contato, Long> {

}
