package com.crm.crm_backend.controller;

import com.crm.crm_backend.dto.ContactoDTO;
import com.crm.crm_backend.service.ClienteService;
import com.crm.crm_backend.service.ContactoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes/{clienteId}/contactos")
@RequiredArgsConstructor
public class ContactoController {

    private final ContactoService contactoService;
    private final ClienteService clienteService;

    @GetMapping
    public List<ContactoDTO> getContactosByCliente(@PathVariable Long clienteId) {
        return contactoService.getContactosByCliente(clienteService.getClienteEntity(clienteId));
    }

    @PostMapping
    public ContactoDTO createContacto(@PathVariable Long clienteId, @RequestBody ContactoDTO contactoDTO) {
        return contactoService.createContacto(contactoDTO, clienteService.getClienteEntity(clienteId));
    }

    @PutMapping("/{id}")
    public ContactoDTO updateContacto(@PathVariable Long id, @RequestBody ContactoDTO contactoDTO) {
        return contactoService.updateContacto(id, contactoDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteContacto(@PathVariable Long id) {
        contactoService.deleteContacto(id);
    }
}
