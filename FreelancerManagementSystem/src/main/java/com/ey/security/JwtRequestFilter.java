package com.ey.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;
        try {
		        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
		            jwt = authorizationHeader.substring(7);
		            
		
		            if (tokenBlacklistService.isTokenBlacklisted(jwt)) {
		                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is blacklisted");
		                return;
		            }
		
		            username = jwtUtil.extractUsername(jwt);
		        }
		
		        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
		            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		
		            if (jwtUtil.validateToken(jwt, userDetails)) {
		                Claims claims = jwtUtil.extractAllClaims(jwt);
		                String role = claims.get("role", String.class);
		
		                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
		
		                UsernamePasswordAuthenticationToken authenticationToken =
		                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
		                
		                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		            }
		        }
		
		        chain.doFilter(request, response);
        	}catch (ExpiredJwtException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token has expired. Please login again.");
        }	
    }
}
