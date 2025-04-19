package com.crm.crm_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    private String nombre;
    private String apellido;
    private String email;
    private String password;
}
