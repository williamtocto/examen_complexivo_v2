package com.example.proyecto_examen_complexivo.service;

import com.example.proyecto_examen_complexivo.modelo.Persona;
import com.example.proyecto_examen_complexivo.modelo.Usuario;


import com.google.gson.JsonArray;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface UsuarioService {
    @GET("listar")
    Call<List<Usuario>> getUser();

    @GET("listar/{usuusuario}")
    Call<Usuario> getUser(@Path("usuusuario") String usuario);

    @POST("login")
    Call<Integer> validar_login(@Body Usuario usuario);

    @POST("crear")
    Call<Usuario> addUsuario(@Body Usuario usuario);

    @PUT("editar/{usu_id}")
    Call<Usuario> updateUsuario(@Path("usu_id") long user_id,@Body Usuario usuario);
}
