package com.crm.crm_backend.controller;

import com.crm.crm_backend.dto.AuthRequest;
import com.crm.crm_backend.dto.AuthResponse;
import com.crm.crm_backend.dto.PerfilUpdateRequest;
import com.crm.crm_backend.entity.Usuario;
import com.crm.crm_backend.exception.ApiException;
import com.crm.crm_backend.repository.UsuarioRepository;
import com.crm.crm_backend.service.AuthService;
import com.crm.crm_backend.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.crm.crm_backend.dto.UpdatePasswordDTO;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request, HttpServletRequest httpRequest) {
        return ResponseEntity.ok(authService.login(request, httpRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping("/me")
    public ResponseEntity<Usuario> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }

        String email = userDetails.getUsername();
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");

        if (refreshToken == null || refreshToken.isBlank()) {
            throw new ApiException("El refreshToken es obligatorio.");
        }

        AuthResponse response = authService.refresh(refreshToken);
        return ResponseEntity.ok(response);
    }

    // actualizar perfil (nombre, apellido)
    @PutMapping("/me")
    public ResponseEntity<Usuario> updatePerfil(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody PerfilUpdateRequest request) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }

        String email = userDetails.getUsername();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("Usuario no encontrado"));
                
        if (request.getNombre() != null && !request.getNombre().isBlank()) {
            usuario.setNombre(request.getNombre());
        }

        if (request.getApellido() != null && !request.getApellido().isBlank()) {
            usuario.setApellido(request.getApellido());
        }

        // 👉 Nuevo campo
        usuario.setUltimoCambioPerfil(LocalDateTime.now());

        usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuario);
    }

    // actualizar contraseña del usuario logueado
    @PutMapping("/me/password")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }

        String email = userDetails.getUsername();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("Usuario no encontrado."));

        if (dto.getCurrentPassword() == null || dto.getCurrentPassword().isBlank() ||
                dto.getNewPassword() == null || dto.getNewPassword().isBlank() ||
                dto.getConfirmPassword() == null || dto.getConfirmPassword().isBlank()) {
            throw new ApiException("Todos los campos son obligatorios.");
        }

        if (!passwordEncoder.matches(dto.getCurrentPassword(), usuario.getPassword())) {
            throw new ApiException("La contraseña actual es incorrecta.");
        }

        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new ApiException("La nueva contraseña y la confirmación no coinciden.");
        }

        if (dto.getNewPassword().length() < 6) {
            throw new ApiException("La nueva contraseña debe tener al menos 6 caracteres.");
        }

        usuario.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        // 👉 Nuevo campo
        usuario.setUltimoCambioPassword(LocalDateTime.now());

        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Contraseña actualizada correctamente.");
    }

}
