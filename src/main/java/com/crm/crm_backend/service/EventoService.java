package com.crm.crm_backend.service;

import com.crm.crm_backend.dto.EventoDTO;
import com.crm.crm_backend.entity.Cliente;

import java.util.List;

public interface EventoService {
    List<EventoDTO> getEventosByCliente(Cliente cliente);
    EventoDTO createEvento(EventoDTO dto);
    EventoDTO updateEvento(Long id, EventoDTO dto);
    void deleteEvento(Long id);
    List<EventoDTO> getAllEventos();
    EventoDTO getEventoById(Long id);
}