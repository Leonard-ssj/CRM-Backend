package com.crm.crm_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String email;
    private String rol;
}
