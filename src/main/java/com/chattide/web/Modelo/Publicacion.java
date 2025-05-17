package com.chattide.web.Modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

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
    private Long publicacionID;

    @NotNull
    @Size(min = 1, max = 2000)
    @Column(name = "contenido", length = 2000, nullable = false)
    private String contenido;

    @Column(name = "fecha_publicacion", nullable = false, updatable = false)
    private Instant fechaPublicacion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idUsuario", nullable = false, updatable = false)
    private Usuario autor;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idGrupo", nullable = false, updatable = false)
    private Grupo grupo;

    @OneToMany(
            mappedBy = "publicacion",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<Comentario> comentarios = new HashSet<>();

    @OneToMany(
            mappedBy = "publicacion",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<Likes> likes = new HashSet<>();

    public Publicacion() {
    }

    public Publicacion(String contenido, Usuario autor, Grupo grupo) {
        this.contenido = contenido;
        this.autor = autor;
        this.grupo = grupo;
    }

    public Long getPublicacionID() {
        return publicacionID;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Instant getFechaPublicacion() {
        return fechaPublicacion;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Set<Comentario> getComentarios() {
        return comentarios;
    }

    public Set<Likes> getLikes() {
        return likes;
    }

    public void setPublicacionID(Long publicacionID) {
        this.publicacionID = publicacionID;
    }

    public void setFechaPublicacion(Instant fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public void setComentarios(Set<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public void setLikes(Set<Likes> likes) {
        this.likes = likes;
    }

    @PrePersist
    protected void onCreate() {
        fechaPublicacion = Instant.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Publicacion)) {
            return false;
        }
        Publicacion that = (Publicacion) o;
        return publicacionID != null && publicacionID.equals(that.publicacionID);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
