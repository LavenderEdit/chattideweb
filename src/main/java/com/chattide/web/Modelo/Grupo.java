package com.chattide.web.Modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;

import com.chattide.web.Utilities.Enum;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Juan - Luis
 */
@Entity
@Table(
        name = "Grupo",
        schema = "bdchattide"
)

public class Grupo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long grupoID;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Description")
    private String descripcionText;

    @Enumerated(EnumType.STRING)
    @Column(name = "EstadoUsuario")
    private Enum.TipoPrivacidad tipoPrivacidad;

    // Conexiones
    @OneToMany(mappedBy = "grupo_usuario")
    private List<Usuario_Grupo> listaGrupoUsuario;

    @OneToMany(mappedBy = "grupo_publicacion")
    private List<Publicacion> listaPublicacion;

    public Grupo() {
    }

    public Grupo(long grupoID) {
        this.grupoID = grupoID;
    }

    public Grupo(long grupoID, String nombre, String descripcionText, Enum.TipoPrivacidad tipoPrivacidad, List<Usuario_Grupo> listaGrupoUsuario, List<Publicacion> listaPublicacion) {
        this.grupoID = grupoID;
        this.nombre = nombre;
        this.descripcionText = descripcionText;
        this.tipoPrivacidad = tipoPrivacidad;
        this.listaGrupoUsuario = listaGrupoUsuario;
        this.listaPublicacion = listaPublicacion;
    }

    public long getGrupoID() {
        return grupoID;
    }

    public void setGrupoID(long grupoID) {
        this.grupoID = grupoID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcionText() {
        return descripcionText;
    }

    public void setDescripcionText(String descripcionText) {
        this.descripcionText = descripcionText;
    }

    public Enum.TipoPrivacidad getTipoPrivacidad() {
        return tipoPrivacidad;
    }

    public void setTipoPrivacidad(Enum.TipoPrivacidad tipoPrivacidad) {
        this.tipoPrivacidad = tipoPrivacidad;
    }

    public List<Usuario_Grupo> getListaGrupoUsuario() {
        return listaGrupoUsuario;
    }

    public void setListaGrupoUsuario(List<Usuario_Grupo> listaGrupoUsuario) {
        this.listaGrupoUsuario = listaGrupoUsuario;
    }

    public List<Publicacion> getListaPublicacion() {
        return listaPublicacion;
    }

    public void setListaPublicacion(List<Publicacion> listaPublicacion) {
        this.listaPublicacion = listaPublicacion;
    }
}
