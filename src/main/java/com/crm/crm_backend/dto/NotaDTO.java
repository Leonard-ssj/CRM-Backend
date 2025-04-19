package com.crm.crm_backend.dto;

import lombok.Data;

@Data
public class NotaDTO {
    private Long id;
    private String contenido;
    private String fecha;
    private Long clienteId;
    private Long usuarioId;
}
