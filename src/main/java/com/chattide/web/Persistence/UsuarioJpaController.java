package com.chattide.web.Persistence;

import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import com.chattide.web.Modelo.Usuario_Grupo;
import java.util.HashSet;
import java.util.Set;
import com.chattide.web.Modelo.Publicacion;
import com.chattide.web.Modelo.Comentario;
import com.chattide.web.Modelo.Likes;
import com.chattide.web.Modelo.Usuario;
import com.chattide.web.Persistence.exceptions.IllegalOrphanException;
import com.chattide.web.Persistence.exceptions.NonexistentEntityException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.RollbackException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juan - Luis
 */
public class UsuarioJpaController extends AbstractJpaController implements Serializable {

    public void create(Usuario usuario) {
        if (usuario.getGrupos() == null) {
            usuario.setGrupos(new HashSet<Usuario_Grupo>());
        }
        if (usuario.getPublicaciones() == null) {
            usuario.setPublicaciones(new HashSet<Publicacion>());
        }
        if (usuario.getComentarios() == null) {
            usuario.setComentarios(new HashSet<Comentario>());
        }
        if (usuario.getLikes() == null) {
            usuario.setLikes(new HashSet<Likes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<Usuario_Grupo> attachedGrupos = new HashSet<Usuario_Grupo>();
            for (Usuario_Grupo gruposUsuario_GrupoToAttach : usuario.getGrupos()) {
                gruposUsuario_GrupoToAttach = em.getReference(gruposUsuario_GrupoToAttach.getClass(), gruposUsuario_GrupoToAttach.getUsuarioGrupoID());
                attachedGrupos.add(gruposUsuario_GrupoToAttach);
            }
            usuario.setGrupos(attachedGrupos);
            Set<Publicacion> attachedPublicaciones = new HashSet<Publicacion>();
            for (Publicacion publicacionesPublicacionToAttach : usuario.getPublicaciones()) {
                publicacionesPublicacionToAttach = em.getReference(publicacionesPublicacionToAttach.getClass(), publicacionesPublicacionToAttach.getPublicacionID());
                attachedPublicaciones.add(publicacionesPublicacionToAttach);
            }
            usuario.setPublicaciones(attachedPublicaciones);
            Set<Comentario> attachedComentarios = new HashSet<Comentario>();
            for (Comentario comentariosComentarioToAttach : usuario.getComentarios()) {
                comentariosComentarioToAttach = em.getReference(comentariosComentarioToAttach.getClass(), comentariosComentarioToAttach.getComentarioID());
                attachedComentarios.add(comentariosComentarioToAttach);
            }
            usuario.setComentarios(attachedComentarios);
            Set<Likes> attachedLikes = new HashSet<Likes>();
            for (Likes likesLikesToAttach : usuario.getLikes()) {
                likesLikesToAttach = em.getReference(likesLikesToAttach.getClass(), likesLikesToAttach.getLikesID());
                attachedLikes.add(likesLikesToAttach);
            }
            usuario.setLikes(attachedLikes);
            em.persist(usuario);
            for (Usuario_Grupo gruposUsuario_Grupo : usuario.getGrupos()) {
                Usuario oldUsuarioOfGruposUsuario_Grupo = gruposUsuario_Grupo.getUsuario();
                gruposUsuario_Grupo.setUsuario(usuario);
                gruposUsuario_Grupo = em.merge(gruposUsuario_Grupo);
                if (oldUsuarioOfGruposUsuario_Grupo != null) {
                    oldUsuarioOfGruposUsuario_Grupo.getGrupos().remove(gruposUsuario_Grupo);
                    oldUsuarioOfGruposUsuario_Grupo = em.merge(oldUsuarioOfGruposUsuario_Grupo);
                }
            }
            for (Publicacion publicacionesPublicacion : usuario.getPublicaciones()) {
                Usuario oldAutorOfPublicacionesPublicacion = publicacionesPublicacion.getAutor();
                publicacionesPublicacion.setAutor(usuario);
                publicacionesPublicacion = em.merge(publicacionesPublicacion);
                if (oldAutorOfPublicacionesPublicacion != null) {
                    oldAutorOfPublicacionesPublicacion.getPublicaciones().remove(publicacionesPublicacion);
                    oldAutorOfPublicacionesPublicacion = em.merge(oldAutorOfPublicacionesPublicacion);
                }
            }
            for (Comentario comentariosComentario : usuario.getComentarios()) {
                Usuario oldAutorOfComentariosComentario = comentariosComentario.getAutor();
                comentariosComentario.setAutor(usuario);
                comentariosComentario = em.merge(comentariosComentario);
                if (oldAutorOfComentariosComentario != null) {
                    oldAutorOfComentariosComentario.getComentarios().remove(comentariosComentario);
                    oldAutorOfComentariosComentario = em.merge(oldAutorOfComentariosComentario);
                }
            }
            for (Likes likesLikes : usuario.getLikes()) {
                Usuario oldUsuarioOfLikesLikes = likesLikes.getUsuario();
                likesLikes.setUsuario(usuario);
                likesLikes = em.merge(likesLikes);
                if (oldUsuarioOfLikesLikes != null) {
                    oldUsuarioOfLikesLikes.getLikes().remove(likesLikes);
                    oldUsuarioOfLikesLikes = em.merge(oldUsuarioOfLikesLikes);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getUsuarioID());
            Set<Usuario_Grupo> gruposOld = persistentUsuario.getGrupos();
            Set<Usuario_Grupo> gruposNew = usuario.getGrupos();
            Set<Publicacion> publicacionesOld = persistentUsuario.getPublicaciones();
            Set<Publicacion> publicacionesNew = usuario.getPublicaciones();
            Set<Comentario> comentariosOld = persistentUsuario.getComentarios();
            Set<Comentario> comentariosNew = usuario.getComentarios();
            Set<Likes> likesOld = persistentUsuario.getLikes();
            Set<Likes> likesNew = usuario.getLikes();
            List<String> illegalOrphanMessages = null;
            for (Usuario_Grupo gruposOldUsuario_Grupo : gruposOld) {
                if (!gruposNew.contains(gruposOldUsuario_Grupo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario_Grupo " + gruposOldUsuario_Grupo + " since its usuario field is not nullable.");
                }
            }
            for (Publicacion publicacionesOldPublicacion : publicacionesOld) {
                if (!publicacionesNew.contains(publicacionesOldPublicacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Publicacion " + publicacionesOldPublicacion + " since its autor field is not nullable.");
                }
            }
            for (Comentario comentariosOldComentario : comentariosOld) {
                if (!comentariosNew.contains(comentariosOldComentario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comentario " + comentariosOldComentario + " since its autor field is not nullable.");
                }
            }
            for (Likes likesOldLikes : likesOld) {
                if (!likesNew.contains(likesOldLikes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Likes " + likesOldLikes + " since its usuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Set<Usuario_Grupo> attachedGruposNew = new HashSet<Usuario_Grupo>();
            for (Usuario_Grupo gruposNewUsuario_GrupoToAttach : gruposNew) {
                gruposNewUsuario_GrupoToAttach = em.getReference(gruposNewUsuario_GrupoToAttach.getClass(), gruposNewUsuario_GrupoToAttach.getUsuarioGrupoID());
                attachedGruposNew.add(gruposNewUsuario_GrupoToAttach);
            }
            gruposNew = attachedGruposNew;
            usuario.setGrupos(gruposNew);
            Set<Publicacion> attachedPublicacionesNew = new HashSet<Publicacion>();
            for (Publicacion publicacionesNewPublicacionToAttach : publicacionesNew) {
                publicacionesNewPublicacionToAttach = em.getReference(publicacionesNewPublicacionToAttach.getClass(), publicacionesNewPublicacionToAttach.getPublicacionID());
                attachedPublicacionesNew.add(publicacionesNewPublicacionToAttach);
            }
            publicacionesNew = attachedPublicacionesNew;
            usuario.setPublicaciones(publicacionesNew);
            Set<Comentario> attachedComentariosNew = new HashSet<Comentario>();
            for (Comentario comentariosNewComentarioToAttach : comentariosNew) {
                comentariosNewComentarioToAttach = em.getReference(comentariosNewComentarioToAttach.getClass(), comentariosNewComentarioToAttach.getComentarioID());
                attachedComentariosNew.add(comentariosNewComentarioToAttach);
            }
            comentariosNew = attachedComentariosNew;
            usuario.setComentarios(comentariosNew);
            Set<Likes> attachedLikesNew = new HashSet<Likes>();
            for (Likes likesNewLikesToAttach : likesNew) {
                likesNewLikesToAttach = em.getReference(likesNewLikesToAttach.getClass(), likesNewLikesToAttach.getLikesID());
                attachedLikesNew.add(likesNewLikesToAttach);
            }
            likesNew = attachedLikesNew;
            usuario.setLikes(likesNew);
            usuario = em.merge(usuario);
            for (Usuario_Grupo gruposNewUsuario_Grupo : gruposNew) {
                if (!gruposOld.contains(gruposNewUsuario_Grupo)) {
                    Usuario oldUsuarioOfGruposNewUsuario_Grupo = gruposNewUsuario_Grupo.getUsuario();
                    gruposNewUsuario_Grupo.setUsuario(usuario);
                    gruposNewUsuario_Grupo = em.merge(gruposNewUsuario_Grupo);
                    if (oldUsuarioOfGruposNewUsuario_Grupo != null && !oldUsuarioOfGruposNewUsuario_Grupo.equals(usuario)) {
                        oldUsuarioOfGruposNewUsuario_Grupo.getGrupos().remove(gruposNewUsuario_Grupo);
                        oldUsuarioOfGruposNewUsuario_Grupo = em.merge(oldUsuarioOfGruposNewUsuario_Grupo);
                    }
                }
            }
            for (Publicacion publicacionesNewPublicacion : publicacionesNew) {
                if (!publicacionesOld.contains(publicacionesNewPublicacion)) {
                    Usuario oldAutorOfPublicacionesNewPublicacion = publicacionesNewPublicacion.getAutor();
                    publicacionesNewPublicacion.setAutor(usuario);
                    publicacionesNewPublicacion = em.merge(publicacionesNewPublicacion);
                    if (oldAutorOfPublicacionesNewPublicacion != null && !oldAutorOfPublicacionesNewPublicacion.equals(usuario)) {
                        oldAutorOfPublicacionesNewPublicacion.getPublicaciones().remove(publicacionesNewPublicacion);
                        oldAutorOfPublicacionesNewPublicacion = em.merge(oldAutorOfPublicacionesNewPublicacion);
                    }
                }
            }
            for (Comentario comentariosNewComentario : comentariosNew) {
                if (!comentariosOld.contains(comentariosNewComentario)) {
                    Usuario oldAutorOfComentariosNewComentario = comentariosNewComentario.getAutor();
                    comentariosNewComentario.setAutor(usuario);
                    comentariosNewComentario = em.merge(comentariosNewComentario);
                    if (oldAutorOfComentariosNewComentario != null && !oldAutorOfComentariosNewComentario.equals(usuario)) {
                        oldAutorOfComentariosNewComentario.getComentarios().remove(comentariosNewComentario);
                        oldAutorOfComentariosNewComentario = em.merge(oldAutorOfComentariosNewComentario);
                    }
                }
            }
            for (Likes likesNewLikes : likesNew) {
                if (!likesOld.contains(likesNewLikes)) {
                    Usuario oldUsuarioOfLikesNewLikes = likesNewLikes.getUsuario();
                    likesNewLikes.setUsuario(usuario);
                    likesNewLikes = em.merge(likesNewLikes);
                    if (oldUsuarioOfLikesNewLikes != null && !oldUsuarioOfLikesNewLikes.equals(usuario)) {
                        oldUsuarioOfLikesNewLikes.getLikes().remove(likesNewLikes);
                        oldUsuarioOfLikesNewLikes = em.merge(oldUsuarioOfLikesNewLikes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = usuario.getUsuarioID();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void updateBasicInfo(Long usuarioId, String nuevoNombre, String nuevoEmail, String nuevoAvatar)
            throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            Usuario usuario = em.find(Usuario.class, usuarioId);
            if (usuario == null) {
                throw new NonexistentEntityException("El usuario con id " + usuarioId + " no existe.");
            }

            usuario.setNombre(nuevoNombre);
            usuario.setEmail(nuevoEmail);
            usuario.setAvatar(nuevoAvatar);

            em.getTransaction().commit();
        } catch (RollbackException e) {
            Throwable cause = e.getCause();
            if (cause instanceof PersistenceException && cause.getMessage().contains("ConstraintViolationException")) {
                throw new NonexistentEntityException("El email " + nuevoEmail + " ya está en uso.", cause);
            }
            throw e;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getUsuarioID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("El usuario con id " + id + " no existe.", enfe);
            }

            for (Likes like : new HashSet<>(usuario.getLikes())) {
                em.remove(em.contains(like) ? like : em.merge(like));
            }

            for (Comentario comentario : new HashSet<>(usuario.getComentarios())) {
                em.remove(em.contains(comentario) ? comentario : em.merge(comentario));
            }

            for (Publicacion pub : new HashSet<>(usuario.getPublicaciones())) {
                em.remove(em.contains(pub) ? pub : em.merge(pub));
            }

            for (Usuario_Grupo ug : new HashSet<>(usuario.getGrupos())) {
                em.remove(em.contains(ug) ? ug : em.merge(ug));
            }

            em.remove(em.contains(usuario) ? usuario : em.merge(usuario));

            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Usuario findByEmail(String email) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
