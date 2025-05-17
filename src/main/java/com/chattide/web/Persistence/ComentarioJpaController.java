package com.chattide.web.Persistence;

import com.chattide.web.Modelo.Comentario;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.chattide.web.Modelo.Publicacion;
import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Persistence.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 *
 * @author Juan - Luis
 */
public class ComentarioJpaController extends AbstractJpaController implements Serializable {

    public void create(Comentario comentario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Publicacion publicacion = comentario.getPublicacion();
            if (publicacion != null) {
                publicacion = em.getReference(publicacion.getClass(), publicacion.getPublicacionID());
                comentario.setPublicacion(publicacion);
            }
            Usuario autor = comentario.getAutor();
            if (autor != null) {
                autor = em.getReference(autor.getClass(), autor.getUsuarioID());
                comentario.setAutor(autor);
            }
            em.persist(comentario);
            if (publicacion != null) {
                publicacion.getComentarios().add(comentario);
                publicacion = em.merge(publicacion);
            }
            if (autor != null) {
                autor.getComentarios().add(comentario);
                autor = em.merge(autor);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comentario comentario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comentario persistentComentario = em.find(Comentario.class, comentario.getComentarioID());
            Publicacion publicacionOld = persistentComentario.getPublicacion();
            Publicacion publicacionNew = comentario.getPublicacion();
            Usuario autorOld = persistentComentario.getAutor();
            Usuario autorNew = comentario.getAutor();
            if (publicacionNew != null) {
                publicacionNew = em.getReference(publicacionNew.getClass(), publicacionNew.getPublicacionID());
                comentario.setPublicacion(publicacionNew);
            }
            if (autorNew != null) {
                autorNew = em.getReference(autorNew.getClass(), autorNew.getUsuarioID());
                comentario.setAutor(autorNew);
            }
            comentario = em.merge(comentario);
            if (publicacionOld != null && !publicacionOld.equals(publicacionNew)) {
                publicacionOld.getComentarios().remove(comentario);
                publicacionOld = em.merge(publicacionOld);
            }
            if (publicacionNew != null && !publicacionNew.equals(publicacionOld)) {
                publicacionNew.getComentarios().add(comentario);
                publicacionNew = em.merge(publicacionNew);
            }
            if (autorOld != null && !autorOld.equals(autorNew)) {
                autorOld.getComentarios().remove(comentario);
                autorOld = em.merge(autorOld);
            }
            if (autorNew != null && !autorNew.equals(autorOld)) {
                autorNew.getComentarios().add(comentario);
                autorNew = em.merge(autorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = comentario.getComentarioID();
                if (findComentario(id) == null) {
                    throw new NonexistentEntityException("The comentario with id " + id + " no longer exists.");
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
            Comentario comentario;
            try {
                comentario = em.getReference(Comentario.class, id);
                comentario.getComentarioID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comentario with id " + id + " no longer exists.", enfe);
            }
            Publicacion publicacion = comentario.getPublicacion();
            if (publicacion != null) {
                publicacion.getComentarios().remove(comentario);
                publicacion = em.merge(publicacion);
            }
            Usuario autor = comentario.getAutor();
            if (autor != null) {
                autor.getComentarios().remove(comentario);
                autor = em.merge(autor);
            }
            em.remove(comentario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comentario> findComentarioEntities() {
        return findComentarioEntities(true, -1, -1);
    }

    public List<Comentario> findComentarioEntities(int maxResults, int firstResult) {
        return findComentarioEntities(false, maxResults, firstResult);
    }

    private List<Comentario> findComentarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comentario.class));
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

    public Comentario findComentario(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comentario.class, id);
        } finally {
            em.close();
        }
    }

    public int getComentarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comentario> rt = cq.from(Comentario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Comentario> findByPublicacion(Long publicacionId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Comentario> q = em.createQuery(
                    "SELECT c FROM Comentario c "
                    + "WHERE c.publicacion.publicacionID = :pid "
                    + "ORDER BY c.fechaComentario DESC",
                    Comentario.class
            );
            q.setParameter("pid", publicacionId);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Comentario> findByUsuario(Long usuarioId) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Comentario> q = em.createQuery(
                    "SELECT c FROM Comentario c "
                    + "WHERE c.autor.usuarioID = :uid "
                    + "ORDER BY c.fechaComentario DESC",
                    Comentario.class
            );
            q.setParameter("uid", usuarioId);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
