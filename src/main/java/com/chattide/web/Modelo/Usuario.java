package com.chattide.web.Modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.NaturalId;
import org.mindrot.jbcrypt.BCrypt;

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
        name = "Usuario",
        schema = "bdchattide",
        uniqueConstraints = @UniqueConstraint(columnNames = "email")
)
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioID;

    @NotNull
    @Size(min = 2, max = 60)
    @Column(name = "nombre", length = 60, nullable = false)
    private String nombre;

    @NaturalId
    @NotNull
    @Email
    @Size(max = 100)
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(name = "contrasenia", length = 60, nullable = false)
    private String contrasenia;

    @Size(max = 200)
    @Column(name = "avatarURL", length = 200)
    private String avatar;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(
            mappedBy = "usuario",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<Usuario_Grupo> grupos = new HashSet<>();

    @OneToMany(
            mappedBy = "autor",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<Publicacion> publicaciones = new HashSet<>();

    @OneToMany(
            mappedBy = "autor",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<Comentario> comentarios = new HashSet<>();

    @OneToMany(
            mappedBy = "usuario",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<Likes> likes = new HashSet<>();

    public Usuario() {
    }

    public Usuario(Long usuarioID) {
        this.usuarioID = usuarioID;
    }

    public Usuario(String nombre, String email, String plainPassword, String avatar) {
        this.nombre = nombre;
        this.email = email;
        setContrasenia(plainPassword);
        this.avatar = avatar;
    }

    public Long getUsuarioID() {
        return usuarioID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String plainPassword) {
        this.contrasenia = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public boolean checkContrasenia(String candidate) {
        return BCrypt.checkpw(candidate, this.contrasenia);
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Set<Usuario_Grupo> getGrupos() {
        return grupos;
    }

    public Set<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public Set<Comentario> getComentarios() {
        return comentarios;
    }

    public Set<Likes> getLikes() {
        return likes;
    }

    public void setUsuarioID(Long usuarioID) {
        this.usuarioID = usuarioID;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setGrupos(Set<Usuario_Grupo> grupos) {
        this.grupos = grupos;
    }

    public void setPublicaciones(Set<Publicacion> publicaciones) {
        this.publicaciones = publicaciones;
    }

    public void setComentarios(Set<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public void setLikes(Set<Likes> likes) {
        this.likes = likes;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}
