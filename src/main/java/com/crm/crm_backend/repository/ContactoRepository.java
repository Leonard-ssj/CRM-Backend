package com.crm.crm_backend.repository;

import com.crm.crm_backend.entity.Cliente;
import com.crm.crm_backend.entity.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactoRepository extends JpaRepository<Contacto, Long> {
    List<Contacto> findByCliente(Cliente cliente);
}
