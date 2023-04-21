package com.api.productos_crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText codigo;
    private EditText nombre;
    private EditText precio;
    private ProductoDao dao;
    private Producto pro = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        codigo = findViewById(R.id.Codigo);
        nombre = findViewById(R.id.Nombre);
        precio = findViewById(R.id.Precio);
        dao = new ProductoDao(this);

        Intent it = getIntent();
        if (it.hasExtra("producto")){
            pro = (Producto) it.getSerializableExtra("producto");
            codigo.setText(pro.getCodigo());
            nombre.setText(pro.getNombre());
            precio.setText(pro.getPrecio().toString());
        }

        Button btnlistar = findViewById(R.id.btnlistar);

        btnlistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListaActivity.class);
                startActivity(intent);
            }
        });

    }

    public void guardar(View view){
        if (pro == null) {
            pro = new Producto();
            pro.setCodigo(codigo.getText().toString());
            pro.setNombre(nombre.getText().toString());
            pro.setPrecio(Double.parseDouble(precio.getText().toString()));
            long pid = dao.insertar(pro);
            limpiar();
            Toast.makeText(this, "Producto insertado", Toast.LENGTH_SHORT).show();

        }else{
            pro.setCodigo(codigo.getText().toString());
            pro.setNombre(nombre.getText().toString());
            pro.setPrecio(Double.parseDouble(precio.getText().toString()));
            dao.actualizar(pro);
            limpiar();
            Toast.makeText(this, "Producto actualizado", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiar(){
        codigo.setText("");
        nombre.setText("");
        precio.setText("");
    }
}