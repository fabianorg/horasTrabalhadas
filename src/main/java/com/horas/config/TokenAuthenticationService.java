package com.horas.config;

import com.horas.exceptions.TokenException;
import com.horas.util.Constants;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

public class TokenAuthenticationService {

	static Authentication getAuthentication(HttpServletRequest request) throws TokenException {
		String token = request.getHeader(Constants.HEADER_STRING);
		if (token != null) {
			String user = null;
			try {
				user = Jwts.parser()
							.setSigningKey(Constants.CHAVE_SECRETA)
							.parseClaimsJws(token.replace(Constants.TOKEN_PREFIX, ""))
							.getBody()
							.getSubject();
			}catch (Exception e) {
				throw new TokenException("Token inválido");
			}
			
			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
			}
		}
		return null;
	}
}
