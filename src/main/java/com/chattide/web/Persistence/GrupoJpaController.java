package com.chattide.web.Persistence;

import com.chattide.web.Modelo.Grupo;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.chattide.web.Modelo.Usuario_Grupo;
import java.util.ArrayList;
import java.util.List;
import com.chattide.web.Modelo.Publicacion;
import com.chattide.web.Persistence.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;

/**
 *
 * @author Juan - Luis
 */
public class GrupoJpaController extends AbstractJpaController implements Serializable {

    public void create(Grupo grupo) {
        if (grupo.getListaGrupoUsuario() == null) {
            grupo.setListaGrupoUsuario(new ArrayList<Usuario_Grupo>());
        }
        if (grupo.getListaPublicacion() == null) {
            grupo.setListaPublicacion(new ArrayList<Publicacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Usuario_Grupo> attachedListaGrupoUsuario = new ArrayList<Usuario_Grupo>();
            for (Usuario_Grupo listaGrupoUsuarioUsuario_GrupoToAttach : grupo.getListaGrupoUsuario()) {
                listaGrupoUsuarioUsuario_GrupoToAttach = em.getReference(listaGrupoUsuarioUsuario_GrupoToAttach.getClass(), listaGrupoUsuarioUsuario_GrupoToAttach.getUsuarioGrupoID());
                attachedListaGrupoUsuario.add(listaGrupoUsuarioUsuario_GrupoToAttach);
            }
            grupo.setListaGrupoUsuario(attachedListaGrupoUsuario);
            List<Publicacion> attachedListaPublicacion = new ArrayList<Publicacion>();
            for (Publicacion listaPublicacionPublicacionToAttach : grupo.getListaPublicacion()) {
                listaPublicacionPublicacionToAttach = em.getReference(listaPublicacionPublicacionToAttach.getClass(), listaPublicacionPublicacionToAttach.getPublicacionID());
                attachedListaPublicacion.add(listaPublicacionPublicacionToAttach);
            }
            grupo.setListaPublicacion(attachedListaPublicacion);
            em.persist(grupo);
            for (Usuario_Grupo listaGrupoUsuarioUsuario_Grupo : grupo.getListaGrupoUsuario()) {
                Grupo oldGrupo_usuarioOfListaGrupoUsuarioUsuario_Grupo = listaGrupoUsuarioUsuario_Grupo.getGrupo_usuario();
                listaGrupoUsuarioUsuario_Grupo.setGrupo_usuario(grupo);
                listaGrupoUsuarioUsuario_Grupo = em.merge(listaGrupoUsuarioUsuario_Grupo);
                if (oldGrupo_usuarioOfListaGrupoUsuarioUsuario_Grupo != null) {
                    oldGrupo_usuarioOfListaGrupoUsuarioUsuario_Grupo.getListaGrupoUsuario().remove(listaGrupoUsuarioUsuario_Grupo);
                    oldGrupo_usuarioOfListaGrupoUsuarioUsuario_Grupo = em.merge(oldGrupo_usuarioOfListaGrupoUsuarioUsuario_Grupo);
                }
            }
            for (Publicacion listaPublicacionPublicacion : grupo.getListaPublicacion()) {
                Grupo oldGrupo_publicacionOfListaPublicacionPublicacion = listaPublicacionPublicacion.getGrupo_publicacion();
                listaPublicacionPublicacion.setGrupo_publicacion(grupo);
                listaPublicacionPublicacion = em.merge(listaPublicacionPublicacion);
                if (oldGrupo_publicacionOfListaPublicacionPublicacion != null) {
                    oldGrupo_publicacionOfListaPublicacionPublicacion.getListaPublicacion().remove(listaPublicacionPublicacion);
                    oldGrupo_publicacionOfListaPublicacionPublicacion = em.merge(oldGrupo_publicacionOfListaPublicacionPublicacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Grupo grupo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo persistentGrupo = em.find(Grupo.class, grupo.getGrupoID());
            List<Usuario_Grupo> listaGrupoUsuarioOld = persistentGrupo.getListaGrupoUsuario();
            List<Usuario_Grupo> listaGrupoUsuarioNew = grupo.getListaGrupoUsuario();
            List<Publicacion> listaPublicacionOld = persistentGrupo.getListaPublicacion();
            List<Publicacion> listaPublicacionNew = grupo.getListaPublicacion();
            List<Usuario_Grupo> attachedListaGrupoUsuarioNew = new ArrayList<Usuario_Grupo>();
            for (Usuario_Grupo listaGrupoUsuarioNewUsuario_GrupoToAttach : listaGrupoUsuarioNew) {
                listaGrupoUsuarioNewUsuario_GrupoToAttach = em.getReference(listaGrupoUsuarioNewUsuario_GrupoToAttach.getClass(), listaGrupoUsuarioNewUsuario_GrupoToAttach.getUsuarioGrupoID());
                attachedListaGrupoUsuarioNew.add(listaGrupoUsuarioNewUsuario_GrupoToAttach);
            }
            listaGrupoUsuarioNew = attachedListaGrupoUsuarioNew;
            grupo.setListaGrupoUsuario(listaGrupoUsuarioNew);
            List<Publicacion> attachedListaPublicacionNew = new ArrayList<Publicacion>();
            for (Publicacion listaPublicacionNewPublicacionToAttach : listaPublicacionNew) {
                listaPublicacionNewPublicacionToAttach = em.getReference(listaPublicacionNewPublicacionToAttach.getClass(), listaPublicacionNewPublicacionToAttach.getPublicacionID());
                attachedListaPublicacionNew.add(listaPublicacionNewPublicacionToAttach);
            }
            listaPublicacionNew = attachedListaPublicacionNew;
            grupo.setListaPublicacion(listaPublicacionNew);
            grupo = em.merge(grupo);
            for (Usuario_Grupo listaGrupoUsuarioOldUsuario_Grupo : listaGrupoUsuarioOld) {
                if (!listaGrupoUsuarioNew.contains(listaGrupoUsuarioOldUsuario_Grupo)) {
                    listaGrupoUsuarioOldUsuario_Grupo.setGrupo_usuario(null);
                    listaGrupoUsuarioOldUsuario_Grupo = em.merge(listaGrupoUsuarioOldUsuario_Grupo);
                }
            }
            for (Usuario_Grupo listaGrupoUsuarioNewUsuario_Grupo : listaGrupoUsuarioNew) {
                if (!listaGrupoUsuarioOld.contains(listaGrupoUsuarioNewUsuario_Grupo)) {
                    Grupo oldGrupo_usuarioOfListaGrupoUsuarioNewUsuario_Grupo = listaGrupoUsuarioNewUsuario_Grupo.getGrupo_usuario();
                    listaGrupoUsuarioNewUsuario_Grupo.setGrupo_usuario(grupo);
                    listaGrupoUsuarioNewUsuario_Grupo = em.merge(listaGrupoUsuarioNewUsuario_Grupo);
                    if (oldGrupo_usuarioOfListaGrupoUsuarioNewUsuario_Grupo != null && !oldGrupo_usuarioOfListaGrupoUsuarioNewUsuario_Grupo.equals(grupo)) {
                        oldGrupo_usuarioOfListaGrupoUsuarioNewUsuario_Grupo.getListaGrupoUsuario().remove(listaGrupoUsuarioNewUsuario_Grupo);
                        oldGrupo_usuarioOfListaGrupoUsuarioNewUsuario_Grupo = em.merge(oldGrupo_usuarioOfListaGrupoUsuarioNewUsuario_Grupo);
                    }
                }
            }
            for (Publicacion listaPublicacionOldPublicacion : listaPublicacionOld) {
                if (!listaPublicacionNew.contains(listaPublicacionOldPublicacion)) {
                    listaPublicacionOldPublicacion.setGrupo_publicacion(null);
                    listaPublicacionOldPublicacion = em.merge(listaPublicacionOldPublicacion);
                }
            }
            for (Publicacion listaPublicacionNewPublicacion : listaPublicacionNew) {
                if (!listaPublicacionOld.contains(listaPublicacionNewPublicacion)) {
                    Grupo oldGrupo_publicacionOfListaPublicacionNewPublicacion = listaPublicacionNewPublicacion.getGrupo_publicacion();
                    listaPublicacionNewPublicacion.setGrupo_publicacion(grupo);
                    listaPublicacionNewPublicacion = em.merge(listaPublicacionNewPublicacion);
                    if (oldGrupo_publicacionOfListaPublicacionNewPublicacion != null && !oldGrupo_publicacionOfListaPublicacionNewPublicacion.equals(grupo)) {
                        oldGrupo_publicacionOfListaPublicacionNewPublicacion.getListaPublicacion().remove(listaPublicacionNewPublicacion);
                        oldGrupo_publicacionOfListaPublicacionNewPublicacion = em.merge(oldGrupo_publicacionOfListaPublicacionNewPublicacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                long id = grupo.getGrupoID();
                if (findGrupo(id) == null) {
                    throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.");
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
            Grupo grupo;
            try {
                grupo = em.getReference(Grupo.class, id);
                grupo.getGrupoID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.", enfe);
            }
            List<Usuario_Grupo> listaGrupoUsuario = grupo.getListaGrupoUsuario();
            for (Usuario_Grupo listaGrupoUsuarioUsuario_Grupo : listaGrupoUsuario) {
                listaGrupoUsuarioUsuario_Grupo.setGrupo_usuario(null);
                listaGrupoUsuarioUsuario_Grupo = em.merge(listaGrupoUsuarioUsuario_Grupo);
            }
            List<Publicacion> listaPublicacion = grupo.getListaPublicacion();
            for (Publicacion listaPublicacionPublicacion : listaPublicacion) {
                listaPublicacionPublicacion.setGrupo_publicacion(null);
                listaPublicacionPublicacion = em.merge(listaPublicacionPublicacion);
            }
            em.remove(grupo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Grupo> findGrupoEntities() {
        return findGrupoEntities(true, -1, -1);
    }

    public List<Grupo> findGrupoEntities(int maxResults, int firstResult) {
        return findGrupoEntities(false, maxResults, firstResult);
    }

    private List<Grupo> findGrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Grupo.class));
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

    public Grupo findGrupo(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Grupo.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrupoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Grupo> rt = cq.from(Grupo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Grupo> findGrupoEntitiesByUsuario(Long usuarioId) {
        var em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT g FROM Grupo g "
                    + "JOIN g.listaGrupoUsuario ug "
                    + "WHERE ug.usuario_grupo.usuarioID = :uid",
                    Grupo.class
            )
                    .setParameter("uid", usuarioId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Grupo> findAvailableGrupoEntities(Long usuarioId) {
        var em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT g FROM Grupo g WHERE g.grupoID NOT IN ("
                    + "  SELECT ug.grupo_usuario.grupoID "
                    + "  FROM Usuario_Grupo ug "
                    + "  WHERE ug.usuario_grupo.usuarioID = :uid"
                    + ")",
                    Grupo.class
            )
                    .setParameter("uid", usuarioId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}
