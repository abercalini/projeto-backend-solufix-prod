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

import com.br.solufix.filter.UsuarioFilter;
import com.br.solufix.model.Usuario;

public class UsuarioRepositoryImpl implements UsuarioRepositoryQuery {
	
	
	@PersistenceContext
	private EntityManager manager;
	
	
	@Override
	public Page<Usuario> listarTodos(UsuarioFilter usuarioFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
		Root<Usuario> root = criteria.from(Usuario.class);
		
		Predicate[] predicates = verificarParamentros(usuarioFilter, builder, root);
		criteria.where(predicates);
		TypedQuery<Usuario> query = manager.createQuery(criteria);
		adicionarRestricoes(query, pageable);
		
		return new PageImpl<Usuario>(query.getResultList(), pageable, total(usuarioFilter));
	}


	private long total(UsuarioFilter usuarioFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Usuario> root = criteria.from(Usuario.class);
		
		criteria.where(verificarParamentros(usuarioFilter, builder, root));
		criteria.select(builder.count(root));
		
		return manager.createQuery(criteria).getSingleResult();
	}


	private void adicionarRestricoes(TypedQuery<Usuario> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistroPorPagina = pageable.getPageSize();
		int primeiroRegistroPagina = paginaAtual * totalRegistroPorPagina;
		
		query.setFirstResult(primeiroRegistroPagina);
		query.setMaxResults(totalRegistroPorPagina);		
	}


	private Predicate[] verificarParamentros(UsuarioFilter usuarioFilter, CriteriaBuilder builder, Root<Usuario> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (!StringUtils.isEmpty(usuarioFilter.getNome()) || !StringUtils.isEmpty(usuarioFilter.getEmail()))  {
			predicates.add(builder.or(builder.like(builder.lower(root.get("nome")), "%" + usuarioFilter.getNome().toLowerCase() + "%"),
					builder.or(builder.like(builder.lower(root.get("email")), "%" + usuarioFilter.getEmail().toLowerCase() + "%"))));
					
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
