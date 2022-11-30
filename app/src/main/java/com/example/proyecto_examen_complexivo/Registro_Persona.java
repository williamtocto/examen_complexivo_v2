package com.example.proyecto_examen_complexivo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.proyecto_examen_complexivo.modelo.Persona;
import com.example.proyecto_examen_complexivo.service.Apis;
import com.example.proyecto_examen_complexivo.service.PersonaService;
import retrofit2.Call;
import retrofit2.Callback;

public class Registro_Persona<validar> extends AppCompatActivity {

    private EditText nombre, apellido, direccion, telefono, correo,cedula;
    private Button btnSiguiente;
    public  static Persona p ;
    boolean validar = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS, WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up1);
        nombre = findViewById(R.id.txt_nombre);
        apellido = findViewById(R.id.txt_apellido);
        direccion = findViewById(R.id.txt_direccion);
        telefono = findViewById(R.id.txt_telefono);
        correo = findViewById(R.id.txt_gmail);
        cedula = findViewById(R.id.txt_cedula);
        btnSiguiente = findViewById(R.id.btn_registrar);

        //ACCION DEL BOTON
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar=false;
                System.out.println(" AQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
                if (nombre.getText().toString().isEmpty()||apellido.getText().toString().isEmpty()||direccion.getText().toString().isEmpty()
                        ||telefono.getText().toString().isEmpty()||correo.getText().toString().isEmpty()||cedula.getText().toString().isEmpty()){
                    Toast.makeText(Registro_Persona.this, "Llene todos los campos", Toast.LENGTH_SHORT).show();
                    validar=true;
                }else{
                    if(cedula.getText().length()<10){
                        Toast.makeText(Registro_Persona.this, "Cedula incorrecta", Toast.LENGTH_SHORT).show();
                        validar =true;
                    }else{
                        if(telefono.getText().length()<10){
                            Toast.makeText(Registro_Persona.this, "Telefono incorrecto", Toast.LENGTH_SHORT).show();
                            validar = true;
                        }else{
                            if (validar==false){
                                p = new Persona();
                                p.setCedula(cedula.getText().toString());
                                p.setNombre(nombre.getText().toString());
                                p.setApellido(apellido.getText().toString());
                                p.setDireccion(direccion.getText().toString());
                                p.setCelular(telefono.getText().toString());
                                p.setCorreo(correo.getText().toString());
                                getPersona(cedula.getText().toString());
                            }
                        }
                    }

                }

            }
        });

    }
    public void abrirVentana(){
        Intent home = new Intent(Registro_Persona.this, Registro_Usuario.class);
        startActivity(home);
        finish();
    }

    public void getPersona(String cedula) {
        validar=false;
        PersonaService personaService;
        personaService = Apis.getPesonaService();
        Call<Persona> call = personaService.getPerson(cedula);
        call.enqueue(new Callback<Persona>() {
            @Override
            public void onResponse(Call<Persona> call, retrofit2.Response<Persona> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(Registro_Persona.this, "Cedula ya registrada", Toast.LENGTH_SHORT).show();
                    validar = true;
                } else if(response.body()==null){
                    Toast.makeText(Registro_Persona.this, "Datos Correctos", Toast.LENGTH_SHORT).show();
                    abrirVentana();
                }
            }
            @Override
            public void onFailure(Call<Persona> call, Throwable t) {
                Toast.makeText(Registro_Persona.this, "Error datos no validos" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}