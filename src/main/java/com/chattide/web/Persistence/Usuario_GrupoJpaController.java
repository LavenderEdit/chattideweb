package com.chattide.web.Persistence;

import com.chattide.web.Modelo.Comentario;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Modelo.Grupo;
import com.chattide.web.Modelo.Likes;
import com.chattide.web.Modelo.Publicacion;
import com.chattide.web.Modelo.Usuario_Grupo;
import com.chattide.web.Persistence.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Juan - Luis
 */
public class Usuario_GrupoJpaController extends AbstractJpaController implements Serializable {

    public void create(Usuario_Grupo usuario_Grupo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario = usuario_Grupo.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getUsuarioID());
                usuario_Grupo.setUsuario(usuario);
            }
            Grupo grupo = usuario_Grupo.getGrupo();
            if (grupo != null) {
                grupo = em.getReference(grupo.getClass(), grupo.getGrupoID());
                usuario_Grupo.setGrupo(grupo);
            }
            em.persist(usuario_Grupo);
            if (usuario != null) {
                usuario.getGrupos().add(usuario_Grupo);
                usuario = em.merge(usuario);
            }
            if (grupo != null) {
                grupo.getMiembros().add(usuario_Grupo);
                grupo = em.merge(grupo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario_Grupo usuario_Grupo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario_Grupo persistentUsuario_Grupo = em.find(Usuario_Grupo.class, usuario_Grupo.getUsuarioGrupoID());
            Usuario usuarioOld = persistentUsuario_Grupo.getUsuario();
            Usuario usuarioNew = usuario_Grupo.getUsuario();
            Grupo grupoOld = persistentUsuario_Grupo.getGrupo();
            Grupo grupoNew = usuario_Grupo.getGrupo();
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getUsuarioID());
                usuario_Grupo.setUsuario(usuarioNew);
            }
            if (grupoNew != null) {
                grupoNew = em.getReference(grupoNew.getClass(), grupoNew.getGrupoID());
                usuario_Grupo.setGrupo(grupoNew);
            }
            usuario_Grupo = em.merge(usuario_Grupo);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getGrupos().remove(usuario_Grupo);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getGrupos().add(usuario_Grupo);
                usuarioNew = em.merge(usuarioNew);
            }
            if (grupoOld != null && !grupoOld.equals(grupoNew)) {
                grupoOld.getMiembros().remove(usuario_Grupo);
                grupoOld = em.merge(grupoOld);
            }
            if (grupoNew != null && !grupoNew.equals(grupoOld)) {
                grupoNew.getMiembros().add(usuario_Grupo);
                grupoNew = em.merge(grupoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = usuario_Grupo.getUsuarioGrupoID();
                if (findUsuario_Grupo(id) == null) {
                    throw new NonexistentEntityException("The usuario_Grupo with id " + id + " no longer exists.");
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
            Usuario_Grupo usuarioGrupo;
            try {
                usuarioGrupo = em.getReference(Usuario_Grupo.class, id);
                usuarioGrupo.getUsuarioGrupoID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("La relación Usuario_Grupo con id " + id + " ya no existe.", enfe);
            }

            Usuario usuario = usuarioGrupo.getUsuario();
            Grupo grupo = usuarioGrupo.getGrupo();

            if (usuario != null && grupo != null) {

                for (Publicacion pub : new HashSet<>(usuario.getPublicaciones())) {
                    if (grupo.equals(pub.getGrupo())) {

                        for (Comentario com : new HashSet<>(pub.getComentarios())) {
                            em.remove(em.contains(com) ? com : em.merge(com));
                        }

                        for (Likes like : new HashSet<>(pub.getLikes())) {
                            em.remove(em.contains(like) ? like : em.merge(like));
                        }

                        em.remove(em.contains(pub) ? pub : em.merge(pub));
                    }
                }

                for (Comentario com : new HashSet<>(usuario.getComentarios())) {
                    Publicacion pub = com.getPublicacion();
                    if (pub != null && grupo.equals(pub.getGrupo())) {
                        em.remove(em.contains(com) ? com : em.merge(com));
                    }
                }

                for (Likes like : new HashSet<>(usuario.getLikes())) {
                    Publicacion pub = like.getPublicacion();
                    if (pub != null && grupo.equals(pub.getGrupo())) {
                        em.remove(em.contains(like) ? like : em.merge(like));
                    }
                }

                usuario.getGrupos().remove(usuarioGrupo);
                grupo.getMiembros().remove(usuarioGrupo);
                em.merge(usuario);
                em.merge(grupo);
            }

            em.remove(em.contains(usuarioGrupo) ? usuarioGrupo : em.merge(usuarioGrupo));
            em.getTransaction().commit();

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario_Grupo> findUsuario_GrupoEntities() {
        return findUsuario_GrupoEntities(true, -1, -1);
    }

    public List<Usuario_Grupo> findUsuario_GrupoEntities(int maxResults, int firstResult) {
        return findUsuario_GrupoEntities(false, maxResults, firstResult);
    }

    private List<Usuario_Grupo> findUsuario_GrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario_Grupo.class));
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

    public Usuario_Grupo findUsuario_Grupo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario_Grupo.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuario_GrupoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario_Grupo> rt = cq.from(Usuario_Grupo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Usuario_Grupo findByUserAndGroup(Long uid, Long gid) {
        var em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT ug FROM Usuario_Grupo ug "
                    + "WHERE ug.usuario.usuarioID = :uid "
                    + "  AND ug.grupo.grupoID   = :gid",
                    Usuario_Grupo.class)
                    .setParameter("uid", uid)
                    .setParameter("gid", gid)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } finally {
            em.close();
        }
    }

    public List<Usuario_Grupo> findByUsuario(Long uid) {
        var em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT ug FROM Usuario_Grupo ug "
                    + "WHERE ug.usuario.usuarioID = :uid",
                    Usuario_Grupo.class)
                    .setParameter("uid", uid)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Usuario_Grupo> findByGrupo(Long gid) {
        var em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT ug FROM Usuario_Grupo ug WHERE ug.grupo.grupoID = :gid",
                    Usuario_Grupo.class)
                    .setParameter("gid", gid)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Long countByUsuario(Long userId) {
        var em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT COUNT(ug) FROM Usuario_Grupo ug WHERE ug.usuario.usuarioID = :uid",
                    Long.class)
                    .setParameter("uid", userId)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }
}
