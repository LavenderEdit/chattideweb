package com.chattide.web.Persistence;

import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Modelo.Grupo;
import com.chattide.web.Modelo.Usuario_Grupo;
import com.chattide.web.Persistence.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
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
            Usuario usuario_grupo = usuario_Grupo.getUsuario_grupo();
            if (usuario_grupo != null) {
                usuario_grupo = em.getReference(usuario_grupo.getClass(), usuario_grupo.getUsuarioID());
                usuario_Grupo.setUsuario_grupo(usuario_grupo);
            }
            Grupo grupo_usuario = usuario_Grupo.getGrupo_usuario();
            if (grupo_usuario != null) {
                grupo_usuario = em.getReference(grupo_usuario.getClass(), grupo_usuario.getGrupoID());
                usuario_Grupo.setGrupo_usuario(grupo_usuario);
            }
            em.persist(usuario_Grupo);
            if (usuario_grupo != null) {
                usuario_grupo.getListaUsuarioGrupo().add(usuario_Grupo);
                usuario_grupo = em.merge(usuario_grupo);
            }
            if (grupo_usuario != null) {
                grupo_usuario.getListaGrupoUsuario().add(usuario_Grupo);
                grupo_usuario = em.merge(grupo_usuario);
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
            Usuario usuario_grupoOld = persistentUsuario_Grupo.getUsuario_grupo();
            Usuario usuario_grupoNew = usuario_Grupo.getUsuario_grupo();
            Grupo grupo_usuarioOld = persistentUsuario_Grupo.getGrupo_usuario();
            Grupo grupo_usuarioNew = usuario_Grupo.getGrupo_usuario();
            if (usuario_grupoNew != null) {
                usuario_grupoNew = em.getReference(usuario_grupoNew.getClass(), usuario_grupoNew.getUsuarioID());
                usuario_Grupo.setUsuario_grupo(usuario_grupoNew);
            }
            if (grupo_usuarioNew != null) {
                grupo_usuarioNew = em.getReference(grupo_usuarioNew.getClass(), grupo_usuarioNew.getGrupoID());
                usuario_Grupo.setGrupo_usuario(grupo_usuarioNew);
            }
            usuario_Grupo = em.merge(usuario_Grupo);
            if (usuario_grupoOld != null && !usuario_grupoOld.equals(usuario_grupoNew)) {
                usuario_grupoOld.getListaUsuarioGrupo().remove(usuario_Grupo);
                usuario_grupoOld = em.merge(usuario_grupoOld);
            }
            if (usuario_grupoNew != null && !usuario_grupoNew.equals(usuario_grupoOld)) {
                usuario_grupoNew.getListaUsuarioGrupo().add(usuario_Grupo);
                usuario_grupoNew = em.merge(usuario_grupoNew);
            }
            if (grupo_usuarioOld != null && !grupo_usuarioOld.equals(grupo_usuarioNew)) {
                grupo_usuarioOld.getListaGrupoUsuario().remove(usuario_Grupo);
                grupo_usuarioOld = em.merge(grupo_usuarioOld);
            }
            if (grupo_usuarioNew != null && !grupo_usuarioNew.equals(grupo_usuarioOld)) {
                grupo_usuarioNew.getListaGrupoUsuario().add(usuario_Grupo);
                grupo_usuarioNew = em.merge(grupo_usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                long id = usuario_Grupo.getUsuarioGrupoID();
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

    public void destroy(long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario_Grupo usuario_Grupo;
            try {
                usuario_Grupo = em.getReference(Usuario_Grupo.class, id);
                usuario_Grupo.getUsuarioGrupoID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario_Grupo with id " + id + " no longer exists.", enfe);
            }
            Usuario usuario_grupo = usuario_Grupo.getUsuario_grupo();
            if (usuario_grupo != null) {
                usuario_grupo.getListaUsuarioGrupo().remove(usuario_Grupo);
                usuario_grupo = em.merge(usuario_grupo);
            }
            Grupo grupo_usuario = usuario_Grupo.getGrupo_usuario();
            if (grupo_usuario != null) {
                grupo_usuario.getListaGrupoUsuario().remove(usuario_Grupo);
                grupo_usuario = em.merge(grupo_usuario);
            }
            em.remove(usuario_Grupo);
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

    public Usuario_Grupo findUsuario_Grupo(long id) {
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
                    + "WHERE ug.usuario_grupo.usuarioID = :uid "
                    + "  AND ug.grupo_usuario.grupoID   = :gid",
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
                    + "WHERE ug.usuario_grupo.usuarioID = :uid",
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
                    "SELECT ug FROM Usuario_Grupo ug "
                    + "WHERE ug.grupo_usuario.grupoID = :gid",
                    Usuario_Grupo.class)
                    .setParameter("gid", gid)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public long countByUsuario(Long userId) {
        var em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT COUNT(ug) FROM Usuario_Grupo ug WHERE ug.usuario_grupo.usuarioID = :uid",
                    Long.class)
                    .setParameter("uid", userId)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }
}
