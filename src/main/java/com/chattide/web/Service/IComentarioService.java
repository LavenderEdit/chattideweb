package com.chattide.web.Service;

import com.chattide.web.DTO.ComentarioDTO;
import com.chattide.web.Modelo.Comentario;
import com.chattide.web.Service.generic.ICrudService;
import java.util.List;

/**
 *
 * @author Juan - Luis
 */
public interface IComentarioService extends ICrudService<Comentario, Long> {

    List<ComentarioDTO> findByPublicacion(Long publicacionId);

    List<Comentario> findByUsuario(Long usuarioId);
}
