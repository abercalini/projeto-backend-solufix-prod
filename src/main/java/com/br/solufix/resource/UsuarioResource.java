package com.br.solufix.resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.solufix.filter.UsuarioFilter;
import com.br.solufix.model.Usuario;
import com.br.solufix.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_USUARIO')")
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
		String encode = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(encode);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRepository.save(usuario));
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_USUARIO')")
	public Page<Usuario> listarTodos(UsuarioFilter usuarioFilter, Pageable pageable) {
		
		return usuarioRepository.listarTodos(usuarioFilter, pageable);
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_USUARIO')")
	public ResponseEntity<Usuario> buscarPorCodigo(@PathVariable Long codigo) {
		Usuario usuarioRetornado = usuarioRepository.findOne(codigo);
		return ResponseEntity.status(HttpStatus.OK).body(usuarioRetornado);
	}
	
	@PutMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_USUARIO')")
	public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long codigo, @RequestBody Usuario usuario) {
		Usuario usuarioRetornado = usuarioRepository.findOne(codigo);
		BeanUtils.copyProperties(usuario, usuarioRetornado, "codigo");
		usuarioRetornado.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
		return ResponseEntity.status(HttpStatus.OK).body(usuarioRepository.save(usuarioRetornado));
	}
	
	@DeleteMapping("/{codigo}")
	@PreAuthorize("hasAuthority('ROLE_REMOVER_USUARIO')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long codigo) {
		usuarioRepository.delete(codigo);
	}
	
	
	
}
