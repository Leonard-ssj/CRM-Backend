package com.crm.crm_backend.service;

import com.crm.crm_backend.dto.SeguimientoDTO;
import com.crm.crm_backend.entity.Cliente;
import com.crm.crm_backend.entity.Usuario;

import java.util.List;

public interface SeguimientoService {
    List<SeguimientoDTO> getSeguimientosByCliente(Cliente cliente);

    SeguimientoDTO createSeguimiento(SeguimientoDTO seguimientoDTO, Cliente cliente, Usuario usuario);

    SeguimientoDTO updateSeguimiento(Long id, SeguimientoDTO seguimientoDTO);

    void deleteSeguimiento(Long id);
}
