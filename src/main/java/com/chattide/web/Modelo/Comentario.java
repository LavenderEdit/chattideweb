package com.chattide.web.Modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

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
    private Long comentarioID;

    @NotBlank
    @Size(max = 1000)
    @Column(name = "contenido", length = 1000, nullable = false)
    private String contenido;

    @Column(name = "fecha_comentario", nullable = false, updatable = false)
    private Instant fechaComentario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "idPublicacion",
            nullable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_comentario_publicacion")
    )
    private Publicacion publicacion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "idUsuario",
            nullable = false,
            updatable = false,
            foreignKey = @ForeignKey(name = "fk_comentario_usuario")
    )
    private Usuario autor;

    public Comentario() {
    }

    public Comentario(String contenido, Publicacion publicacion, Usuario autor) {
        this.contenido = contenido;
        this.publicacion = publicacion;
        this.autor = autor;
    }

    public Long getComentarioID() {
        return comentarioID;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Instant getFechaComentario() {
        return fechaComentario;
    }

    public Date getFechaComentarioDate() {
        return (fechaComentario != null)
                ? Date.from(fechaComentario)
                : null;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public void setComentarioID(Long comentarioID) {
        this.comentarioID = comentarioID;
    }

    public void setFechaComentario(Instant fechaComentario) {
        this.fechaComentario = fechaComentario;
    }

    @PrePersist
    protected void onCreate() {
        fechaComentario = Instant.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comentario)) {
            return false;
        }
        Comentario that = (Comentario) o;
        return Objects.equals(comentarioID, that.comentarioID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comentarioID);
    }
}
