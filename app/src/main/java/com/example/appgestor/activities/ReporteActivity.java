package com.example.appgestor.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appgestor.R;
import com.example.appgestor.adapters.ProductoAdapter;
import com.example.appgestor.bd.DbProductos;
import com.example.appgestor.entities.Producto;

import java.util.ArrayList;

public class ReporteActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    TextView mTextViewTittleBar;
    ImageView mImgBackMenuReporte,mBtnSaveReporte;
    String title,nom,correo;
    SearchView mSearchProducto;
    RecyclerView mListaProducto;
    ArrayList<Producto>listaArrayProducto;
    ProductoAdapter adapter;

    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);
        mTextViewTittleBar=findViewById(R.id.textViewTittleBar);
        mImgBackMenuReporte=findViewById(R.id.backMenuReporte);
        mBtnSaveReporte=findViewById(R.id.btnSaveReporte);
        mSearchProducto=findViewById(R.id.search_producto);
        mListaProducto=findViewById(R.id.lista_producto);
        mListaProducto.setLayoutManager(new LinearLayoutManager(ReporteActivity.this));

        mSearchProducto.setOnQueryTextListener(this);

        mImgBackMenuReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ReporteActivity.this,MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("nombre",nom);
                intent.putExtra("correo",correo);
                startActivity(intent);
            }
        });

        mBtnSaveReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ReporteActivity.this, "DATOS GUARDADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(ReporteActivity.this,MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("nombre",nom);
                intent.putExtra("correo",correo);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle bundle=this.getIntent().getExtras();
        title=bundle.getString("nombre");
        id=bundle.getInt("id");
        mTextViewTittleBar.setText(title);
        cargarPreferencias();

        DbProductos dbProductos=new DbProductos(ReporteActivity.this);
        listaArrayProducto=new ArrayList<>();
        adapter=new ProductoAdapter(dbProductos.mostrarProducto(id));
        mListaProducto.setAdapter(adapter);
    }

    private void cargarPreferencias(){
        SharedPreferences preferences=getSharedPreferences("Datos", Context.MODE_PRIVATE);
        nom=preferences.getString("nombre","NO HAY");
        correo=preferences.getString("correo","NO HAY");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filtrado(newText);
        return false;
    }
}