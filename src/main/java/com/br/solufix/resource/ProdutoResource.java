package com.br.solufix.resource;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.br.solufix.filter.ProdutoFilter;
import com.br.solufix.model.Produto;
import com.br.solufix.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {
	
	@Autowired
	private AmazonS3 amazonS3;
	
	private String caminhoFoto;
	private static final String nomeBucket = "solufix-ws";

	// private static String diretorioUpload = System.getProperty("user.dir")+"/uploads";
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@PostMapping("/upload")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PRODUTO')")
	public String upload(@RequestParam MultipartFile foto) {
		
		try {
			amazonS3.putObject(nomeBucket, foto.getOriginalFilename(), foto.getInputStream(), null);
					caminhoFoto = "https://solufix-ws.s3-sa-east-1.amazonaws.com/"+foto.getOriginalFilename(); 
			return caminhoFoto;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
	
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PRODUTO')")
	public ResponseEntity<Produto> salvar(@RequestBody Produto produto) {
		produto.setCaminhoFoto(caminhoFoto);
		Produto produroRetornado = produtoRepository.save(produto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(produroRetornado);
	} 
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PRODUTO')")
	public Page<Produto> listar(ProdutoFilter produtoFilter, Pageable pageable) {
		return produtoRepository.listarTodos(produtoFilter, pageable);
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PRODUTO')")
	public ResponseEntity<Produto> buscarPorCodigo(@PathVariable Long codigo) {
		return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.findOne(codigo));
	}
	
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PRODUTO')")
	public ResponseEntity<Produto> editar(@PathVariable Long codigo, @RequestBody Produto produto) {
		Produto produtoRetornado = produtoRepository.findOne(codigo);
		BeanUtils.copyProperties(produto, produtoRetornado, "codigo");
		return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produtoRetornado));
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_PRODUTO')")
	public void excluir(@PathVariable Long codigo) {
		produtoRepository.delete(codigo);
	}
	
	
	@GetMapping("/android")
	// @PreAuthorize("hasAuthority('ROLE_PESQUISAR_PRODUTO')")
	public List<Produto> listarProdutosAndroid(String categoria) {
		return this.produtoRepository.listarTodosAndroid(categoria);
	}
	
	@GetMapping("/android/{codigo}")
	public ResponseEntity<Produto> buscarPorCodigoAndroid(@PathVariable Long codigo) {
		return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.findOne(codigo));
	}
	
	
}
