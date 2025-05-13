package com.chattide.web.DTO;

/**
 *
 * @author Juan - Luis
 */
public class UsuarioDTO {

    private long id;
    private String nombre;
    private String email;
    private String avatar;

    public UsuarioDTO() {
    }

    public UsuarioDTO(long id, String nombre, String email, String avatar) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.avatar = avatar;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
