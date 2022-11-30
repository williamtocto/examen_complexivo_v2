package com.example.proyecto_examen_complexivo.base_temp;

import android.widget.ImageView;

import com.example.proyecto_examen_complexivo.modelo.Persona;
import com.example.proyecto_examen_complexivo.modelo.Rol;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Utilidades {


    //Esta clase se utiliza para armar las tablas

    //Constantes campos tabla persona
    public static final String TABLA_CARRITO = "carrito ";
    public static final String ID_CARRITO = "id_carrito ";
    public static final String NOMBRE_PRODUCTO = "nombre_producto ";
    public static final String CANTIDAD_CARRITO = "cantidad ";
    public static final String PRECIO = "precio ";
    public static final String DESCRIPCION_PRODUCTO = "descripcion_producto ";
    public static final String IMAGENFOTO = "IMAGEN ";
    public static final String TIPO = "tipo";
    public static final String IDPRODUCTO = "idproducto";


    public static final String CREAR_TABLA_CARRITO = "CREATE TABLE " +
            "" + TABLA_CARRITO + " (" + ID_CARRITO + " " +
            " TEXT PRIMARY KEY, "
            + NOMBRE_PRODUCTO + " TEXT,"
            + CANTIDAD_CARRITO + " INTEGER,"
            + PRECIO + " DOUBLE, "
            + DESCRIPCION_PRODUCTO + " TEXT,"
            + IMAGENFOTO + " TEXT,"
            + TIPO + " TEXT,"
            + IDPRODUCTO + " INTEGER)";


    public static final String TABLA_USUARIO = "usuario ";
    public static final String ID_USUARIO = "ud_id ";
    public static final String USUUSUARIO = "usuusuario ";
    public static final String USU_CONTRASENA = "usu_contrasena ";
    public static final String CEDULA = "cedula ";
    public static final String NOMBRE = "nombre ";
    public static final String APELLIDO = "apellido ";
    public static final String IMAGEN = "IMAGEN ";
    public static final String TELEFONO = "telefono ";
    public static final String DIRECCION = "direccion ";
    public static final String CORREO = "correo ";
    public static final String ROL_ID = "rol_id ";
    public static final String PERSONA_ID = "persona_id ";



    public static final String CREAR_TABLA_USUARIO= "CREATE TABLE "+TABLA_USUARIO+" (" +
            " "+ID_USUARIO+" INTEGER PRIMARY KEY NOT NULL," +
            " "+USUUSUARIO+" text, " +
            " "+USU_CONTRASENA+" text, " +
            " "+CEDULA+" text, " +
            " "+NOMBRE+" text, " +
            " "+APELLIDO+" text, " +
            " "+DIRECCION+" text, " +
            " "+TELEFONO+" text, " +
            " "+CORREO+" text, " +
            " "+ROL_ID+" INTEGER, " +
            " "+PERSONA_ID+" INTEGER) ";

}
