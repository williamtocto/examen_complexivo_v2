package com.example.proyecto_examen_complexivo.service;

public class Apis {

    public static final String BASE_URL = "http://10.0.2.2:8080/api/";


    public static UsuarioService getUsuarioService() {
        return Peticion.peticion(BASE_URL+"usuario/").create(UsuarioService.class);
    }

    public static PersonaService getPesonaService() {
        return Peticion.peticion(BASE_URL+"persona/").create(PersonaService.class);
    }
}
