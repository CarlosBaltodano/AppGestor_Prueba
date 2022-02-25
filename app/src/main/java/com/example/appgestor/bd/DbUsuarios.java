package com.example.appgestor.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.appgestor.entities.User;

public class DbUsuarios extends DbHelper{

    Context context;

    public DbUsuarios(@Nullable Context context) {
        super(context);
        this.context=context;
    }

    public long insertarUsuario(String nombre,String correo,String user,String pass,byte[] image){
        long id=0;
        try{
            DbHelper dbHelper=new DbHelper(context);
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            ContentValues values=new ContentValues();
            values.put("nombre",nombre);
            values.put("correo",correo);
            values.put("usuario",user);
            values.put("password",pass);
            values.put("image",image);
            id=db.insert(TABLE_USERS,null,values);
        }catch (Exception ex){
            ex.toString();
        }
        return  id;
    }

    public boolean updateUser(int id,String nombre,String correo){
        try{
            ContentValues cv = new ContentValues();
            cv.put("nombre",nombre);
            cv.put("correo",correo);
            SQLiteDatabase db = this.getWritableDatabase();
            db.update(TABLE_USERS,cv, "id=?", new String[]{ String.valueOf(id) } );
            db.close();
            return true;
        }
        catch (Exception e){
            e.getMessage();
            return false;
        }
    }

    public Cursor ConsultarUsuPass(String usu,String pass) throws SQLException{
        Cursor mcursor=null;
        mcursor=this.getReadableDatabase().query("usuarios",
                new String[]{"id","nombre","correo","usuario","password"}
                ,"usuario like '"+usu+"' and password like'"+pass+"'"
                ,null,null,null,null);
        return mcursor;
    }

    public User getUser(int id){
        try
        {
            User user = null;
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT image FROM usuarios WHERE id="+id, null);
            if(cursor.moveToFirst()){
                byte[] image = cursor.getBlob(0);
                user=new User(image);
            }
            return user;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}
