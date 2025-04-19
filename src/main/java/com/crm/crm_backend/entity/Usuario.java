package com.crm.crm_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    private LocalDateTime ultimoAcceso;

    @Column(name = "ip_acceso")
    private String ip;

    @Column(name = "ultimo_cambio_perfil")
    private LocalDateTime ultimoCambioPerfil;

    @Column(name = "ultimo_cambio_password")
    private LocalDateTime ultimoCambioPassword;

}
