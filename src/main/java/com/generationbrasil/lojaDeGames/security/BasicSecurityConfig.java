package com.generationbrasil.lojaDeGames.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter{
	//Serve para comparar os dados digitados com os dados salvos no banco de dados
	@Autowired
	private UserDetailsService userDetailsService;
	
	//usuario em memoria PARA TESTE
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService); //sem essa linha não é feito a verificação dos dados com o BD!
		auth.inMemoryAuthentication()
		.withUser("root")
		.password(passwordEncoder().encode("root"))
		.authorities("ROLE_USER");
	}
	
	//notação que deixa uma função acessivel globalmente(em toda a minha aplicação)
	@Bean
	//função que criptografa a senha digitada
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		.antMatchers("/usuarios/logar").permitAll() // De qualquer lugar, você terá acesso a login
		.antMatchers("/usuarios/cadastrar").permitAll() // e cadastro já que as rotas estão abertas
		.antMatchers(HttpMethod.OPTIONS).permitAll() /* Permite que as rotas estejam acessíveis com GET
		 											  * Permite saber quais métodos estão abertos na
		 											  * documentação da API e que estão abertos nela 
		 											  * e é possível utilizar eles. */
		.anyRequest().authenticated() // Para outras requisições, tem que está ou cadastrado ou em memória
		.and().httpBasic() // HttpBasic = CRUD | Define que só será aceito métodos CRUD
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
		/* ^ Define que toda requisição tem começo, meio e fim. Uma por vez e ajuda a prevenir ataques 
		 * cibernéticos e invasões com várias requisições de uma forma | Tipo quando expira o token 
		 * em um site como na plataforma da Generation Brasil. */
		.and().cors() /* Funciona como o '@CrossOrigins', vendo de qual porta está vindo a requisição e
		liberando acesso para todas (Do Front-end pro Back-end basicamente) */
		.and().csrf().disable(); // Autoriza PUT e DELETE na requisição

	}

}
