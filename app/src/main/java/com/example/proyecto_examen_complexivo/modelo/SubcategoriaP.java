package com.example.proyecto_examen_complexivo.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubcategoriaP {
    @SerializedName("subcatproid")
    @Expose
    private Long id;

    @SerializedName("subcatpronombre")
    @Expose
    private String nombre;

    public SubcategoriaP() {
    }

    public SubcategoriaP(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
