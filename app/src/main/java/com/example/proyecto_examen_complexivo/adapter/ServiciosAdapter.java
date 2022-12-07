package com.example.proyecto_examen_complexivo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto_examen_complexivo.R;
import com.example.proyecto_examen_complexivo.modelo.Producto;
import com.example.proyecto_examen_complexivo.modelo.Servicio;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ServiciosAdapter extends RecyclerView.Adapter<ServiciosAdapter.ViewHolder> {


    private List<Servicio> servicios;
    private Context context;
    private RecyclerItemClick itemClick;

    public ServiciosAdapter(List<Servicio> servicios, Context context,  RecyclerItemClick itemClick) {
        this.servicios = servicios;
        this.context = context;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_servicio,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Servicio ser=servicios.get(position);
        holder.txnombre.setText(ser.getNombre());
        holder.servicioprecio.setText("$ "+ser.getPrecio());
        System.out.println(ser.getFoto());
        holder.empresa.setText("Empresa: "+ser.getIdempresa().getEmpnombre());
        if(ser.getFoto()!=null){
            Picasso.get().load(ser.getFoto()).resize(300,450).centerCrop()
                    .into(holder.imageView);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.itemCLick(ser);
            }
        });
    }

    @Override
    public int getItemCount() {
        return servicios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView txnombre,servicioprecio,empresa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             imageView=itemView.findViewById(R.id.iv_portada);
             txnombre=itemView.findViewById(R.id.tv_titulo);
            servicioprecio=itemView.findViewById(R.id.servicioprecio);
            empresa=itemView.findViewById(R.id.servicio_empresa);
        }
    }

    public interface RecyclerItemClick{
        void itemCLick(Servicio servicio);
    }

}
