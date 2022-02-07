package com.example.appgestor.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appgestor.R;
import com.example.appgestor.bd.DbPDV;
import com.example.appgestor.bd.DbProductos;
import com.example.appgestor.entities.Pdv;

import java.util.ArrayList;

public class ProductosActivity extends AppCompatActivity {
    EditText mEditTextNombre,mEditTextCosto,mEditTextMayor,mEditTextStock;
    Spinner mSpnProducto;
    Button mBtnAddProd;
    ImageView mBackMenuProd;
    ArrayList<Pdv>listaArrayPDV;
    ArrayList<String>listaPDV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        mBackMenuProd=findViewById(R.id.backMenuProd);
        mEditTextNombre=findViewById(R.id.editTextNombre);
        mEditTextCosto=findViewById(R.id.editTextCosto);
        mEditTextMayor=findViewById(R.id.editTextMayor);
        mEditTextStock=findViewById(R.id.editTextStock);
        mSpnProducto=findViewById(R.id.spnProducto);
        mBtnAddProd=findViewById(R.id.btnAddProducto);


        mBtnAddProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom=mEditTextNombre.getText().toString();
                double costo=Double.parseDouble(mEditTextNombre.getText().toString());
                double mayor=Double.parseDouble(mEditTextNombre.getText().toString());
                int stock=Integer.parseInt(mEditTextNombre.getText().toString());
                int idspn=(int)mSpnProducto.getSelectedItemId();
                try {
                    DbProductos dbProductos = new DbProductos(ProductosActivity.this);
                    long id=dbProductos.insertarProducto(1,"Galletas",10.99,10.89,1);
                    if (id > 0) {
                        Toast.makeText(ProductosActivity.this, "REGISTRO PRODUCTO EXITOSO", Toast.LENGTH_SHORT).show();
                        limpiar();
                    } else {
                        Toast.makeText(ProductosActivity.this, "ERROR REGISTRO PRODUCTO", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Log.wtf("hola",e);
                }

            }
        });


        mBackMenuProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void limpiar() {
        mEditTextStock.setText("");
        mEditTextMayor.setText("");
        mEditTextNombre.setText("");
        mEditTextCosto.setText("");
        //mSpnProducto.setSelection(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        obtenerLista();
        ArrayAdapter<CharSequence> adaptador=new ArrayAdapter(this, android.R.layout.simple_spinner_item,listaPDV);
        mSpnProducto.setAdapter(adaptador);
    }

    private void obtenerLista() {
        DbPDV dbPDV=new DbPDV(ProductosActivity.this);
        listaPDV=new ArrayList<String>();
        listaPDV.add("Seleccione");
        for(int i=0;i<dbPDV.consultarPDV().size();i++){
            listaPDV.add(dbPDV.consultarPDV().get(i).getId()+" - "+dbPDV.consultarPDV().get(i).getNombre());
        }
    }
}