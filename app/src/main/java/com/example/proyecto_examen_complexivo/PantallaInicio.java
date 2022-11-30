package com.example.proyecto_examen_complexivo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class PantallaInicio extends AppCompatActivity {

    private Button signUp;
    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicio);
        signIn= findViewById(R.id.btnSignIn);
        signUp= findViewById(R.id.btnSignUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp1= new Intent(PantallaInicio.this, Registro_Persona.class);
                startActivity(signUp1);
                finish();
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login= new Intent( PantallaInicio.this, Inicio_Login.class);
                startActivity(login);
                finish();
            }
        });

    }

}