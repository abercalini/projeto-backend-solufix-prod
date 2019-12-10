package com.br.solufix.queryPersonalizada;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.br.solufix.filter.CategoriaFilter;
import com.br.solufix.model.Categoria;

public class CategoriaRepositoryImpl implements CategoriaRepositoryQuey {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Categoria> listarTodos(CategoriaFilter categoriaFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Categoria> criteria = builder.createQuery(Categoria.class);
		Root<Categoria> root = criteria.from(Categoria.class);
		
		Predicate[] predicates = verificarFiltros(categoriaFilter, builder, root);
		criteria.where(predicates);
		TypedQuery<Categoria> query = manager.createQuery(criteria);
		adicionarRestricoes(query, pageable);
		
		return new PageImpl<Categoria>(query.getResultList(), pageable, total(categoriaFilter));
	}

	private Predicate[] verificarFiltros(CategoriaFilter categoriaFilter, CriteriaBuilder builder,
			Root<Categoria> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if (!StringUtils.isEmpty(categoriaFilter.getDescricao())) {
			predicates.add(builder.like(builder.lower(root.get("descricao")), "%" + categoriaFilter.getDescricao().toLowerCase() + "%"));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionarRestricoes(TypedQuery<Categoria> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistroPorPagina = pageable.getPageSize();
		int primeiroRegistroPagina = paginaAtual * totalRegistroPorPagina;
		
		query.setFirstResult(primeiroRegistroPagina);
		query.setMaxResults(totalRegistroPorPagina);
	}
	
	private long total(CategoriaFilter categoriaFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Categoria> root = criteria.from(Categoria.class);
		
		criteria.where(verificarFiltros(categoriaFilter, builder, root));
		criteria.select(builder.count(root));
		
		return manager.createQuery(criteria).getSingleResult();
	}

	

}
