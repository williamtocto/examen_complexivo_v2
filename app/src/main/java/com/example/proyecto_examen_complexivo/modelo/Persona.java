package com.example.proyecto_examen_complexivo.modelo;

import android.content.Context;
import android.database.Cursor;

import com.example.proyecto_examen_complexivo.base_temp.DbHelper;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Persona {
    @SerializedName("idpersona")
    @Expose
    private int idpersona;
    @SerializedName("cedula")
    @Expose
    private String cedula;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("apellido")
    @Expose
    private String apellido;
    @SerializedName("celular")
    @Expose
    private String celular;

    @SerializedName("correo")
    @Expose
    private String correo;
    @SerializedName("direccion")
    @Expose
    private String direccion;


    public Persona(int idpersona) {
        this.idpersona = idpersona;
    }
    public Persona() {

    }
    public Persona(int idpersona, String cedula, String nombre, String apellido, String celular, String correo, String direccion) {
        this.idpersona = idpersona;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.celular = celular;
        this.correo = correo;
        this.direccion = direccion;

    }



    public int getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(int idpersona) {
        this.idpersona = idpersona;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }


    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }



    public Persona getpersona(Context context){
        DbHelper dbHelper = new DbHelper(context, "basetemp", null, 2);
        String sql = "SELECT * FROM persona;";
        Persona persona=new Persona();
        Cursor cursor = dbHelper.query(sql);
        while (cursor.moveToNext()){

            persona.setIdpersona(cursor.getInt(0));
            persona.setNombre(cursor.getString(1));
            persona.setApellido(cursor.getString(2));
            persona.setCedula(cursor.getString(3));
            persona.setCelular(cursor.getString(4));
            persona.setCorreo(cursor.getString(5));
            persona.setDireccion(cursor.getString(6));
            dbHelper.close();
        }
        return persona;
    }
}
