package com.crm.crm_backend.serviceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.crm.crm_backend.dto.NotaDTO;
import com.crm.crm_backend.entity.Cliente;
import com.crm.crm_backend.entity.Nota;
import com.crm.crm_backend.entity.Usuario;
import com.crm.crm_backend.exception.ApiException;
import com.crm.crm_backend.repository.NotaRepository;
import com.crm.crm_backend.service.NotaService;
import com.crm.crm_backend.service.UsuarioService;
import com.crm.crm_backend.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotaServiceImpl implements NotaService {

    private final NotaRepository notaRepository;
    private final UsuarioService usuarioService;

    @Override
    public List<NotaDTO> getNotasByCliente(Cliente cliente) {
        List<Nota> notas = notaRepository.findByCliente(cliente);
        return notas.stream().map(MapperUtil::toNotaDTO).collect(Collectors.toList());
    }

    @Override
    public NotaDTO createNota(NotaDTO notaDTO, Cliente cliente, Usuario usuario) {
        Nota nota = MapperUtil.toNotaEntity(notaDTO);
        nota.setCliente(cliente);
        nota.setUsuario(usuario);
        Nota savedNota = notaRepository.save(nota);
        return MapperUtil.toNotaDTO(savedNota);
    }

    @Override
    public NotaDTO updateNota(Long id, NotaDTO notaDTO) {
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new ApiException("Nota no encontrada"));

        nota.setContenido(notaDTO.getContenido());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        nota.setFecha(LocalDateTime.parse(notaDTO.getFecha(), formatter));

        // ACTUALIZAR USUARIO RESPONSABLE
        Usuario nuevoUsuario = usuarioService.getUsuarioById(notaDTO.getUsuarioId());
        nota.setUsuario(nuevoUsuario);

        Nota updatedNota = notaRepository.save(nota);
        return MapperUtil.toNotaDTO(updatedNota);
    }

    @Override
    public void deleteNota(Long id) {
        Nota nota = notaRepository.findById(id)
                .orElseThrow(() -> new ApiException("Nota no encontrada"));
        notaRepository.delete(nota);
    }
}
