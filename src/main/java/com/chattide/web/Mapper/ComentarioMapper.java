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
        dto.setContenido(c.getContenido());
        dto.setFechaComentario(c.getFechaComentario());
        dto.setAvatarUsuarioUrl(c.getAutor().getAvatar());
        if (c.getPublicacion() != null) {
            dto.setPublicacionId(c.getPublicacion().getPublicacionID());
        }
        if (c.getAutor() != null) {
            dto.setUsuarioId(c.getAutor().getUsuarioID());
            dto.setUsuarioNombre(c.getAutor().getNombre());
        }
        return dto;
    }
}
