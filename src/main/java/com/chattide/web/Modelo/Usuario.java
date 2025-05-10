package com.chattide.web.Modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;

import java.io.Serializable;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Juan - Luis
 */
@Entity
@Table(
        name = "Usuario",
        schema = "bdchattide"
)

public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long usuarioID;

    @Column(name = "Nombre", length = 60, nullable = false)
    private String nombre;

    @Column(name = "Email", length = 100, nullable = false)
    private String email;

    @Column(name = "Contrasenia", length = 60, nullable = false)
    private String contrasenia;

    @Column(name = "AvatarURL", length = 200, nullable = false)
    private String avatar;

    // Conexiones
    @OneToMany(mappedBy = "usuario_grupo")
    private List<Usuario_Grupo> listaUsuarioGrupo;

    @OneToMany(mappedBy = "usuario_publicacion")
    private List<Publicacion> listaPublicacion;

    @OneToMany(mappedBy = "usuario_comentario")
    private List<Comentario> listaComentarios;

    @OneToMany(mappedBy = "usuario_likes")
    private List<Likes> listaLikes;

    public Usuario() {
    }

    public Usuario(long usuarioID) {
        this.usuarioID = usuarioID;
    }

    public Usuario(String nombre, String email, String contrasenia, String avatar) {
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
        this.avatar = avatar;
    }

    public Usuario(long usuarioID, String nombre, String email, String contrasenia, String avatar, List<Usuario_Grupo> listaUsuarioGrupo, List<Publicacion> listaPublicacion, List<Comentario> listaComentarios, List<Likes> listaLikes) {
        this.usuarioID = usuarioID;
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
        this.avatar = avatar;
        this.listaUsuarioGrupo = listaUsuarioGrupo;
        this.listaPublicacion = listaPublicacion;
        this.listaComentarios = listaComentarios;
        this.listaLikes = listaLikes;
    }

    public long getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(long usuarioID) {
        this.usuarioID = usuarioID;
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

    public void setContrasenia(String contrasenia) {
        this.contrasenia = BCrypt.hashpw(contrasenia, BCrypt.gensalt());
    }

    public boolean checkContrasenia(String contra) {
        return BCrypt.checkpw(contra, this.contrasenia);
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Usuario_Grupo> getListaUsuarioGrupo() {
        return listaUsuarioGrupo;
    }

    public void setListaUsuarioGrupo(List<Usuario_Grupo> listaUsuarioGrupo) {
        this.listaUsuarioGrupo = listaUsuarioGrupo;
    }

    public List<Publicacion> getListaPublicacion() {
        return listaPublicacion;
    }

    public void setListaPublicacion(List<Publicacion> listaPublicacion) {
        this.listaPublicacion = listaPublicacion;
    }

    public List<Comentario> getListaComentarios() {
        return listaComentarios;
    }

    public void setListaComentarios(List<Comentario> listaComentarios) {
        this.listaComentarios = listaComentarios;
    }

    public List<Likes> getListaLikes() {
        return listaLikes;
    }

    public void setListaLikes(List<Likes> listaLikes) {
        this.listaLikes = listaLikes;
    }
}
