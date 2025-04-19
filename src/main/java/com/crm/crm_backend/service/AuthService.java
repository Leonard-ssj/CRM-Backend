package com.crm.crm_backend.service;

import com.crm.crm_backend.dto.AuthRequest;
import com.crm.crm_backend.dto.AuthResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    AuthResponse login(AuthRequest request, HttpServletRequest httpRequest);

    AuthResponse register(AuthRequest request);

    // Añadir la firma para el método refresh()
    AuthResponse refresh(String refreshToken);

}
