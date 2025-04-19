package com.crm.crm_backend.controller;

import com.crm.crm_backend.dto.ClienteDTO;
import com.crm.crm_backend.entity.Usuario;
import com.crm.crm_backend.service.ClienteService;
import com.crm.crm_backend.service.UsuarioService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final UsuarioService usuarioService;

    @GetMapping
    public List<ClienteDTO> getAllClientes() {
        return clienteService.getAllClientes();
    }

    @GetMapping("/{id}")
    public ClienteDTO getClienteById(@PathVariable Long id) {
        return clienteService.getClienteById(id);
    }

    @PostMapping
    public ClienteDTO createCliente(@RequestBody ClienteDTO clienteDTO) {
        // ❌ Eliminamos creadoPor por ahora
        return clienteService.createCliente(clienteDTO);
    }

    @PutMapping("/{id}")
    public ClienteDTO updateCliente(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        return clienteService.updateCliente(id, clienteDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
    }
}
