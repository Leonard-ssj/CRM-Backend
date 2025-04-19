package com.crm.crm_backend.controller;

import com.crm.crm_backend.dto.TareaDTO;
import com.crm.crm_backend.entity.Usuario;
import com.crm.crm_backend.service.TareaService;
import com.crm.crm_backend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
@RequiredArgsConstructor
public class TareaUsuarioController {

    private final TareaService tareaService;
    private final UsuarioService usuarioService;

    @GetMapping("/asignadas")
    public List<TareaDTO> getTareasAsignadasAlUsuario() {
        Usuario usuario = usuarioService.getUsuarioActual(); // ✅ Ya funciona
        return tareaService.getTareasAsignadasAUsuario(usuario);
    }

    @GetMapping("/{id}")
    public TareaDTO getTareaById(@PathVariable Long id) {
        return tareaService.getTareaById(id);
    }

}
