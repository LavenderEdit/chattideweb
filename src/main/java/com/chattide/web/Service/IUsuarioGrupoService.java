package com.chattide.web.Service;

import com.chattide.web.Modelo.Usuario_Grupo;
import java.util.List;

/**
 *
 * @author Juan - Luis
 */
public interface IUsuarioGrupoService {

    boolean joinGroup(Long usuarioId, Long grupoId);

    boolean leaveGroup(Long usuarioId, Long grupoId);

    List<Usuario_Grupo> findByUsuario(Long usuarioId);

    List<Usuario_Grupo> findByGrupo(Long grupoId);

    boolean isMember(Long usuarioId, Long grupoId);

    long countByUsuario(Long userId);
}
