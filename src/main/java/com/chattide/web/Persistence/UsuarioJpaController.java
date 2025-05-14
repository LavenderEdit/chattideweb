package com.chattide.web.Persistence;

import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.chattide.web.Modelo.Usuario_Grupo;
import java.util.ArrayList;
import java.util.List;
import com.chattide.web.Modelo.Publicacion;
import com.chattide.web.Modelo.Comentario;
import com.chattide.web.Modelo.Likes;
import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Persistence.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

/**
 *
 * @author Juan - Luis
 */
public class UsuarioJpaController extends AbstractJpaController implements Serializable {

    public void create(Usuario usuario) {
        if (usuario.getListaUsuarioGrupo() == null) {
            usuario.setListaUsuarioGrupo(new ArrayList<Usuario_Grupo>());
        }
        if (usuario.getListaPublicacion() == null) {
            usuario.setListaPublicacion(new ArrayList<Publicacion>());
        }
        if (usuario.getListaComentarios() == null) {
            usuario.setListaComentarios(new ArrayList<Comentario>());
        }
        if (usuario.getListaLikes() == null) {
            usuario.setListaLikes(new ArrayList<Likes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Usuario_Grupo> attachedListaUsuarioGrupo = new ArrayList<Usuario_Grupo>();
            for (Usuario_Grupo listaUsuarioGrupoUsuario_GrupoToAttach : usuario.getListaUsuarioGrupo()) {
                listaUsuarioGrupoUsuario_GrupoToAttach = em.getReference(listaUsuarioGrupoUsuario_GrupoToAttach.getClass(), listaUsuarioGrupoUsuario_GrupoToAttach.getUsuarioGrupoID());
                attachedListaUsuarioGrupo.add(listaUsuarioGrupoUsuario_GrupoToAttach);
            }
            usuario.setListaUsuarioGrupo(attachedListaUsuarioGrupo);
            List<Publicacion> attachedListaPublicacion = new ArrayList<Publicacion>();
            for (Publicacion listaPublicacionPublicacionToAttach : usuario.getListaPublicacion()) {
                listaPublicacionPublicacionToAttach = em.getReference(listaPublicacionPublicacionToAttach.getClass(), listaPublicacionPublicacionToAttach.getPublicacionID());
                attachedListaPublicacion.add(listaPublicacionPublicacionToAttach);
            }
            usuario.setListaPublicacion(attachedListaPublicacion);
            List<Comentario> attachedListaComentarios = new ArrayList<Comentario>();
            for (Comentario listaComentariosComentarioToAttach : usuario.getListaComentarios()) {
                listaComentariosComentarioToAttach = em.getReference(listaComentariosComentarioToAttach.getClass(), listaComentariosComentarioToAttach.getComentarioID());
                attachedListaComentarios.add(listaComentariosComentarioToAttach);
            }
            usuario.setListaComentarios(attachedListaComentarios);
            List<Likes> attachedListaLikes = new ArrayList<Likes>();
            for (Likes listaLikesLikesToAttach : usuario.getListaLikes()) {
                listaLikesLikesToAttach = em.getReference(listaLikesLikesToAttach.getClass(), listaLikesLikesToAttach.getLikesID());
                attachedListaLikes.add(listaLikesLikesToAttach);
            }
            usuario.setListaLikes(attachedListaLikes);
            em.persist(usuario);
            for (Usuario_Grupo listaUsuarioGrupoUsuario_Grupo : usuario.getListaUsuarioGrupo()) {
                Usuario oldUsuario_grupoOfListaUsuarioGrupoUsuario_Grupo = listaUsuarioGrupoUsuario_Grupo.getUsuario_grupo();
                listaUsuarioGrupoUsuario_Grupo.setUsuario_grupo(usuario);
                listaUsuarioGrupoUsuario_Grupo = em.merge(listaUsuarioGrupoUsuario_Grupo);
                if (oldUsuario_grupoOfListaUsuarioGrupoUsuario_Grupo != null) {
                    oldUsuario_grupoOfListaUsuarioGrupoUsuario_Grupo.getListaUsuarioGrupo().remove(listaUsuarioGrupoUsuario_Grupo);
                    oldUsuario_grupoOfListaUsuarioGrupoUsuario_Grupo = em.merge(oldUsuario_grupoOfListaUsuarioGrupoUsuario_Grupo);
                }
            }
            for (Publicacion listaPublicacionPublicacion : usuario.getListaPublicacion()) {
                Usuario oldUsuario_publicacionOfListaPublicacionPublicacion = listaPublicacionPublicacion.getUsuario_publicacion();
                listaPublicacionPublicacion.setUsuario_publicacion(usuario);
                listaPublicacionPublicacion = em.merge(listaPublicacionPublicacion);
                if (oldUsuario_publicacionOfListaPublicacionPublicacion != null) {
                    oldUsuario_publicacionOfListaPublicacionPublicacion.getListaPublicacion().remove(listaPublicacionPublicacion);
                    oldUsuario_publicacionOfListaPublicacionPublicacion = em.merge(oldUsuario_publicacionOfListaPublicacionPublicacion);
                }
            }
            for (Comentario listaComentariosComentario : usuario.getListaComentarios()) {
                Usuario oldUsuario_comentarioOfListaComentariosComentario = listaComentariosComentario.getUsuario_comentario();
                listaComentariosComentario.setUsuario_comentario(usuario);
                listaComentariosComentario = em.merge(listaComentariosComentario);
                if (oldUsuario_comentarioOfListaComentariosComentario != null) {
                    oldUsuario_comentarioOfListaComentariosComentario.getListaComentarios().remove(listaComentariosComentario);
                    oldUsuario_comentarioOfListaComentariosComentario = em.merge(oldUsuario_comentarioOfListaComentariosComentario);
                }
            }
            for (Likes listaLikesLikes : usuario.getListaLikes()) {
                Usuario oldUsuario_likesOfListaLikesLikes = listaLikesLikes.getUsuario_likes();
                listaLikesLikes.setUsuario_likes(usuario);
                listaLikesLikes = em.merge(listaLikesLikes);
                if (oldUsuario_likesOfListaLikesLikes != null) {
                    oldUsuario_likesOfListaLikesLikes.getListaLikes().remove(listaLikesLikes);
                    oldUsuario_likesOfListaLikesLikes = em.merge(oldUsuario_likesOfListaLikesLikes);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getUsuarioID());
            List<Usuario_Grupo> listaUsuarioGrupoOld = persistentUsuario.getListaUsuarioGrupo();
            List<Usuario_Grupo> listaUsuarioGrupoNew = usuario.getListaUsuarioGrupo();
            List<Publicacion> listaPublicacionOld = persistentUsuario.getListaPublicacion();
            List<Publicacion> listaPublicacionNew = usuario.getListaPublicacion();
            List<Comentario> listaComentariosOld = persistentUsuario.getListaComentarios();
            List<Comentario> listaComentariosNew = usuario.getListaComentarios();
            List<Likes> listaLikesOld = persistentUsuario.getListaLikes();
            List<Likes> listaLikesNew = usuario.getListaLikes();
            List<Usuario_Grupo> attachedListaUsuarioGrupoNew = new ArrayList<Usuario_Grupo>();
            for (Usuario_Grupo listaUsuarioGrupoNewUsuario_GrupoToAttach : listaUsuarioGrupoNew) {
                listaUsuarioGrupoNewUsuario_GrupoToAttach = em.getReference(listaUsuarioGrupoNewUsuario_GrupoToAttach.getClass(), listaUsuarioGrupoNewUsuario_GrupoToAttach.getUsuarioGrupoID());
                attachedListaUsuarioGrupoNew.add(listaUsuarioGrupoNewUsuario_GrupoToAttach);
            }
            listaUsuarioGrupoNew = attachedListaUsuarioGrupoNew;
            usuario.setListaUsuarioGrupo(listaUsuarioGrupoNew);
            List<Publicacion> attachedListaPublicacionNew = new ArrayList<Publicacion>();
            for (Publicacion listaPublicacionNewPublicacionToAttach : listaPublicacionNew) {
                listaPublicacionNewPublicacionToAttach = em.getReference(listaPublicacionNewPublicacionToAttach.getClass(), listaPublicacionNewPublicacionToAttach.getPublicacionID());
                attachedListaPublicacionNew.add(listaPublicacionNewPublicacionToAttach);
            }
            listaPublicacionNew = attachedListaPublicacionNew;
            usuario.setListaPublicacion(listaPublicacionNew);
            List<Comentario> attachedListaComentariosNew = new ArrayList<Comentario>();
            for (Comentario listaComentariosNewComentarioToAttach : listaComentariosNew) {
                listaComentariosNewComentarioToAttach = em.getReference(listaComentariosNewComentarioToAttach.getClass(), listaComentariosNewComentarioToAttach.getComentarioID());
                attachedListaComentariosNew.add(listaComentariosNewComentarioToAttach);
            }
            listaComentariosNew = attachedListaComentariosNew;
            usuario.setListaComentarios(listaComentariosNew);
            List<Likes> attachedListaLikesNew = new ArrayList<Likes>();
            for (Likes listaLikesNewLikesToAttach : listaLikesNew) {
                listaLikesNewLikesToAttach = em.getReference(listaLikesNewLikesToAttach.getClass(), listaLikesNewLikesToAttach.getLikesID());
                attachedListaLikesNew.add(listaLikesNewLikesToAttach);
            }
            listaLikesNew = attachedListaLikesNew;
            usuario.setListaLikes(listaLikesNew);
            usuario = em.merge(usuario);
            for (Usuario_Grupo listaUsuarioGrupoOldUsuario_Grupo : listaUsuarioGrupoOld) {
                if (!listaUsuarioGrupoNew.contains(listaUsuarioGrupoOldUsuario_Grupo)) {
                    listaUsuarioGrupoOldUsuario_Grupo.setUsuario_grupo(null);
                    listaUsuarioGrupoOldUsuario_Grupo = em.merge(listaUsuarioGrupoOldUsuario_Grupo);
                }
            }
            for (Usuario_Grupo listaUsuarioGrupoNewUsuario_Grupo : listaUsuarioGrupoNew) {
                if (!listaUsuarioGrupoOld.contains(listaUsuarioGrupoNewUsuario_Grupo)) {
                    Usuario oldUsuario_grupoOfListaUsuarioGrupoNewUsuario_Grupo = listaUsuarioGrupoNewUsuario_Grupo.getUsuario_grupo();
                    listaUsuarioGrupoNewUsuario_Grupo.setUsuario_grupo(usuario);
                    listaUsuarioGrupoNewUsuario_Grupo = em.merge(listaUsuarioGrupoNewUsuario_Grupo);
                    if (oldUsuario_grupoOfListaUsuarioGrupoNewUsuario_Grupo != null && !oldUsuario_grupoOfListaUsuarioGrupoNewUsuario_Grupo.equals(usuario)) {
                        oldUsuario_grupoOfListaUsuarioGrupoNewUsuario_Grupo.getListaUsuarioGrupo().remove(listaUsuarioGrupoNewUsuario_Grupo);
                        oldUsuario_grupoOfListaUsuarioGrupoNewUsuario_Grupo = em.merge(oldUsuario_grupoOfListaUsuarioGrupoNewUsuario_Grupo);
                    }
                }
            }
            for (Publicacion listaPublicacionOldPublicacion : listaPublicacionOld) {
                if (!listaPublicacionNew.contains(listaPublicacionOldPublicacion)) {
                    listaPublicacionOldPublicacion.setUsuario_publicacion(null);
                    listaPublicacionOldPublicacion = em.merge(listaPublicacionOldPublicacion);
                }
            }
            for (Publicacion listaPublicacionNewPublicacion : listaPublicacionNew) {
                if (!listaPublicacionOld.contains(listaPublicacionNewPublicacion)) {
                    Usuario oldUsuario_publicacionOfListaPublicacionNewPublicacion = listaPublicacionNewPublicacion.getUsuario_publicacion();
                    listaPublicacionNewPublicacion.setUsuario_publicacion(usuario);
                    listaPublicacionNewPublicacion = em.merge(listaPublicacionNewPublicacion);
                    if (oldUsuario_publicacionOfListaPublicacionNewPublicacion != null && !oldUsuario_publicacionOfListaPublicacionNewPublicacion.equals(usuario)) {
                        oldUsuario_publicacionOfListaPublicacionNewPublicacion.getListaPublicacion().remove(listaPublicacionNewPublicacion);
                        oldUsuario_publicacionOfListaPublicacionNewPublicacion = em.merge(oldUsuario_publicacionOfListaPublicacionNewPublicacion);
                    }
                }
            }
            for (Comentario listaComentariosOldComentario : listaComentariosOld) {
                if (!listaComentariosNew.contains(listaComentariosOldComentario)) {
                    listaComentariosOldComentario.setUsuario_comentario(null);
                    listaComentariosOldComentario = em.merge(listaComentariosOldComentario);
                }
            }
            for (Comentario listaComentariosNewComentario : listaComentariosNew) {
                if (!listaComentariosOld.contains(listaComentariosNewComentario)) {
                    Usuario oldUsuario_comentarioOfListaComentariosNewComentario = listaComentariosNewComentario.getUsuario_comentario();
                    listaComentariosNewComentario.setUsuario_comentario(usuario);
                    listaComentariosNewComentario = em.merge(listaComentariosNewComentario);
                    if (oldUsuario_comentarioOfListaComentariosNewComentario != null && !oldUsuario_comentarioOfListaComentariosNewComentario.equals(usuario)) {
                        oldUsuario_comentarioOfListaComentariosNewComentario.getListaComentarios().remove(listaComentariosNewComentario);
                        oldUsuario_comentarioOfListaComentariosNewComentario = em.merge(oldUsuario_comentarioOfListaComentariosNewComentario);
                    }
                }
            }
            for (Likes listaLikesOldLikes : listaLikesOld) {
                if (!listaLikesNew.contains(listaLikesOldLikes)) {
                    listaLikesOldLikes.setUsuario_likes(null);
                    listaLikesOldLikes = em.merge(listaLikesOldLikes);
                }
            }
            for (Likes listaLikesNewLikes : listaLikesNew) {
                if (!listaLikesOld.contains(listaLikesNewLikes)) {
                    Usuario oldUsuario_likesOfListaLikesNewLikes = listaLikesNewLikes.getUsuario_likes();
                    listaLikesNewLikes.setUsuario_likes(usuario);
                    listaLikesNewLikes = em.merge(listaLikesNewLikes);
                    if (oldUsuario_likesOfListaLikesNewLikes != null && !oldUsuario_likesOfListaLikesNewLikes.equals(usuario)) {
                        oldUsuario_likesOfListaLikesNewLikes.getListaLikes().remove(listaLikesNewLikes);
                        oldUsuario_likesOfListaLikesNewLikes = em.merge(oldUsuario_likesOfListaLikesNewLikes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                long id = usuario.getUsuarioID();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getUsuarioID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<Usuario_Grupo> listaUsuarioGrupo = usuario.getListaUsuarioGrupo();
            for (Usuario_Grupo listaUsuarioGrupoUsuario_Grupo : listaUsuarioGrupo) {
                listaUsuarioGrupoUsuario_Grupo.setUsuario_grupo(null);
                listaUsuarioGrupoUsuario_Grupo = em.merge(listaUsuarioGrupoUsuario_Grupo);
            }
            List<Publicacion> listaPublicacion = usuario.getListaPublicacion();
            for (Publicacion listaPublicacionPublicacion : listaPublicacion) {
                listaPublicacionPublicacion.setUsuario_publicacion(null);
                listaPublicacionPublicacion = em.merge(listaPublicacionPublicacion);
            }
            List<Comentario> listaComentarios = usuario.getListaComentarios();
            for (Comentario listaComentariosComentario : listaComentarios) {
                listaComentariosComentario.setUsuario_comentario(null);
                listaComentariosComentario = em.merge(listaComentariosComentario);
            }
            List<Likes> listaLikes = usuario.getListaLikes();
            for (Likes listaLikesLikes : listaLikes) {
                listaLikesLikes.setUsuario_likes(null);
                listaLikesLikes = em.merge(listaLikesLikes);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    // Dentro de UsuarioJpaController
    public Usuario findByEmail(String email) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
