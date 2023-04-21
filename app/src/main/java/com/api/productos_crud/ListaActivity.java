package com.api.productos_crud;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity {


    private ListView listViewv;
    private ProductoDao dao;
    private List<Producto> productos;
    private final List<Producto> productosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_activity);

        listViewv = findViewById(R.id.ListarProductos);
        dao = new ProductoDao(this);
        productos = dao.obtenerTodos();
        productosFiltrados.addAll(productos);
        ArrayAdapter<Producto> ad = new ArrayAdapter<Producto>(this, android.R.layout.simple_list_item_1,productosFiltrados);
        listViewv.setAdapter(ad);

        registerForContextMenu(listViewv);

        Button btnAgregar = findViewById(R.id.btnAgregar);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_principal,menu);

        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                procurProducto(s);
                return false;
            }
        });
        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu,v,menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto,menu);
    }

    public void procurProducto(String codigo){
        productosFiltrados.clear();
        for (Producto p: productos){
            if (p.getCodigo().toLowerCase().contains(codigo.toLowerCase())){
                productosFiltrados.add(p);
            }
        }
        listViewv.invalidateViews();
    }

    public void gestion(MenuItem item){
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }



    public void eliminar(MenuItem item){
        AdapterView.AdapterContextMenuInfo mi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Producto edelete = productosFiltrados.get(mi.position);

        AlertDialog d = new AlertDialog.Builder(this)
                .setTitle("Atención")
                .setMessage("Seguro desea eliminar este producto")
                .setNegativeButton("No",null)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        productosFiltrados.remove(edelete);
                        productos.remove(edelete);
                        dao.eliminar(edelete);
                        listViewv.invalidateViews();

                    }
                }).create();
        d.show();
    }

    public void actualizar(MenuItem item){
        AdapterView.AdapterContextMenuInfo mi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Producto pactu = productosFiltrados.get(mi.position);
        Intent in = new Intent(this, MainActivity.class);
        in.putExtra("producto",pactu);
        startActivity(in);
    }

    @Override
    public void onResume(){
        super.onResume();
        productos = dao.obtenerTodos();
        productosFiltrados.clear();
        productosFiltrados.addAll(productos);
        listViewv.invalidateViews();
    }
}
