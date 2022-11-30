package com.example.proyecto_examen_complexivo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_examen_complexivo.R;
import com.example.proyecto_examen_complexivo.modelo.CategoriaP;
import com.example.proyecto_examen_complexivo.modelo.Producto;

import java.util.List;

public class CategoriaPAdapter extends RecyclerView.Adapter<CategoriaPAdapter.ViewHolder> {
    private List<CategoriaP> categoriaList;
    private Context context;

    private RecyclerItemClickc itemClickc;


    public CategoriaPAdapter(List<CategoriaP> categoriaList, Context context, RecyclerItemClickc itemClickc) {
        this.categoriaList = categoriaList;
        this.context = context;
        this.itemClickc = itemClickc;
    }

    @NonNull
    @Override
    public CategoriaPAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_categoria,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaPAdapter.ViewHolder holder, int position) {
        CategoriaP ca=categoriaList.get(position);
        holder.nombrecategoria.setText(ca.getNombre());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickc.itemCLick(ca);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriaList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nombrecategoria;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombrecategoria=itemView.findViewById(R.id.categorias);
        }

    }

    public interface RecyclerItemClickc{
        void itemCLick(CategoriaP categoriaP);
    }
}
