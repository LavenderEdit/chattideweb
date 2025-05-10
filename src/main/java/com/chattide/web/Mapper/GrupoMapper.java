package com.chattide.web.Mapper;

import com.chattide.web.DTO.GrupoDTO;
import com.chattide.web.Modelo.Grupo;

/**
 *
 * @author Juan - Luis
 */
public class GrupoMapper {

    public static GrupoDTO toDTO(Grupo g) {
        GrupoDTO dto = new GrupoDTO();
        dto.setId(g.getGrupoID());
        dto.setNombre(g.getNombre());
        dto.setDescripcion(g.getDescripcionText());
        dto.setTipoPrivacidad(g.getTipoPrivacidad());
        dto.setMiembrosCount(g.getListaGrupoUsuario() != null ? g.getListaGrupoUsuario().size() : 0);
        dto.setPublicacionesCount(g.getListaPublicacion() != null ? g.getListaPublicacion().size() : 0);
        return dto;
    }
}
