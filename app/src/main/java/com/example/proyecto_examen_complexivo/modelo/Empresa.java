package com.example.proyecto_examen_complexivo.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Empresa implements Serializable {
    @SerializedName("idempresa")
    @Expose
    private long idempresa;

    @SerializedName("empnombre")
    @Expose
    private String empnombre;

    @SerializedName("emprepresentante")
    @Expose
    private String emprepresentante;

    @SerializedName("emprazon")
    @Expose
    private String emprazon;

    @SerializedName("empciudad")
    @Expose
    private String empciudad;

    @SerializedName("empingresoanual")
    @Expose
    private int empingresoanual;

    @SerializedName("empobservaciones")
    @Expose
    private String empobservaciones;

    public long getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(long idempresa) {
        this.idempresa = idempresa;
    }

    public String getEmpnombre() {
        return empnombre;
    }

    public void setEmpnombre(String empnombre) {
        this.empnombre = empnombre;
    }

    public String getEmprepresentante() {
        return emprepresentante;
    }

    public void setEmprepresentante(String emprepresentante) {
        this.emprepresentante = emprepresentante;
    }

    public String getEmprazon() {
        return emprazon;
    }

    public void setEmprazon(String emprazon) {
        this.emprazon = emprazon;
    }

    public String getEmpciudad() {
        return empciudad;
    }

    public void setEmpciudad(String empciudad) {
        this.empciudad = empciudad;
    }

    public int getEmpingresoanual() {
        return empingresoanual;
    }

    public void setEmpingresoanual(int empingresoanual) {
        this.empingresoanual = empingresoanual;
    }

    public String getEmpobservaciones() {
        return empobservaciones;
    }

    public void setEmpobservaciones(String empobservaciones) {
        this.empobservaciones = empobservaciones;
    }
}
