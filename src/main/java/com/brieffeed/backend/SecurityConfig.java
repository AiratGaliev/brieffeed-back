package com.brieffeed.backend;

import com.brieffeed.backend.service.UserDetailServiceImpl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// определение защищенных и незащищенных конечных точек
	// https://spring.io/guides/gs/securing-web/
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.cors().and().authorizeRequests().antMatchers(HttpMethod.GET, "/login").permitAll().anyRequest()
//				.authenticated().and()
//				// Фильтр для запросов api / login
//				.addFilterBefore(new LoginFilter("/login", authenticationManager()),
//						UsernamePasswordAuthenticationFilter.class)
//				// Фильтр для других запросов для проверки JWT в заголовке
//				.addFilterBefore(new AuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// disable caching
		http.headers().cacheControl();
		http.csrf().disable() // disable csrf for our requests.
				.authorizeRequests().antMatchers("/").permitAll().antMatchers(HttpMethod.POST, "/login").permitAll()
				.anyRequest().authenticated().and()
				// We filter the api/login requests
				.addFilterBefore(new LoginFilter("/login", authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)
				// And filter other requests to check the presence of JWT in header
				.addFilterBefore(new AuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Autowired
	private UserDetailServiceImpl userDetailService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailService)
				.passwordEncoder(new BCryptPasswordEncoder(12));
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("*"));
		config.setAllowedMethods(Arrays.asList("*"));
		config.setAllowedHeaders(Arrays.asList("*"));
		config.setAllowCredentials(true);
		config.applyPermitDefaultValues();

		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
