package com.crm.crm_backend.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.crm.crm_backend.dto.*;
import com.crm.crm_backend.entity.*;

public class MapperUtil {

    public static ClienteDTO toClienteDTO(Cliente cliente) {
        return ClienteDTO.builder()
                .id(cliente.getId())
                .nombre(cliente.getNombre())
                .email(cliente.getEmail())
                .telefono(cliente.getTelefono())
                .empresa(cliente.getEmpresa())
                .ubicacion(cliente.getUbicacion())
                // Prevenir null en fechaCreacion
                .fechaCreacion(cliente.getFechaCreacion() != null ? cliente.getFechaCreacion().toString() : null)
                .build();
        // Eliminamos creadoPor del mapeo
        // .creadoPor(cliente.getCreadoPor() != null ? cliente.getCreadoPor().getEmail()
        // : null)
    }

    public static Cliente toClienteEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setEmpresa(dto.getEmpresa());
        cliente.setUbicacion(dto.getUbicacion());
        return cliente;
    }

    public static ContactoDTO toContactoDTO(Contacto contacto) {
        ContactoDTO dto = new ContactoDTO();
        dto.setId(contacto.getId());
        dto.setNombre(contacto.getNombre());
        dto.setEmail(contacto.getEmail());
        dto.setTelefono(contacto.getTelefono());
        dto.setPuesto(contacto.getPuesto());
        dto.setNotas(contacto.getNotas());
        dto.setClienteId(contacto.getCliente().getId());
        dto.setClienteNombre(contacto.getCliente().getNombre());
        return dto;
    }

    public static Contacto toContactoEntity(ContactoDTO dto) {
        Contacto contacto = new Contacto();
        contacto.setNombre(dto.getNombre());
        contacto.setEmail(dto.getEmail());
        contacto.setTelefono(dto.getTelefono());
        contacto.setPuesto(dto.getPuesto());
        contacto.setNotas(dto.getNotas());
        return contacto;
    }

    public static TareaDTO toTareaDTO(Tarea tarea) {
        TareaDTO dto = new TareaDTO();
        dto.setId(tarea.getId());
        dto.setTitulo(tarea.getTitulo());
        dto.setDescripcion(tarea.getDescripcion());
        dto.setFechaLimite(tarea.getFechaLimite().toString());
        dto.setHoraLimite(tarea.getHoraLimite());
        dto.setEstado(tarea.getEstado().name());
        dto.setClienteId(tarea.getCliente().getId());
        dto.setAsignadoA(tarea.getAsignadoA().getId());

        dto.setClienteNombre(tarea.getCliente().getNombre());
        dto.setAsignadoANombre(tarea.getAsignadoA().getNombre());
        return dto;
    }

    public static Tarea toTareaEntity(TareaDTO dto) {
        Tarea tarea = new Tarea();
        tarea.setTitulo(dto.getTitulo());
        tarea.setDescripcion(dto.getDescripcion());
        tarea.setEstado(mapEstadoTarea(dto.getEstado()));

        // Asegurar que fecha y hora se combinen correctamente
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String fechaConHora = dto.getFechaLimite().contains("T")
                ? dto.getFechaLimite()
                : dto.getFechaLimite() + "T" + dto.getHoraLimite();

        tarea.setFechaLimite(LocalDateTime.parse(fechaConHora, formatter));
        tarea.setHoraLimite(dto.getHoraLimite());
        return tarea;
    }

    public static SeguimientoDTO toSeguimientoDTO(Seguimiento seguimiento) {
        SeguimientoDTO dto = new SeguimientoDTO();
        dto.setId(seguimiento.getId());
        dto.setTipo(seguimiento.getTipo().name());
        dto.setFecha(seguimiento.getFecha().toString());
        dto.setComentarios(seguimiento.getComentarios());
        dto.setClienteId(seguimiento.getCliente().getId());
        dto.setUsuarioId(seguimiento.getUsuario().getId());
        return dto;
    }

    public static Seguimiento toSeguimientoEntity(SeguimientoDTO dto) {
        Seguimiento seguimiento = new Seguimiento();
        seguimiento.setTipo(TipoSeguimiento.valueOf(dto.getTipo().toUpperCase()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        seguimiento.setFecha(LocalDateTime.parse(dto.getFecha(), formatter));
        seguimiento.setComentarios(dto.getComentarios());
        return seguimiento;
    }

    public static NotaDTO toNotaDTO(Nota nota) {
        NotaDTO dto = new NotaDTO();
        dto.setId(nota.getId());
        dto.setContenido(nota.getContenido());
        dto.setFecha(nota.getFecha().toString());
        dto.setClienteId(nota.getCliente().getId());
        dto.setUsuarioId(nota.getUsuario().getId());
        return dto;
    }

    public static Nota toNotaEntity(NotaDTO dto) {
        Nota nota = new Nota();
        nota.setContenido(dto.getContenido());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        nota.setFecha(LocalDateTime.parse(dto.getFecha(), formatter));
        return nota;
    }

    public static UsuarioDTO toUsuarioDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setRol(usuario.getRol().name());
        return dto;
    }

    public static EstadoTarea mapEstadoTarea(String estado) {
        switch (estado.toLowerCase()) {
            case "pendiente":
                return EstadoTarea.PENDIENTE;
            case "en progreso":
            case "en_progreso":
                return EstadoTarea.EN_PROGRESO;
            case "completada":
                return EstadoTarea.COMPLETADA;
            default:
                throw new IllegalArgumentException("Estado de tarea inválido: " + estado);
        }
    }

    // 4. MapperUtil (añadir métodos)
    public static EventoDTO toEventoDTO(Evento evento) {
        EventoDTO dto = new EventoDTO();
        dto.setId(evento.getId());
        dto.setTitulo(evento.getTitulo());
        dto.setFecha(evento.getFecha().toString());
        dto.setTipo(evento.getTipo().name());
        dto.setDescripcion(evento.getDescripcion());
        if (evento.getCliente() != null) {
            dto.setClienteId(evento.getCliente().getId());
            dto.setClienteNombre(evento.getCliente().getNombre());
        }
        if (evento.getUsuario() != null) {
            dto.setUsuarioId(evento.getUsuario().getId());
        }
        return dto;
    }

    public static Evento toEventoEntity(EventoDTO dto) {
        Evento evento = new Evento();
        evento.setTitulo(dto.getTitulo());
        evento.setDescripcion(dto.getDescripcion());
        evento.setTipo(TipoEvento.valueOf(dto.getTipo().toUpperCase()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        evento.setFecha(LocalDateTime.parse(dto.getFecha(), formatter));
        return evento;
    }

}
