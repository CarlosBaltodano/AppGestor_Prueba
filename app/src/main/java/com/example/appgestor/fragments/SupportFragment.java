package com.example.appgestor.fragments;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.appgestor.R;
public class SupportFragment extends Fragment {

    CardView mCardViewTelefono,mCardViewWhatsapp,mCardViewFacebook;
    LinearLayout mLnrLyt;

    public SupportFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_support, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLnrLyt = view.findViewById(R.id.lnrLyt);
        mCardViewTelefono=view.findViewById(R.id.cardViewTelefono);
        mCardViewWhatsapp=view.findViewById(R.id.cardViewWhatsapp);
        mCardViewFacebook=view.findViewById(R.id.cardViewFacebook);


        mCardViewFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{ Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/FreeFireLigueSports" ));
                    startActivity(intent);
                }catch(Exception e){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/grupolucky"))); }
            }
        });

        mCardViewWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contact = "51956176198";
                String url = "https://api.whatsapp.com/send/?phone="+contact+"&text=Buenos+dias+%2C+soy+Carlos";
                try {
                    PackageManager pm = getContext().getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_TEXT,"Hola");
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(getContext(), "La aplicación Whastapp no se encuentra instalada en el dispositivo.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        mCardViewTelefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "015238324";
                Uri call = Uri.parse("tel:" + phoneNumber);
                Intent intent2 = new Intent(Intent.ACTION_DIAL, call);
                startActivity(intent2);
            }
        });
    }

}