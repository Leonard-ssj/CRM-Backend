package com.crm.crm_backend.serviceImpl;

import com.crm.crm_backend.dto.EventoDTO;
import com.crm.crm_backend.entity.Cliente;
import com.crm.crm_backend.entity.Evento;
import com.crm.crm_backend.entity.TipoEvento;
import com.crm.crm_backend.entity.Usuario;
import com.crm.crm_backend.exception.ApiException;
import com.crm.crm_backend.repository.ClienteRepository;
import com.crm.crm_backend.repository.EventoRepository;
import com.crm.crm_backend.repository.UsuarioRepository;
import com.crm.crm_backend.service.EventoService;
import com.crm.crm_backend.util.MapperUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventoServiceImpl implements EventoService {

    private final EventoRepository eventoRepository;
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<EventoDTO> getEventosByCliente(Cliente cliente) {
        return eventoRepository.findByCliente(cliente)
                .stream()
                .map(MapperUtil::toEventoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EventoDTO createEvento(EventoDTO dto) {
        Evento evento = MapperUtil.toEventoEntity(dto);

        // Si viene clienteId, buscar cliente y asignarlo
        if (dto.getClienteId() != null) {
            Cliente cliente = clienteRepository.findById(dto.getClienteId())
                    .orElseThrow(() -> new ApiException("Cliente no encontrado con ID: " + dto.getClienteId()));
            evento.setCliente(cliente);
        }

        // Asignar usuario autenticado
        evento.setUsuario(getCurrentUser());

        return MapperUtil.toEventoDTO(eventoRepository.save(evento));
    }

    @Override
    public EventoDTO updateEvento(Long id, EventoDTO dto) {
        Usuario usuario = getCurrentUser();

        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ApiException("Evento no encontrado"));

        if (!evento.getUsuario().getId().equals(usuario.getId())) {
            throw new ApiException("No tienes permiso para modificar este evento");
        }

        evento.setTitulo(dto.getTitulo());
        evento.setDescripcion(dto.getDescripcion());

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        evento.setFecha(LocalDateTime.parse(dto.getFecha(), formatter));
        evento.setTipo(TipoEvento.valueOf(dto.getTipo().toUpperCase()));

        return MapperUtil.toEventoDTO(eventoRepository.save(evento));
    }

    @Override
    public void deleteEvento(Long id) {
        Usuario usuario = getCurrentUser();

        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ApiException("Evento no encontrado"));

        if (!evento.getUsuario().getId().equals(usuario.getId())) {
            throw new ApiException("No tienes permiso para eliminar este evento");
        }

        eventoRepository.delete(evento);
    }

    @Override
    public List<EventoDTO> getAllEventos() {
        Usuario usuario = getCurrentUser();
        return eventoRepository.findByUsuario(usuario)
                .stream()
                .map(MapperUtil::toEventoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EventoDTO getEventoById(Long id) {
        Usuario usuario = getCurrentUser();

        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ApiException("Evento no encontrado"));

        if (!evento.getUsuario().getId().equals(usuario.getId())) {
            throw new ApiException("No tienes permiso para ver este evento");
        }

        return MapperUtil.toEventoDTO(evento);
    }

    private Usuario getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("Usuario no autenticado"));
    }

}