package com.crm.crm_backend.controller;

import com.crm.crm_backend.dto.SeguimientoDTO;
import com.crm.crm_backend.service.ClienteService;
import com.crm.crm_backend.service.SeguimientoService;
import com.crm.crm_backend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes/{clienteId}/seguimientos")
@RequiredArgsConstructor
public class SeguimientoController {

    private final SeguimientoService seguimientoService;
    private final ClienteService clienteService;
    private final UsuarioService usuarioService;

    @GetMapping
    public List<SeguimientoDTO> getSeguimientosByCliente(@PathVariable Long clienteId) {
        return seguimientoService.getSeguimientosByCliente(clienteService.getClienteEntity(clienteId));
    }

    @PostMapping
    public SeguimientoDTO createSeguimiento(@PathVariable Long clienteId, @RequestBody SeguimientoDTO seguimientoDTO) {
        return seguimientoService.createSeguimiento(
                seguimientoDTO,
                clienteService.getClienteEntity(clienteId),
                usuarioService.getUsuarioById(seguimientoDTO.getUsuarioId())
        );
    }

    @PutMapping("/{id}")
    public SeguimientoDTO updateSeguimiento(@PathVariable Long id, @RequestBody SeguimientoDTO seguimientoDTO) {
        return seguimientoService.updateSeguimiento(id, seguimientoDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteSeguimiento(@PathVariable Long id) {
        seguimientoService.deleteSeguimiento(id);
    }
}
