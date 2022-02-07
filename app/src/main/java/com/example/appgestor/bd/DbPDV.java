package com.example.appgestor.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.appgestor.entities.Pdv;

import java.util.ArrayList;

public class DbPDV extends DbHelper{

    Context context;

    public DbPDV(@Nullable Context context) {
        super(context);
        this.context=context;
    }

    public long insertarPDV(String codigo,String nombre,String direccion){
        long id=0;
        try{
            DbHelper dbHelper=new DbHelper(context);
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            ContentValues values=new ContentValues();
            values.put("codigo",codigo);
            values.put("nombre",nombre);
            values.put("direccion",direccion);
            id=db.insert(TABLE_PDV,null,values);
        }catch (Exception ex){
            ex.toString();
        }
        return  id;
    }

    public ArrayList<Pdv>mostrarPDV(){
        DbHelper dbHelper=new DbHelper(context);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ArrayList<Pdv>listaPDV=new ArrayList<>();
        Pdv pdv=null;
        Cursor cursorPDV=null;
        cursorPDV=db.rawQuery("SELECT * FROM "+TABLE_PDV,null);

        if(cursorPDV.moveToFirst()){
            do{
                pdv=new Pdv();
                pdv.setId(cursorPDV.getInt(0));
                pdv.setCodigo(cursorPDV.getString(1));
                pdv.setNombre(cursorPDV.getString(2));
                pdv.setDireccion(cursorPDV.getString(3));
                listaPDV.add(pdv);
            }while (cursorPDV.moveToNext());
        }
        cursorPDV.close();
        return listaPDV;
    }

    public boolean update(int id, byte[] image){
        try{
            ContentValues cv = new ContentValues();
            cv.put("image", image);
            SQLiteDatabase db = this.getWritableDatabase();
            db.update("pdv", cv, "id=?", new String[]{ String.valueOf(id) } );
            db.close();
            return true;
        }
        catch (Exception e){
            e.getMessage();
            return false;
        }
    }

    public ArrayList<Pdv>consultarPDV(){
        DbHelper dbHelper=new DbHelper(context);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ArrayList<Pdv>listaPDV=new ArrayList<>();
        Pdv pdv=null;
        Cursor cursorPDV=null;
        cursorPDV=db.rawQuery("SELECT * FROM "+TABLE_PDV,null);

        while(cursorPDV.moveToNext()){
                pdv=new Pdv();
                pdv.setId(cursorPDV.getInt(0));
                pdv.setCodigo(cursorPDV.getString(1));
                pdv.setNombre(cursorPDV.getString(2));
                pdv.setDireccion(cursorPDV.getString(3));
                listaPDV.add(pdv);
            }
        cursorPDV.close();
        return listaPDV;
    }
}
