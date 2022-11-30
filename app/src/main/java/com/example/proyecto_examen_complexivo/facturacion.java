package com.example.proyecto_examen_complexivo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_examen_complexivo.Fragments.Fragment_UpdatePerson;
import com.example.proyecto_examen_complexivo.adapter.FacturacionAdapter;
import com.example.proyecto_examen_complexivo.base_temp.DbHelper;
import com.example.proyecto_examen_complexivo.modelo.Carrito;
import com.example.proyecto_examen_complexivo.modelo.DetalleFactura;
import com.example.proyecto_examen_complexivo.modelo.Factura;
import com.example.proyecto_examen_complexivo.modelo.Persona;
import com.example.proyecto_examen_complexivo.modelo.Producto;
import com.example.proyecto_examen_complexivo.modelo.Servicio;
import com.example.proyecto_examen_complexivo.modelo.Usuario;
import com.example.proyecto_examen_complexivo.network.Constantes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.ProgressDialog.show;

public class facturacion extends AppCompatActivity {

    ArrayList<Carrito> listCarrito;
    Button btnComprarFacturacion;
    RecyclerView recyclerView1;
    TextView txtTotalPagar, txtUsuarioFacturacion, txtCedulaFacturacion, txtDireccionFacturacion, txtCorreoFacturacion, txtTelefonoFacturacion, txtFechaFacturacion;

    private Persona persona = new Persona();
    private Usuario usuario = new Usuario();
    private static Factura factura = new Factura();
    private DetalleFactura detalleFactura = new DetalleFactura();
    private static Servicio servicio = new Servicio();
    private static Producto producto=new Producto();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_facturacion);
        listCarrito = new ArrayList<>();
        recyclerView1 = (RecyclerView) findViewById(R.id.recyclerViewFactura);
        recyclerView1.setLayoutManager(new LinearLayoutManager(facturacion.this));
        consultarComprasCarrito();
        FacturacionAdapter adapter = new FacturacionAdapter(listCarrito, facturacion.this);
        recyclerView1.setAdapter(adapter);
        txtTotalPagar = findViewById(R.id.txtTotalPagar);
        txtUsuarioFacturacion = findViewById(R.id.txtUsuarioFacturacion);
        txtCedulaFacturacion = findViewById(R.id.txtCedulaFacturacion);
        txtDireccionFacturacion = findViewById(R.id.txtDireccionFacturacion);
        txtCorreoFacturacion = findViewById(R.id.txtCorreoFacturacion);
        txtTelefonoFacturacion = findViewById(R.id.txtTelefonoFacturacion);
        txtFechaFacturacion = findViewById(R.id.txtFechaFacturacion);
        btnComprarFacturacion = findViewById(R.id.btnComprarFacturacion);
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

    }

    public void consultausuario() {
        DbHelper dbHelper = new DbHelper(facturacion.this, "basetemp", null, 2);
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
        listCarrito = carrito.getcomprados(facturacion.this);

    }

    public void crearfactura() {

        //crea factura
        DbHelper dbhelper = new DbHelper(facturacion.this, "basetemp", null, 2);
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
                }


            @Override
            public void onFailure(Call<Factura> call, Throwable t) {
                Toast.makeText(facturacion.this, "Error Factura", Toast.LENGTH_SHORT).show();
            }
        });
        //crea factura
        //crea detalle factura
        //dato quemado servicio
        servicio.setId(3L);

        for (Carrito car : listCarrito) {
            double total = 0;
            if (car.getCantidad() > 1) {
                total = car.getPrecio_producto() * car.getCantidad();
            }

            producto.setId((long) car.getIdproducto());
            detalleFactura.setIddetalle(0);
            detalleFactura.setCantidad(car.getCantidad());
            detalleFactura.setTipo(car.getTipo());
            detalleFactura.setTipo(car.getTipo());
            detalleFactura.setPrecio(total);
            detalleFactura.setIdfactura(factura);
            detalleFactura.setIdservicio(servicio);
            detalleFactura.setIdproducto(producto);
            Toast.makeText(this, ""+detalleFactura.getTipo(), Toast.LENGTH_SHORT).show();
            Call<DetalleFactura> res = constantes.getApiService().getdetallefactura(detalleFactura);
            res.enqueue(new Callback<DetalleFactura>() {
                @Override
                public void onResponse(Call<DetalleFactura> call, Response<DetalleFactura> response) {
                    Carrito carrito = new Carrito();
                    carrito.Limpiarcarrito(facturacion.this);
                    Toast.makeText(facturacion.this, "Compra exitosa", Toast.LENGTH_SHORT).show();

                    ///////////////////////////aqui para regresar al fragament de producto corregir
                    if (response.isSuccessful()) {
                        FragmentManager fm= getSupportFragmentManager();
                        fm.beginTransaction().replace(R.id.container, new Fragment_UpdatePerson()).commit();
                    }
                }

                @Override
                public void onFailure(Call<DetalleFactura> call, Throwable t) {
                    Toast.makeText(facturacion.this, "Error detalle", Toast.LENGTH_SHORT).show();

                }
            });
        }



    }
}