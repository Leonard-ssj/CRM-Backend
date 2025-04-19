package com.crm.crm_backend.repository;

import com.crm.crm_backend.entity.Cliente;
import com.crm.crm_backend.entity.Tarea;
import com.crm.crm_backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
    List<Tarea> findByCliente(Cliente cliente);

    List<Tarea> findByAsignadoA(Usuario usuario);
}
