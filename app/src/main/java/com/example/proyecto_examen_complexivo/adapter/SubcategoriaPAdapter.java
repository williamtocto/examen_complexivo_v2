package com.example.proyecto_examen_complexivo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_examen_complexivo.R;
import com.example.proyecto_examen_complexivo.modelo.SubcategoriaP;
import com.example.proyecto_examen_complexivo.modelo.SubcategoriaS;

import java.util.List;

public class SubcategoriaPAdapter extends RecyclerView.Adapter<SubcategoriaPAdapter.ViewHolder> {

    private List<SubcategoriaP> listsubcategoria;
    private Context context;

    private RecyclerItemClickc itemClicks;

    public SubcategoriaPAdapter(List<SubcategoriaP> listsubcategoria, Context context,RecyclerItemClickc itemClicks ) {
        this.listsubcategoria = listsubcategoria;
        this.context = context;
        this.itemClicks = itemClicks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_subcategoria,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubcategoriaP sub=listsubcategoria.get(position);
        holder.nombresubcategoria.setText(sub.getNombre());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClicks.itemCLick(sub);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listsubcategoria.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombresubcategoria;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombresubcategoria=itemView.findViewById(R.id.subcategorias);
        }
    }

    public interface RecyclerItemClickc{
        void itemCLick(SubcategoriaP subcategoriaP);
    }
}
