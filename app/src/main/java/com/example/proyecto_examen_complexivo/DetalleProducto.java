package com.example.proyecto_examen_complexivo;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.proyecto_examen_complexivo.Fragments.ProductosFragment;
import com.example.proyecto_examen_complexivo.base_temp.DbHelper;
import com.example.proyecto_examen_complexivo.modelo.Carrito;
import com.example.proyecto_examen_complexivo.modelo.Producto;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class DetalleProducto extends AppCompatActivity {
    private Producto productoDetalle;
    Toolbar toolbar;
    ImageView img, btn_mas, btn_menos;
    TextView nombreProdcuto, precioProdcuto, descripcionProducto, stockProdcuto;
    EditText cantidad;
    Button btnAñadirCarrito;
    int stock;
    String nombre, precio, descripcion;
    String image;
    String id_producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);
        setTitle("Detalle del Producto");
        btn_mas = findViewById(R.id.btnMasDetPro);
        btn_menos = findViewById(R.id.btnMenosDetPro);
        cantidad = findViewById(R.id.txt_cantidadProductoDetPro);

        btnAñadirCarrito = findViewById(R.id.btnAñadirCarrito);

        //Referencias UI
        toolbar = findViewById(R.id.toolBar);

        //CONFIGURACION DEL TOOL BAR Y FLECHA DE RETORNO AL HOME //////////////////////
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.icon_btn_back_view));
        Intent home = new Intent(this, MainActivity.class);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //regresar...
                startActivity(home);
                finish();
            }
        });
        //////////////////////////////////////////////////////////////////////////////////////////////
        productoDetalle = (Producto) getIntent().getExtras().getSerializable("itemDetail");
        //RECIVO DATOS MANDADOS AL SELECCIONAR UN PRODUCTO EN LA LISTA DEL HOME //////////////////////
        nombre = productoDetalle.getNombre();
        image = productoDetalle.getFoto();
        precio = String.valueOf(productoDetalle.getPrecio());
        descripcion = productoDetalle.getDescripcion();
        stock = productoDetalle.getStock();
        id_producto = CodigoArchivo();

        nombreProdcuto = findViewById(R.id.productName);
        descripcionProducto = findViewById(R.id.prodDesc);
        precioProdcuto = findViewById(R.id.prodPrice);
        img = findViewById(R.id.big_image);
        stockProdcuto = findViewById(R.id.qty);

        nombreProdcuto.setText(nombre);
        precioProdcuto.setText(precio);
        descripcionProducto.setText(descripcion);
        stockProdcuto.setText(String.valueOf(stock));
        Picasso.get()
                .load(image)
                .error(R.mipmap.ic_launcher)
                .into(img);
        ////////////////////////////////////////////////////////////////////////////////////////////////

        //ACCIONES DE LA BARRA DE CANTIDAD DE PRODUCTO //////////////////////
        btn_mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cantidad.getText().toString().equals(stockProdcuto.getText().toString())) {
                    sumCantidad();
                }
            }
        });

        btn_menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!cantidad.getText().toString().equals("1")) {
                    resCantidad();
                }
            }
        });
        /*VALIDACION DE ENTRADA DE CATIDAD PRODCUTO*/
        cantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /*Validadciones de entrada de cantidad de producto a comprar*/
                try {
                    /*Transformamos las entradas de los edit text en enteros para moyor comodidad*/
                    int stock;
                    int cantidadInput;
                    stock = Integer.parseInt(stockProdcuto.getText().toString());
                    if (cantidad.getText().toString().equalsIgnoreCase("")) { //If para controlar exepcion al dejar el edit text vacio
                        cantidadInput = 0;
                    } else {
                        cantidadInput = Integer.parseInt(cantidad.getText().toString());
                    }
                    /*Validamos que la cantidad ingresada no supere al stock de la tienda*/
                    if (cantidadInput > stock) {
                        new AlertDialog.Builder(DetalleProducto.this)
                                .setIcon(R.drawable.icon_warning)
                                .setTitle("¡Stock superado!")
                                .setMessage("El stock de este producto ha sido superado, no hay unidades disponibles")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                })
                                .show();

                        btnAñadirCarrito.setTextColor(R.color.rojo_eliminar);
                    } else {
                        btnAñadirCarrito.setEnabled(true);
                        btnAñadirCarrito.setTextColor(R.color.white);
                    }

                } catch (NumberFormatException ex) { /*capturamos el error si es que el usuario ingrese cualquier valor que no sea un numero entero*/
                    new AlertDialog.Builder(DetalleProducto.this)
                            .setIcon(R.drawable.icon_warning)
                            .setTitle("¡Valor no soportado!")
                            .setMessage("Por favor ingrese solo numeros enteros")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            })
                            .show();
                    cantidad.setText("1");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //ACCION AL BOTON DE AÑADIR CARRITO //////////////////////

        btnAñadirCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Integer.parseInt(stockProdcuto.getText().toString()) == 0) {
                    new AlertDialog.Builder(DetalleProducto.this)
                            .setIcon(R.drawable.icon_warning)
                            .setTitle("¡Sin productos!")
                            .setMessage("No existen Productos")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            })
                            .show();
                } else {
                    if (cantidad.getText().toString().equalsIgnoreCase("") || cantidad.getText().toString().equalsIgnoreCase("0")) {
                        new AlertDialog.Builder(DetalleProducto.this)
                                .setIcon(R.drawable.icon_warning)
                                .setTitle("¡Cantidad nula!")
                                .setMessage("Antes de añadir al carrito asegurate de ingresar una catidad diferente de 0")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                })
                                .show();
                    } else {
                        addCarrito(true);
                    }
                }

            }
        });
    }

    private void sumCantidad() {
        int num = Integer.parseInt(cantidad.getText().toString());
        int suma = num + 1;
        cantidad.setText(String.valueOf(suma));
    }

    private void resCantidad() {
        int num = Integer.parseInt(cantidad.getText().toString());
        int resta = num - 1;
        cantidad.setText(String.valueOf(resta));
    }

    private void addCarrito(boolean estado) {

        DbHelper db = new DbHelper(DetalleProducto.this);
        String nombreP = nombreProdcuto.getText().toString();
        String descP = descripcionProducto.getText().toString();
        String precioP = precioProdcuto.getText().toString().substring(1, precioProdcuto.getText().toString().length());
        String cantidadCompra = cantidad.getText().toString();
        String imagen = image;
        //   int stock= Integer.parseInt(stockProdcuto.getText().toString());

        Boolean validacion = db.ValidacionCarrito(productoDetalle.getNombre());
        if (validacion == true) {
            Toast.makeText(DetalleProducto.this, "Producto ya ingresado", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Carrito carritoActual = new Carrito();
            carritoActual.setId_producto(CodigoArchivo());
            carritoActual.setNombre_producto(nombreP);
            carritoActual.setPrecio_producto(productoDetalle.getPrecio());
            carritoActual.setTipo("Producto");
            carritoActual.setDescricpion_producto(descP);
            carritoActual.setCantidad(Integer.parseInt(cantidadCompra));
            carritoActual.setImg(imagen);
            carritoActual.setIdproducto(productoDetalle.getIdproducto());
            carritoActual.Guardar(DetalleProducto.this);
            finish();

        }

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