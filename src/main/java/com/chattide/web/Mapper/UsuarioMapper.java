package com.chattide.web.Mapper;

import com.chattide.web.DTO.UsuarioDTO;
import com.chattide.web.Modelo.Usuario;

/**
 *
 * @author Juan - Luis
 */
public class UsuarioMapper {

    public static UsuarioDTO toDTO(Usuario u) {
        UsuarioDTO dto = new UsuarioDTO(
                u.getUsuarioID(),
                u.getNombre(),
                u.getEmail(),
                u.getAvatar()
        );
        return dto;
    }
}
