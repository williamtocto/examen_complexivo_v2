package com.example.proyecto_examen_complexivo.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.proyecto_examen_complexivo.*;
import com.example.proyecto_examen_complexivo.base_temp.DbHelper;
import com.example.proyecto_examen_complexivo.datos_sqlite.CargarUsuario;
import com.example.proyecto_examen_complexivo.modelo.Persona;
import com.example.proyecto_examen_complexivo.service.Apis;
import com.example.proyecto_examen_complexivo.service.PersonaService;
import retrofit2.Call;
import retrofit2.Callback;


public class Fragment_UpdatePerson extends Fragment {

    EditText txtCedula, txtNombre, txtApellido, txtDireccion, txtTelefono, txtEmail, txtUsuario, txtContrasenia, txt_nueva_contra, confirm_password;
    Button btn_guardar, btn_cancelar, btn_editar, btn_cerrar_sesion;
    View view;
    long idpersona;

    Persona per = new Persona();
    String cedula_anterior;

    Dialog confirm_password_dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        CargarUsuario usu = new CargarUsuario(getContext());
        view = inflater.inflate(R.layout.fragment_update_person, container, false);
        txtCedula = view.findViewById(R.id.txt_cedula_update);
        txtNombre = view.findViewById(R.id.txt_nombre_update);
        txtApellido = view.findViewById(R.id.txt_apellido_update);
        txtDireccion = view.findViewById(R.id.txtDireccionUpdate);
        txtTelefono = view.findViewById(R.id.txt_telefono_update);
        txtEmail = view.findViewById(R.id.txt_usuario_update);
        btn_cerrar_sesion = view.findViewById(R.id.btn_cerrar_sesion);


        btn_cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext()).setIcon(R.drawable.icon_warning).setTitle("Advertencia").setMessage("¿Esta seguro de Cerrar Sesion?")
                        //Boton de Si (Se supone: El Usurio es consiente de lo que esta haciendo)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getContext(), PantallaInicio.class);
                                startActivity(intent);
                                DbHelper bd = new DbHelper(getContext());
                                bd.eliminarUsuario();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).show();



            }
        });


        //CARGAR DATOS USUARIO
        if (usu.listarUsuarioP() != null) {
            cedula_anterior = usu.listarUsuarioP().get(0).getIdpersona().getCedula();
            txtCedula.setText(cedula_anterior);
            txtNombre.setText(usu.listarUsuarioP().get(0).getIdpersona().getNombre());
            txtApellido.setText(usu.listarUsuarioP().get(0).getIdpersona().getApellido());
            txtDireccion.setText(usu.listarUsuarioP().get(0).getIdpersona().getDireccion());
            txtTelefono.setText(usu.listarUsuarioP().get(0).getIdpersona().getCelular());
            txtEmail.setText(usu.listarUsuarioP().get(0).getIdpersona().getCorreo());
//            txtUsuario.setText(usu.listarUsuarioP().get(0).getUsuusuario());
            idpersona = usu.listarUsuarioP().get(0).getIdpersona().getIdpersona();

        }


        btn_guardar = (Button) view.findViewById(R.id.btn_guardar);
        btn_editar = (Button) view.findViewById(R.id.btn_editar);
        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUpdateUser fr = new FragmentUpdateUser();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentFrame, fr)
                        .addToBackStack(null)
                        .commit();

            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getContext()).setIcon(R.drawable.icon_warning).setTitle("Advertencia").setMessage("¿Esta seguro de Cambiar los datos?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (txtNombre.getText().toString().isEmpty() || txtApellido.getText().toString().isEmpty() || txtDireccion.getText().toString().isEmpty() || txtTelefono.getText().toString().isEmpty() || txtEmail.getText().toString().isEmpty() || txtCedula.getText().toString().isEmpty()) {
                                    Toast.makeText(getContext(), "Llene todos los campos", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (txtCedula.getText().length() < 10) {
                                        Toast.makeText(getContext(), "Cedula incorrecta", Toast.LENGTH_SHORT).show();
                                    }else {
                                        if (txtTelefono.getText().length() < 10) {
                                            Toast.makeText(getContext(), "Telefono incorrecto", Toast.LENGTH_SHORT).show();
                                        }else{
                                            System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
                                            //CARGAR DATOS OBJETO PERSONA
                                            per.setCedula(txtCedula.getText().toString());
                                            per.setNombre(txtNombre.getText().toString());
                                            per.setApellido(txtApellido.getText().toString());
                                            per.setDireccion(txtDireccion.getText().toString());
                                            per.setCelular(txtTelefono.getText().toString());
                                            per.setCorreo(txtEmail.getText().toString());
                                            confirm_password_dialog = new Dialog(getActivity());
                                            confirm_password();
                                        }
                                    }

                                }

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).show();

            }

        });
        btn_cancelar = (Button) view.findViewById(R.id.btn_cancelar);
        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductosFragment fr = new ProductosFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentFrame, fr)
                        .addToBackStack(null)
                        .commit();
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Productos");

                Toast.makeText(getContext(), "Cancelar", Toast.LENGTH_LONG).show();
            }
        });
        return view;

    }

    boolean bandera = false;

    public void confirm_password() {
        bandera = false;
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
                    Toast.makeText(getContext(), "Contaseña Correcta", Toast.LENGTH_LONG).show();
                    updatePerson(per);
                    confirm_password_dialog.dismiss();
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

    PersonaService personaService;

    public void updatePerson(Persona person) {

        personaService = Apis.getPesonaService();
        Call<Persona> call = personaService.updatePerson(idpersona, person);
        call.enqueue(new Callback<Persona>() {
            @Override
            public void onResponse(Call<Persona> call, retrofit2.Response<Persona> response) {
                if (response.isSuccessful()) {
                    per = response.body();
                    DbHelper bd = new DbHelper(getContext());
                    bd.editarPersona(per.getCedula(), per.getNombre(), per.getApellido(), per.getDireccion(), per.getCelular(), per.getCorreo(), cedula_anterior);
                    Toast.makeText(getContext(), "Persona Actualizada", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Persona> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}