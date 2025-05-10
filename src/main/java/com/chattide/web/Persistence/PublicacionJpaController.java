package com.chattide.web.Persistence;

import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Modelo.Grupo;
import com.chattide.web.Modelo.Comentario;
import java.util.ArrayList;
import java.util.List;
import com.chattide.web.Modelo.Likes;
import com.chattide.web.Modelo.Publicacion;
import com.chattide.web.Persistence.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;

/**
 *
 * @author Juan - Luis
 */
public class PublicacionJpaController extends AbstractJpaController implements Serializable {

    public void create(Publicacion publicacion) {
        if (publicacion.getListaComentarios() == null) {
            publicacion.setListaComentarios(new ArrayList<Comentario>());
        }
        if (publicacion.getListaLikes() == null) {
            publicacion.setListaLikes(new ArrayList<Likes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario_publicacion = publicacion.getUsuario_publicacion();
            if (usuario_publicacion != null) {
                usuario_publicacion = em.getReference(usuario_publicacion.getClass(), usuario_publicacion.getUsuarioID());
                publicacion.setUsuario_publicacion(usuario_publicacion);
            }
            Grupo grupo_publicacion = publicacion.getGrupo_publicacion();
            if (grupo_publicacion != null) {
                grupo_publicacion = em.getReference(grupo_publicacion.getClass(), grupo_publicacion.getGrupoID());
                publicacion.setGrupo_publicacion(grupo_publicacion);
            }
            List<Comentario> attachedListaComentarios = new ArrayList<Comentario>();
            for (Comentario listaComentariosComentarioToAttach : publicacion.getListaComentarios()) {
                listaComentariosComentarioToAttach = em.getReference(listaComentariosComentarioToAttach.getClass(), listaComentariosComentarioToAttach.getComentarioID());
                attachedListaComentarios.add(listaComentariosComentarioToAttach);
            }
            publicacion.setListaComentarios(attachedListaComentarios);
            List<Likes> attachedListaLikes = new ArrayList<Likes>();
            for (Likes listaLikesLikesToAttach : publicacion.getListaLikes()) {
                listaLikesLikesToAttach = em.getReference(listaLikesLikesToAttach.getClass(), listaLikesLikesToAttach.getLikesID());
                attachedListaLikes.add(listaLikesLikesToAttach);
            }
            publicacion.setListaLikes(attachedListaLikes);
            em.persist(publicacion);
            if (usuario_publicacion != null) {
                usuario_publicacion.getListaPublicacion().add(publicacion);
                usuario_publicacion = em.merge(usuario_publicacion);
            }
            if (grupo_publicacion != null) {
                grupo_publicacion.getListaPublicacion().add(publicacion);
                grupo_publicacion = em.merge(grupo_publicacion);
            }
            for (Comentario listaComentariosComentario : publicacion.getListaComentarios()) {
                Publicacion oldPublicacion_comentarioOfListaComentariosComentario = listaComentariosComentario.getPublicacion_comentario();
                listaComentariosComentario.setPublicacion_comentario(publicacion);
                listaComentariosComentario = em.merge(listaComentariosComentario);
                if (oldPublicacion_comentarioOfListaComentariosComentario != null) {
                    oldPublicacion_comentarioOfListaComentariosComentario.getListaComentarios().remove(listaComentariosComentario);
                    oldPublicacion_comentarioOfListaComentariosComentario = em.merge(oldPublicacion_comentarioOfListaComentariosComentario);
                }
            }
            for (Likes listaLikesLikes : publicacion.getListaLikes()) {
                Publicacion oldPublicacion_likesOfListaLikesLikes = listaLikesLikes.getPublicacion_likes();
                listaLikesLikes.setPublicacion_likes(publicacion);
                listaLikesLikes = em.merge(listaLikesLikes);
                if (oldPublicacion_likesOfListaLikesLikes != null) {
                    oldPublicacion_likesOfListaLikesLikes.getListaLikes().remove(listaLikesLikes);
                    oldPublicacion_likesOfListaLikesLikes = em.merge(oldPublicacion_likesOfListaLikesLikes);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Publicacion publicacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Publicacion persistentPublicacion = em.find(Publicacion.class, publicacion.getPublicacionID());
            Usuario usuario_publicacionOld = persistentPublicacion.getUsuario_publicacion();
            Usuario usuario_publicacionNew = publicacion.getUsuario_publicacion();
            Grupo grupo_publicacionOld = persistentPublicacion.getGrupo_publicacion();
            Grupo grupo_publicacionNew = publicacion.getGrupo_publicacion();
            List<Comentario> listaComentariosOld = persistentPublicacion.getListaComentarios();
            List<Comentario> listaComentariosNew = publicacion.getListaComentarios();
            List<Likes> listaLikesOld = persistentPublicacion.getListaLikes();
            List<Likes> listaLikesNew = publicacion.getListaLikes();
            if (usuario_publicacionNew != null) {
                usuario_publicacionNew = em.getReference(usuario_publicacionNew.getClass(), usuario_publicacionNew.getUsuarioID());
                publicacion.setUsuario_publicacion(usuario_publicacionNew);
            }
            if (grupo_publicacionNew != null) {
                grupo_publicacionNew = em.getReference(grupo_publicacionNew.getClass(), grupo_publicacionNew.getGrupoID());
                publicacion.setGrupo_publicacion(grupo_publicacionNew);
            }
            List<Comentario> attachedListaComentariosNew = new ArrayList<Comentario>();
            for (Comentario listaComentariosNewComentarioToAttach : listaComentariosNew) {
                listaComentariosNewComentarioToAttach = em.getReference(listaComentariosNewComentarioToAttach.getClass(), listaComentariosNewComentarioToAttach.getComentarioID());
                attachedListaComentariosNew.add(listaComentariosNewComentarioToAttach);
            }
            listaComentariosNew = attachedListaComentariosNew;
            publicacion.setListaComentarios(listaComentariosNew);
            List<Likes> attachedListaLikesNew = new ArrayList<Likes>();
            for (Likes listaLikesNewLikesToAttach : listaLikesNew) {
                listaLikesNewLikesToAttach = em.getReference(listaLikesNewLikesToAttach.getClass(), listaLikesNewLikesToAttach.getLikesID());
                attachedListaLikesNew.add(listaLikesNewLikesToAttach);
            }
            listaLikesNew = attachedListaLikesNew;
            publicacion.setListaLikes(listaLikesNew);
            publicacion = em.merge(publicacion);
            if (usuario_publicacionOld != null && !usuario_publicacionOld.equals(usuario_publicacionNew)) {
                usuario_publicacionOld.getListaPublicacion().remove(publicacion);
                usuario_publicacionOld = em.merge(usuario_publicacionOld);
            }
            if (usuario_publicacionNew != null && !usuario_publicacionNew.equals(usuario_publicacionOld)) {
                usuario_publicacionNew.getListaPublicacion().add(publicacion);
                usuario_publicacionNew = em.merge(usuario_publicacionNew);
            }
            if (grupo_publicacionOld != null && !grupo_publicacionOld.equals(grupo_publicacionNew)) {
                grupo_publicacionOld.getListaPublicacion().remove(publicacion);
                grupo_publicacionOld = em.merge(grupo_publicacionOld);
            }
            if (grupo_publicacionNew != null && !grupo_publicacionNew.equals(grupo_publicacionOld)) {
                grupo_publicacionNew.getListaPublicacion().add(publicacion);
                grupo_publicacionNew = em.merge(grupo_publicacionNew);
            }
            for (Comentario listaComentariosOldComentario : listaComentariosOld) {
                if (!listaComentariosNew.contains(listaComentariosOldComentario)) {
                    listaComentariosOldComentario.setPublicacion_comentario(null);
                    listaComentariosOldComentario = em.merge(listaComentariosOldComentario);
                }
            }
            for (Comentario listaComentariosNewComentario : listaComentariosNew) {
                if (!listaComentariosOld.contains(listaComentariosNewComentario)) {
                    Publicacion oldPublicacion_comentarioOfListaComentariosNewComentario = listaComentariosNewComentario.getPublicacion_comentario();
                    listaComentariosNewComentario.setPublicacion_comentario(publicacion);
                    listaComentariosNewComentario = em.merge(listaComentariosNewComentario);
                    if (oldPublicacion_comentarioOfListaComentariosNewComentario != null && !oldPublicacion_comentarioOfListaComentariosNewComentario.equals(publicacion)) {
                        oldPublicacion_comentarioOfListaComentariosNewComentario.getListaComentarios().remove(listaComentariosNewComentario);
                        oldPublicacion_comentarioOfListaComentariosNewComentario = em.merge(oldPublicacion_comentarioOfListaComentariosNewComentario);
                    }
                }
            }
            for (Likes listaLikesOldLikes : listaLikesOld) {
                if (!listaLikesNew.contains(listaLikesOldLikes)) {
                    listaLikesOldLikes.setPublicacion_likes(null);
                    listaLikesOldLikes = em.merge(listaLikesOldLikes);
                }
            }
            for (Likes listaLikesNewLikes : listaLikesNew) {
                if (!listaLikesOld.contains(listaLikesNewLikes)) {
                    Publicacion oldPublicacion_likesOfListaLikesNewLikes = listaLikesNewLikes.getPublicacion_likes();
                    listaLikesNewLikes.setPublicacion_likes(publicacion);
                    listaLikesNewLikes = em.merge(listaLikesNewLikes);
                    if (oldPublicacion_likesOfListaLikesNewLikes != null && !oldPublicacion_likesOfListaLikesNewLikes.equals(publicacion)) {
                        oldPublicacion_likesOfListaLikesNewLikes.getListaLikes().remove(listaLikesNewLikes);
                        oldPublicacion_likesOfListaLikesNewLikes = em.merge(oldPublicacion_likesOfListaLikesNewLikes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                long id = publicacion.getPublicacionID();
                if (findPublicacion(id) == null) {
                    throw new NonexistentEntityException("The publicacion with id " + id + " no longer exists.");
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
            Publicacion publicacion;
            try {
                publicacion = em.getReference(Publicacion.class, id);
                publicacion.getPublicacionID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The publicacion with id " + id + " no longer exists.", enfe);
            }
            Usuario usuario_publicacion = publicacion.getUsuario_publicacion();
            if (usuario_publicacion != null) {
                usuario_publicacion.getListaPublicacion().remove(publicacion);
                usuario_publicacion = em.merge(usuario_publicacion);
            }
            Grupo grupo_publicacion = publicacion.getGrupo_publicacion();
            if (grupo_publicacion != null) {
                grupo_publicacion.getListaPublicacion().remove(publicacion);
                grupo_publicacion = em.merge(grupo_publicacion);
            }
            List<Comentario> listaComentarios = publicacion.getListaComentarios();
            for (Comentario listaComentariosComentario : listaComentarios) {
                listaComentariosComentario.setPublicacion_comentario(null);
                listaComentariosComentario = em.merge(listaComentariosComentario);
            }
            List<Likes> listaLikes = publicacion.getListaLikes();
            for (Likes listaLikesLikes : listaLikes) {
                listaLikesLikes.setPublicacion_likes(null);
                listaLikesLikes = em.merge(listaLikesLikes);
            }
            em.remove(publicacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Publicacion> findPublicacionEntities() {
        return findPublicacionEntities(true, -1, -1);
    }

    public List<Publicacion> findPublicacionEntities(int maxResults, int firstResult) {
        return findPublicacionEntities(false, maxResults, firstResult);
    }

    private List<Publicacion> findPublicacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Publicacion.class));
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

    public Publicacion findPublicacion(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Publicacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPublicacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Publicacion> rt = cq.from(Publicacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
