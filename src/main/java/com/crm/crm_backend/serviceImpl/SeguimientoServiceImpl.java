package com.crm.crm_backend.serviceImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.crm.crm_backend.dto.SeguimientoDTO;
import com.crm.crm_backend.entity.Cliente;
import com.crm.crm_backend.entity.Seguimiento;
import com.crm.crm_backend.entity.TipoSeguimiento;
import com.crm.crm_backend.entity.Usuario;
import com.crm.crm_backend.exception.ApiException;
import com.crm.crm_backend.repository.SeguimientoRepository;
import com.crm.crm_backend.service.SeguimientoService;
import com.crm.crm_backend.service.UsuarioService;
import com.crm.crm_backend.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeguimientoServiceImpl implements SeguimientoService {

    private final SeguimientoRepository seguimientoRepository;
    private final UsuarioService usuarioService;

    @Override
    public List<SeguimientoDTO> getSeguimientosByCliente(Cliente cliente) {
        List<Seguimiento> seguimientos = seguimientoRepository.findByCliente(cliente);
        return seguimientos.stream().map(MapperUtil::toSeguimientoDTO).collect(Collectors.toList());
    }

    @Override
    public SeguimientoDTO createSeguimiento(SeguimientoDTO seguimientoDTO, Cliente cliente, Usuario usuario) {
        Seguimiento seguimiento = MapperUtil.toSeguimientoEntity(seguimientoDTO);
        seguimiento.setCliente(cliente);
        seguimiento.setUsuario(usuario);
        Seguimiento savedSeguimiento = seguimientoRepository.save(seguimiento);
        return MapperUtil.toSeguimientoDTO(savedSeguimiento);
    }

    @Override
    public SeguimientoDTO updateSeguimiento(Long id, SeguimientoDTO seguimientoDTO) {
        Seguimiento seguimiento = seguimientoRepository.findById(id)
                .orElseThrow(() -> new ApiException("Seguimiento no encontrado"));

        seguimiento.setTipo(TipoSeguimiento.valueOf(seguimientoDTO.getTipo().toUpperCase()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        seguimiento.setFecha(LocalDateTime.parse(seguimientoDTO.getFecha(), formatter));

        seguimiento.setComentarios(seguimientoDTO.getComentarios());

        // ✅ ACTUALIZAR USUARIO RESPONSABLE
        Usuario nuevoUsuario = usuarioService.getUsuarioById(seguimientoDTO.getUsuarioId());
        seguimiento.setUsuario(nuevoUsuario);

        Seguimiento updatedSeguimiento = seguimientoRepository.save(seguimiento);
        return MapperUtil.toSeguimientoDTO(updatedSeguimiento);
    }

    @Override
    public void deleteSeguimiento(Long id) {
        Seguimiento seguimiento = seguimientoRepository.findById(id)
                .orElseThrow(() -> new ApiException("Seguimiento no encontrado"));
        seguimientoRepository.delete(seguimiento);
    }
}
