package com.chattide.web.Service;

import com.chattide.web.DTO.GrupoDTO;
import com.chattide.web.Modelo.Grupo;
import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Service.generic.ICrudService;
import java.util.List;

/**
 *
 * @author Juan - Luis
 */
public interface IGrupoService extends ICrudService<Grupo, Long> {

    boolean createGrupoWithConnection(Grupo grupo, Usuario usuario);
    
    List<GrupoDTO> findAllDTO();

    List<GrupoDTO> findDTOsByUsuario(Long usuarioId);

    List<GrupoDTO> findAvailableDTOs(Long usuarioId);

    GrupoDTO findDTOById(Long id);
}
