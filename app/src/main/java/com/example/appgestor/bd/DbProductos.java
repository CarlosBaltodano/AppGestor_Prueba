package com.example.appgestor.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.appgestor.entities.Producto;

import java.util.ArrayList;

public class DbProductos extends DbHelper{

    Context context;
    public DbProductos(@Nullable Context context) {
        super(context);
        this.context=context;
    }

    public long insertarProducto(int id_pdv,String nombre,double costo,double mayor,int stock){
        long id=0;
        try{
            DbHelper dbHelper=new DbHelper(context);
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            ContentValues values=new ContentValues();
            values.put("id_pdv",id_pdv);
            values.put("nombre",nombre);
            values.put("costo",costo);
            values.put("mayor",mayor);
            values.put("stock",stock);
            id=db.insert(TABLE_PRODUCTOS,null,values);
        }catch (Exception ex){
            ex.toString();
        }
        return  id;
    }

    public ArrayList<Producto> mostrarProducto(int id_producto){
        DbHelper dbHelper=new DbHelper(context);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ArrayList<Producto>listaProducto=new ArrayList<>();
        Producto prod=null;
        Cursor cursorProducto=null;
        cursorProducto=db.rawQuery("SELECT * FROM "+TABLE_PRODUCTOS+" WHERE id_pdv="+id_producto,null);

        if(cursorProducto.moveToFirst()){
            do{
                prod=new Producto();
                prod.setId(cursorProducto.getInt(0));
                prod.setId_pdv(cursorProducto.getInt(1));
                prod.setProducto(cursorProducto.getString(2));
                prod.setCosto(cursorProducto.getDouble(3));
                prod.setP_mayor(cursorProducto.getDouble(4));
                prod.setStock(cursorProducto.getInt(5));
                listaProducto.add(prod);
            }while (cursorProducto.moveToNext());
        }
        cursorProducto.close();
        return listaProducto;
    }

    public boolean updateCosto(int id,double costo){
        try{
            ContentValues cv = new ContentValues();
            cv.put("costo",costo);
            SQLiteDatabase db = this.getWritableDatabase();
            db.update("productos", cv, "id=?", new String[]{ String.valueOf(id) } );
            db.close();
            return true;
        }
        catch (Exception e){
            e.getMessage();
            return false;
        }
    }
}
