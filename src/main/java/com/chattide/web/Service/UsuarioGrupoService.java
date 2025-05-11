package com.chattide.web.Service;

import com.chattide.web.Modelo.Usuario_Grupo;
import com.chattide.web.Persistence.Usuario_GrupoJpaController;
import com.chattide.web.Persistence.exceptions.NonexistentEntityException;
import jakarta.inject.Inject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan - Luis
 */
public class UsuarioGrupoService implements IUsuarioGrupoService {

    @Inject
    Usuario_GrupoJpaController ugc;

    public UsuarioGrupoService() {
        this.ugc = new Usuario_GrupoJpaController();
    }

    @Override
    public boolean joinGroup(Long usuarioId, Long grupoId) {
        try {
            Usuario_Grupo ug = new Usuario_Grupo();
            ug.setUsuario_grupo(new com.chattide.web.Modelo.Usuario(usuarioId));
            ug.setGrupo_usuario(new com.chattide.web.Modelo.Grupo(grupoId));
            ugc.create(ug);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioGrupoService.class.getName())
                    .log(Level.SEVERE, "Error uniendo usuario a grupo", ex);
            return false;
        }
    }

    @Override
    public boolean leaveGroup(Long usuarioId, Long grupoId) {
        try {
            Usuario_Grupo ug = ugc.findByUserAndGroup(usuarioId, grupoId);
            if (ug == null) {
                return false;
            }
            ugc.destroy(ug.getUsuarioGrupoID());
            return true;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(UsuarioGrupoService.class.getName())
                    .log(Level.SEVERE, "Error saliendo del grupo", ex);
            return false;
        }
    }

    @Override
    public List<Usuario_Grupo> findByUsuario(Long usuarioId) {
        return ugc.findByUsuario(usuarioId);
    }

    @Override
    public List<Usuario_Grupo> findByGrupo(Long grupoId) {
        return ugc.findByGrupo(grupoId);
    }

    @Override
    public boolean isMember(Long usuarioId, Long grupoId) {
        return ugc.findByUserAndGroup(usuarioId, grupoId) != null;
    }
}
