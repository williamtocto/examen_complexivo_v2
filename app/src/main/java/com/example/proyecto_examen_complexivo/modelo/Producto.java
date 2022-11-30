package com.example.proyecto_examen_complexivo.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Producto  implements Serializable {

    @SerializedName("idproducto")
    @Expose
    private long idproducto;


    @SerializedName("nombre")
    @Expose
    private String nombre;

    @SerializedName("descripcion")
    @Expose
    private String descripcion;

    @SerializedName("precio")
    @Expose
    private double precio;

    @SerializedName("imagen")
    private String foto;

    @SerializedName("stock")
    private int stock;

    private Long id;


    public Producto() {

    }

    public Producto(long idproducto, Long id, String nombre, double precio, String foto, String  descripcion) {
        this.id = id;
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.precio = precio;
        this.foto = foto;
        this.descripcion = descripcion;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Long getIdproducto() {
        return idproducto;
    }


    public void setIdproducto(long idproducto) {
        this.idproducto = idproducto;
    }

    public Long getId() {
        return id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
