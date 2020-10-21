package com.example.GithubAuth2;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GithubAuth2Application extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		//// @formatter:off
		
		http
			.authorizeRequests(a -> a.antMatchers("/", "/error", "/webjars/**")
									 .permitAll()
									 .anyRequest()
									 .authenticated())
			.logout(l -> l.logoutSuccessUrl("/")
					      .permitAll())
			.csrf( c -> c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
			
			.oauth2Login();
		
		// @formatter:on

	}
	
	@GetMapping("/user")
	public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal){
		return Collections.singletonMap("login", principal.getAttribute("login"));
	}
	
	public static void main(String[] args) {
		SpringApplication.run(GithubAuth2Application.class, args);
	}
	

}
