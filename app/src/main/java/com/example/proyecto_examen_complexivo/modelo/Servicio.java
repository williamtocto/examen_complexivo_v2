package com.example.proyecto_examen_complexivo.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Servicio implements Serializable {

    @SerializedName("idservicio")
    @Expose
    private Long id;

    @SerializedName("sernombre")
    @Expose
    private String nombre;

    @SerializedName("serprecio")
    @Expose
    private String precio;

    @SerializedName("serimagen")
    @Expose
    private String foto;

    @SerializedName("serdescripcion")
    @Expose
    private String  descripcion;

    @SerializedName("idempresa")
    private Empresa idempresa;



    public Servicio() {

    }

    public Servicio(Long id, String nombre, String precio, String foto, String  descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.foto = foto;
        this.descripcion = descripcion;
    }

    public Empresa getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(Empresa idempresa) {
        this.idempresa = idempresa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

}
