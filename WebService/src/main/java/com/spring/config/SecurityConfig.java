package com.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(
				passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/admin/**")
				.access("hasRole('Admin')").and().formLogin()
				.defaultSuccessUrl("/").loginPage("/login")
				.failureUrl("/login?error").usernameParameter("email")
				.passwordParameter("password").and().logout()
				.logoutSuccessUrl("/").and().csrf().and().exceptionHandling()
				.accessDeniedPage("/403");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// This is here to ensure that the static content (JavaScript, CSS, etc)
		// is accessible from the login page without authentication
		web.ignoring().antMatchers("/user/**");
	}

	@Bean
	public Md5PasswordEncoder passwordEncoder() {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		return encoder;
	}

}