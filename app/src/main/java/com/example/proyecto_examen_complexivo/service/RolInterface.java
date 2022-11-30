package com.example.proyecto_examen_complexivo.service;

import com.example.proyecto_examen_complexivo.modelo.Rol;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface RolInterface {
    @GET("list")
    Call<List<Rol>> getPosts();
}
