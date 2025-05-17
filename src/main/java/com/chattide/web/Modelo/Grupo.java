package com.chattide.web.Modelo;

import com.chattide.web.Utilities.Enum;
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
        name = "Grupo",
        schema = "bdchattide",
        uniqueConstraints = @UniqueConstraint(columnNames = "nombre")
)
public class Grupo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long grupoID;

    @NotNull
    @Size(min = 3, max = 100)
    @Column(name = "nombre", length = 100, nullable = false, unique = true)
    private String nombre;

    @NotNull
    @Size(min = 10, max = 500)
    @Column(name = "descripcion", length = 500, nullable = false)
    private String descripcion;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_privacidad", length = 20, nullable = false)
    private Enum.TipoPrivacidad tipoPrivacidad;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(
            mappedBy = "grupo",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<Usuario_Grupo> miembros = new HashSet<>();

    @OneToMany(
            mappedBy = "grupo",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<Publicacion> publicaciones = new HashSet<>();

    public Grupo() {
    }

    public Grupo(Long grupoID) {
        this.grupoID = grupoID;
    }

    public Grupo(String nombre, String descripcion, Enum.TipoPrivacidad tipoPrivacidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoPrivacidad = tipoPrivacidad;
    }

    public Long getGrupoID() {
        return grupoID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Enum.TipoPrivacidad getTipoPrivacidad() {
        return tipoPrivacidad;
    }

    public void setTipoPrivacidad(Enum.TipoPrivacidad tipoPrivacidad) {
        this.tipoPrivacidad = tipoPrivacidad;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Set<Usuario_Grupo> getMiembros() {
        return miembros;
    }

    public Set<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public void setGrupoID(Long grupoID) {
        this.grupoID = grupoID;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setMiembros(Set<Usuario_Grupo> miembros) {
        this.miembros = miembros;
    }

    public void setPublicaciones(Set<Publicacion> publicaciones) {
        this.publicaciones = publicaciones;
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
