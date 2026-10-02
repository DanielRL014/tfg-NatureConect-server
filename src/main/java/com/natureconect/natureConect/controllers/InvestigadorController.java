package com.natureconect.natureConect.controllers;

import com.natureconect.natureConect.models.investigador;
import com.natureconect.natureConect.service.InvestigadorService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * contorlador de investidador
 */
@RestController
@RequestMapping("/api/investigador")
public class InvestigadorController {
    /**
     * servicio de investigador
     */
    @Autowired
    private InvestigadorService inse;

    /**
     * clave para la autentificacion de los investigadores
     */
    private static final String SECRET_KEY = "**********************";

    /**
     * metodo para registrar un investigador
     * @param nombre nombre del investigador
     * @param apellidos apellidos del investigador
     * @param intitucion intitucion del investigador
     * @param password contraseña del investigador
     * @param correo correo del investigador
     * @return
     */
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestParam("nombre") String nombre,@RequestParam("apellidos") String apellidos,@RequestParam("institucion") String intitucion, @RequestParam("password") String password, @RequestParam("correo") String correo) {
        try {
            investigador reigstrado= inse.registrar(nombre, apellidos,intitucion,correo, BCrypt.hashpw(password, BCrypt.gensalt()));
            SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

            String jwt = Jwts.builder()
                    .subject(correo)
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hora
                    .signWith(key)
                    .compact();
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "token", jwt,
                    "data", reigstrado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * metodo para hacer el inicio de sesion
     * @param correo correo del investigador
     * @param password contraseña del investigador
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("correo") String correo, @RequestParam("password") String password) {
        try {
            investigador reigstrado= inse.login(correo,password);
            SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

            String jwt = Jwts.builder()
                    .subject(correo)
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                    .signWith(key)
                    .compact();

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "token", jwt,
                    "data", reigstrado));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
