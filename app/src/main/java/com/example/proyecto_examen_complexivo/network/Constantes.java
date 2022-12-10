package com.example.proyecto_examen_complexivo.network;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Constantes {
    private static Retrofit retrofit = null;

    public static final String API_BASE_URL="https://bryantenemea.com/";

    public static Api getApiService(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(Api.class);
    }
}