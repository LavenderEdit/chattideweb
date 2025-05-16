package com.chattide.web.Service;

import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Persistence.UsuarioJpaController;
import com.chattide.web.Persistence.exceptions.NonexistentEntityException;
import com.chattide.web.Utilities.GlobalFunctions.SvUtils;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan - Luis
 */
@Named
public class UsuarioService implements IUsuarioService {

    @Inject
    UsuarioJpaController usuarioJpaController;

    public UsuarioService() {
        this.usuarioJpaController = new UsuarioJpaController();
    }

    @Override
    public Usuario findByEmail(String email) {
        return usuarioJpaController.findByEmail(email);
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioJpaController.findUsuario(id);
    }

    @Override
    public ArrayList<Usuario> findAll() {
        ArrayList<Usuario> usuarios = SvUtils.toArrayList(usuarioJpaController.findUsuarioEntities());
        return usuarios;
    }

    @Override
    public boolean create(Usuario u) {
        try {
            usuarioJpaController.create(u);
            return true;
        } catch (Exception e) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    @Override
    public boolean update(Usuario u) {
        try {
            usuarioJpaController.edit(u);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public void delete(Long id) {
        try {
            usuarioJpaController.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
