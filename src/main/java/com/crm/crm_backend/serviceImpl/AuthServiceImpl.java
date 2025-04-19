package com.crm.crm_backend.serviceImpl;

import com.crm.crm_backend.dto.AuthRequest;
import com.crm.crm_backend.dto.AuthResponse;
import com.crm.crm_backend.entity.Rol;
import com.crm.crm_backend.entity.Usuario;
import com.crm.crm_backend.exception.ApiException;
import com.crm.crm_backend.repository.UsuarioRepository;
import com.crm.crm_backend.service.AuthService;
import com.crm.crm_backend.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    @Override
    public AuthResponse register(AuthRequest request) {
        if (request.getNombre() == null || request.getNombre().isBlank()) {
            throw new ApiException("El nombre es obligatorio.");
        }

        if (request.getApellido() == null || request.getApellido().isBlank()) {
            throw new ApiException("El apellido es obligatorio.");
        }

        if (request.getEmail() == null || request.getEmail().isBlank() || !request.getEmail().contains("@")) {
            throw new ApiException("El correo es inválido.");
        }

        if (request.getPassword() == null || request.getPassword().length() < 6) {
            throw new ApiException("La contraseña debe tener al menos 6 caracteres.");
        }

        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ApiException("El correo ya está registrado.");
        }

        // Validar nombre y apellido solo letras y mínimo 3 caracteres
        if (!request.getNombre().matches("^[a-zA-Z]{3,}$")) {
            throw new ApiException(
                    "El nombre debe tener al menos 3 letras y solo puede contener caracteres alfabéticos.");
        }

        if (!request.getApellido().matches("^[a-zA-Z]{3,}$")) {
            throw new ApiException(
                    "El apellido debe tener al menos 3 letras y solo puede contener caracteres alfabéticos.");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(Rol.USUARIO);
        usuario.setUltimoAcceso(null); // Se actualiza solo en login

        usuarioRepository.save(usuario);

        // Generar accessToken y refreshToken
        // Generar accessToken y refreshToken usando valores configurados
        String accessToken = jwtUtil.generateToken(usuario.getEmail(),
                (int) jwtUtil.getJwtExpirationMs() / (60 * 1000)); // Tiempo del accessToken
        String refreshToken = jwtUtil.generateToken(usuario.getEmail(),
                (int) jwtUtil.getJwtRefreshExpirationMs() / (60 * 1000)); // Tiempo del refreshToken

        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public AuthResponse login(AuthRequest request, HttpServletRequest httpRequest) {
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new ApiException("El email es obligatorio.");
        }

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new ApiException("La contraseña es obligatoria.");
        }

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApiException("Email o contraseña incorrecta."));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new ApiException("Email o contraseña incorrecta.");
        }

        // Autenticación
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        // Guardar último acceso y IP
        String ip = httpRequest.getRemoteAddr();
        usuario.setUltimoAcceso(LocalDateTime.now());
        usuario.setIp(ip);
        usuarioRepository.save(usuario);

        // Generar accessToken y refreshToken
        // Generar accessToken y refreshToken usando valores configurados
        String accessToken = jwtUtil.generateToken(usuario.getEmail(),
                (int) jwtUtil.getJwtExpirationMs() / (60 * 1000)); // Tiempo del accessToken
        String refreshToken = jwtUtil.generateToken(usuario.getEmail(),
                (int) jwtUtil.getJwtRefreshExpirationMs() / (60 * 1000)); // Tiempo del refreshToken

        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public AuthResponse refresh(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new ApiException("Refresh token inválido o expirado.");
        }

        String email = jwtUtil.extractUsername(refreshToken);
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("Usuario no encontrado"));

        // ✅ Generar nuevos tokens si el refreshToken es válido
        String newAccessToken = jwtUtil.generateToken(usuario.getEmail(),
                (int) jwtUtil.getJwtExpirationMs() / (60 * 1000));

        String newRefreshToken = jwtUtil.generateToken(usuario.getEmail(),
                (int) jwtUtil.getJwtRefreshExpirationMs() / (60 * 1000));

        return new AuthResponse(newAccessToken, newRefreshToken);
    }

}
