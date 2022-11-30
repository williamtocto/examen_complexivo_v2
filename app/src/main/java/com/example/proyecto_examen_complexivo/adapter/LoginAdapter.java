package com.example.proyecto_examen_complexivo.adapter;


import android.os.AsyncTask;

import android.widget.Toast;
import com.example.proyecto_examen_complexivo.Inicio_Login;
import com.example.proyecto_examen_complexivo.MainActivity;
import com.example.proyecto_examen_complexivo.base_temp.DbHelper;
import com.example.proyecto_examen_complexivo.modelo.Usuario;
import com.example.proyecto_examen_complexivo.service.Apis;
import com.example.proyecto_examen_complexivo.service.UsuarioService;
import com.example.proyecto_examen_complexivo.service.Validacion_user;
import retrofit2.Call;
import retrofit2.Callback;

import java.util.ArrayList;

public class LoginAdapter extends AsyncTask<Object, Void, Boolean> {

    private Validacion_user comunicacion;

    //Contructor
    public LoginAdapter(Validacion_user comunicacion) {
        this.comunicacion = comunicacion;
    }

    // Codigo que se ejcuta antes de comenzar el hilo
    @Override
    protected void onPreExecute() {
        comunicacion.toggleProgressBar(true);
    }


    // Codigo de segundo plano evaluar credenciales de usuario
    @Override
    protected Boolean doInBackground(Object... objects) {

        try {
            Thread.sleep((int) objects[1]);
            int validar_login = (int) objects[0];
            if(validar_login==0){
               return  false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    // Codigo despues del hilo
    @Override
    protected void onPostExecute(Boolean bo) {
        if (bo) {
            this.comunicacion.lanzarActividad(MainActivity.class);
            this.comunicacion.showMessage("Datos Correctos");
            this.comunicacion.toggleProgressBar(false);
        } else {
            this.comunicacion.showMessage("Datos Incorrectos");
            this.comunicacion.toggleProgressBar(false);
        }
    }


}
