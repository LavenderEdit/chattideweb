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
            Publicacion publicacion_comentario = comentario.getPublicacion_comentario();
            if (publicacion_comentario != null) {
                publicacion_comentario = em.getReference(publicacion_comentario.getClass(), publicacion_comentario.getPublicacionID());
                comentario.setPublicacion_comentario(publicacion_comentario);
            }
            Usuario usuario_comentario = comentario.getUsuario_comentario();
            if (usuario_comentario != null) {
                usuario_comentario = em.getReference(usuario_comentario.getClass(), usuario_comentario.getUsuarioID());
                comentario.setUsuario_comentario(usuario_comentario);
            }
            em.persist(comentario);
            if (publicacion_comentario != null) {
                publicacion_comentario.getListaComentarios().add(comentario);
                publicacion_comentario = em.merge(publicacion_comentario);
            }
            if (usuario_comentario != null) {
                usuario_comentario.getListaComentarios().add(comentario);
                usuario_comentario = em.merge(usuario_comentario);
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
            Publicacion publicacion_comentarioOld = persistentComentario.getPublicacion_comentario();
            Publicacion publicacion_comentarioNew = comentario.getPublicacion_comentario();
            Usuario usuario_comentarioOld = persistentComentario.getUsuario_comentario();
            Usuario usuario_comentarioNew = comentario.getUsuario_comentario();
            if (publicacion_comentarioNew != null) {
                publicacion_comentarioNew = em.getReference(publicacion_comentarioNew.getClass(), publicacion_comentarioNew.getPublicacionID());
                comentario.setPublicacion_comentario(publicacion_comentarioNew);
            }
            if (usuario_comentarioNew != null) {
                usuario_comentarioNew = em.getReference(usuario_comentarioNew.getClass(), usuario_comentarioNew.getUsuarioID());
                comentario.setUsuario_comentario(usuario_comentarioNew);
            }
            comentario = em.merge(comentario);
            if (publicacion_comentarioOld != null && !publicacion_comentarioOld.equals(publicacion_comentarioNew)) {
                publicacion_comentarioOld.getListaComentarios().remove(comentario);
                publicacion_comentarioOld = em.merge(publicacion_comentarioOld);
            }
            if (publicacion_comentarioNew != null && !publicacion_comentarioNew.equals(publicacion_comentarioOld)) {
                publicacion_comentarioNew.getListaComentarios().add(comentario);
                publicacion_comentarioNew = em.merge(publicacion_comentarioNew);
            }
            if (usuario_comentarioOld != null && !usuario_comentarioOld.equals(usuario_comentarioNew)) {
                usuario_comentarioOld.getListaComentarios().remove(comentario);
                usuario_comentarioOld = em.merge(usuario_comentarioOld);
            }
            if (usuario_comentarioNew != null && !usuario_comentarioNew.equals(usuario_comentarioOld)) {
                usuario_comentarioNew.getListaComentarios().add(comentario);
                usuario_comentarioNew = em.merge(usuario_comentarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                long id = comentario.getComentarioID();
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

    public void destroy(long id) throws NonexistentEntityException {
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
            Publicacion publicacion_comentario = comentario.getPublicacion_comentario();
            if (publicacion_comentario != null) {
                publicacion_comentario.getListaComentarios().remove(comentario);
                publicacion_comentario = em.merge(publicacion_comentario);
            }
            Usuario usuario_comentario = comentario.getUsuario_comentario();
            if (usuario_comentario != null) {
                usuario_comentario.getListaComentarios().remove(comentario);
                usuario_comentario = em.merge(usuario_comentario);
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

    public Comentario findComentario(long id) {
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

}
