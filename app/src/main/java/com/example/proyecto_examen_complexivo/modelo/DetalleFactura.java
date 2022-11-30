package com.example.proyecto_examen_complexivo.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetalleFactura {

    @SerializedName("iddetalle")
    @Expose
    private long iddetalle;

    @SerializedName("cantidad")
    @Expose
    private int cantidad;

    @SerializedName("precio")
    @Expose
    private double precio;

    @SerializedName("tipo")
    @Expose
    private String tipo;

    @SerializedName("idproducto")
    @Expose
    private Producto idproducto;

    @SerializedName("idfactura")
    @Expose
    private Factura idfactura;

    @SerializedName("idservicio")
    @Expose
    private Servicio idservicio;


    public DetalleFactura() {
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public long getIddetalle() {
        return iddetalle;
    }

    public void setIddetalle(long iddetalle) {
        this.iddetalle = iddetalle;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Producto getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Producto idproducto) {
        this.idproducto = idproducto;
    }

    public Factura getIdfactura() {
        return idfactura;
    }

    public void setIdfactura(Factura idfactura) {
        this.idfactura = idfactura;
    }

    public Servicio getIdservicio() {
        return idservicio;
    }

    public void setIdservicio(Servicio idservicio) {
        this.idservicio = idservicio;
    }
}

