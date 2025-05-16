package com.chattide.web.Service;

import com.chattide.web.DTO.PublicacionDTO;
import com.chattide.web.Mapper.PublicacionMapper;
import com.chattide.web.Modelo.Likes;
import com.chattide.web.Modelo.Publicacion;
import com.chattide.web.Persistence.PublicacionJpaController;
import com.chattide.web.Persistence.exceptions.NonexistentEntityException;
import com.chattide.web.Utilities.GlobalFunctions.SvUtils;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Juan - Luis
 */
@Named
public class PublicacionService implements IPublicacionService {

    @Inject
    PublicacionJpaController publicacionJpaController;

    @Inject
    LikeService likeService;

    public PublicacionService() {
        this.publicacionJpaController = new PublicacionJpaController();
        this.likeService = new LikeService();
    }

    @Override
    public Publicacion findById(Long id) {
        return publicacionJpaController.findPublicacion(id);
    }

    @Override
    public ArrayList<Publicacion> findAll() {
        List<Publicacion> listaPubli = publicacionJpaController.findPublicacionEntities();
        ArrayList<Publicacion> lista = SvUtils.toArrayList(listaPubli);
        return lista;
    }

    @Override
    public List<PublicacionDTO> findDTOByGrupo(Long grupoId) {
        return publicacionJpaController.findByGrupo(grupoId).stream()
                .map(PublicacionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PublicacionDTO findDTOById(Long publId) {
        Publicacion publicacion = publicacionJpaController.findPublicacion(publId);
        PublicacionDTO pubDTO = PublicacionMapper.toDTO(publicacion);
        return pubDTO;
    }

    @Override
    public boolean create(Publicacion entity) {
        try {
            publicacionJpaController.create(entity);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(PublicacionService.class.getName())
                    .log(Level.SEVERE, "Error creando Publicación", ex);
            return false;
        }
    }

    @Override
    public boolean update(Publicacion entity) {
        try {
            publicacionJpaController.edit(entity);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(PublicacionService.class.getName())
                    .log(Level.SEVERE, "Error actualizando Publicación", ex);
            return false;
        }
    }

    @Override
    public void delete(Long id) {
        try {
            publicacionJpaController.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(PublicacionService.class.getName())
                    .log(Level.SEVERE, "Error borrando Publicación", ex);
        }
    }

    @Override
    public List<PublicacionDTO> findDTOByGrupoYUsuario(Long grupoId, Long usuarioId) {
        List<PublicacionDTO> dtos = findDTOByGrupo(grupoId);
        for (PublicacionDTO dto : dtos) {
            Optional<Likes> opt = likeService.findByUserAndPublication(usuarioId, dto.getId());
            dto.setLikeId(opt.map(Likes::getLikesID).orElse(null));
        }
        return dtos;
    }

    @Override
    public long countByUsuario(Long userId) {
        return publicacionJpaController.countByUsuario(userId);
    }
}
