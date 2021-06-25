package com.horas.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@NoArgsConstructor
@Builder
@Data
public class PapelSecurity implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	private Papel papel;

	public PapelSecurity(Papel papel) {
		this.papel = papel;
	}

	@Override
	public String getAuthority() {
		return "ROLE_" + this.papel.name();
	}
}
