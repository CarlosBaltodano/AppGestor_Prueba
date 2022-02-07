package com.example.appgestor.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appgestor.R;
import com.example.appgestor.bd.DbHelper;
import com.example.appgestor.bd.DbUsuarios;

public class LoginActivity extends AppCompatActivity {
    EditText mEditTextUser,mEditTextPass;
    Button mButtonLogin;
    RelativeLayout layout1,layout2,layoutprincipal;
    private Cursor fila;
    private ProgressDialog mPogressDialog;
    byte[] imagen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        layout1=findViewById(R.id.layout1);
        layout2=findViewById(R.id.layout2);
        layoutprincipal=findViewById(R.id.layoutprincipal);
        mEditTextUser=findViewById(R.id.editTextUser);
        mEditTextPass=findViewById(R.id.editTextPass);
        mButtonLogin=findViewById(R.id.buttonLogin);

        mPogressDialog=new ProgressDialog(LoginActivity.this);
        mPogressDialog.setCanceledOnTouchOutside(false);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=mEditTextUser.getText().toString().trim();
                String pass=mEditTextPass.getText().toString().trim();

                DbUsuarios dbUsuarios=new DbUsuarios(LoginActivity.this);
                Cursor cursor=dbUsuarios.ConsultarUsuPass(user,pass);
                if(cursor.getCount()>0){
                    ingresar(user,pass);
                }else{
                    Toast.makeText(LoginActivity.this,"Usuario y/o Incorrectos",Toast.LENGTH_SHORT).show();
               }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        DbHelper dbHelper=new DbHelper(LoginActivity.this);
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        if(db!=null){
           // Toast.makeText(LoginActivity.this,"creado correctamente",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(LoginActivity.this,"error",Toast.LENGTH_SHORT).show();
        }

        SharedPreferences preferences=getSharedPreferences("Datos", Context.MODE_PRIVATE);
        Boolean sesion=preferences.getBoolean("sesion",false);
        String nom=preferences.getString("nombre","NO HAY");
        String correo=preferences.getString("correo","NO HAY");
        if(sesion){
            layoutprincipal.setVisibility(View.GONE);
            Intent intent=new Intent(LoginActivity.this,MenuActivity.class);
            intent.putExtra("nombre",nom);
            intent.putExtra("correo",correo);
            startActivity(intent);
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    layout1.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.VISIBLE);
                }
            },2000);}
        }




    public void ingresar(String user,String pass){
        DbHelper admin=new DbHelper(LoginActivity.this);
        SQLiteDatabase db=admin.getWritableDatabase();

        fila=db.rawQuery("select id,nombre,correo,usuario,password,image from usuarios where usuario='"+user+"' and password='"+pass+"'",null);
        try{
            if(fila.moveToFirst()) {
                int id=fila.getInt(0);
                String nombre = fila.getString(1);
                String correo = fila.getString(2);
                String usuario = fila.getString(3);
                String password = fila.getString(4);
                imagen=fila.getBlob(5);
                if(usuario.equals(user) && password.equals(pass)){
                    guardarDatos(nombre,correo,id);
                    Intent intent=new Intent(LoginActivity.this,MenuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this,"Bienvenido "+nombre,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this,"Usuario y/o Incorrectos",Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){
            Toast.makeText(LoginActivity.this,"Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void guardarDatos(String nombre,String correo,int id){
        SharedPreferences preferences=getSharedPreferences("Datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("nombre",nombre);
        editor.putString("correo",correo);
        editor.putBoolean("sesion",true);
        editor.putInt("id",id);
        editor.commit();
    }

}