package com.chattide.web.Modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Juan - Luis
 */
@Entity
@Table(
        name = "Publicacion",
        schema = "bdchattide"
)

public class Publicacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long publicacionID;

    @Column(name = "Contenido")
    private String contenidoText;

    @Column(name = "FechaPublicacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPublicacion;

    //Conexiones
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario_publicacion;

    @ManyToOne
    @JoinColumn(name = "idGrupo")
    private Grupo grupo_publicacion;

    @OneToMany(mappedBy = "publicacion_comentario")
    private List<Comentario> listaComentarios;

    @OneToMany(mappedBy = "publicacion_likes")
    private List<Likes> listaLikes;

    public Publicacion() {
    }

    public Publicacion(long publicacionID, String contenidoText, Date fechaPublicacion, Usuario usuario_publicacion, Grupo grupo_publicacion, List<Comentario> listaComentarios, List<Likes> listaLikes) {
        this.publicacionID = publicacionID;
        this.contenidoText = contenidoText;
        this.fechaPublicacion = fechaPublicacion;
        this.usuario_publicacion = usuario_publicacion;
        this.grupo_publicacion = grupo_publicacion;
        this.listaComentarios = listaComentarios;
        this.listaLikes = listaLikes;
    }

    public long getPublicacionID() {
        return publicacionID;
    }

    public void setPublicacionID(long publicacionID) {
        this.publicacionID = publicacionID;
    }

    public String getContenidoText() {
        return contenidoText;
    }

    public void setContenidoText(String contenidoText) {
        this.contenidoText = contenidoText;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Usuario getUsuario_publicacion() {
        return usuario_publicacion;
    }

    public void setUsuario_publicacion(Usuario usuario_publicacion) {
        this.usuario_publicacion = usuario_publicacion;
    }

    public Grupo getGrupo_publicacion() {
        return grupo_publicacion;
    }

    public void setGrupo_publicacion(Grupo grupo_publicacion) {
        this.grupo_publicacion = grupo_publicacion;
    }

    public List<Comentario> getListaComentarios() {
        return listaComentarios;
    }

    public void setListaComentarios(List<Comentario> listaComentarios) {
        this.listaComentarios = listaComentarios;
    }

    public List<Likes> getListaLikes() {
        return listaLikes;
    }

    public void setListaLikes(List<Likes> listaLikes) {
        this.listaLikes = listaLikes;
    }
}
