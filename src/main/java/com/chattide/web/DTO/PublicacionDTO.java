package com.chattide.web.DTO;

import java.util.Date;

/**
 *
 * @author Juan - Luis
 */
public class PublicacionDTO {

    private Long id;
    private String contenido;
    private Date fechaPublicacion;
    private Long autorId;
    private String autorNombre;
    private String autorAvatar;
    private Long grupoId;
    private Long likeId;
    private int likeCount;
    private int comentarioCount;

    public PublicacionDTO() {
    }

    public PublicacionDTO(Long id, String contenido, Date fechaPublicacion, Long autorId, String autorNombre, String autorAvatar, Long grupoId, Long likeId, int likeCount, int comentarioCount) {
        this.id = id;
        this.contenido = contenido;
        this.fechaPublicacion = fechaPublicacion;
        this.autorId = autorId;
        this.autorNombre = autorNombre;
        this.autorAvatar = autorAvatar;
        this.grupoId = grupoId;
        this.likeId = likeId;
        this.likeCount = likeCount;
        this.comentarioCount = comentarioCount;
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

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Long getAutorId() {
        return autorId;
    }

    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    }

    public String getAutorNombre() {
        return autorNombre;
    }

    public void setAutorNombre(String autorNombre) {
        this.autorNombre = autorNombre;
    }

    public String getAutorAvatar() {
        return autorAvatar;
    }

    public void setAutorAvatar(String autorAvatar) {
        this.autorAvatar = autorAvatar;
    }

    public Long getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Long grupoId) {
        this.grupoId = grupoId;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getComentarioCount() {
        return comentarioCount;
    }

    public void setComentarioCount(int comentarioCount) {
        this.comentarioCount = comentarioCount;
    }

    public Long getLikeId() {
        return likeId;
    }

    public void setLikeId(Long likeId) {
        this.likeId = likeId;
    }
}
