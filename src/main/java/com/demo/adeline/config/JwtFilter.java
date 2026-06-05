package com.demo.adeline.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter {
    private String secret = "estaEsUnaClaveMuyLargaYSeguraDeAlMenos32CaracteresParaCumplirConHS256"; // Debe ser igual al de JwtUtil

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                // 1. Obtenemos el cuerpo del token (Claims)
                var claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
                String username = claims.getSubject();
                
                // 2. EXTRAEMOS LOS ROLES del token (ajusta "role" según cómo guardes el rol en tu JWT)
                String role = claims.get("rol", String.class); 
                System.out.println("DEBUG: Username: " + username + " - Role encontrado: " + role);
                if (username != null) {
                    // 3. Convertimos el rol a una GrantedAuthority
                    List<GrantedAuthority> authorities = new ArrayList<>();
               
                    if (role != null) {
                        authorities.add(new SimpleGrantedAuthority(role));
                    }

                    // 4. PASAMOS LAS AUTHORITIES aquí, no una lista vacía
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception e) {
                // Token inválido o expirado
            }
        }
        filterChain.doFilter(request, response);
    }
}