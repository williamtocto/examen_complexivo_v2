package com.example.proyecto_examen_complexivo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_examen_complexivo.R;
import com.example.proyecto_examen_complexivo.modelo.CategoriaS;

import java.util.List;

public class CategoriaSAdapter extends RecyclerView.Adapter<CategoriaSAdapter.ViewHolder> {

    private List<CategoriaS> categoriaSList;
    private Context context;

    private RecyclerItemClicks itemClicks;

    public CategoriaSAdapter(List<CategoriaS> categoriaSList, Context context, RecyclerItemClicks itemClicks) {
        this.categoriaSList = categoriaSList;
        this.context = context;
        this.itemClicks = itemClicks;
    }

    @NonNull
    @Override
    public CategoriaSAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_categoria,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaSAdapter.ViewHolder holder, int position) {

        CategoriaS ca=categoriaSList.get(position);
        holder.nombrecategoria.setText(ca.getNombre());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClicks.itemCLick(ca);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriaSList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        private TextView nombrecategoria;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombrecategoria=itemView.findViewById(R.id.categorias);
        }
    }

    public interface  RecyclerItemClicks{
        void itemCLick(CategoriaS categoriaS);
    }
}
