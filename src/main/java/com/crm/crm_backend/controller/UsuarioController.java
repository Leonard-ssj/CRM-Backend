package com.crm.crm_backend.controller;

import com.crm.crm_backend.dto.UsuarioDTO;
import com.crm.crm_backend.service.UsuarioService;
import com.crm.crm_backend.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioDTO> getAllUsuarios() {
        return usuarioService.getAllUsuarios().stream()
                .map(MapperUtil::toUsuarioDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UsuarioDTO getUsuarioById(@PathVariable Long id) {
        return MapperUtil.toUsuarioDTO(usuarioService.getUsuarioById(id));
    }
}
