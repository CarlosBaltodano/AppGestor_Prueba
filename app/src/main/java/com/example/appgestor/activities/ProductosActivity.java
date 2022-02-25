package com.example.appgestor.activities;

import android.os.Bundle;
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
        mEditTextNombre=findViewById(R.id.editTextNombreProducto);
        mEditTextCosto=findViewById(R.id.editTextCostoProducto);
        mEditTextMayor=findViewById(R.id.editTextMayorProducto);
        mEditTextStock=findViewById(R.id.editTextStockProducto);
        mSpnProducto=findViewById(R.id.spnProducto);
        mBtnAddProd=findViewById(R.id.btnAddProducto);


        mBtnAddProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre=mEditTextNombre.getText().toString().trim();
                String cos=mEditTextCosto.getText().toString();
                String may=mEditTextMayor.getText().toString();
                String sto=mEditTextStock.getText().toString();
                int spn=mSpnProducto.getSelectedItemPosition();
                //Toast.makeText(ProductosActivity.this, ""+spn, Toast.LENGTH_SHORT).show();

                if(!nombre.isEmpty() && !cos.isEmpty() && !may.isEmpty() && !sto.isEmpty() && spn!=0){

                    double costo=Double.valueOf(mEditTextCosto.getText().toString());
                    double mayor=Double.parseDouble(mEditTextMayor.getText().toString());
                    int stock=Integer.parseInt(mEditTextStock.getText().toString());
                    int idspn=mSpnProducto.getSelectedItemPosition();
                    DbProductos dbProductos = new DbProductos(ProductosActivity.this);

                    long id=dbProductos.insertarProducto(idspn,nombre,costo,mayor,stock);
                    if (id > 0) {
                        Toast.makeText(ProductosActivity.this, "REGISTRO PRODUCTO EXITOSO", Toast.LENGTH_SHORT).show();
                        limpiar();
                    } else {
                        Toast.makeText(ProductosActivity.this, "ERROR REGISTRO PRODUCTO", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(ProductosActivity.this, "Por Favor complete todo los datos", Toast.LENGTH_SHORT).show();
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
        mEditTextNombre.requestFocus();
        mEditTextStock.setText("");
        mEditTextMayor.setText("");
        mEditTextNombre.setText("");
        mEditTextCosto.setText("");
        mSpnProducto.setSelection(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        obtenerLista();
        ArrayAdapter<CharSequence> adaptador=new ArrayAdapter(this,R.layout.simple_spinner_spn,listaPDV);
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