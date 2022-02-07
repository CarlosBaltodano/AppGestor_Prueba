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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.appgestor.R;
import com.example.appgestor.bd.DbPDV;

import java.io.ByteArrayOutputStream;

public class PDVVisitaActivity extends AppCompatActivity {
    Toolbar mtoolbar;
    ImageView mBackMenu,mImgBtnPhoto;
    int id;
    String nombre,direccion,codigo;
    LinearLayout mLinearLayout;
    Button mBtnVisit;
    TextView mTextViewNombreVisit,mTextViewDireccionVisit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdvvisita);
        mLinearLayout=findViewById(R.id.linearLayoutVisit);
        mtoolbar=findViewById(R.id.toolbar);
       //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(mtoolbar);
        mBackMenu=findViewById(R.id.backMenu);
        mTextViewNombreVisit=findViewById(R.id.textViewNombreVisit);
        mTextViewDireccionVisit=findViewById(R.id.textViewDireccionVisit);
        mBtnVisit=findViewById(R.id.btnVisit);
        mImgBtnPhoto=findViewById(R.id.imgBtnPhoto);
        mBackMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        foo(mBtnVisit);
        mBtnVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] bytesPP = convertImageViewToByteArray(mImgBtnPhoto);
                DbPDV dbPDV = new DbPDV(PDVVisitaActivity.this);
                if (dbPDV.update(id, bytesPP)) {
                    Intent intent = new Intent(PDVVisitaActivity.this, ReporteActivity.class);
                    intent.putExtra("nombre", nombre);
                    intent.putExtra("id", id);
                    startActivity(intent);

                    Toast.makeText(PDVVisitaActivity.this, "REGISTRO EXITOSO", Toast.LENGTH_SHORT).show();
                    limpiar();
                } else {
                    Toast.makeText(PDVVisitaActivity.this, "ERROR REGISTRO", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mImgBtnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseProfilePicture();
            }
        });


    }

    private byte[] convertImageViewToByteArray(ImageView mImgBtnPhoto) {
        Bitmap bitmap = ((BitmapDrawable) mImgBtnPhoto.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void chooseProfilePicture() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PDVVisitaActivity.this);
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
            int cameraPermission = ActivityCompat.checkSelfPermission(PDVVisitaActivity.this, Manifest.permission.CAMERA);
            if(cameraPermission == PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(PDVVisitaActivity.this, new String[]{Manifest.permission.CAMERA}, 20);
                return false;
            }
        }
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        Bundle bundle=this.getIntent().getExtras();
        id=bundle.getInt("id");
        codigo=bundle.getString("codigo");
        nombre=bundle.getString("nombre");
        direccion=bundle.getString("direccion");
        mTextViewNombreVisit.setText(nombre);
        mTextViewDireccionVisit.setText(direccion.toUpperCase());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImageUri = data.getData();
                    mImgBtnPhoto.setImageURI(selectedImageUri);
                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    Bitmap bitmapImage = (Bitmap) bundle.get("data");
                    mImgBtnPhoto.setImageBitmap(bitmapImage);
                }
                break;
        }
    }

    private void limpiar(){
        mImgBtnPhoto.setImageResource(R.drawable.ic_baseline_photo_camera_24);
    }

    public void foo(View v){
        if (v.getId() == R.id.imgBtnPhoto){
            Toast.makeText(PDVVisitaActivity.this, "Presiono", Toast.LENGTH_SHORT).show();
        }else if (v.getId() == R.id.btnVisit){

        }else{
            Toast.makeText(PDVVisitaActivity.this, "Por Favor, Tome una foto", Toast.LENGTH_SHORT).show();
        }
    }
}