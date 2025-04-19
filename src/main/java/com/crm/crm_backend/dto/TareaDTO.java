package com.crm.crm_backend.dto;

import lombok.Data;

@Data
public class TareaDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String fechaLimite;
    private String horaLimite;
    private String estado;
    private Long clienteId;
    private Long asignadoA;

    private String clienteNombre;
    private String asignadoANombre; 
}
