package com.chattide.web.Service;

import com.chattide.web.Modelo.Likes;
import com.chattide.web.Persistence.LikesJpaController;
import com.chattide.web.Persistence.exceptions.NonexistentEntityException;
import com.chattide.web.Utilities.GlobalFunctions.SvUtils;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan - Luis
 */
@Named
public class LikeService implements ILikeService {

    @Inject
    LikesJpaController ljc;

    public LikeService() {
        this.ljc = new LikesJpaController();
    }

    @Override
    public Likes findById(Long id) {
        return ljc.findLikes(id);
    }

    @Override
    public ArrayList<Likes> findAll() {
        ArrayList<Likes> lista = SvUtils.toArrayList(ljc.findLikesEntities());
        return lista;
    }

    @Override
    public boolean create(Likes entity) {
        try {
            ljc.create(entity);
            return true;
        } catch (Exception er) {
            Logger.getLogger(LikeService.class.getName())
                    .log(Level.SEVERE, "Error creando like", er);
            return false;
        }
    }

    @Override
    public boolean update(Likes entity) {
        try {
            ljc.edit(entity);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(LikeService.class.getName())
                    .log(Level.SEVERE, "Error al editar el like", ex);
            return false;
        }
    }

    @Override
    public void delete(Long id) {
        try {
            ljc.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(GrupoService.class.getName())
                    .log(Level.SEVERE, "Error borrando el like", ex);
        }
    }

    @Override
    public Optional<Likes> findByUserAndPublication(Long userId, Long pubId) {
        Optional<Likes> existing = ljc.findByUserAndPublication(userId, pubId);
        return existing;
    }

    @Override
    public boolean deleteLike(Long likeId) {
        try {
            ljc.destroy(likeId);
            return true;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(GrupoService.class.getName())
                    .log(Level.SEVERE, "Error borrando el like", ex);
            return false;
        }
    }

    @Override
    public Long countByPublication(Long pubId) {
        return ljc.countByPublication(pubId);
    }
}
