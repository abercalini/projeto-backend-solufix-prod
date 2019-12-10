package com.br.solufix.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CodificadorDeSenha {
	public static void main(String[] args) {
		
		BCryptPasswordEncoder enconder = new BCryptPasswordEncoder();
		System.out.println(enconder.encode("flavio"));
	}
}
