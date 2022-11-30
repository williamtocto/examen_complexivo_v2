package com.example.proyecto_examen_complexivo.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;

public class Factura {

    @SerializedName("usu_id")
    @Expose
    private Usuario usu_id;

    @SerializedName("idfactura")
    @Expose
    private long idfactura;

    @SerializedName("fecha_factura")
    @Expose
    private String fecha_factura;


    public Factura() {
    }



    public String getFecha_factura() {
        return fecha_factura;
    }

    public void setFecha_factura(String fecha_factura) {
        this.fecha_factura = fecha_factura;
    }

    public Long getIdfactura() {
        return idfactura;
    }

    public void setIdfactura(Long idfactura) {
        this.idfactura = idfactura;
    }

    public Usuario getUsu_id() {
        return usu_id;
    }

    public void setUsu_id(Usuario usu_id) {
        this.usu_id = usu_id;
    }

    public void setIdfactura(long idfactura) {
        this.idfactura = idfactura;
    }
}
