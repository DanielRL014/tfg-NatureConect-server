package com.natureconect.natureConect.security;

import com.natureconect.natureConect.filters.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * clase con la configuracion de la seguridad del servidor
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /**
     *  configuracion de la seguridad del servidor
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/publicaciones/**").permitAll()
                        .requestMatchers("/api/investigador/login", "/api/investigador/registrar", "/login.html","/registrar.html", "/js/**", "/css/**").permitAll()
                        .requestMatchers("/informes.html").authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}