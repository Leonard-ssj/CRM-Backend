package com.crm.crm_backend.service;

import com.crm.crm_backend.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario getUsuarioById(Long id);

    Optional<Usuario> getUsuarioByEmail(String email);

    List<Usuario> getAllUsuarios();

    Usuario getUsuarioActual();

}
