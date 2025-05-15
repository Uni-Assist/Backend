package com.example.UniAssist.component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final HandlerExceptionResolver handlerExceptionResolver;

    public JwtFilter(JwtProvider jwtProvider, HandlerExceptionResolver handlerExceptionResolver) {
        this.jwtProvider = jwtProvider;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain fc) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        try {
            if (token != null && jwtProvider.validateToken(token)) {
                String userId = jwtProvider.getSubject(token);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(UUID.fromString(userId), null, List.of());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            fc.doFilter(request, response);
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException ex) {
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

