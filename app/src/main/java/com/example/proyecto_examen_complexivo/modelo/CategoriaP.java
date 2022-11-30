package com.example.proyecto_examen_complexivo.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoriaP {
    @SerializedName("catproid")
    @Expose
    private Long id;

    @SerializedName("catpronombre")
    @Expose
    private String nombre;

    @SerializedName("catprodescripcion")
    @Expose
    private String catprodescripcion;


    public CategoriaP() {
    }

    public CategoriaP(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public CategoriaP(Long id, String nombre, String catprodescripcion) {
        this.id = id;
        this.nombre = nombre;
        this.catprodescripcion = catprodescripcion;
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


