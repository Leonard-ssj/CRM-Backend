package com.crm.crm_backend.repository;

import com.crm.crm_backend.entity.Cliente;
import com.crm.crm_backend.entity.Evento;
import com.crm.crm_backend.entity.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByCliente(Cliente cliente);
    List<Evento> findByUsuario(Usuario usuario);

}
