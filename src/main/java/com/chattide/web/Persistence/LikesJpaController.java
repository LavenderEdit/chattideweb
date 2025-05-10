package com.chattide.web.Persistence;

import com.chattide.web.Modelo.Likes;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Modelo.Publicacion;
import com.chattide.web.Persistence.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author Juan - Luis
 */
public class LikesJpaController extends AbstractJpaController implements Serializable {

    public void create(Likes likes) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario_likes = likes.getUsuario_likes();
            if (usuario_likes != null) {
                usuario_likes = em.getReference(usuario_likes.getClass(), usuario_likes.getUsuarioID());
                likes.setUsuario_likes(usuario_likes);
            }
            Publicacion publicacion_likes = likes.getPublicacion_likes();
            if (publicacion_likes != null) {
                publicacion_likes = em.getReference(publicacion_likes.getClass(), publicacion_likes.getPublicacionID());
                likes.setPublicacion_likes(publicacion_likes);
            }
            em.persist(likes);
            if (usuario_likes != null) {
                usuario_likes.getListaLikes().add(likes);
                usuario_likes = em.merge(usuario_likes);
            }
            if (publicacion_likes != null) {
                publicacion_likes.getListaLikes().add(likes);
                publicacion_likes = em.merge(publicacion_likes);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Likes likes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Likes persistentLikes = em.find(Likes.class, likes.getLikesID());
            Usuario usuario_likesOld = persistentLikes.getUsuario_likes();
            Usuario usuario_likesNew = likes.getUsuario_likes();
            Publicacion publicacion_likesOld = persistentLikes.getPublicacion_likes();
            Publicacion publicacion_likesNew = likes.getPublicacion_likes();
            if (usuario_likesNew != null) {
                usuario_likesNew = em.getReference(usuario_likesNew.getClass(), usuario_likesNew.getUsuarioID());
                likes.setUsuario_likes(usuario_likesNew);
            }
            if (publicacion_likesNew != null) {
                publicacion_likesNew = em.getReference(publicacion_likesNew.getClass(), publicacion_likesNew.getPublicacionID());
                likes.setPublicacion_likes(publicacion_likesNew);
            }
            likes = em.merge(likes);
            if (usuario_likesOld != null && !usuario_likesOld.equals(usuario_likesNew)) {
                usuario_likesOld.getListaLikes().remove(likes);
                usuario_likesOld = em.merge(usuario_likesOld);
            }
            if (usuario_likesNew != null && !usuario_likesNew.equals(usuario_likesOld)) {
                usuario_likesNew.getListaLikes().add(likes);
                usuario_likesNew = em.merge(usuario_likesNew);
            }
            if (publicacion_likesOld != null && !publicacion_likesOld.equals(publicacion_likesNew)) {
                publicacion_likesOld.getListaLikes().remove(likes);
                publicacion_likesOld = em.merge(publicacion_likesOld);
            }
            if (publicacion_likesNew != null && !publicacion_likesNew.equals(publicacion_likesOld)) {
                publicacion_likesNew.getListaLikes().add(likes);
                publicacion_likesNew = em.merge(publicacion_likesNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                long id = likes.getLikesID();
                if (findLikes(id) == null) {
                    throw new NonexistentEntityException("The likes with id " + id + " no longer exists.");
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
            Likes likes;
            try {
                likes = em.getReference(Likes.class, id);
                likes.getLikesID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The likes with id " + id + " no longer exists.", enfe);
            }
            Usuario usuario_likes = likes.getUsuario_likes();
            if (usuario_likes != null) {
                usuario_likes.getListaLikes().remove(likes);
                usuario_likes = em.merge(usuario_likes);
            }
            Publicacion publicacion_likes = likes.getPublicacion_likes();
            if (publicacion_likes != null) {
                publicacion_likes.getListaLikes().remove(likes);
                publicacion_likes = em.merge(publicacion_likes);
            }
            em.remove(likes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Likes> findLikesEntities() {
        return findLikesEntities(true, -1, -1);
    }

    public List<Likes> findLikesEntities(int maxResults, int firstResult) {
        return findLikesEntities(false, maxResults, firstResult);
    }

    private List<Likes> findLikesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Likes.class));
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

    public Likes findLikes(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Likes.class, id);
        } finally {
            em.close();
        }
    }

    public int getLikesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Likes> rt = cq.from(Likes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
