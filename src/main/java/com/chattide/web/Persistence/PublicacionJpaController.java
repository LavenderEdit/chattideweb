package com.chattide.web.Persistence;

import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Modelo.Grupo;
import com.chattide.web.Modelo.Comentario;
import java.util.HashSet;
import java.util.Set;
import com.chattide.web.Modelo.Likes;
import com.chattide.web.Modelo.Publicacion;
import com.chattide.web.Persistence.exceptions.IllegalOrphanException;
import com.chattide.web.Persistence.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juan - Luis
 */
public class PublicacionJpaController extends AbstractJpaController implements Serializable {

    public void create(Publicacion publicacion) {
        if (publicacion.getComentarios() == null) {
            publicacion.setComentarios(new HashSet<Comentario>());
        }
        if (publicacion.getLikes() == null) {
            publicacion.setLikes(new HashSet<Likes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario autor = publicacion.getAutor();
            if (autor != null) {
                autor = em.getReference(autor.getClass(), autor.getUsuarioID());
                publicacion.setAutor(autor);
            }
            Grupo grupo = publicacion.getGrupo();
            if (grupo != null) {
                grupo = em.getReference(grupo.getClass(), grupo.getGrupoID());
                publicacion.setGrupo(grupo);
            }
            Set<Comentario> attachedComentarios = new HashSet<Comentario>();
            for (Comentario comentariosComentarioToAttach : publicacion.getComentarios()) {
                comentariosComentarioToAttach = em.getReference(comentariosComentarioToAttach.getClass(), comentariosComentarioToAttach.getComentarioID());
                attachedComentarios.add(comentariosComentarioToAttach);
            }
            publicacion.setComentarios(attachedComentarios);
            Set<Likes> attachedLikes = new HashSet<Likes>();
            for (Likes likesLikesToAttach : publicacion.getLikes()) {
                likesLikesToAttach = em.getReference(likesLikesToAttach.getClass(), likesLikesToAttach.getLikesID());
                attachedLikes.add(likesLikesToAttach);
            }
            publicacion.setLikes(attachedLikes);
            em.persist(publicacion);
            if (autor != null) {
                autor.getPublicaciones().add(publicacion);
                autor = em.merge(autor);
            }
            if (grupo != null) {
                grupo.getPublicaciones().add(publicacion);
                grupo = em.merge(grupo);
            }
            for (Comentario comentariosComentario : publicacion.getComentarios()) {
                Publicacion oldPublicacionOfComentariosComentario = comentariosComentario.getPublicacion();
                comentariosComentario.setPublicacion(publicacion);
                comentariosComentario = em.merge(comentariosComentario);
                if (oldPublicacionOfComentariosComentario != null) {
                    oldPublicacionOfComentariosComentario.getComentarios().remove(comentariosComentario);
                    oldPublicacionOfComentariosComentario = em.merge(oldPublicacionOfComentariosComentario);
                }
            }
            for (Likes likesLikes : publicacion.getLikes()) {
                Publicacion oldPublicacionOfLikesLikes = likesLikes.getPublicacion();
                likesLikes.setPublicacion(publicacion);
                likesLikes = em.merge(likesLikes);
                if (oldPublicacionOfLikesLikes != null) {
                    oldPublicacionOfLikesLikes.getLikes().remove(likesLikes);
                    oldPublicacionOfLikesLikes = em.merge(oldPublicacionOfLikesLikes);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Publicacion publicacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Publicacion persistentPublicacion = em.find(Publicacion.class, publicacion.getPublicacionID());
            Usuario autorOld = persistentPublicacion.getAutor();
            Usuario autorNew = publicacion.getAutor();
            Grupo grupoOld = persistentPublicacion.getGrupo();
            Grupo grupoNew = publicacion.getGrupo();
            Set<Comentario> comentariosOld = persistentPublicacion.getComentarios();
            Set<Comentario> comentariosNew = publicacion.getComentarios();
            Set<Likes> likesOld = persistentPublicacion.getLikes();
            Set<Likes> likesNew = publicacion.getLikes();
            List<String> illegalOrphanMessages = null;
            for (Comentario comentariosOldComentario : comentariosOld) {
                if (!comentariosNew.contains(comentariosOldComentario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comentario " + comentariosOldComentario + " since its publicacion field is not nullable.");
                }
            }
            for (Likes likesOldLikes : likesOld) {
                if (!likesNew.contains(likesOldLikes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Likes " + likesOldLikes + " since its publicacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (autorNew != null) {
                autorNew = em.getReference(autorNew.getClass(), autorNew.getUsuarioID());
                publicacion.setAutor(autorNew);
            }
            if (grupoNew != null) {
                grupoNew = em.getReference(grupoNew.getClass(), grupoNew.getGrupoID());
                publicacion.setGrupo(grupoNew);
            }
            Set<Comentario> attachedComentariosNew = new HashSet<Comentario>();
            for (Comentario comentariosNewComentarioToAttach : comentariosNew) {
                comentariosNewComentarioToAttach = em.getReference(comentariosNewComentarioToAttach.getClass(), comentariosNewComentarioToAttach.getComentarioID());
                attachedComentariosNew.add(comentariosNewComentarioToAttach);
            }
            comentariosNew = attachedComentariosNew;
            publicacion.setComentarios(comentariosNew);
            Set<Likes> attachedLikesNew = new HashSet<Likes>();
            for (Likes likesNewLikesToAttach : likesNew) {
                likesNewLikesToAttach = em.getReference(likesNewLikesToAttach.getClass(), likesNewLikesToAttach.getLikesID());
                attachedLikesNew.add(likesNewLikesToAttach);
            }
            likesNew = attachedLikesNew;
            publicacion.setLikes(likesNew);
            publicacion = em.merge(publicacion);
            if (autorOld != null && !autorOld.equals(autorNew)) {
                autorOld.getPublicaciones().remove(publicacion);
                autorOld = em.merge(autorOld);
            }
            if (autorNew != null && !autorNew.equals(autorOld)) {
                autorNew.getPublicaciones().add(publicacion);
                autorNew = em.merge(autorNew);
            }
            if (grupoOld != null && !grupoOld.equals(grupoNew)) {
                grupoOld.getPublicaciones().remove(publicacion);
                grupoOld = em.merge(grupoOld);
            }
            if (grupoNew != null && !grupoNew.equals(grupoOld)) {
                grupoNew.getPublicaciones().add(publicacion);
                grupoNew = em.merge(grupoNew);
            }
            for (Comentario comentariosNewComentario : comentariosNew) {
                if (!comentariosOld.contains(comentariosNewComentario)) {
                    Publicacion oldPublicacionOfComentariosNewComentario = comentariosNewComentario.getPublicacion();
                    comentariosNewComentario.setPublicacion(publicacion);
                    comentariosNewComentario = em.merge(comentariosNewComentario);
                    if (oldPublicacionOfComentariosNewComentario != null && !oldPublicacionOfComentariosNewComentario.equals(publicacion)) {
                        oldPublicacionOfComentariosNewComentario.getComentarios().remove(comentariosNewComentario);
                        oldPublicacionOfComentariosNewComentario = em.merge(oldPublicacionOfComentariosNewComentario);
                    }
                }
            }
            for (Likes likesNewLikes : likesNew) {
                if (!likesOld.contains(likesNewLikes)) {
                    Publicacion oldPublicacionOfLikesNewLikes = likesNewLikes.getPublicacion();
                    likesNewLikes.setPublicacion(publicacion);
                    likesNewLikes = em.merge(likesNewLikes);
                    if (oldPublicacionOfLikesNewLikes != null && !oldPublicacionOfLikesNewLikes.equals(publicacion)) {
                        oldPublicacionOfLikesNewLikes.getLikes().remove(likesNewLikes);
                        oldPublicacionOfLikesNewLikes = em.merge(oldPublicacionOfLikesNewLikes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = publicacion.getPublicacionID();
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

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Publicacion publicacion;
            try {
                publicacion = em.getReference(Publicacion.class, id);
                publicacion.getPublicacionID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("La publicación con id " + id + " ya no existe.", enfe);
            }

            for (Comentario comentario : new HashSet<>(publicacion.getComentarios())) {
                em.remove(em.contains(comentario) ? comentario : em.merge(comentario));
            }

            for (Likes like : new HashSet<>(publicacion.getLikes())) {
                em.remove(em.contains(like) ? like : em.merge(like));
            }

            Usuario autor = publicacion.getAutor();
            if (autor != null) {
                autor.getPublicaciones().remove(publicacion);
                em.merge(autor);
            }

            Grupo grupo = publicacion.getGrupo();
            if (grupo != null) {
                grupo.getPublicaciones().remove(publicacion);
                em.merge(grupo);
            }

            em.remove(em.contains(publicacion) ? publicacion : em.merge(publicacion));

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

    public Publicacion findPublicacion(Long id) {
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

    public List<Publicacion> findByGrupo(Long grupoId) {
        var em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT p FROM Publicacion p "
                    + "WHERE p.grupo.grupoID = :gid "
                    + "ORDER BY p.fechaPublicacion DESC",
                    Publicacion.class
            )
                    .setParameter("gid", grupoId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Long countByUsuario(Long userId) {
        var em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT COUNT(p) FROM Publicacion p WHERE p.autor.usuarioID = :uid",
                    Long.class
            )
                    .setParameter("uid", userId)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }
}
