package com.crm.crm_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerfilUpdateRequest {
    private String nombre;
    private String apellido;
}