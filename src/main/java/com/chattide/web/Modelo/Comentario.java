package com.chattide.web.Modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Juan - Luis
 */
@Entity
@Table(
        name = "Comentario",
        schema = "bdchattide"
)

public class Comentario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long comentarioID;

    @Column(name = "Contenido")
    private String contenidoText;

    @Column(name = "FechaComentario")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaComentario;

    //Conexiones
    @ManyToOne
    @JoinColumn(name = "idPublicacion")
    private Publicacion publicacion_comentario;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario_comentario;

    public Comentario() {
    }

    public Comentario(long comentarioID, String contenidoText, Date fechaComentario, Publicacion publicacion_comentario, Usuario usuario_comentario) {
        this.comentarioID = comentarioID;
        this.contenidoText = contenidoText;
        this.fechaComentario = fechaComentario;
        this.publicacion_comentario = publicacion_comentario;
        this.usuario_comentario = usuario_comentario;
    }

    public long getComentarioID() {
        return comentarioID;
    }

    public void setComentarioID(long comentarioID) {
        this.comentarioID = comentarioID;
    }

    public String getContenidoText() {
        return contenidoText;
    }

    public void setContenidoText(String contenidoText) {
        this.contenidoText = contenidoText;
    }

    public Date getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(Date fechaComentario) {
        this.fechaComentario = fechaComentario;
    }

    public Publicacion getPublicacion_comentario() {
        return publicacion_comentario;
    }

    public void setPublicacion_comentario(Publicacion publicacion_comentario) {
        this.publicacion_comentario = publicacion_comentario;
    }

    public Usuario getUsuario_comentario() {
        return usuario_comentario;
    }

    public void setUsuario_comentario(Usuario usuario_comentario) {
        this.usuario_comentario = usuario_comentario;
    }
}
