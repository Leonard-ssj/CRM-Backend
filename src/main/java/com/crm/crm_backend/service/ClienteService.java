package com.crm.crm_backend.service;

import com.crm.crm_backend.dto.ClienteDTO;
import com.crm.crm_backend.entity.Cliente;
import com.crm.crm_backend.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface ClienteService {
    
    List<ClienteDTO> getAllClientes();

    ClienteDTO getClienteById(Long id); // Cambiar retorno

    ClienteDTO createCliente(ClienteDTO clienteDTO);

    ClienteDTO updateCliente(Long id, ClienteDTO clienteDTO);

    void deleteCliente(Long id);

    Cliente getClienteEntity(Long id);
}
