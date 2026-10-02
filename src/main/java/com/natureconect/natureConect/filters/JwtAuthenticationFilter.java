package com.natureconect.natureConect.filters;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * clase para el filtro de la web de investigadores
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    /**
     * contraseña para la incriptación
     */
    private static final String SECRET_KEY = "*********";

    /**
     * configuracion del filtro
     * @param request
     * @param response
     * @param filterChain
     * @throws jakarta.servlet.ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {

        String token = request.getParameter("token");

        if (token != null ) {


            try {
                SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
                Claims claims = Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

                String userEmail = claims.getSubject();

                if (userEmail != null) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userEmail, null, Collections.emptyList());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            } catch (Exception e) {

                System.out.println("Token inválido: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
