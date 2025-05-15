package com.chattide.web.Service;

import com.chattide.web.DTO.PublicacionDTO;
import com.chattide.web.Modelo.Publicacion;
import com.chattide.web.Service.generic.ICrudService;
import java.util.List;

/**
 *
 * @author Juan - Luis
 */
public interface IPublicacionService extends ICrudService<Publicacion, Long> {

    List<PublicacionDTO> findDTOByGrupo(Long grupoId);

    PublicacionDTO findDTOById(Long publId);
}
