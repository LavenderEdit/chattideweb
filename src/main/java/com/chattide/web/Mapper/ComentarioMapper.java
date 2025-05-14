package com.chattide.web.Mapper;

import com.chattide.web.DTO.ComentarioDTO;
import com.chattide.web.Modelo.Comentario;

/**
 *
 * @author Juan - Luis
 */
public class ComentarioMapper {

    public static ComentarioDTO toDTO(Comentario c) {
        if (c == null) {
            return null;
        }
        ComentarioDTO dto = new ComentarioDTO();
        dto.setId(c.getComentarioID());
        dto.setContenido(c.getContenidoText());
        dto.setFechaComentario(c.getFechaComentario());
        if (c.getPublicacion_comentario() != null) {
            dto.setPublicacionId(c.getPublicacion_comentario().getPublicacionID());
        }
        if (c.getUsuario_comentario() != null) {
            dto.setUsuarioId(c.getUsuario_comentario().getUsuarioID());
            dto.setUsuarioNombre(c.getUsuario_comentario().getNombre());
        }
        return dto;
    }
}
