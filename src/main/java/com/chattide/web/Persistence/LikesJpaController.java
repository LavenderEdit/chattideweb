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
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

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
            Usuario usuario = likes.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getUsuarioID());
                likes.setUsuario(usuario);
            }
            Publicacion publicacion = likes.getPublicacion();
            if (publicacion != null) {
                publicacion = em.getReference(publicacion.getClass(), publicacion.getPublicacionID());
                likes.setPublicacion(publicacion);
            }
            em.persist(likes);
            if (usuario != null) {
                usuario.getLikes().add(likes);
                usuario = em.merge(usuario);
            }
            if (publicacion != null) {
                publicacion.getLikes().add(likes);
                publicacion = em.merge(publicacion);
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
            Usuario usuarioOld = persistentLikes.getUsuario();
            Usuario usuarioNew = likes.getUsuario();
            Publicacion publicacionOld = persistentLikes.getPublicacion();
            Publicacion publicacionNew = likes.getPublicacion();
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getUsuarioID());
                likes.setUsuario(usuarioNew);
            }
            if (publicacionNew != null) {
                publicacionNew = em.getReference(publicacionNew.getClass(), publicacionNew.getPublicacionID());
                likes.setPublicacion(publicacionNew);
            }
            likes = em.merge(likes);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getLikes().remove(likes);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getLikes().add(likes);
                usuarioNew = em.merge(usuarioNew);
            }
            if (publicacionOld != null && !publicacionOld.equals(publicacionNew)) {
                publicacionOld.getLikes().remove(likes);
                publicacionOld = em.merge(publicacionOld);
            }
            if (publicacionNew != null && !publicacionNew.equals(publicacionOld)) {
                publicacionNew.getLikes().add(likes);
                publicacionNew = em.merge(publicacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = likes.getLikesID();
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

    public void destroy(Long id) throws NonexistentEntityException {
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
            Usuario usuario = likes.getUsuario();
            if (usuario != null) {
                usuario.getLikes().remove(likes);
                usuario = em.merge(usuario);
            }
            Publicacion publicacion = likes.getPublicacion();
            if (publicacion != null) {
                publicacion.getLikes().remove(likes);
                publicacion = em.merge(publicacion);
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

    public Likes findLikes(Long id) {
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

    public Optional<Likes> findByUserAndPublication(Long userId, Long pubId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Likes> q = em.createQuery(
                    "SELECT l FROM Likes l "
                    + "WHERE l.usuario.usuarioID = :uid "
                    + "AND l.publicacion.publicacionID = :pid",
                    Likes.class
            );
            q.setParameter("uid", userId);
            q.setParameter("pid", pubId);
            Likes like = q.getSingleResult();
            return Optional.of(like);
        } catch (NoResultException ex) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    public Long countByPublication(Long pubId) {
        var em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT COUNT(l) FROM Likes l "
                    + "WHERE l.publicacion.publicacionID = :p",
                    Long.class
            )
                    .setParameter("p", pubId)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }
}
