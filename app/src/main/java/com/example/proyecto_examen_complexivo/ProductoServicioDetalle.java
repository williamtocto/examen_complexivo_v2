package com.example.proyecto_examen_complexivo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.proyecto_examen_complexivo.base_temp.DbHelper;
import com.example.proyecto_examen_complexivo.modelo.Carrito;
import com.example.proyecto_examen_complexivo.modelo.Producto;
import com.squareup.picasso.Picasso;


import java.util.Random;

public class ProductoServicioDetalle extends AppCompatActivity {

    private ImageView imgDetail;
    private TextView txtNombre;
    private TextView txtDescripcion, txtPrecio, txtCantidad;
    private Producto productoDetalle;
    private Button btnGuardar;
   private  DbHelper conn, db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_servicio_detalle);
        setTitle(getClass().getSimpleName());
        conn = new DbHelper( ProductoServicioDetalle.this, "basetemp", null, 2);
        initViews();
        initValues();
        GuardarCarrito();
    }

    public void GuardarCarrito(){
        productoDetalle = (Producto) getIntent().getExtras().getSerializable("itemDetail");
        btnGuardar = findViewById(R.id.btnGuardarCarrito);
        txtNombre = findViewById(R.id.txtNombre_Producto3);
        txtDescripcion = findViewById(R.id.txtDescripcionProducto3);
        txtPrecio = findViewById(R.id.txtPrecio_Producto3);
        txtCantidad = findViewById(R.id.editTextCantidadCarrito);
        db = new DbHelper(ProductoServicioDetalle.this, "basetemp", null, 2);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(ProductoServicioDetalle.this, "Todo correcto", Toast.LENGTH_SHORT).show();
                } else {
                    // Si no, entonces indicamos el error y damos focus
                    txtCantidad.setError("Número fuera de rango");
                    txtCantidad.requestFocus();
                }

                Boolean validacion = db.ValidacionCarrito(productoDetalle.getNombre());
                if (validacion==true){
                    Toast.makeText(ProductoServicioDetalle.this, "Producto ya ingresado", Toast.LENGTH_SHORT).show();
                    finish();
                }else {

                    Carrito carritoActual = new Carrito();
                    carritoActual.setId_producto(CodigoArchivo());
                    carritoActual.setNombre_producto(productoDetalle.getNombre());
                    carritoActual.setPrecio_producto(productoDetalle.getPrecio());
                    carritoActual.setTipo("producto");
                    carritoActual.setDescricpion_producto(productoDetalle.getDescripcion());
                    carritoActual.setCantidad(Integer.parseInt(txtCantidad.getText().toString()));
                    carritoActual.setImg(productoDetalle.getFoto());
                    carritoActual.setIdproducto(productoDetalle.getIdproducto());
                    carritoActual.Guardar(ProductoServicioDetalle.this);

                    finish();
                    Toast.makeText(ProductoServicioDetalle.this, "Se ha guardado en el carrito", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void initViews(){
        imgDetail = findViewById(R.id.imgfotoproductocompra3);
        txtNombre = findViewById(R.id.txtNombre_Producto3);
        txtDescripcion = findViewById(R.id.txtDescripcionProducto3);
        txtPrecio = findViewById(R.id.txtPrecio_Producto3);
    }

    private void initValues(){

        productoDetalle = (Producto) getIntent().getExtras().getSerializable("itemDetail");
        Picasso.get().load(productoDetalle.getFoto()).resize(300,450).centerCrop().into(imgDetail);
        txtNombre.setText(productoDetalle.getNombre());
        txtPrecio.setText(String.valueOf(productoDetalle.getPrecio()));
        txtDescripcion.setText(productoDetalle.getDescripcion());
    }


    public String CodigoArchivo() {
        //  txtCodigoArchivo = findViewById(R.id.txtCodigoArchivoPdf);
        Random random = new Random();
        String abecedario = "ABCDEFGHIJKMOPRSTUVWXYZ";
        String cadena = "";
        String archivo = "";
        int m = 0, pos = 0, num;
        while (m < 1) {
            pos = (int) (random.nextDouble() * abecedario.length() - 1 + 0);
            num = (int) (random.nextDouble() * 9999 + 1000);
            cadena = cadena + abecedario.charAt(pos) + num;
            pos = (int) (random.nextDouble() * abecedario.length() - 1 + 0);
            cadena = cadena + abecedario.charAt(pos);
            archivo = ("ARCHIVO-" + cadena);
            m++;
        }
        return archivo;
    }


}