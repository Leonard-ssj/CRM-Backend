package com.crm.crm_backend.serviceImpl;

import com.crm.crm_backend.dto.ContactoDTO;
import com.crm.crm_backend.entity.Cliente;
import com.crm.crm_backend.entity.Contacto;
import com.crm.crm_backend.exception.ApiException;
import com.crm.crm_backend.repository.ContactoRepository;
import com.crm.crm_backend.service.ContactoService;
import com.crm.crm_backend.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactoServiceImpl implements ContactoService {

    private final ContactoRepository contactoRepository;

    @Override
    public List<ContactoDTO> getContactosByCliente(Cliente cliente) {
        List<Contacto> contactos = contactoRepository.findByCliente(cliente);
        return contactos.stream().map(MapperUtil::toContactoDTO).collect(Collectors.toList());
    }

    @Override
    public ContactoDTO createContacto(ContactoDTO contactoDTO, Cliente cliente) {
        Contacto contacto = MapperUtil.toContactoEntity(contactoDTO);
        contacto.setCliente(cliente);
        Contacto savedContacto = contactoRepository.save(contacto);
        return MapperUtil.toContactoDTO(savedContacto);
    }

    @Override
    public ContactoDTO updateContacto(Long id, ContactoDTO contactoDTO) {
        Contacto contacto = contactoRepository.findById(id)
                .orElseThrow(() -> new ApiException("Contacto no encontrado"));

        contacto.setNombre(contactoDTO.getNombre());
        contacto.setEmail(contactoDTO.getEmail());
        contacto.setTelefono(contactoDTO.getTelefono());
        contacto.setPuesto(contactoDTO.getPuesto());
        contacto.setNotas(contactoDTO.getNotas());

        Contacto updatedContacto = contactoRepository.save(contacto);
        return MapperUtil.toContactoDTO(updatedContacto);
    }

    @Override
    public void deleteContacto(Long id) {
        Contacto contacto = contactoRepository.findById(id)
                .orElseThrow(() -> new ApiException("Contacto no encontrado"));
        contactoRepository.delete(contacto);
    }

    @Override
    public List<ContactoDTO> getAllContactos() {
        List<Contacto> contactos = contactoRepository.findAll();
        return contactos.stream().map(MapperUtil::toContactoDTO).collect(Collectors.toList());
    }

    @Override
    public ContactoDTO getContactoById(Long id) {
        Contacto contacto = contactoRepository.findById(id)
                .orElseThrow(() -> new ApiException("Contacto no encontrado"));
        return MapperUtil.toContactoDTO(contacto);
    }

}
