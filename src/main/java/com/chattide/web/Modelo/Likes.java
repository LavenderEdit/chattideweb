package com.chattide.web.Modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 *
 * @author Juan - Luis
 */
@Entity
@Table(
        name = "Likes",
        schema = "bdchattide",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"idUsuario", "idPublicacion"},
                name = "uk_likes_usuario_publicacion"
        )
)
public class Likes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likesID;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "idUsuario",
            nullable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_likes_usuario")
    )
    private Usuario usuario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "idPublicacion",
            nullable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_likes_publicacion")
    )
    private Publicacion publicacion;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    public Likes() {
    }

    public Likes(Usuario usuario, Publicacion publicacion) {
        this.usuario = usuario;
        this.publicacion = publicacion;
    }

    public Long getLikesID() {
        return likesID;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Likes)) {
            return false;
        }
        Likes likes = (Likes) o;
        return Objects.equals(usuario, likes.usuario)
                && Objects.equals(publicacion, likes.publicacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, publicacion);
    }
}
