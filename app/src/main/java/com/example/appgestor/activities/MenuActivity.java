package com.example.appgestor.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
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
    ImageView mImgFotoUser,mImgEditUser;
    TextView mTextViewNombreUser,mTextViewCorreoUser;
    ActionBarDrawerToggle mToogle;
    ImageButton mImgBtnModifiedUser;
    int id_user;
    String nom,correo;


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
        mImgBtnModifiedUser=findViewById(R.id.imgBtnModifiedUser);

        getSupportFragmentManager().beginTransaction()
                .add(com.google.android.material.R.id.content,new LocationFragment()).commit();
        setTitle("Puntos de Venta");

        setSupportActionBar(mtoolbar);
        mToogle=setUpDrawerToggle();
        mDraweLayout.addDrawerListener(mToogle);

        mNavigationView.setNavigationItemSelectedListener(this);

        mImgBtnModifiedUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDraweLayout.closeDrawers();
                AlertDialog.Builder builder=new AlertDialog.Builder(MenuActivity.this);
                View mView=getLayoutInflater().inflate(R.layout.edit_profile,null);
                final EditText mNombreEdit=mView.findViewById(R.id.editTextNombreEdit);
                final EditText mCorreoEdit=mView.findViewById(R.id.editTextCorreoEdit);
                Button mBtnEditUser=mView.findViewById(R.id.btnEditUser);
                mImgEditUser= mView.findViewById(R.id.imgBtnEdit);

                DbUsuarios dbUsuarios = new DbUsuarios(MenuActivity.this);
                User user = dbUsuarios.getUser(id_user);
                if(user.getImage() != null){
                    Bitmap bitmap = convertByteArrayToBitmap(user.getImage());
                    mImgEditUser.setImageBitmap(bitmap);
                }else{ }

                mNombreEdit.setText(nom);
                mCorreoEdit.setText(correo);

                builder.setView(mView);
                AlertDialog dialog=builder.create();
                dialog.show();

                mBtnEditUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DbUsuarios dbUsuarios = new DbUsuarios(MenuActivity.this);
                        String nombreUser=mNombreEdit.getText().toString();
                        String correoUser=mCorreoEdit.getText().toString();
                        if(dbUsuarios.updateUser(id_user,nombreUser,correoUser)){
                            guardarDatos(nombreUser,correoUser,id_user);
                            Toast.makeText(mView.getContext(), "Editado Correctamente", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                            //mToogle.onDrawerClosed(mDraweLayout);
                            startActivity(getIntent());
                        }else{
                            Toast.makeText(mView.getContext(), "Error al editar Usuario", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                mImgEditUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chooseProfilePicture();
                    }
                });

            }
        });


    }

    private void chooseProfilePicture() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MenuActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_profile_picture, null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        ImageView imageViewADPPCamera = dialogView.findViewById(R.id.imageViewADPPCamera);
        ImageView imageViewADPPGallery = dialogView.findViewById(R.id.imageViewADPPGallery);

        final androidx.appcompat.app.AlertDialog alertDialogProfilePicture = builder.create();
        alertDialogProfilePicture.show();

        imageViewADPPCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAndRequestPermissions()) {
                    takePictureFromCamera();
                    alertDialogProfilePicture.cancel();
                }
            }
        });

        imageViewADPPGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePictureFromGallery();
                alertDialogProfilePicture.cancel();
            }
        });
    }

    private void takePictureFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);
    }

    private void takePictureFromCamera() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePicture.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePicture, 2);
        }
    }

    private boolean checkAndRequestPermissions() {
        if(Build.VERSION.SDK_INT >= 23){
            int cameraPermission = ActivityCompat.checkSelfPermission(MenuActivity.this, Manifest.permission.CAMERA);
            if(cameraPermission == PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(MenuActivity.this, new String[]{Manifest.permission.CAMERA}, 20);
                return false;
            }
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImageUri = data.getData();
                    mImgEditUser.setImageURI(selectedImageUri);
                    mImgEditUser.setTag("Nueva");
                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    Bitmap bitmapImage = (Bitmap) bundle.get("data");
                    mImgEditUser.setImageBitmap(bitmapImage);
                    mImgEditUser.setTag("Nueva");
                }
                break;
        }
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
        nom=preferences.getString("nombre","NO HAY");
        correo=preferences.getString("correo","NO HAY");
        id_user=preferences.getInt("id",0);

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
            case R.id.item2:
                 Intent intent1=new Intent(MenuActivity.this,ProductosActivity.class);
                startActivity(intent1);
                break;
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