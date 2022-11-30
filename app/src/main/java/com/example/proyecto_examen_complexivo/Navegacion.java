package com.example.proyecto_examen_complexivo;

import android.content.Intent;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.proyecto_examen_complexivo.Fragments.Fragment_UpdatePerson;
import com.example.proyecto_examen_complexivo.Fragments.ProductosFragment;
import com.example.proyecto_examen_complexivo.Fragments.ServiciosFragment;
import com.example.proyecto_examen_complexivo.Fragments.detalle_compras;
import com.example.proyecto_examen_complexivo.datos_sqlite.CargarUsuario;
import com.google.android.material.navigation.NavigationView;

import java.sql.SQLOutput;

import static android.app.PendingIntent.getActivity;


public class Navegacion extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

        DrawerLayout drawerLayout;
        NavigationView navigationView;
        Toolbar toolbar;
        ActionBarDrawerToggle toggle;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            //Referencias UI
            drawerLayout= findViewById(R.id.contenidoPrincipal);
            navigationView= findViewById(R.id.nav_view_bar);
            toolbar= findViewById(R.id.toolBar);

            //Configurar Fragment por defecto (El que aparece al principio)
            getSupportFragmentManager().beginTransaction().add(R.id.contentFrame, new ProductosFragment()).commit();
            setTitle("Productos");

            //Configuracion de ToolBars
            setSupportActionBar(toolbar);

            toggle= setDrawerToggle();
            drawerLayout.addDrawerListener(toggle);

            //Dar acciones a los items del menu
            navigationView.setNavigationItemSelectedListener(this);

            // ENVIAR DATOS DE USUARIO AL HEADER NAV
            NavigationView navigationView = findViewById(R.id.nav_view_bar);
            View header = navigationView.getHeaderView(0);
            TextView textUsername = header.findViewById(R.id.nombre_usuario);

            //Consulta base de datos sqlite
           CargarUsuario usu = new CargarUsuario(Navegacion.this);
            if (usu.listarUsuarioP()!=null){
                textUsername.setText(usu.listarUsuarioP().get(0).getIdpersona().getNombre()+" " +usu.listarUsuarioP().get(0).getIdpersona().getApellido());
            }

        }

        private ActionBarDrawerToggle setDrawerToggle() {
            return new ActionBarDrawerToggle(this,
                    drawerLayout,
                    toolbar,
                    R.string.drawer_open,
                    R.string.drawer_close
            );
        }

        @Override
        protected void onPostCreate(@Nullable Bundle savedInstanceState) {
            super.onPostCreate(savedInstanceState);
            toggle.syncState();
        }

        @Override
        public void onConfigurationChanged(@NonNull Configuration newConfig) {
            super.onConfigurationChanged(newConfig);
            toggle.onConfigurationChanged(newConfig);
        }

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            listerSelectedItem(item);
            return true;
        }

        private void listerSelectedItem(MenuItem item) {

            FragmentManager fm= getSupportFragmentManager();
            FragmentTransaction ft= fm.beginTransaction();

            switch (item.getItemId()){

                case R.id.nav_home:
                    ft.replace(R.id.contentFrame, new ProductosFragment()).commit();
                    getSupportActionBar().setTitle("Productos");
                    break;
                case R.id.nav_servicios:
                    ft.replace(R.id.contentFrame, new ServiciosFragment()).commit();
                    getSupportActionBar().setTitle("Servicios");
                    break;
                case R.id.nav_carrito:
                    /*Intent carritoActi = new Intent(this, CarritoCompras.class);
                    startActivity(carritoActi);
                    finish();*/
                   ft.replace(R.id.contentFrame, new detalle_compras()).commit();
                    getSupportActionBar().setTitle("Carrito");
                    break;
                case R.id.nav_perfil:
                    ft.replace(R.id.contentFrame, new Fragment_UpdatePerson()).commit();
                    getSupportActionBar().setTitle("Mi Perfil");
                    break;
            }
            drawerLayout.closeDrawers();
        }



        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if (toggle.onOptionsItemSelected(item)) {
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            return super.onCreateOptionsMenu(menu);
        }

    }
