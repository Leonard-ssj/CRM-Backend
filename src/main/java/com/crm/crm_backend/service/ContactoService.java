package com.crm.crm_backend.service;

import com.crm.crm_backend.dto.ContactoDTO;
import com.crm.crm_backend.entity.Cliente;

import java.util.List;

public interface ContactoService {
    List<ContactoDTO> getContactosByCliente(Cliente cliente);

    ContactoDTO createContacto(ContactoDTO contactoDTO, Cliente cliente);

    ContactoDTO updateContacto(Long id, ContactoDTO contactoDTO);

    void deleteContacto(Long id);

    List<ContactoDTO> getAllContactos(); // nuevo método

    ContactoDTO getContactoById(Long id);

}
