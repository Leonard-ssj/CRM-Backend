package com.crm.crm_backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteDTO {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String empresa;
    private String ubicacion;
    private String fechaCreacion;
    // Eliminamos el campo creadoPor
    // private String creadoPor;

}
