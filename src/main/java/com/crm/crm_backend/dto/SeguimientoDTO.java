package com.crm.crm_backend.dto;

import lombok.Data;

@Data
public class SeguimientoDTO {
    private Long id;
    private String tipo;
    private String fecha;
    private String comentarios;
    private Long clienteId;
    private Long usuarioId;
}
