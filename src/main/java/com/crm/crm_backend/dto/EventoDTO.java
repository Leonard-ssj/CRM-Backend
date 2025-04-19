package com.crm.crm_backend.dto;

import lombok.Data;

@Data
public class EventoDTO {
    private Long id;
    private String titulo;
    private String fecha; // formato ISO string
    private String tipo;
    private String descripcion;
    private Long clienteId;
    private String clienteNombre;
    private Long usuarioId;
}
