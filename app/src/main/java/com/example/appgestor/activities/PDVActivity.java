package com.example.appgestor.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appgestor.R;
import com.example.appgestor.bd.DbPDV;
import com.example.appgestor.bd.DbUsuarios;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class PDVActivity extends AppCompatActivity {
    FloatingActionButton mFabBack;
    EditText mEditTextNombrePDV,mEditTextCodigoPDV,mEditTextDireccionPDV;
    Button mBtnAddPDV;
    String nom,correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdvactivity);
        mFabBack=findViewById(R.id.fab_back);
        mEditTextNombrePDV=findViewById(R.id.editTextNombrePDV);
        mEditTextDireccionPDV=findViewById(R.id.editTextDireccionPDV);
        mEditTextCodigoPDV=findViewById(R.id.editTextCodigoPDV);
        mBtnAddPDV=findViewById(R.id.btnAddPDV);

        mBtnAddPDV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbPDV dbPDV=new DbPDV(PDVActivity.this);

                String nom=mEditTextNombrePDV.getText().toString();
                String cod=mEditTextCodigoPDV.getText().toString().trim();
                String dir=mEditTextDireccionPDV.getText().toString();

                if(!nom.isEmpty() && !cod.isEmpty() && !dir.isEmpty() && nom.length()>5 && dir.length()>10 ){
                    long id= dbPDV.insertarPDV(cod,nom, dir);
                    if(id>0){
                        Snackbar.make(findViewById(R.id.llpdv), "Registro exitoso", Snackbar.LENGTH_LONG)
                                .setBackgroundTint(getResources().getColor(R.color.sucess)).show();
                        limpiar();
                    }else{
                        Snackbar.make(findViewById(R.id.llpdv), "Error de Registro", Snackbar.LENGTH_LONG)
                                .setBackgroundTint(getResources().getColor(R.color.sucess)).show();
                    }
                }else{
                    Snackbar.make(findViewById(R.id.llpdv), "Datos Incompletos", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(getResources().getColor(R.color.danger)).show();
                }
            }
        });

        mFabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
               /* Intent intent=new Intent(PDVActivity.this,MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("nombre",nom);
                intent.putExtra("correo",correo);
                startActivity(intent);*/
            }
        });
    }

    private void limpiar() {
        mEditTextNombrePDV.requestFocus();
        mEditTextNombrePDV.setText("");
        mEditTextCodigoPDV.setText("");
        mEditTextDireccionPDV.setText("");
    }

    private void cargarPreferencias(){
        SharedPreferences preferences=getSharedPreferences("Datos", Context.MODE_PRIVATE);
        nom=preferences.getString("nombre","NO HAY");
        correo=preferences.getString("correo","NO HAY");
    }
}