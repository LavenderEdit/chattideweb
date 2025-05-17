package com.chattide.web.Mapper;

import com.chattide.web.DTO.PublicacionDTO;
import com.chattide.web.Modelo.Publicacion;

/**
 *
 * @author Juan - Luis
 */
public class PublicacionMapper {

    public static PublicacionDTO toDTO(Publicacion p) {
        if (p == null) {
            return null;
        }

        PublicacionDTO dto = new PublicacionDTO();
        dto.setId(p.getPublicacionID());
        dto.setContenido(p.getContenido());
        dto.setFechaPublicacion(p.getFechaPublicacion());
        dto.setAutorId(p.getAutor().getUsuarioID());
        dto.setAutorNombre(p.getAutor().getNombre());
        dto.setAutorAvatar(p.getAutor().getAvatar());
        dto.setGrupoId(p.getGrupo().getGrupoID());

        dto.setLikeCount(p.getLikes() != null
                ? p.getLikes().size() : 0);

        dto.setComentarioCount(p.getComentarios() != null
                ? p.getComentarios().size() : 0);

        return dto;
    }
}
