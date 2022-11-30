package com.example.proyecto_examen_complexivo.service;

public interface Validacion_user {
    void toggleProgressBar(boolean status);
    void lanzarActividad(Class<?> tipoActividad);
    void showMessage(String msg);
}
