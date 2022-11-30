package com.example.proyecto_examen_complexivo.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.proyecto_examen_complexivo.DetalleProducto;
import com.example.proyecto_examen_complexivo.EditarProductoDetalle;
import com.example.proyecto_examen_complexivo.R;
import com.example.proyecto_examen_complexivo.adapter.DetallecomprasAdapter;
import com.example.proyecto_examen_complexivo.databinding.FragmentDetalleComprasBinding;
import com.example.proyecto_examen_complexivo.modelo.Carrito;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link detalle_compras#newInstance} factory method to
 * create an instance of this fragment.
 */
public class detalle_compras extends Fragment implements DetallecomprasAdapter.RecyclerItemClick {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentDetalleComprasBinding binding;


    //carrito
    private ArrayList<Carrito> listCarrito = new ArrayList<>();
    static DetallecomprasAdapter adapter;
    public static TextView txtTotalCompras, txt_carrito_vacio;
    private Button btnComprar;
    //recyclerview
    public static RecyclerView recyclerView;
    public static LottieAnimationView imgCarritoVacio;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public detalle_compras() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static detalle_compras newInstance(String param1, String param2) {
        detalle_compras fragment = new detalle_compras();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        consultarComprasCarrito();
    }

    public void consultarComprasCarrito(){
        Carrito carrito = new Carrito();
        listCarrito = carrito.getcomprados(detalle_compras.this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetalleComprasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        adapter = new DetallecomprasAdapter(listCarrito, getContext(), this);
        recyclerView= binding.recyclecompras;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        consultarComprasCarrito();
        txtTotalCompras = binding.txtTotalCompra;
        // carrito vacio
        imgCarritoVacio = binding.imgCarritoVacio;
        imgCarritoVacio.setAnimation(R.raw.carrito_vacio);
        imgCarritoVacio.playAnimation();
        imgCarritoVacio.setRepeatCount(2000);
        txt_carrito_vacio=binding.carVacio;
        //ocultar imagen carrito vacio
        if(listCarrito.size()>0){
            imgCarritoVacio.setVisibility(View.INVISIBLE);
            txt_carrito_vacio.setVisibility(View.INVISIBLE);
        }
        //calcular del precio
        restarTotal(getContext(),txtTotalCompras,txt_carrito_vacio,imgCarritoVacio);
        btnComprar = binding.btnComprar;
        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listCarrito.size()<=0){
                    new AlertDialog.Builder(getContext())
                            .setIcon(R.drawable.icon_warning)
                            .setTitle("¡Carrito Vacio!")
                            .setMessage("No hay productos agregados")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            })
                            .show();
                }else{
                    abrirFragmnet();
                }

            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void abrirFragmnet (){
        FragmentPago fr = new FragmentPago();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentFrame, fr)
                .addToBackStack(null)
                .commit();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Datos Pago");

    }

    @Override
    public void itemCLick(Carrito producto) {
        Intent intent = new Intent(detalle_compras.this.getContext(), EditarProductoDetalle.class);
        intent.putExtra("productodetalle", producto);
        startActivity(intent);
    }

    public void restarTotal(Context contex,TextView text_total,TextView txt_carrito_vacio,LottieAnimationView img){
        double resultado = 0;
        double suma=0;
        Carrito carrito = new Carrito();
        listCarrito = carrito.getcomprados(contex);
        for (Carrito car: listCarrito){
            if(car.getCantidad()>1){
                suma=car.getCantidad()*car.getPrecio_producto();
                resultado+=suma;
            }else{
                resultado += car.getPrecio_producto();
            }

        }
        System.out.println(resultado+"11111111111111111111111");
        txtTotalCompras.setText("$ "+resultado);
        if(listCarrito.size()==0){
            imgCarritoVacio.setVisibility(View.VISIBLE);
            txt_carrito_vacio.setVisibility(View.VISIBLE);
        }
    }



}