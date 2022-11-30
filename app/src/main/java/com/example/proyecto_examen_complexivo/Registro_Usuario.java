package com.example.proyecto_examen_complexivo;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import com.example.proyecto_examen_complexivo.base_temp.DbHelper;
import com.example.proyecto_examen_complexivo.modelo.Persona;
import com.example.proyecto_examen_complexivo.modelo.Rol;
import com.example.proyecto_examen_complexivo.modelo.Usuario;
import com.example.proyecto_examen_complexivo.service.Apis;
import com.example.proyecto_examen_complexivo.service.PersonaService;
import com.example.proyecto_examen_complexivo.service.UsuarioService;
import com.google.android.material.navigation.NavigationView;
import retrofit2.Call;
import retrofit2.Callback;

import static com.example.proyecto_examen_complexivo.Registro_Persona.p;


public class Registro_Usuario extends AppCompatActivity {
    private EditText usuario, contra, repetir;
    private Button btnregistra;
    boolean validar = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);
        usuario = findViewById(R.id.txt_usuario1);
        contra = findViewById(R.id.txt_contra_1);
        repetir = findViewById(R.id.txt_rep_1);
        btnregistra = findViewById(R.id.btn_registrar_1);


        btnregistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar=false;
                if (usuario.getText().toString().isEmpty() || contra.getText().toString().isEmpty() || repetir.getText().toString().isEmpty()) {
                    Toast.makeText(Registro_Usuario.this, "Llene todos los campos", Toast.LENGTH_LONG).show();
                } else {
                    if (contra.getText().length() <= 5) {
                        validar = true;
                        Toast.makeText(Registro_Usuario.this, "La contraseña 6 caracteres minimo", Toast.LENGTH_LONG).show();
                    } else if (usuario.getText().length() <= 2) {
                        Toast.makeText(Registro_Usuario.this, "Error usuario debe ser mayor 2 caracteres", Toast.LENGTH_LONG).show();
                        validar = true;
                    } else if (contra.getText().toString().equals(repetir.getText().toString())) {
                        if (validar == false) {
                            getUser();
                        }
                    }else{
                        validar=true;
                        Toast.makeText(Registro_Usuario.this, "La contraseñas no coinciden", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    public void addPersona() {
        personaService = Apis.getPesonaService();
        Call<Persona> call = personaService.createPerson(p);
        call.enqueue(new Callback<Persona>() {
            @Override
            public void onResponse(Call<Persona> call, retrofit2.Response<Persona> response) {
                if (response.isSuccessful()) {
                    Persona p = new Persona(response.body().getIdpersona());
                    Rol r = new Rol(1);

                    Usuario u = new Usuario(usuario.getText().toString(), contra.getText().toString(), p, r);
                    addUsuario(u);
                }
            }
            @Override
            public void onFailure(Call<Persona> call, Throwable t) {
                Toast.makeText(Registro_Usuario.this, "Error al agregar usuario", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getUser() {
        usuarioService = Apis.getUsuarioService();
        Call<Usuario> call = usuarioService.getUser(usuario.getText().toString());
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, retrofit2.Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Registro_Usuario.this, "Usuario ya Registrado", Toast.LENGTH_SHORT).show();
                } else if (response.body() == null) {
                    addPersona();
                }
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(Registro_Usuario.this, "Error al agregar usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }

    UsuarioService usuarioService;
    public void addUsuario(Usuario usuario) {

        usuarioService = Apis.getUsuarioService();
        Call<Usuario> call = usuarioService.addUsuario(usuario);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, retrofit2.Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Usuario usuario1 = response.body();
                    //Agregar Usuario Base sqlite
                    DbHelper bd = new DbHelper(Registro_Usuario.this, "basetemp", null, 2);
                    bd.agregarUsuario(usuario1.getUsu_id(), usuario1.getUsuusuario(), usuario1.getUsu_contrasena(),p.getCedula(),p.getNombre(),p.getApellido(),
                            p.getDireccion(),p.getCelular(),p.getCorreo(),usuario1.getRol_id().getIdrol(),
                            usuario1.getIdpersona().getIdpersona());
                    Toast.makeText(Registro_Usuario.this, "Usuario Agregado", Toast.LENGTH_LONG).show();
                    //ABRIR VENTANA de navegacion
                    Intent home = new Intent(Registro_Usuario.this, Navegacion.class);
                    startActivity(home);
                    finish();
                } else {
                    Toast.makeText(Registro_Usuario.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(Registro_Usuario.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

    }

    PersonaService personaService;


}