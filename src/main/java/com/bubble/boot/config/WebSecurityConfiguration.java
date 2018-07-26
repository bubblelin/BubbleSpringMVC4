package com.bubble.boot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author yanlin
 */
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.requiresChannel().anyRequest().requiresSecure() //http重定向到https
			.and()
			.formLogin()
			.loginPage("/login") // <= custom login page
			.defaultSuccessUrl("/profile")
			.and()
			.logout().logoutSuccessUrl("/login")
			.and()
			.authorizeRequests()
			.antMatchers("/webjars/**", "/login", "/signin/**", "/signup").permitAll()
			.anyRequest().authenticated();
	}
	
}
