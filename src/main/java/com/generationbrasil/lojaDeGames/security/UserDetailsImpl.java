package com.generationbrasil.lojaDeGames.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.generationbrasil.lojaDeGames.model.Usuario;


public class UserDetailsImpl implements UserDetails{
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String password;
	
	//autoriza todos os privilegios de usuario
	private List<GrantedAuthority> authorities;
	
	public UserDetailsImpl(Usuario usuario) {
		this.userName = usuario.getUsuario();
		this.password = usuario.getSenha();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	@Override
	public String getUsername() {

		return userName;
	}
	
	//Validar se a credencial não expirou
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	//Validar se a conta não está bloqueada
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	//Validar se a credencial não expirou
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//Validar se a conta está ativa
	@Override
	public boolean isEnabled() {
		return true;
	}	
}
