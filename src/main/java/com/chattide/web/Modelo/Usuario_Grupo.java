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
        name = "Usuario_Grupo",
        schema = "bdchattide"
)
public class Usuario_Grupo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long usuarioGrupoID;

    @Column(name = "FechaUnion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaUnion;

    //Conexiones
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario_grupo;

    @ManyToOne
    @JoinColumn(name = "idGrupo")
    private Grupo grupo_usuario;

    public Usuario_Grupo() {
    }

    public Usuario_Grupo(long usuarioGrupoID, Date fechaUnion, Usuario usuario_grupo, Grupo grupo_usuario) {
        this.usuarioGrupoID = usuarioGrupoID;
        this.fechaUnion = fechaUnion;
        this.usuario_grupo = usuario_grupo;
        this.grupo_usuario = grupo_usuario;
    }

    public long getUsuarioGrupoID() {
        return usuarioGrupoID;
    }

    public void setUsuarioGrupoID(long usuarioGrupoID) {
        this.usuarioGrupoID = usuarioGrupoID;
    }

    public Date getFechaUnion() {
        return fechaUnion;
    }

    public void setFechaUnion(Date fechaUnion) {
        this.fechaUnion = fechaUnion;
    }

    public Usuario getUsuario_grupo() {
        return usuario_grupo;
    }

    public void setUsuario_grupo(Usuario usuario_grupo) {
        this.usuario_grupo = usuario_grupo;
    }

    public Grupo getGrupo_usuario() {
        return grupo_usuario;
    }

    public void setGrupo_usuario(Grupo grupo_usuario) {
        this.grupo_usuario = grupo_usuario;
    }
}
