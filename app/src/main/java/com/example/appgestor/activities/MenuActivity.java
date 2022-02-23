package com.example.appgestor.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appgestor.R;
import com.example.appgestor.bd.DbUsuarios;
import com.example.appgestor.entities.User;
import com.example.appgestor.fragments.LocationFragment;
import com.example.appgestor.fragments.SupportFragment;
import com.google.android.material.navigation.NavigationView;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout mDraweLayout;
    NavigationView mNavigationView;
    Toolbar mtoolbar;
    ImageView mImgFotoUser;
    TextView mTextViewNombreUser,mTextViewCorreoUser;
    ActionBarDrawerToggle mToogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mDraweLayout=findViewById(R.id.drawer_layout);
        mNavigationView=findViewById(R.id.nav_view);
        mtoolbar=findViewById(R.id.toolbar);
        mTextViewNombreUser=findViewById(R.id.textViewNombreUser);
        mTextViewCorreoUser=findViewById(R.id.textViewCorreoUser);
        mImgFotoUser=findViewById(R.id.imgFotoUser);


        getSupportFragmentManager().beginTransaction()
                .add(com.google.android.material.R.id.content,new LocationFragment()).commit();
        setTitle("Puntos de Venta");

        setSupportActionBar(mtoolbar);


        mToogle=setUpDrawerToggle();
        mDraweLayout.addDrawerListener(mToogle);

        mNavigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences preferences=getSharedPreferences("Datos", Context.MODE_PRIVATE);
        String nom=preferences.getString("nombre","NO HAY");
        String correo=preferences.getString("correo","NO HAY");
        int id_user=preferences.getInt("id",0);
        mTextViewNombreUser.setText(nom);
        mTextViewCorreoUser.setText(correo);
        showUser(id_user);
    }

    private void showUser(int id){
        DbUsuarios dbUsuarios = new DbUsuarios(MenuActivity.this);
        User user = dbUsuarios.getUser(id);
        if(user.getImage() != null){
            Bitmap bitmap = convertByteArrayToBitmap(user.getImage());
            mImgFotoUser.setImageBitmap(bitmap);
        }else{

        }
    }
    private Bitmap convertByteArrayToBitmap(byte[] bytes){
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }


    private ActionBarDrawerToggle setUpDrawerToggle() {
        return new ActionBarDrawerToggle(this,
                mDraweLayout,
                mtoolbar,
                R.string.drawer_opem,
                R.string.drawer_close);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToogle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mToogle.onConfigurationChanged(newConfig);
    }

    private void selectItemNav(MenuItem item) {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        switch (item.getItemId()){
            case R.id.nav_location:
                ft.replace(R.id.content,new LocationFragment()).commit();
                break;
            case R.id.nav_support:
                ft.replace(R.id.content,new SupportFragment()).commit();
                break;
            case R.id.nav_logout:
                AlertDialog.Builder alerta=new AlertDialog.Builder(MenuActivity.this);
                alerta.setMessage("\n                ¿ Desea Cerrar Sesion ?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences preferences=getSharedPreferences("Datos", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor=preferences.edit();
                                editor.putBoolean("sesion",false);
                                editor.commit();
                                Intent intent=new Intent(MenuActivity.this,LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog titulo=alerta.create();
                titulo.setTitle("CERRAR SESION");
                titulo.setIcon(R.drawable.ic_logout);
                titulo.show();
                break;
        }
        setTitle(item.getTitle());
        mDraweLayout.closeDrawers();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        selectItemNav(item);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToogle.onOptionsItemSelected(item)){
            return true;
        }
        switch (item.getItemId()){
            case R.id.item1:
                Intent intent=new Intent(MenuActivity.this,UsersActivity.class);
                startActivity(intent);
                break;
         //   case R.id.item2:
            //      Intent intent1=new Intent(MenuActivity.this,ProductosActivity.class);
         //       startActivity(intent1);
         //       break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.options_menu,menu);
        return true;
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Por favor, haga clic en atras de nuevo para salir.", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;

            }
        }, 2000);
    }
}