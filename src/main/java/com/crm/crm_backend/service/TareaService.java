package com.crm.crm_backend.service;

import com.crm.crm_backend.dto.TareaDTO;
import com.crm.crm_backend.entity.Cliente;
import com.crm.crm_backend.entity.Usuario;

import java.util.List;

public interface TareaService {
    List<TareaDTO> getTareasByCliente(Cliente cliente);

    TareaDTO createTarea(TareaDTO tareaDTO, Cliente cliente, Usuario asignadoA);

    TareaDTO updateTarea(Long id, TareaDTO tareaDTO);

    void deleteTarea(Long id);

    List<TareaDTO> getTareasAsignadasAUsuario(Usuario usuario);

    TareaDTO getTareaById(Long id);


}
