package com.crm.crm_backend.controller;

import com.crm.crm_backend.dto.EventoDTO;
import com.crm.crm_backend.service.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoGeneralController {

    private final EventoService eventoService;

    @GetMapping
    public List<EventoDTO> getAllEventos() {
        return eventoService.getAllEventos();
    }

    @GetMapping("/{id}")
    public EventoDTO getEventoById(@PathVariable Long id) {
        return eventoService.getEventoById(id);
    }

    @PostMapping
    public EventoDTO createEvento(@RequestBody EventoDTO dto) {
        return eventoService.createEvento(dto);
    }

    @PutMapping("/{id}")
    public EventoDTO updateEvento(@PathVariable Long id, @RequestBody EventoDTO dto) {
        return eventoService.updateEvento(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteEvento(@PathVariable Long id) {
        eventoService.deleteEvento(id);
    }
}