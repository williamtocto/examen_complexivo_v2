package com.example.proyecto_examen_complexivo.base_temp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DbHelper extends   SQLiteOpenHelper {

    //Creamos nuestra base temporal que va ser utilizada siempre en la version 2
    private static final String DATABASE_NOMBRE = "basetemp.db";
    private static final int DATABASE_VERSION=2;


    public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    public DbHelper(@Nullable Context context) {
        super(context,  DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Utilidades.CREAR_TABLA_CARRITO);
        sqLiteDatabase.execSQL(Utilidades.CREAR_TABLA_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_CARRITO);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_USUARIO);
        onCreate(sqLiteDatabase);
    }

    public  void noQuery(String nsql ){
        this.getWritableDatabase().execSQL(nsql);
    }

    public Cursor query(String sql){
        return this.getReadableDatabase().rawQuery(sql, null);
    }



    //TABLA USUARIO
    public boolean agregarUsuario(long usu_id, String usuusuario, String usu_contrasena,  String cedula,String nombre, String apellido,String direccion, String telefono, String correo,long rol_id, long persona_id){
        SQLiteDatabase bd= getWritableDatabase();

        if (bd!=null){
            try{
                bd.execSQL("DELETE FROM usuario");
                System.out.println(usuusuario+" pppppppppppppppppppppppppppp");
                bd.execSQL("INSERT INTO usuario VALUES("+usu_id+",'"+usuusuario+"','"+usu_contrasena+"','"+cedula+"','"+nombre+"','"+apellido+"','"+direccion+"','"+telefono+"','"+correo+"','"+rol_id+"','"+persona_id+"')");
                bd.close();
                return true;
            }catch (SQLiteConstraintException e){
                return false;
            }

        }else{
            return false;
        }
    }

    public Boolean ValidacionCarrito(String id){
        SQLiteDatabase sql = this.getWritableDatabase();
        Cursor cursor = sql.rawQuery("SELECT * FROM carrito WHERE nombre_producto = ? ", new String[]{id});
        if (cursor.getCount()>0){
            return true;
        }else{
            return false;
        }

    }

    public void eliminarUsuario(){
        SQLiteDatabase bd= getWritableDatabase();
        bd.execSQL("DELETE FROM usuario");
        bd.close();
    }


    public void editarUsuario(String nombreUsuario, String contrasenia, String user_anterior){
        SQLiteDatabase bd= getWritableDatabase();
        bd.execSQL("UPDATE usuario SET usuusuario='"+nombreUsuario+"', usu_contrasena='"+contrasenia+"'"+" WHERE usuusuario= '"+user_anterior+"'");
        bd.close();
    }


    public void editarPersona(String cedula, String nombre, String apellido, String direccion, String telefono, String correo,String cedula_anterior){
        SQLiteDatabase bd= this.getWritableDatabase();
        bd.execSQL("UPDATE usuario SET cedula='"+cedula+"', nombre='"+nombre+"',apellido='"+ apellido+"',direccion= '"+direccion+"',telefono= '"+telefono+"', correo= '"+correo+"'  WHERE cedula= '"+cedula+"'");
        bd.close();
    }

/*
    public void eliminarCarrito(String name){
        SQLiteDatabase bd= getWritableDatabase();
        String sql="";
        if (name == null) {
            sql= "DELETE FROM carrito";
        }else {
            sql= "DELETE FROM carrito WHERE nombreProducto='"+name+"'";
        }
        bd.execSQL(sql);
        bd.close();

    }



    public void editarCarrito(String nombre, String cantidadCompra){
        SQLiteDatabase bd= this.getWritableDatabase();
        bd.execSQL("UPDATE carrito SET cantidadCompra="+cantidadCompra+" WHERE nombreProducto='"+nombre+"'");
        bd.close();
    }*/
}
