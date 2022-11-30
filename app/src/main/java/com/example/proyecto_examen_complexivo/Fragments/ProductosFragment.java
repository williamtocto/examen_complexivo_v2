package com.example.proyecto_examen_complexivo.Fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.example.proyecto_examen_complexivo.DetalleProducto;
import com.example.proyecto_examen_complexivo.MainActivity;
import com.example.proyecto_examen_complexivo.ProductoServicioDetalle;
import com.example.proyecto_examen_complexivo.R;
import com.example.proyecto_examen_complexivo.adapter.CategoriaPAdapter;
import com.example.proyecto_examen_complexivo.adapter.ProductoAdapter;
import com.example.proyecto_examen_complexivo.adapter.SubcategoriaPAdapter;
import com.example.proyecto_examen_complexivo.databinding.FragmentProductosBinding;
import com.example.proyecto_examen_complexivo.modelo.CategoriaP;
import com.example.proyecto_examen_complexivo.modelo.Producto;
import com.example.proyecto_examen_complexivo.modelo.SubcategoriaP;
import com.example.proyecto_examen_complexivo.network.Api;
import com.example.proyecto_examen_complexivo.network.Constantes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductosFragment extends Fragment implements ProductoAdapter.RecyclerItemClick, CategoriaPAdapter.RecyclerItemClickc, SubcategoriaPAdapter.RecyclerItemClickc {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentProductosBinding binding;

    //productos
    private List<Producto> listaProductoscompleta =new ArrayList<>();
    private List<Producto> listproducto =new ArrayList<>();
    private ProductoAdapter adapter;
    private TextView txtfiltro;

    //recicleviews
    private RecyclerView recyclerView,recyclersubcategoria, recyclerViewcategoria;

    //subcategoria
    private List<SubcategoriaP> subcategoriaList =new ArrayList<>();
    private SubcategoriaPAdapter adaptersubcategoria;

    //categoria
    private List<CategoriaP> categoriaList=new ArrayList<>();
    private CategoriaPAdapter adaptercategoria;



    //retrofit api
    private Constantes constantes=new Constantes();


    public ProductosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductosFragment newInstance(String param1, String param2) {
        ProductosFragment fragment = new ProductosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentProductosBinding.inflate(inflater, container,false);
        View root=binding.getRoot();

        //categoria
        adaptercategoria=new CategoriaPAdapter(categoriaList,getContext(),this);
        recyclerViewcategoria=binding.recyclercategoria;
        recyclerViewcategoria.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewcategoria.setAdapter(adaptercategoria);
        retrofitCategoria();

        //subcategoria
        adaptersubcategoria=new SubcategoriaPAdapter(subcategoriaList,getContext(), this);
        recyclersubcategoria=binding.recyclersubcategoria;
        recyclersubcategoria.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        recyclersubcategoria.setAdapter(adaptersubcategoria);
        retrofitSubCa();

        //producto
        adapter=new ProductoAdapter(listproducto,getContext(), this);
        recyclerView=binding.recycleproductos;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapter);
        retrofitIni();

        //busqueda producto
        txtfiltro=binding.filtro;
        txtfiltro.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                listproducto.clear();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    listproducto.addAll(listaProductoscompleta.stream()
                            .filter(x->x.getNombre().contains(txtfiltro.getText()))
                            .collect(Collectors.toList()));
                }
                adapter.notifyDataSetChanged();
                return false;
            }
        });


        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void retrofitIni(){
        Call<List<Producto>> res=constantes.getApiService().getProducto();
        res.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                listaProductoscompleta.addAll(response.body());
                listproducto.clear();
                listproducto.addAll(listaProductoscompleta);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void retrofitSubCa(){
        Call<List<SubcategoriaP>> res=constantes.getApiService().getSubcategoria();
        res.enqueue(new Callback<List<SubcategoriaP>>() {
            @Override
            public void onResponse(Call<List<SubcategoriaP>> call, Response<List<SubcategoriaP>> response) {
                subcategoriaList.clear();
                subcategoriaList.addAll(response.body());
                adaptersubcategoria.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<SubcategoriaP>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void retrofitCategoria(){
        Call<List<CategoriaP>> res=constantes.getApiService().getCegoria();
        res.enqueue(new Callback<List<CategoriaP>>() {
            @Override
            public void onResponse(Call<List<CategoriaP>> call, Response<List<CategoriaP>> response) {
                categoriaList.clear();
                categoriaList.add(new CategoriaP(-1L,"Todo","todo"));
                categoriaList.addAll(response.body());
                adaptercategoria.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CategoriaP>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void itemCLick(Producto producto) {
        Intent intent = new Intent(ProductosFragment.this.getContext(), DetalleProducto.class);
        intent.putExtra("itemDetail", producto);
        startActivity(intent);
    }


    @Override
    public void itemCLick(CategoriaP categoriaP) {
        if(categoriaP.getId()==-1){
            listaProductoscompleta.clear();
            retrofitSubCa();
            retrofitIni();
        }else{
            Call<List<Producto>> res=constantes.getApiService().getProductoCat(categoriaP.getId());
            res.enqueue(new Callback<List<Producto>>() {
                @Override
                public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                    listaProductoscompleta.clear();
                    listaProductoscompleta.addAll(response.body());
                    listproducto.clear();
                    listproducto.addAll(listaProductoscompleta);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<Producto>> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public void itemCLick(SubcategoriaP subcategoriaP) {
        Call<List<Producto>> res=constantes.getApiService().getProductoSub(subcategoriaP.getId());
        res.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                listaProductoscompleta.clear();
                listaProductoscompleta.addAll(response.body());
                listproducto.clear();
                listproducto.addAll(listaProductoscompleta);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();

            }
        });
    }
}