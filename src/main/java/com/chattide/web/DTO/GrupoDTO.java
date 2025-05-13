package com.chattide.web.DTO;

import com.chattide.web.Utilities.Enum.TipoPrivacidad;

/**
 *
 * @author Juan - Luis
 */
public class GrupoDTO {

    private long id;
    private String nombre;
    private String descripcion;
    private TipoPrivacidad tipoPrivacidad;
    private int miembrosCount;
    private int publicacionesCount;

    public GrupoDTO() {
    }

    public GrupoDTO(long id, String nombre, String descripcion, TipoPrivacidad tipoPrivacidad, int miembrosCount, int publicacionesCount) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoPrivacidad = tipoPrivacidad;
        this.miembrosCount = miembrosCount;
        this.publicacionesCount = publicacionesCount;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoPrivacidad getTipoPrivacidad() {
        return tipoPrivacidad;
    }

    public void setTipoPrivacidad(TipoPrivacidad tipoPrivacidad) {
        this.tipoPrivacidad = tipoPrivacidad;
    }

    public int getMiembrosCount() {
        return miembrosCount;
    }

    public void setMiembrosCount(int miembrosCount) {
        this.miembrosCount = miembrosCount;
    }

    public int getPublicacionesCount() {
        return publicacionesCount;
    }

    public void setPublicacionesCount(int publicacionesCount) {
        this.publicacionesCount = publicacionesCount;
    }
}
