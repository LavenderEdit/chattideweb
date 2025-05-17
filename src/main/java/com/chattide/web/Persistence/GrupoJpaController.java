package com.chattide.web.Persistence;

import com.chattide.web.Modelo.Comentario;
import com.chattide.web.Modelo.Grupo;
import com.chattide.web.Modelo.Likes;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.chattide.web.Modelo.Usuario_Grupo;
import java.util.HashSet;
import java.util.Set;
import com.chattide.web.Modelo.Publicacion;
import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Persistence.exceptions.IllegalOrphanException;
import com.chattide.web.Persistence.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juan - Luis
 */
public class GrupoJpaController extends AbstractJpaController implements Serializable {

    public void create(Grupo grupo, Usuario creador) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            em.persist(grupo);

            Usuario managedUser = em.merge(creador);

            Usuario_Grupo relacion = new Usuario_Grupo();
            relacion.setUsuario(managedUser);
            relacion.setGrupo(grupo);
            em.persist(relacion);

            grupo.getMiembros().add(relacion);
            managedUser.getGrupos().add(relacion);

            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Grupo grupo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo persistentGrupo = em.find(Grupo.class, grupo.getGrupoID());
            Set<Usuario_Grupo> miembrosOld = persistentGrupo.getMiembros();
            Set<Usuario_Grupo> miembrosNew = grupo.getMiembros();
            Set<Publicacion> publicacionesOld = persistentGrupo.getPublicaciones();
            Set<Publicacion> publicacionesNew = grupo.getPublicaciones();
            List<String> illegalOrphanMessages = null;
            for (Usuario_Grupo miembrosOldUsuario_Grupo : miembrosOld) {
                if (!miembrosNew.contains(miembrosOldUsuario_Grupo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario_Grupo " + miembrosOldUsuario_Grupo + " since its grupo field is not nullable.");
                }
            }
            for (Publicacion publicacionesOldPublicacion : publicacionesOld) {
                if (!publicacionesNew.contains(publicacionesOldPublicacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Publicacion " + publicacionesOldPublicacion + " since its grupo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Set<Usuario_Grupo> attachedMiembrosNew = new HashSet<Usuario_Grupo>();
            for (Usuario_Grupo miembrosNewUsuario_GrupoToAttach : miembrosNew) {
                miembrosNewUsuario_GrupoToAttach = em.getReference(miembrosNewUsuario_GrupoToAttach.getClass(), miembrosNewUsuario_GrupoToAttach.getUsuarioGrupoID());
                attachedMiembrosNew.add(miembrosNewUsuario_GrupoToAttach);
            }
            miembrosNew = attachedMiembrosNew;
            grupo.setMiembros(miembrosNew);
            Set<Publicacion> attachedPublicacionesNew = new HashSet<Publicacion>();
            for (Publicacion publicacionesNewPublicacionToAttach : publicacionesNew) {
                publicacionesNewPublicacionToAttach = em.getReference(publicacionesNewPublicacionToAttach.getClass(), publicacionesNewPublicacionToAttach.getPublicacionID());
                attachedPublicacionesNew.add(publicacionesNewPublicacionToAttach);
            }
            publicacionesNew = attachedPublicacionesNew;
            grupo.setPublicaciones(publicacionesNew);
            grupo = em.merge(grupo);
            for (Usuario_Grupo miembrosNewUsuario_Grupo : miembrosNew) {
                if (!miembrosOld.contains(miembrosNewUsuario_Grupo)) {
                    Grupo oldGrupoOfMiembrosNewUsuario_Grupo = miembrosNewUsuario_Grupo.getGrupo();
                    miembrosNewUsuario_Grupo.setGrupo(grupo);
                    miembrosNewUsuario_Grupo = em.merge(miembrosNewUsuario_Grupo);
                    if (oldGrupoOfMiembrosNewUsuario_Grupo != null && !oldGrupoOfMiembrosNewUsuario_Grupo.equals(grupo)) {
                        oldGrupoOfMiembrosNewUsuario_Grupo.getMiembros().remove(miembrosNewUsuario_Grupo);
                        oldGrupoOfMiembrosNewUsuario_Grupo = em.merge(oldGrupoOfMiembrosNewUsuario_Grupo);
                    }
                }
            }
            for (Publicacion publicacionesNewPublicacion : publicacionesNew) {
                if (!publicacionesOld.contains(publicacionesNewPublicacion)) {
                    Grupo oldGrupoOfPublicacionesNewPublicacion = publicacionesNewPublicacion.getGrupo();
                    publicacionesNewPublicacion.setGrupo(grupo);
                    publicacionesNewPublicacion = em.merge(publicacionesNewPublicacion);
                    if (oldGrupoOfPublicacionesNewPublicacion != null && !oldGrupoOfPublicacionesNewPublicacion.equals(grupo)) {
                        oldGrupoOfPublicacionesNewPublicacion.getPublicaciones().remove(publicacionesNewPublicacion);
                        oldGrupoOfPublicacionesNewPublicacion = em.merge(oldGrupoOfPublicacionesNewPublicacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = grupo.getGrupoID();
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

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo grupo;
            try {
                grupo = em.getReference(Grupo.class, id);
                grupo.getGrupoID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("El grupo con id " + id + " ya no existe.", enfe);
            }

            for (Publicacion publicacion : new HashSet<>(grupo.getPublicaciones())) {

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

                em.remove(em.contains(publicacion) ? publicacion : em.merge(publicacion));
            }

            for (Usuario_Grupo relacion : new HashSet<>(grupo.getMiembros())) {
                Usuario usuario = relacion.getUsuario();
                if (usuario != null) {
                    usuario.getGrupos().remove(relacion);
                    em.merge(usuario);
                }

                em.remove(em.contains(relacion) ? relacion : em.merge(relacion));
            }

            em.remove(em.contains(grupo) ? grupo : em.merge(grupo));
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

    public Grupo findGrupo(Long id) {
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
                    + "JOIN g.miembros ug "
                    + "WHERE ug.usuario.usuarioID = :uid",
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
                    "SELECT g FROM Grupo g "
                    + "WHERE g.grupoID NOT IN ("
                    + "  SELECT ug.grupo.grupoID "
                    + "  FROM Usuario_Grupo ug "
                    + "  WHERE ug.usuario.usuarioID = :uid"
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
