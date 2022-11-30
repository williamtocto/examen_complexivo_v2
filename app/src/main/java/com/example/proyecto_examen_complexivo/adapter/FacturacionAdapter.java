package com.example.proyecto_examen_complexivo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_examen_complexivo.R;
import com.example.proyecto_examen_complexivo.modelo.Carrito;
import com.example.proyecto_examen_complexivo.modelo.Producto;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FacturacionAdapter extends RecyclerView.Adapter<FacturacionAdapter.ViewHolder> {

    private ArrayList<Carrito> ListaCarrito;
    private Context context;
    private String total;

    public FacturacionAdapter() {
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public FacturacionAdapter(ArrayList<Carrito> listaCarrito, Context context) {
        ListaCarrito = listaCarrito;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_productos_facturacion, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Carrito pro = ListaCarrito.get(position);
        holder.txtCantidad.setText(String.valueOf(pro.getCantidad()));
        holder.txtDescripcion.setText(pro.getDescricpion_producto());
        holder.tipofactura.setText(pro.getTipo());
        holder.txtValorUnitario.setText(String.valueOf(pro.getPrecio_producto()));
        double resultado =0;
        for (int i=0; i<pro.getCantidad(); i++){
            resultado += pro.getPrecio_producto();
        }
        holder.txtValorTotal.setText(String.valueOf(resultado));
        Picasso.get().load(pro.getImg()).resize(300,450).centerCrop()
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return ListaCarrito.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{

        TextView txtCantidad, txtDescripcion, txtValorUnitario, txtValorTotal,tipofactura;
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCantidad = itemView.findViewById(R.id.txtcantidadFacturacion);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcionFactura);
            txtValorUnitario = itemView.findViewById(R.id.txt_valor_unitario);
            txtValorTotal = itemView.findViewById(R.id.txt_valor_total);
            tipofactura = itemView.findViewById(R.id.tipofactura);
            img = itemView.findViewById(R.id.imgProductoFacturacion);
        }
    }


}
