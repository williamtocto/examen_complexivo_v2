package com.example.proyecto_examen_complexivo.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.proyecto_examen_complexivo.R;
import com.example.proyecto_examen_complexivo.base_temp.DbHelper;
import com.example.proyecto_examen_complexivo.datos_sqlite.CargarUsuario;
import com.example.proyecto_examen_complexivo.modelo.Persona;
import com.example.proyecto_examen_complexivo.modelo.Rol;
import com.example.proyecto_examen_complexivo.modelo.Usuario;
import com.example.proyecto_examen_complexivo.service.Apis;
import com.example.proyecto_examen_complexivo.service.UsuarioService;
import retrofit2.Call;
import retrofit2.Callback;

import java.util.ArrayList;


public class FragmentUpdateUser extends Fragment {
    EditText txtUser, txt_password, txt_confirm_pasword;
    Button btn_cancel, btn_save;
    boolean validar = false;
    String user_anterior;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment__update__user, container, false);
        txtUser = view.findViewById(R.id.username);
        txt_password = view.findViewById(R.id.password);
        txt_confirm_pasword = view.findViewById(R.id.password2);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancelar_user);
        btn_save = (Button) view.findViewById(R.id.btn_guardar_user);

        CargarUsuario usu = new CargarUsuario(getContext());
        if (usu.listarUsuarioP() != null) {
            user_anterior = usu.listarUsuarioP().get(0).getUsuusuario();
            txtUser.setText(user_anterior);
        }

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_UpdatePerson fr = new Fragment_UpdatePerson();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentFrame, fr)
                        .addToBackStack(null)
                        .commit();
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Mi Perfil");
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                validar = false;
                if (txtUser.getText().toString().isEmpty() || txt_password.getText().toString().isEmpty() || txt_confirm_pasword.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Llene todos los campos", Toast.LENGTH_LONG).show();
                } else {
                    if (txt_password.getText().length() <= 5) {
                        validar = true;
                        Toast.makeText(getContext(), "La contraseña 6 caracteres minimo", Toast.LENGTH_LONG).show();
                    } else if (txtUser.getText().length() <= 2) {
                        Toast.makeText(getContext(), "Error usuario debe ser mayor 2 caracteres", Toast.LENGTH_LONG).show();
                        validar = true;
                    } else if (txt_password.getText().toString().equals(txt_confirm_pasword.getText().toString())) {
                        if (validar == false) {
                            confirm_password_dialog = new Dialog(getActivity());
                            confirm_password();
                        }
                    } else {
                        validar = true;
                        Toast.makeText(getContext(), "La contraseñas no coinciden", Toast.LENGTH_LONG).show();
                    }
                }

            }

        });

        return view;
    }


    UsuarioService usuarioService;

    public void getUser() {
        usuarioService = Apis.getUsuarioService();
        Call<Usuario> call = usuarioService.getUser(txtUser.getText().toString());
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, retrofit2.Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Usuario ya Registrado", Toast.LENGTH_SHORT).show();
                } else if (response.body() == null) {
                    CargarUsuario usu = new CargarUsuario(getContext());
                    ArrayList<Usuario> array = new ArrayList<>();
                    array = usu.listarUsuarioP();
                    System.out.println(array);
                    Usuario u = new Usuario();
                    Persona p = new Persona();
                    Rol r = new Rol();
                    r.setIdrol(1);
                    p.setIdpersona(usu.listarUsuarioP().get(0).getIdpersona().getIdpersona());
                    u.setIdpersona(p);
                    u.setRol_id(r);
                    u.setUsuusuario(txtUser.getText().toString());
                    u.setUsu_contrasena(txt_password.getText().toString());
                    updateUsuario(usu.listarUsuarioP().get(0).getUsu_id(), u);
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getContext(), "Error al agregar usuario", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void updateUsuario(long id_user, Usuario usuario) {
        usuarioService = Apis.getUsuarioService();
        Call<Usuario> call = usuarioService.updateUsuario(id_user, usuario);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, retrofit2.Response<Usuario> response) {
                if (response.isSuccessful()) {
                    Usuario usuario1;
                    usuario1 = response.body();
                    //Eidtar Usuario Base sqlite
                    DbHelper bd = new DbHelper(getContext());
                    System.out.println(usuario1.getUsuusuario());
                    bd.editarUsuario(usuario1.getUsuusuario(), usuario1.getUsu_contrasena(), user_anterior);
                    Toast.makeText(getContext(), "Usuario Actualizado", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

    }


    Dialog confirm_password_dialog;

    public void confirm_password() {
        boolean bandera = false;
        CargarUsuario use = new CargarUsuario(getContext());
        EditText editTextPassword;
        Button cancel;
        Button confirm;
        confirm_password_dialog.setContentView(R.layout.dialog_confirm_password);
        editTextPassword = confirm_password_dialog.findViewById(R.id.txt_password_dialog);
        cancel = confirm_password_dialog.findViewById(R.id.btn_cancelar_dialog);
        confirm = confirm_password_dialog.findViewById(R.id.btn_confirm_dialog);


        confirm_password_dialog.show();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (use.listarUsuarioP().get(0).getUsu_contrasena().equals(editTextPassword.getText().toString())) {


                    new AlertDialog.Builder(getContext()).setIcon(R.drawable.icon_warning).setTitle("Advertencia").setMessage("¿Esta seguro de Cambiar los datos?")
                            //Boton de Si (Se supone: El Usurio es consiente de lo que esta haciendo)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    getUser();
                                    confirm_password_dialog.dismiss();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            }).show();


                } else {
                    Toast.makeText(getContext(), "Contaseña Incorrecta", Toast.LENGTH_LONG).show();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm_password_dialog.dismiss();
            }
        });


    }
}