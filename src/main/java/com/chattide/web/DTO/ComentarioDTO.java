package com.chattide.web.DTO;

import java.util.Date;

/**
 *
 * @author Juan - Luis
 */
public class ComentarioDTO {
    private Long id;
    private String contenido;
    private Date fechaComentario;
    private Long publicacionId;
    private Long usuarioId;
    private String usuarioNombre;

    public ComentarioDTO() {
    }

    public ComentarioDTO(Long id, String contenido, Date fechaComentario, Long publicacionId, Long usuarioId, String usuarioNombre) {
        this.id = id;
        this.contenido = contenido;
        this.fechaComentario = fechaComentario;
        this.publicacionId = publicacionId;
        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
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
        return fechaComentario;
    }

    public void setFechaComentario(Date fechaComentario) {
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
}
