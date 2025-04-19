package com.crm.crm_backend.controller;

import com.crm.crm_backend.dto.ContactoDTO;
import com.crm.crm_backend.service.ContactoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contactos")
@RequiredArgsConstructor
public class ContactoGeneralController {

    private final ContactoService contactoService;

    // Obtener todos los contactos sin importar el cliente
    @GetMapping
    public List<ContactoDTO> getAllContactos() {
        return contactoService.getAllContactos();
    }

    @GetMapping("/{id}")
    public ContactoDTO getContactoById(@PathVariable Long id) {
        return contactoService.getContactoById(id);
    }

}
