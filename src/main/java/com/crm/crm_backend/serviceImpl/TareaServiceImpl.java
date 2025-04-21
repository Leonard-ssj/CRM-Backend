package com.crm.crm_backend.serviceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.crm.crm_backend.dto.TareaDTO;
import com.crm.crm_backend.entity.Cliente;
import com.crm.crm_backend.entity.EstadoTarea;
import com.crm.crm_backend.entity.Tarea;
import com.crm.crm_backend.entity.Usuario;
import com.crm.crm_backend.exception.ApiException;
import com.crm.crm_backend.repository.TareaRepository;
import com.crm.crm_backend.repository.UsuarioRepository;
import com.crm.crm_backend.service.TareaService;
import com.crm.crm_backend.service.UsuarioService;
import com.crm.crm_backend.util.MapperUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TareaServiceImpl implements TareaService {

    private final TareaRepository tareaRepository;
    private final UsuarioService usuarioService;

    @Override
    public List<TareaDTO> getTareasByCliente(Cliente cliente) {
        List<Tarea> tareas = tareaRepository.findByCliente(cliente);
        return tareas.stream().map(MapperUtil::toTareaDTO).collect(Collectors.toList());
    }

    @Override
    public TareaDTO createTarea(TareaDTO tareaDTO, Cliente cliente, Usuario asignadoA) {
        Tarea tarea = MapperUtil.toTareaEntity(tareaDTO);
        tarea.setCliente(cliente);
        tarea.setAsignadoA(asignadoA);
        Tarea savedTarea = tareaRepository.save(tarea);
        return MapperUtil.toTareaDTO(savedTarea);
    }

    @Override
    public TareaDTO updateTarea(Long id, TareaDTO tareaDTO) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new ApiException("Tarea no encontrada"));

        tarea.setTitulo(tareaDTO.getTitulo());
        tarea.setDescripcion(tareaDTO.getDescripcion());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        tarea.setFechaLimite(LocalDateTime.parse(tareaDTO.getFechaLimite(), formatter));

        tarea.setEstado(EstadoTarea.valueOf(tareaDTO.getEstado().toUpperCase()));

        // ACTUALIZA EL USUARIO ASIGNADO
        Usuario nuevoAsignado = usuarioService.getUsuarioById(tareaDTO.getAsignadoA());
        tarea.setAsignadoA(nuevoAsignado);

        Tarea updatedTarea = tareaRepository.save(tarea);
        return MapperUtil.toTareaDTO(updatedTarea);
    }

    @Override
    public void deleteTarea(Long id) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new ApiException("Tarea no encontrada"));
        tareaRepository.delete(tarea);
    }

    @Override
    public List<TareaDTO> getTareasAsignadasAUsuario(Usuario usuario) {
        List<Tarea> tareas = tareaRepository.findByAsignadoA(usuario);
        return tareas.stream().map(MapperUtil::toTareaDTO).collect(Collectors.toList());
    }

    @Override
    public TareaDTO getTareaById(Long id) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new ApiException("Tarea no encontrada"));
        return MapperUtil.toTareaDTO(tarea);
    }

}
