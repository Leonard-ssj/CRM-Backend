package com.crm.crm_backend.dto;

import lombok.Data;

@Data
public class ContactoDTO {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String puesto;
    private String notas;
    private Long clienteId;
    private String clienteNombre;
}
