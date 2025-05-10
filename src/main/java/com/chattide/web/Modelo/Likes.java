package com.chattide.web.Modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serializable;

/**
 *
 * @author Juan - Luis
 */
@Entity
@Table(
        name = "Likes",
        schema = "bdchattide"
)

public class Likes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long likesID;

    //Conexiones
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario_likes;

    @ManyToOne
    @JoinColumn(name = "idPublicacion")
    private Publicacion publicacion_likes;

    public Likes() {
    }

    public Likes(long likesID, Usuario usuario_likes, Publicacion publicacion_likes) {
        this.likesID = likesID;
        this.usuario_likes = usuario_likes;
        this.publicacion_likes = publicacion_likes;
    }

    public long getLikesID() {
        return likesID;
    }

    public void setLikesID(long likesID) {
        this.likesID = likesID;
    }

    public Usuario getUsuario_likes() {
        return usuario_likes;
    }

    public void setUsuario_likes(Usuario usuario_likes) {
        this.usuario_likes = usuario_likes;
    }

    public Publicacion getPublicacion_likes() {
        return publicacion_likes;
    }

    public void setPublicacion_likes(Publicacion publicacion_likes) {
        this.publicacion_likes = publicacion_likes;
    }
}
