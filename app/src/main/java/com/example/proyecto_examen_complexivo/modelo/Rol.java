package com.example.proyecto_examen_complexivo.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rol {
    @SerializedName("idrol")
    @Expose
    private long idrol;
    @SerializedName("rol")
    @Expose
    private String rol;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;


    public Rol(long idrol) {
        this.idrol = idrol;
    }
    public Rol() {
    }

    public Rol(long idrol, String rol, String descripcion) {
        this.idrol = idrol;
        this.rol = rol;
        this.descripcion = descripcion;
    }

    public long getIdrol() {
        return idrol;
    }

    public void setIdrol(long idrol) {
        this.idrol = idrol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
