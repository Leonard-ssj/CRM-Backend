package com.crm.crm_backend.repository;

import com.crm.crm_backend.entity.Cliente;
import com.crm.crm_backend.entity.Seguimiento;
import com.crm.crm_backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeguimientoRepository extends JpaRepository<Seguimiento, Long> {
    List<Seguimiento> findByCliente(Cliente cliente);

    List<Seguimiento> findByUsuario(Usuario usuario);
}
