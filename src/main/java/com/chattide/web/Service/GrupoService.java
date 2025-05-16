package com.chattide.web.Service;

import com.chattide.web.DTO.GrupoDTO;
import com.chattide.web.Mapper.GrupoMapper;
import com.chattide.web.Modelo.Grupo;
import com.chattide.web.Persistence.GrupoJpaController;
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
public class GrupoService implements IGrupoService {

    @Inject
    GrupoJpaController grupoJpaController;

    public GrupoService() {
        this.grupoJpaController = new GrupoJpaController();
    }

    @Override
    public Grupo findById(Long id) {
        return grupoJpaController.findGrupo(id);
    }

    @Override
    public ArrayList<Grupo> findAll() {
        ArrayList<Grupo> grupos = SvUtils.toArrayList(grupoJpaController.findGrupoEntities());
        return grupos;
    }

    @Override
    public boolean create(Grupo entity) {
        try {
            grupoJpaController.create(entity);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(GrupoService.class.getName())
                    .log(Level.SEVERE, "Error creando grupo", ex);
            return false;
        }
    }

    @Override
    public boolean update(Grupo entity) {
        try {
            grupoJpaController.edit(entity);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(GrupoService.class.getName())
                    .log(Level.SEVERE, "Error actualizando grupo", ex);
            return false;
        }
    }

    @Override
    public void delete(Long id) {
        try {
            grupoJpaController.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(GrupoService.class.getName())
                    .log(Level.SEVERE, "Error borrando grupo", ex);
        }
    }

    @Override
    public List<GrupoDTO> findAllDTO() {
        return findAll().stream()
                .map(GrupoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<GrupoDTO> findDTOsByUsuario(Long usuarioId) {
        return grupoJpaController.findGrupoEntitiesByUsuario(usuarioId)
                .stream()
                .map(GrupoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<GrupoDTO> findAvailableDTOs(Long usuarioId) {
        return grupoJpaController.findAvailableGrupoEntities(usuarioId)
                .stream()
                .map(GrupoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GrupoDTO findDTOById(Long id) {
        Grupo g = findById(id);
        return g != null ? GrupoMapper.toDTO(g) : null;
    }
}
