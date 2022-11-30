package com.example.proyecto_examen_complexivo.service;

import com.example.proyecto_examen_complexivo.modelo.Persona;

import retrofit2.Call;
import retrofit2.http.*;


public interface PersonaService {
    @POST("crear")
    Call<Persona> createPerson(@Body Persona persona);

    @GET("listar/{cedula}")
    Call<Persona> getPerson(@Path("cedula") String cedula);

    @PUT("editar/{idpersona}")
    Call<Persona> updatePerson(@Path("idpersona") long idpersona,@Body Persona persona);
}
