package com.crm.crm_backend.service;

import com.crm.crm_backend.dto.NotaDTO;
import com.crm.crm_backend.entity.Cliente;
import com.crm.crm_backend.entity.Usuario;

import java.util.List;

public interface NotaService {
    List<NotaDTO> getNotasByCliente(Cliente cliente);

    NotaDTO createNota(NotaDTO notaDTO, Cliente cliente, Usuario usuario);

    NotaDTO updateNota(Long id, NotaDTO notaDTO);

    void deleteNota(Long id);
}
