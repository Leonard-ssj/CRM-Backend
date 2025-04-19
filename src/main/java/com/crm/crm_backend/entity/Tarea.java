package com.crm.crm_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tareas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_limite", nullable = false)
    private LocalDateTime fechaLimite;

    @Column(name = "hora_limite", nullable = false)
    private String horaLimite;

    @Enumerated(EnumType.STRING)
    private EstadoTarea estado;

    // Relación con Cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // Relación con Usuario (asignado a la tarea)
    @ManyToOne
    @JoinColumn(name = "asignado_a", nullable = false)
    private Usuario asignadoA;
}
