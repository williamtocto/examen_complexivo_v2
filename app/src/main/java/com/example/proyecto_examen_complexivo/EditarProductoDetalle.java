package com.example.proyecto_examen_complexivo;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyecto_examen_complexivo.Fragments.detalle_compras;
import com.example.proyecto_examen_complexivo.adapter.DetallecomprasAdapter;
import com.example.proyecto_examen_complexivo.modelo.Carrito;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EditarProductoDetalle extends AppCompatActivity {

    TextView txtNombre, txtPrecio, txtDescripcion;
    EditText txtCantidad;
    ImageView img;
    Button btnActualizar, btnEliminar;
    private Carrito producto;
    RecyclerView recyclerViewdetalle;
    DetallecomprasAdapter adapter;
    Carrito carrito = new Carrito();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto_detalle);
        initViews();
        initValues();

        ActualizarDatos();
        EliminarDatoCarrito();
    }


    public void ActualizarDatos(){
        btnActualizar = findViewById(R.id.btnActualizarCarrito);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtCantidad.setError(null);
                String posibleNumero = txtCantidad.getText().toString();
                // Vemos si está vacío...
                // Notación yoda: https://parzibyte.me/blog/2018/09/24/notacion-yoda-en-programacion/
                if ("".equals(posibleNumero)) {
                    // Primer error
                    txtCantidad.setError("Introduce un número");
                    // Le damos focus
                    txtCantidad.requestFocus();
                    // Y terminamos la ejecución
                    return;
                }
                // En caso de que hayan puesto algo, convertimos a entero
                int numero = Integer.parseInt(posibleNumero);
                // Comparar si está en el rango
                if (numero >= 5 && numero <= 13) {
                    // La validación termina y hacemos lo que vayamos a hacer
                    Toast.makeText(EditarProductoDetalle.this, "Todo correcto", Toast.LENGTH_SHORT).show();
                } else {
                    // Si no, entonces indicamos el error y damos focus
                    txtCantidad.setError("Número fuera de rango");
                    txtCantidad.requestFocus();
                }
                Carrito carritoActual = new Carrito();
                producto = (Carrito) getIntent().getExtras().getSerializable("productodetalle");
                Toast.makeText(EditarProductoDetalle.this, ""+producto.getId_producto(), Toast.LENGTH_SHORT).show();
                carritoActual.setCantidad(Integer.parseInt(txtCantidad.getText().toString()));

                carritoActual.ActualizarCarrito(EditarProductoDetalle.this, producto.getId_producto());
                finish();
                ArrayList<Carrito> listCarrito = carrito.getcomprados(EditarProductoDetalle.this);
                DetallecomprasAdapter.RecyclerItemClick itemClick=DetallecomprasAdapter.itemClick;
                recyclerViewdetalle=detalle_compras.recyclerView;
                adapter=new DetallecomprasAdapter(listCarrito,EditarProductoDetalle.this,itemClick);
                recyclerViewdetalle.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

        });
    }

    public void EliminarDatoCarrito(){
        btnEliminar = findViewById(R.id.btnEliminarCarrito);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Carrito carritoActual = new Carrito();
                producto = (Carrito) getIntent().getExtras().getSerializable("productodetalle");
                Toast.makeText(EditarProductoDetalle.this, ""+producto.getId_producto(), Toast.LENGTH_SHORT).show();
                carritoActual.EliminarCarrito(EditarProductoDetalle.this, producto.getId_producto());
                finish();
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(EditarProductoDetalle.this, LinearLayoutManager.VERTICAL, false);
                ArrayList<Carrito> listCarrito = carrito.getcomprados(EditarProductoDetalle.this);
                DetallecomprasAdapter.RecyclerItemClick itemClick=DetallecomprasAdapter.itemClick;
                recyclerViewdetalle=detalle_compras.recyclerView;
                adapter=new DetallecomprasAdapter(listCarrito,EditarProductoDetalle.this,itemClick);
                recyclerViewdetalle.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initValues(){
        producto = (Carrito) getIntent().getExtras().getSerializable("productodetalle");
        txtNombre.setText(producto.getNombre_producto());
        txtPrecio.setText(String.valueOf(producto.getPrecio_producto()));
        txtDescripcion.setText(producto.getDescricpion_producto());
        txtCantidad.setText(String.valueOf(producto.getCantidad()));
        Picasso.get().load(producto.getImg()).resize(300,450).centerCrop().into(img);
    }

    private void initViews(){
        txtNombre = findViewById(R.id.txtNombreEditarCarrito);
        txtPrecio = findViewById(R.id.txtPrecioEditarCarrito);
        txtDescripcion = findViewById(R.id.txtdescripcionEditarCarrito);
        txtCantidad = findViewById(R.id.editTextCantidadEditarCarrito);
        img = findViewById(R.id.imgeditarCarrito);
    }




}