package com.br.solufix.queryPersonalizada;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.br.solufix.filter.ProdutoFilter;
import com.br.solufix.model.Categoria;
import com.br.solufix.model.Produto;

public class ProdutoRepositoryImpl implements ProdutoRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Page<Produto> listarTodos(ProdutoFilter produtoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteria = builder.createQuery(Produto.class);
		Root<Produto> root = criteria.from(Produto.class);
		
		Predicate[] predicates = verificarParametros(produtoFilter, builder, root);
		criteria.where(predicates);
		TypedQuery<Produto> query = manager.createQuery(criteria);
		
		adicionarRestricoes(query, pageable);
		
		return new PageImpl<Produto>(query.getResultList(), pageable, total(produtoFilter));
	}

	private Predicate[] verificarParametros(ProdutoFilter produtoFilter, CriteriaBuilder builder, Root<Produto> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (!StringUtils.isEmpty(produtoFilter.getNome()) || !StringUtils.isEmpty(produtoFilter.getDescricao()) 
				|| !StringUtils.isEmpty(produtoFilter.getCodigoBarra()))   {
			
			System.out.println("Caiu aqui");
			
			predicates.add(builder.or(builder.like(builder.lower(root.get("nome")), "%" + produtoFilter.getNome().toLowerCase() + "%"),
					builder.or(builder.like(builder.lower(root.get("descricao")), "%" + produtoFilter.getDescricao().toLowerCase() + "%"),
					builder.or(builder.like(builder.lower(root.get("codigoBarra")), "%" + produtoFilter.getCodigoBarra() + "%")))));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionarRestricoes(TypedQuery<Produto> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistroPorPagina = pageable.getPageSize();
		int primeiroRegistroPagina = paginaAtual * totalRegistroPorPagina;
		
		query.setFirstResult(primeiroRegistroPagina);
		query.setMaxResults(totalRegistroPorPagina);
	}
	
	private long total(ProdutoFilter produtoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Produto> root = criteria.from(Produto.class);
		
		criteria.where(verificarParametros(produtoFilter, builder, root));
		criteria.select(builder.count(root));
		
		return manager.createQuery(criteria).getSingleResult();
	}

	@Override
	public List<Produto> listarTodosAndroid(String nome) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteria = builder.createQuery(Produto.class);
		Root<Produto> root = criteria.from(Produto.class);
		
		Join<Produto, Categoria> joinCategoria = root.join("categoria", JoinType.INNER);
		
		Predicate[] predicates = verificarParametrosAndroid(nome, joinCategoria, builder, root);
		criteria.where(predicates);
		
		return manager.createQuery(criteria).getResultList();
	}

	private Predicate[] verificarParametrosAndroid(String categoria, Join<Produto, Categoria> joinCategoria, CriteriaBuilder builder, Root<Produto> root) {
		List<Predicate> predicate = new ArrayList<Predicate>();
		
		if (!StringUtils.isEmpty(categoria)) {
			predicate.add(builder.like(builder.lower(joinCategoria.get("descricao")), "%" + categoria.toLowerCase() + "%"));
		}
		
		return predicate.toArray(new Predicate[predicate.size()]);
	}
	
	

}
