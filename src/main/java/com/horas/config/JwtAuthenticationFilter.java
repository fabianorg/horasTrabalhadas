package com.horas.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.horas.exceptions.TokenException;
import com.horas.response.MensagemRetorno;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		Authentication authentication = null;
		try {
			authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest) request);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			filterChain.doFilter(request, response);
		} catch (TokenException e) {
			ObjectMapper mapper = new ObjectMapper();
			String json =  mapper.writeValueAsString(new MensagemRetorno("Token inválido"));
			HttpServletResponse res = (HttpServletResponse) response;
			res.reset();
			res.setHeader("Content-Type", "application/json;charset=UTF-8");
			res.setStatus(400);
			res.getWriter().write(json);
		}
	}

}
