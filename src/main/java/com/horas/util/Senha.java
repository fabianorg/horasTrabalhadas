package com.horas.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Senha {

    public static String criptografarSenha(String senha) {
    	if (senha == null) {
    		return senha;
    	}
    	
    	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    	return bCryptPasswordEncoder.encode(senha);
    }
    
    public static boolean verificarSenha(String senha, String senhaCriptografada) {
    	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    	return bCryptPasswordEncoder.matches(senha, senhaCriptografada);
    }
}
