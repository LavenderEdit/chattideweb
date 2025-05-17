package com.chattide.web.DTO;

import java.time.Instant;
import java.util.Date;

/**
 *
 * @author Juan - Luis
 */
public class ComentarioDTO {

    private Long id;
    private String contenido;
    private Instant fechaComentario;
    private Long publicacionId;
    private Long usuarioId;
    private String usuarioNombre;
    private String avatarUsuarioUrl;

    public ComentarioDTO() {
    }

    public ComentarioDTO(Long id, String contenido, Instant fechaComentario, Long publicacionId, Long usuarioId, String usuarioNombre, String avatarUsuarioUrl) {
        this.id = id;
        this.contenido = contenido;
        this.fechaComentario = fechaComentario;
        this.publicacionId = publicacionId;
        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
        this.avatarUsuarioUrl = avatarUsuarioUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFechaComentario() {
        return (fechaComentario != null)
               ? Date.from(fechaComentario)
               : null;
    }

    public void setFechaComentario(Instant fechaComentario) {
        this.fechaComentario = fechaComentario;
    }

    public Long getPublicacionId() {
        return publicacionId;
    }

    public void setPublicacionId(Long publicacionId) {
        this.publicacionId = publicacionId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public String getAvatarUsuarioUrl() {
        return avatarUsuarioUrl;
    }

    public void setAvatarUsuarioUrl(String avatarUsuarioUrl) {
        this.avatarUsuarioUrl = avatarUsuarioUrl;
    }
}
