package com.natureconect.natureConect.controllers;

import com.natureconect.natureConect.models.Usuario;
import com.natureconect.natureConect.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * controlador de usuario
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    /**
     * servicio de usuario
     */
    @Autowired
    private UsuarioService usse;

    /**
     * metodo para registrar un usuario
     * @param nombre nombre del usuario
     * @param password contraseña del usuario
     * @param email correo del usuario
     * @return
     */
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestParam("nombre") String nombre,  @RequestParam("password") String password,@RequestParam("email") String email) {
        try {
            Usuario reigstrado= usse.register(nombre, email, BCrypt.hashpw(password, BCrypt.gensalt()));
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "data", reigstrado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * metodo de inicio de sesion
     * @param nombre nombre del usuario
     * @param password contraseña del usuario
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("nombre") String nombre, @RequestParam("password") String password) {
        try {
            Usuario usuario = usse.login(nombre,password);
                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "data", usuario
                ));

        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
