package com.api.productos_crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProductoDao {

    private Conexion con;
    private SQLiteDatabase gestion;

    public ProductoDao(Context context){
        con = new Conexion(context);
        gestion = con.getWritableDatabase();
    }

    public long insertar(Producto pro){
        long id =0;
        try {

            ContentValues values = new ContentValues();
            values.put("codigo",pro.getCodigo());
            values.put("nombre",pro.getNombre());
            values.put("precio",pro.getPrecio());


            id = gestion.insert("producto", null, values);
        }catch(Exception e){
            e.toString();
        }
        return id;
    }

    public List<Producto> obtenerTodos(){
        List<Producto> pro = new ArrayList<>();
        Cursor cu = gestion.query("producto", new String[]{"id","codigo","nombre","precio"},
                null,null,null,null,null);
        while(cu.moveToNext()){
            Producto p = new Producto();
            p.setId(cu.getInt(0));
            p.setCodigo(cu.getString(1));
            p.setNombre(cu.getString(2));
            p.setPrecio(cu.getDouble(3));
            pro.add(p);
        }
        return pro;
    }

    public void eliminar(Producto p){
        gestion.delete("producto","id = ?",new String[]{p.getId().toString()});
    }

    public void actualizar(Producto pro){
        ContentValues values = new ContentValues();
        values.put("codigo",pro.getCodigo());
        values.put("nombre",pro.getNombre());
        values.put("precio",pro.getPrecio());
        gestion.update("producto",values,"id = ?",new String[]{pro.getId().toString()});
        obtenerTodos();
    }
}
