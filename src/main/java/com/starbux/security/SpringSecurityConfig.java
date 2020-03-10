package com.starbux.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.inMemoryAuthentication()
				.withUser("admin").password("{noop}password").roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				//HTTP Basic authentication
				.httpBasic()
				.and()
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/v1/topping").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "/v1/topping/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.PATCH, "/v1/topping/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/v1/topping/**").hasRole("ADMIN")


				.antMatchers(HttpMethod.GET, "/v1/report/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/v1/product").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "/v1/product/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.PATCH, "/v1/product/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/v1/product/**").hasRole("ADMIN")
				.and()
				.csrf().disable()
				.formLogin().disable();
	}

}