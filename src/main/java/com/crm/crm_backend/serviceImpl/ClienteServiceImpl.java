package com.crm.crm_backend.serviceImpl;

import com.crm.crm_backend.dto.ClienteDTO;
import com.crm.crm_backend.entity.Cliente;
import com.crm.crm_backend.entity.Usuario;
import com.crm.crm_backend.exception.ApiException;
import com.crm.crm_backend.repository.ClienteRepository;
import com.crm.crm_backend.service.ClienteService;
import com.crm.crm_backend.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public List<ClienteDTO> getAllClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream().map(MapperUtil::toClienteDTO).collect(Collectors.toList());
    }

    @Override
    public ClienteDTO getClienteById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ApiException("Cliente no encontrado"));
        return MapperUtil.toClienteDTO(cliente); // ✅ Corregido
    }

    @Override
    public ClienteDTO createCliente(ClienteDTO clienteDTO) {
        Cliente cliente = MapperUtil.toClienteEntity(clienteDTO);

        // ✅ Asignar la fecha de creación automáticamente
        if (cliente.getFechaCreacion() == null) {
            cliente.setFechaCreacion(LocalDateTime.now());
        }

        Cliente savedCliente = clienteRepository.save(cliente);
        return MapperUtil.toClienteDTO(savedCliente);
    }

    @Override
    public ClienteDTO updateCliente(Long id, ClienteDTO clienteDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ApiException("Cliente no encontrado"));

        cliente.setNombre(clienteDTO.getNombre());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente.setEmpresa(clienteDTO.getEmpresa());
        cliente.setUbicacion(clienteDTO.getUbicacion());

        Cliente updatedCliente = clienteRepository.save(cliente);
        return MapperUtil.toClienteDTO(updatedCliente);
    }

    @Override
    public void deleteCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ApiException("Cliente no encontrado"));
        clienteRepository.delete(cliente);
    }

    @Override
    public Cliente getClienteEntity(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ApiException("Cliente no encontrado."));
    }
}
