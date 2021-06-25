package com.horas.controller;


import com.horas.model.Papel;
import com.horas.model.Usuario;
import com.horas.response.AuthToken;
import com.horas.service.UsuarioService;
import com.horas.util.Constants;
import com.horas.util.Senha;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value="/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	AuthenticationManager authenticationManager;
	
	private static final long EXPIRATION_TIME = 1000 * Constants.TOKEN_EXPIRAR_MINUTOS;
	
	@PostMapping(path="/cadastrar")
	public String salvar(@RequestBody Usuario usuario){
		usuario.setPapel(Papel.USER);
		usuario.setSenha(Senha.criptografarSenha(usuario.getSenha()));
		usuarioService.salvar(usuario);
		return "sucesso";
	}
	
	@Cacheable("usuariosCache")
	@GetMapping(path="/listar")
	public List<Usuario> listar(){
		return usuarioService.listar();
	}
	
	@Cacheable("usuarioCache")
	@GetMapping(path="/buscar")
	public Usuario buscar(Long id) {
		return usuarioService.buscar(id);
	}
	
	@CachePut("usuarioCache")
	@PutMapping(path="/atualizar")
	public String atualizar(@RequestBody Usuario usuario) {
		usuarioService.salvar(usuario);
		return "sucesso";
	}
	
	@DeleteMapping(path="/remover")
	public String remover(Long id) {
		usuarioService.excluir(id);
		return "sucesso";
	}
	
	@PostMapping(path="/logar")
	public AuthToken logar(@RequestBody Usuario usuario){
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				usuario.getUsername(),
				usuario.getPassword(),
				Collections.emptyList()
		));
		String JWT = Jwts.builder()
						.setSubject(authentication.getName())
						.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
						.signWith(SignatureAlgorithm.HS512, Constants.CHAVE_SECRETA)
						.compact();
		return new AuthToken(Constants.TOKEN_PREFIX + " " + JWT);
	}
}
