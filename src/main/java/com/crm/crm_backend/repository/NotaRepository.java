package com.crm.crm_backend.repository;

import com.crm.crm_backend.entity.Cliente;
import com.crm.crm_backend.entity.Nota;
import com.crm.crm_backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Long> {
    List<Nota> findByCliente(Cliente cliente);

    List<Nota> findByUsuario(Usuario usuario);
}
