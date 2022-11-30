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
import com.example.proyecto_examen_complexivo.modelo.Servicio;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class ServicioDetalle extends AppCompatActivity {
    private ImageView imgDetail;
    private TextView txtNombre, txtPrecio, txtDescripcion;
    private Servicio detalleServicio;
    private Button btnGuardar;
    private DbHelper conn, db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_detalle);
        setTitle(getClass().getSimpleName());
        conn = new DbHelper( ServicioDetalle.this, "basetemp", null, 2);
        initViews();
        initValues();
        guardar();
    }

    public void guardar(){
        detalleServicio = (Servicio) getIntent().getExtras().getSerializable("itemDetail");
        db = new DbHelper(ServicioDetalle.this, "basetemp", null, 2);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean validacion = db.ValidacionCarrito(detalleServicio.getNombre());
                if (validacion==true){
                    Toast.makeText(ServicioDetalle.this, "Servicio ya ingresado", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Carrito carritoActual = new Carrito();
                    carritoActual.setId_producto(CodigoArchivo());
                    carritoActual.setNombre_producto(detalleServicio.getNombre());
                    carritoActual.setCantidad(1);
                    carritoActual.setImg(detalleServicio.getFoto());
                    carritoActual.setTipo("Servicio");
                    carritoActual.setPrecio_producto(Double.parseDouble(detalleServicio.getPrecio()));
                    carritoActual.setDescricpion_producto(detalleServicio.getDescripcion());
                    carritoActual.setTotal_precio(Double.parseDouble(detalleServicio.getPrecio()));
                    carritoActual.Guardar(ServicioDetalle.this);
                    finish();
                }
            }
        });
    }
    
    private void initViews(){
        imgDetail = findViewById(R.id.imgFotoServicio);
        txtNombre = findViewById(R.id.txtNombre_Servicio);
        txtDescripcion = findViewById(R.id.txtDescripcionServicio3);
        txtPrecio = findViewById(R.id.txtPrecio_Servicio3);
        btnGuardar = findViewById(R.id.btnGuardarServicioCarrito);
    }

    private void initValues(){
        detalleServicio = (Servicio) getIntent().getExtras().getSerializable("itemDetail");
        Picasso.get().load(detalleServicio.getFoto()).resize(300,450).centerCrop().into(imgDetail);
        txtNombre.setText(detalleServicio.getNombre());
        txtDescripcion.setText(detalleServicio.getDescripcion());
        txtPrecio.setText(String.valueOf(detalleServicio.getPrecio()));
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