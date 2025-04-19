package com.crm.crm_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestRolController {

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> soloAdmin() {
        return ResponseEntity.ok("Hola, ADMIN 😎");
    }

    @GetMapping("/usuario")
    @PreAuthorize("hasRole('USUARIO')")
    public ResponseEntity<String> soloUsuario() {
        return ResponseEntity.ok("Hola, USUARIO 👤");
    }
}
