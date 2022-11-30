package com.example.proyecto_examen_complexivo.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.proyecto_examen_complexivo.*;

public class FragmentPago extends Fragment {

    View view;
    EditText txt_numero_tarjeta;
    EditText txt_mes, txt_anio, txtCVV;
    Button btn_continuar, btn_cancelar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_datos_pago_ejemplo, container, false);
        txt_numero_tarjeta = view.findViewById(R.id.txtNumTarjeta);
        txt_mes = view.findViewById(R.id.txtMesVencimiento);
        txt_anio = view.findViewById(R.id.txtAnioVencimiento);
        txtCVV = view.findViewById(R.id.txtCVV);
        btn_continuar = view.findViewById(R.id.btn_continuar_pago);
        btn_cancelar = view.findViewById(R.id.btn_cancelar_pago);

        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        txt_numero_tarjeta.setText(sharedPreferences.getString("num",""));
        txt_mes.setText(sharedPreferences.getString("mes",""));
        txt_anio.setText(sharedPreferences.getString("anio",""));
        txtCVV.setText(sharedPreferences.getString("cvv",""));

        btn_continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              validarCampos();
            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detalle_compras fr = new detalle_compras();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentFrame, fr)
                        .addToBackStack(null)
                        .commit();
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Detalle de Compras");
            }
        });

        return view;
    }

    public void validarCampos() {

        SharedPreferences preferences= getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("num",txt_numero_tarjeta.getText().toString());
        editor.putString("mes",txt_mes.getText().toString());
        editor.putString("anio",txt_anio.getText().toString());
        editor.putString("cvv",txtCVV.getText().toString());
        editor.commit();
        if (txt_numero_tarjeta.getText().toString().isEmpty() || txt_mes.getText().toString().isEmpty() ||
                txt_anio.getText().toString().isEmpty() || txtCVV.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Llene Todos los campos", Toast.LENGTH_SHORT).show();
        } else
            if (txt_numero_tarjeta.getText().toString().length() < 6) {
                Toast.makeText(getContext(), "Formato de tarjeta icorrecto", Toast.LENGTH_SHORT).show();
            } else
                if (Integer.parseInt(txt_mes.getText().toString())< 0 || Integer.parseInt(txt_mes.getText().toString()) > 12) {
                    Toast.makeText(getContext(), "Mes Incorrecto", Toast.LENGTH_SHORT).show();
                } else
                    if (Integer.parseInt(txt_anio.getText().toString()) < 2022) {
                        Toast.makeText(getContext(), "Año Incorrecto", Toast.LENGTH_SHORT).show();
                    } else
                        if (txtCVV.getText().toString().length() < 3) {
                            Toast.makeText(getContext(), "CVV incorrecto", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Datos Validados correctamente", Toast.LENGTH_SHORT).show();
                            FragmentFacturacion fr = new FragmentFacturacion();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.contentFrame, fr)
                                    .addToBackStack(null)
                                    .commit();
                            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Detalle de Factura");

                        }
                    }
                }


