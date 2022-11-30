package com.example.proyecto_examen_complexivo.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Usuario {
    @SerializedName("usu_id")
    @Expose
    private long usu_id;
    @SerializedName("usuusuario")
    @Expose
    private String usuusuario;
    @SerializedName("usu_contrasena")
    @Expose
    private String usu_contrasena;
    @SerializedName("persona")
    @Expose
    private Persona idpersona;
    @SerializedName("rol_id")
    @Expose
    private Rol rol_id;;

    public Usuario(long usu_id, String usuusuario, String usu_contrasena, Persona idpersona, Rol rol_id) {
        this.usu_id = usu_id;
        this.usuusuario = usuusuario;
        this.usu_contrasena = usu_contrasena;
        this.idpersona = idpersona;
        this.rol_id = rol_id;
    }

    public Usuario( String usuusuario, String usu_contrasena, Persona idpersona, Rol rol_id) {
        this.usuusuario = usuusuario;
        this.usu_contrasena = usu_contrasena;
        this.idpersona = idpersona;
        this.rol_id = rol_id;
    }

    public Usuario() {
    }
    public Persona getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(Persona idpersona) {
        this.idpersona = idpersona;
    }

    public long getUsu_id() {
        return usu_id;
    }

    public void setUsu_id(long usu_id) {
        this.usu_id = usu_id;
    }

    public String getUsuusuario() {
        return usuusuario;
    }

    public void setUsuusuario(String usuusuario) {
        this.usuusuario = usuusuario;
    }

    public String getUsu_contrasena() {
        return usu_contrasena;
    }

    public void setUsu_contrasena(String usu_contrasena) {
        this.usu_contrasena = usu_contrasena;
    }

    public Rol getRol_id() {
        return rol_id;
    }

    public void setRol_id(Rol rol_id) {
        this.rol_id = rol_id;
    }
}