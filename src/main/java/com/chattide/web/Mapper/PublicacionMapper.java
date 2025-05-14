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
        dto.setContenido(p.getContenidoText());
        dto.setFechaPublicacion(p.getFechaPublicacion());
        dto.setAutorId(p.getUsuario_publicacion().getUsuarioID());
        dto.setAutorNombre(p.getUsuario_publicacion().getNombre());
        dto.setGrupoId(p.getGrupo_publicacion().getGrupoID());

        dto.setLikeCount(p.getListaLikes() != null
                ? p.getListaLikes().size() : 0);

        dto.setComentarioCount(p.getListaComentarios() != null
                ? p.getListaComentarios().size() : 0);

        return dto;
    }
}
