package com.demo.adeline.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .cors().and()
	        .csrf().disable()
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        .and()
	        .authorizeHttpRequests(auth -> auth
	            // 1. Rutas públicas
	            .requestMatchers("/api/usuarios/login", "/api/usuarios/register", "/api/usuarios/auth/firebase", "/uploads/**").permitAll()
	            
	            // 2. Rutas protegidas - Especifica los roles necesarios
	            .requestMatchers("/api/regalos/**").hasAuthority("ADMIN") 
	            .requestMatchers("/api/livekit/**").hasAnyAuthority("ADMIN", "USER") 
	            
	            // 3. Cualquier otra ruta requiere autenticación
	            .anyRequest().authenticated()
	        )
	        // 4. EL FILTRO ES LA LLAVE: Debe estar antes del filtro de usuario/password
	        .addFilterBefore(new JwtFilter(), org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}
    
}