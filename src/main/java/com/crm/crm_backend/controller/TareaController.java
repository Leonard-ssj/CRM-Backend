package com.crm.crm_backend.controller;

import com.crm.crm_backend.dto.TareaDTO;
import com.crm.crm_backend.entity.Usuario;
import com.crm.crm_backend.service.ClienteService;
import com.crm.crm_backend.service.TareaService;
import com.crm.crm_backend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes/{clienteId}/tareas")
@RequiredArgsConstructor
public class TareaController {

    private final TareaService tareaService;
    private final ClienteService clienteService;
    private final UsuarioService usuarioService;

    @GetMapping
    public List<TareaDTO> getTareasByCliente(@PathVariable Long clienteId) {
        return tareaService.getTareasByCliente(clienteService.getClienteEntity(clienteId));
    }

    @PostMapping
    public TareaDTO createTarea(@PathVariable Long clienteId, @RequestBody TareaDTO tareaDTO) {
        return tareaService.createTarea(
                tareaDTO,
                clienteService.getClienteEntity(clienteId),
                usuarioService.getUsuarioById(tareaDTO.getAsignadoA()));
    }

    @PutMapping("/{id}")
    public TareaDTO updateTarea(@PathVariable Long id, @RequestBody TareaDTO tareaDTO) {
        return tareaService.updateTarea(id, tareaDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTarea(@PathVariable Long id) {
        tareaService.deleteTarea(id);
    }

}
