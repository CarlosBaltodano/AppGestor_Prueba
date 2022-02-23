package com.example.appgestor.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.appgestor.R;
import com.example.appgestor.bd.DbUsuarios;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;

public class UsersActivity extends AppCompatActivity {

    EditText mEditTextNombre, mEditTextCorreo, mEditTextUsuario, mEditTextClave;
    Button mBtnAddUsers;
    ImageView mImgBtnPhotoUser,mBackMenuUser;
    LinearLayout mLnrLyt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        mEditTextNombre = findViewById(R.id.editTextNombre);
        mEditTextCorreo = findViewById(R.id.editTextCorreo);
        mEditTextUsuario = findViewById(R.id.editTextUsername);
        mEditTextClave = findViewById(R.id.editTextPassword);
        mBtnAddUsers = findViewById(R.id.btnAddUser);
        mImgBtnPhotoUser = findViewById(R.id.imgBtnPhotoUser);
        mImgBtnPhotoUser.setTag("Photo");
        mLnrLyt = findViewById(R.id.lnrLyt);
        mEditTextNombre.requestFocus();
        mBackMenuUser=findViewById(R.id.backMenuUser);

        mBackMenuUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mImgBtnPhotoUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseProfilePicture();
            }
        });

        mBtnAddUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = mEditTextNombre.getText().toString();
                String correo = mEditTextCorreo.getText().toString().trim();
                String user = mEditTextUsuario.getText().toString().trim();
                String pass = mEditTextClave.getText().toString().trim();

                if(!nom.isEmpty() && !correo.isEmpty() && !user.isEmpty() && !pass.isEmpty()){
                    if(mImgBtnPhotoUser.getTag()=="Photo"){
                        Toast.makeText(UsersActivity.this, "POR FAVOR AGREGUE UNA FOTO DE PERFIL", Toast.LENGTH_SHORT).show();
                    }else {
                        byte[] bytesPP = convertImageViewToByteArray(mImgBtnPhotoUser);
                        DbUsuarios dbUsuarios = new DbUsuarios(UsersActivity.this);
                        long id = dbUsuarios.insertarUsuario(nom, correo, user, pass, bytesPP);
                        if (id > 0) {
                            Snackbar.make(findViewById(R.id.lnrLyt), "Registro exitoso", Snackbar.LENGTH_LONG)
                                    .setBackgroundTint(getResources().getColor(R.color.sucess)).show();
                            limpiar();
                        } else {
                            Snackbar.make(findViewById(R.id.lnrLyt), "No registro", Snackbar.LENGTH_LONG)
                                    .setBackgroundTint(getResources().getColor(R.color.danger)).show();
                        }
                    }
                }else{
                    Snackbar.make(findViewById(R.id.lnrLyt), "Por favor , Complete todo los campos", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(getResources().getColor(R.color.danger)).show();
                }
            }
        });
    }

    private byte[] convertImageViewToByteArray(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void chooseProfilePicture() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UsersActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_profile_picture, null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        ImageView imageViewADPPCamera = dialogView.findViewById(R.id.imageViewADPPCamera);
        ImageView imageViewADPPGallery = dialogView.findViewById(R.id.imageViewADPPGallery);

        final AlertDialog alertDialogProfilePicture = builder.create();
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

    private void takePictureFromGallery(){
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
            int cameraPermission = ActivityCompat.checkSelfPermission(UsersActivity.this, Manifest.permission.CAMERA);
            if(cameraPermission == PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(UsersActivity.this, new String[]{Manifest.permission.CAMERA}, 20);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 20 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            takePictureFromCamera();
        }
        else
            Toast.makeText(UsersActivity.this, "Permission not Granted", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImageUri = data.getData();
                    mImgBtnPhotoUser.setImageURI(selectedImageUri);
                    mImgBtnPhotoUser.setTag("Nueva");
                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    Bitmap bitmapImage = (Bitmap) bundle.get("data");
                    mImgBtnPhotoUser.setImageBitmap(bitmapImage);
                    mImgBtnPhotoUser.setTag("Nueva");
                }
                break;
        }
    }

    private void limpiar(){
        mEditTextNombre.requestFocus();
        mEditTextNombre.setText("");
        mEditTextCorreo.setText("");
        mEditTextUsuario.setText("");
        mEditTextClave.setText("");
        mImgBtnPhotoUser.setImageResource(R.drawable.ic_baseline_photo_camera_24);
    }

}