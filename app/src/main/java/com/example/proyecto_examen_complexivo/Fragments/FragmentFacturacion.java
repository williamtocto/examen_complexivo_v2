    package com.example.proyecto_examen_complexivo.Fragments;

    import android.database.Cursor;
    import android.os.Bundle;
    import android.widget.Button;
    import android.widget.TextView;
    import android.widget.Toast;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.fragment.app.Fragment;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import androidx.fragment.app.FragmentManager;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;
    import com.example.proyecto_examen_complexivo.R;
    import com.example.proyecto_examen_complexivo.adapter.FacturacionAdapter;
    import com.example.proyecto_examen_complexivo.base_temp.DbHelper;
    import com.example.proyecto_examen_complexivo.facturacion;
    import com.example.proyecto_examen_complexivo.modelo.*;
    import com.example.proyecto_examen_complexivo.network.Constantes;
    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;
    import retrofit2.Retrofit;

    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Date;


    public class FragmentFacturacion extends Fragment {
        ArrayList<Carrito> listCarrito;
        Button btnComprarFacturacion, btn_cancelar;
        RecyclerView recyclerView1;
        TextView txtTotalPagar, txtUsuarioFacturacion, txtCedulaFacturacion, txtDireccionFacturacion, txtCorreoFacturacion, txtTelefonoFacturacion, txtFechaFacturacion;

        private Persona persona = new Persona();
        private Usuario usuario = new Usuario();
        private  Factura factura = new Factura();
        private DetalleFactura detalleFactura = new DetalleFactura();
        private  Servicio servicio = new Servicio();
        private  Producto producto=new Producto();

        View view;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_facturacion, container, false);
            listCarrito = new ArrayList<>();
            recyclerView1 = (RecyclerView) view.findViewById(R.id.recyclerViewFactura);
            recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
            consultarComprasCarrito();
            FacturacionAdapter adapter = new FacturacionAdapter(listCarrito, getContext());
            recyclerView1.setAdapter(adapter);
            txtTotalPagar = view.findViewById(R.id.txtTotalPagar);
            txtUsuarioFacturacion = view.findViewById(R.id.txtUsuarioFacturacion);
            txtCedulaFacturacion = view.findViewById(R.id.txtCedulaFacturacion);
            txtDireccionFacturacion =  view.findViewById(R.id.txtDireccionFacturacion);
            txtCorreoFacturacion =  view.findViewById(R.id.txtCorreoFacturacion);
            txtTelefonoFacturacion =  view.findViewById(R.id.txtTelefonoFacturacion);
            txtFechaFacturacion =  view.findViewById(R.id.txtFechaFacturacion);
            btnComprarFacturacion =  view.findViewById(R.id.btnComprarFacturacion);
            btn_cancelar= view.findViewById(R.id.btnCancelaracturacion2);
            btn_cancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     FragmentPago fr = new FragmentPago();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentFrame, fr)
                            .addToBackStack(null)
                            .commit();
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Datos Pago");
                }
            });


        btnComprarFacturacion.setOnClickListener(x -> {
            crearfactura();
        });


        double resultado = 0;
        for (Carrito car : listCarrito) {
            double suma = 0;
            if (car.getCantidad() > 1) {
                suma = car.getPrecio_producto() * car.getCantidad();
                resultado += suma;
            } else {
                resultado += car.getPrecio_producto();
            }

        }
        consultausuario();
        txtTotalPagar.setText(resultado + "");
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        factura.setFecha_factura(date);
        txtFechaFacturacion.setText(date);




        return  view;
    }
    public void consultausuario() {
        DbHelper dbHelper = new DbHelper(getContext(), "basetemp", null, 2);
        String sql = "SELECT cedula, nombre, apellido, direccion, telefono, correo FROM usuario ";
        Cursor cursor = dbHelper.query(sql);
        while (cursor.moveToNext()) {
            persona.setCedula(cursor.getString(0));
            txtCedulaFacturacion.setText(persona.getCedula());
            persona.setNombre(cursor.getString(1));
            persona.setApellido(cursor.getString(2));
            txtUsuarioFacturacion.setText(persona.getNombre() + " " + persona.getApellido());
            persona.setDireccion(cursor.getString(3));
            txtDireccionFacturacion.setText(persona.getDireccion());
            persona.setCelular(cursor.getString(4));
            txtTelefonoFacturacion.setText(persona.getCelular());
            persona.setCorreo(cursor.getString(5));
            txtCorreoFacturacion.setText(persona.getCorreo());
            dbHelper.close();
        }

    }

    public void consultarComprasCarrito() {
        Carrito carrito = new Carrito();
        listCarrito = carrito.getcomprados(getContext());

    }

    public void crearfactura() {

        //crea factura
        DbHelper dbhelper = new DbHelper(getContext(), "basetemp", null, 2);
        String nsql = "SELECT ud_id from usuario";
        Cursor cursor = dbhelper.query(nsql);
        while (cursor.moveToNext()) {
            usuario.setUsu_id(cursor.getInt(0));
            factura.setUsu_id(usuario);
            dbhelper.close();
        }
        Constantes constantes = new Constantes();
        Call<Factura> fac = constantes.getApiService().getfactura(factura);
        fac.enqueue(new Callback<Factura>() {
            @Override
            public void onResponse(Call<Factura> call, retrofit2.Response<Factura> response) {
                factura.setIdfactura(response.body().getIdfactura());

                //crea factura
                //crea detalle factura
                //dato quemado servicio
                for (Carrito car : listCarrito) {
                    double total = 0;
                    if (car.getCantidad() > 1) {
                        total = car.getPrecio_producto() * car.getCantidad();
                    }
                    switch (car.getTipo().toString()){
                        case "producto":
                            servicio.setId(3L);
                            producto.setIdproducto(car.getIdproducto());

                            break;
                        case "servicio":
                            producto.setIdproducto(4);
                            servicio.setId(car.getIdproducto());
                            break;
                    }

                    detalleFactura.setIddetalle(0);
                    detalleFactura.setCantidad(car.getCantidad());
                    detalleFactura.setPrecio(total);
                    detalleFactura.setTipo(car.getTipo());
                    detalleFactura.setIdfactura(factura);
                    detalleFactura.setIdproducto(producto);
                    detalleFactura.setIdservicio(servicio);

                    Call<DetalleFactura> res = constantes.getApiService().getdetallefactura(detalleFactura);
                    res.enqueue(new Callback<DetalleFactura>() {
                @Override
                public void onResponse(Call<DetalleFactura> call, Response<DetalleFactura> response) {
                    Carrito carrito = new Carrito();
                    carrito.Limpiarcarrito(getContext());
                    Toast.makeText(getContext(), "Compra exitosa", Toast.LENGTH_SHORT).show();
                    ///////////////////////////aqui para regresar al fragament de producto corregir
                    if (response.isSuccessful()) {
                        FragmentManager fm= getActivity().getSupportFragmentManager();
                        fm.beginTransaction().replace(R.id.contentFrame, new ProductosFragment()).commit();
                    }

                }


                @Override
                public void onFailure(Call<DetalleFactura> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getContext(), "Error detalle", Toast.LENGTH_SHORT).show();

                }
            });

                }


            }
            @Override
            public void onFailure(Call<Factura> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "Error Factura", Toast.LENGTH_SHORT).show();
            }
        });



    }
}