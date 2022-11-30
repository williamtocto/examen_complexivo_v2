package com.example.proyecto_examen_complexivo.datos_sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;
import com.example.proyecto_examen_complexivo.R;
import com.example.proyecto_examen_complexivo.base_temp.DbHelper;
import com.example.proyecto_examen_complexivo.modelo.Persona;
import com.example.proyecto_examen_complexivo.modelo.Usuario;


import java.util.ArrayList;

public class CargarUsuario {
    
    private Context context;
    private ArrayList<Usuario> usuarioArrayList= new ArrayList<>();


    public CargarUsuario(Context context) {
        this.context = context;
    }

    public ArrayList<Usuario> listarUsuarioP(){

        DbHelper base= new DbHelper(context, "basetemp", null, 2);
        SQLiteDatabase open= base.getReadableDatabase();

        Cursor fila= open.rawQuery("SELECT * FROM usuario",null );

        if (fila.moveToFirst()){
            do{
                Usuario usuario= new Usuario();
                usuario.setUsu_id(fila.getLong(0));
                usuario.setUsuusuario(fila.getString(1));
                usuario.setUsu_contrasena(fila.getString(2));
                Persona p=new Persona();
                p.setCedula(fila.getString(3));
                p.setNombre(fila.getString(4));
                p.setApellido(fila.getString(5));
                p.setDireccion(fila.getString(6));
                p.setCelular(fila.getString(7));
                p.setCorreo(fila.getString(8));
                p.setIdpersona(fila.getInt(10));
                usuario.setIdpersona(p);
                usuarioArrayList.add(usuario);
            }while (fila.moveToNext());
            base.close();
            open.close();
            return usuarioArrayList;
        }else{
            Toast.makeText(context, "No olvides registrarte", Toast.LENGTH_LONG).show();
            return null;
        }
    }
}
