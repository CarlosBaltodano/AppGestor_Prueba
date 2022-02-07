package com.example.appgestor.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgestor.R;
import com.example.appgestor.activities.PDVActivity;
import com.example.appgestor.adapters.PDVAdapter;
import com.example.appgestor.bd.DbPDV;
import com.example.appgestor.entities.Pdv;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class LocationFragment extends Fragment implements SearchView.OnQueryTextListener {
    FloatingActionButton mFab;
    RecyclerView mListaPDV;
    ArrayList<Pdv>listaArrayPDV;
    SearchView mSearchView;
    PDVAdapter adapter;
    public LocationFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            mFab=view.findViewById(R.id.fab_addPDV);
           mListaPDV=view.findViewById(R.id.lista_pdv);
           mSearchView=view.findViewById(R.id.search_pdv);
           mListaPDV.setLayoutManager(new LinearLayoutManager(getContext()));
            DbPDV dbPDV=new DbPDV(getContext());
            listaArrayPDV=new ArrayList<>();

        adapter=new PDVAdapter(dbPDV.mostrarPDV());
        mListaPDV.setAdapter(adapter);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getContext(), PDVActivity.class);
                startActivity(intent);
            }
        });

        mSearchView.setOnQueryTextListener(this);
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