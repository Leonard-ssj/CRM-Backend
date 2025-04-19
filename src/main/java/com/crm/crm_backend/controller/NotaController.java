package com.crm.crm_backend.controller;

import com.crm.crm_backend.dto.NotaDTO;
import com.crm.crm_backend.service.ClienteService;
import com.crm.crm_backend.service.NotaService;
import com.crm.crm_backend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes/{clienteId}/notas")
@RequiredArgsConstructor
public class NotaController {

    private final NotaService notaService;
    private final ClienteService clienteService;
    private final UsuarioService usuarioService;

    @GetMapping
    public List<NotaDTO> getNotasByCliente(@PathVariable Long clienteId) {
        return notaService.getNotasByCliente(clienteService.getClienteEntity(clienteId));
    }

    @PostMapping
    public NotaDTO createNota(@PathVariable Long clienteId, @RequestBody NotaDTO notaDTO) {
        return notaService.createNota(
                notaDTO,
                clienteService.getClienteEntity(clienteId),
                usuarioService.getUsuarioById(notaDTO.getUsuarioId())
        );
    }

    @PutMapping("/{id}")
    public NotaDTO updateNota(@PathVariable Long id, @RequestBody NotaDTO notaDTO) {
        return notaService.updateNota(id, notaDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteNota(@PathVariable Long id) {
        notaService.deleteNota(id);
    }
}
