package com.chattide.web.Service;

import com.chattide.web.DTO.ComentarioDTO;
import com.chattide.web.Mapper.ComentarioMapper;
import com.chattide.web.Modelo.Comentario;
import com.chattide.web.Persistence.ComentarioJpaController;
import com.chattide.web.Persistence.exceptions.NonexistentEntityException;
import com.chattide.web.Utilities.GlobalFunctions.SvUtils;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Juan - Luis
 */
@Named
public class ComentarioService implements IComentarioService {

    @Inject
    ComentarioJpaController comentarioJpaController;

    public ComentarioService() {
        this.comentarioJpaController = new ComentarioJpaController();
    }

    @Override
    public List<ComentarioDTO> findByPublicacion(Long publicacionId) {
        return comentarioJpaController.findByPublicacion(publicacionId).stream()
                .map(ComentarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Comentario> findByUsuario(Long usuarioId) {
        return comentarioJpaController.findByUsuario(usuarioId);
    }

    @Override
    public Comentario findById(Long id) {
        return comentarioJpaController.findComentario(id);

    }

    @Override
    public ArrayList<Comentario> findAll() {
        ArrayList<Comentario> comentarios = SvUtils.toArrayList(comentarioJpaController.findComentarioEntities());
        return comentarios;
    }

    @Override
    public boolean create(Comentario entity) {
        try {
            comentarioJpaController.create(entity);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(ComentarioService.class.getName())
                    .log(Level.SEVERE, "Error creando Comentario", ex);
            return false;
        }
    }

    @Override
    public boolean update(Comentario entity) {
        try {
            comentarioJpaController.edit(entity);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(ComentarioService.class.getName())
                    .log(Level.SEVERE, "Error actualizando Comentario", ex);
            return false;
        }
    }

    @Override
    public void delete(Long id) {
        try {
            comentarioJpaController.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ComentarioService.class.getName())
                    .log(Level.SEVERE, "Error borrando Comentario", ex);
        }

    }
}
