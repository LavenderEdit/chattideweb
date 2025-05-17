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
        name = "Usuario_Grupo",
        schema = "bdchattide",
        uniqueConstraints = @UniqueConstraint(columnNames = {"idUsuario", "idGrupo"})
)
public class Usuario_Grupo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioGrupoID;

    @NotNull
    @Column(name = "fecha_union", nullable = false, updatable = false)
    private Instant fechaUnion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idUsuario", nullable = false, updatable = false)
    private Usuario usuario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idGrupo", nullable = false, updatable = false)
    private Grupo grupo;

    public Usuario_Grupo() {
    }

    public Usuario_Grupo(Usuario usuario, Grupo grupo) {
        this.usuario = usuario;
        this.grupo = grupo;
    }

    public Long getUsuarioGrupoID() {
        return usuarioGrupoID;
    }

    public Instant getFechaUnion() {
        return fechaUnion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    @PrePersist
    protected void onCreate() {
        fechaUnion = Instant.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Usuario_Grupo)) {
            return false;
        }
        Usuario_Grupo that = (Usuario_Grupo) o;
        return usuario.equals(that.usuario) && grupo.equals(that.grupo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                usuario != null ? usuario.getUsuarioID() : null,
                grupo != null ? grupo.getGrupoID() : null
        );
    }
}
