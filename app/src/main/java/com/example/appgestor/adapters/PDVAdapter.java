package com.example.appgestor.adapters;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgestor.R;
import com.example.appgestor.activities.PDVVisitaActivity;
import com.example.appgestor.entities.Pdv;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PDVAdapter extends RecyclerView.Adapter<PDVAdapter.PdvViewHolder> {

    ArrayList<Pdv>listaPDV;
    ArrayList<Pdv>listaOriginal;
    public PDVAdapter(ArrayList<Pdv>listaPDV){
        this.listaPDV=listaPDV;
        listaOriginal=new ArrayList<>();
        listaOriginal.addAll(listaPDV);
    }
    @NonNull
    @Override
    public PdvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_pdv,null,false);
        return new PdvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdvViewHolder holder, int position) {
        holder.mTextViewNombrePDV.setText(listaPDV.get(position).getNombre());
        holder.mTextViewCodigoPDV.setText(listaPDV.get(position).getCodigo());
        holder.mTextViewDireccionPDV.setText(listaPDV.get(position).getDireccion());
        holder.id=listaPDV.get(position).getId();
    }
    public void filtrado(String txtBuscar){
        int longitud=txtBuscar.length();
        if(longitud==0){
            listaPDV.clear();
            listaPDV.addAll(listaOriginal);
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Pdv>collection=listaPDV.stream().filter(i ->i.getNombre().toLowerCase()
                        .contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
                listaPDV.clear();
                listaPDV.addAll(collection);
            }else{
                for(Pdv p: listaOriginal){
                    if(p.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())){
                        listaPDV.add(p);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
       return listaPDV.size();
    }


    public class PdvViewHolder extends RecyclerView.ViewHolder {

        TextView mTextViewNombrePDV,mTextViewCodigoPDV,mTextViewDireccionPDV;
        ImageView mImgViewMap,mImgViewNext;
        int id;

        public PdvViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewNombrePDV=itemView.findViewById(R.id.textViewNombrePDV);
            mTextViewCodigoPDV=itemView.findViewById(R.id.textViewCodigoPDV);
            mTextViewDireccionPDV=itemView.findViewById(R.id.textViewDireccionPDV);

            mImgViewMap=itemView.findViewById(R.id.imgViewMap);
            mImgViewNext=itemView.findViewById(R.id.imgViewNext);

            mImgViewNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tittle=mTextViewNombrePDV.getText().toString();
                    String dir=mTextViewDireccionPDV.getText().toString();
                    String cod=mTextViewCodigoPDV.getText().toString();
                    AlertDialog.Builder alerta=new AlertDialog.Builder(itemView.getContext());
                    alerta.setMessage("\n                ¿ Atenderá el PDV ?")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent=new Intent(itemView.getContext(), PDVVisitaActivity.class);
                                    intent.putExtra("id",id);
                                    intent.putExtra("codigo",cod);
                                    //Toast.makeText(itemView.getContext(),""+id,Toast.LENGTH_SHORT).show();
                                    intent.putExtra("nombre",tittle);
                                    intent.putExtra("direccion",dir);

                                    itemView.getContext().startActivity(intent);

                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog titulo=alerta.create();
                    titulo.setTitle(tittle.toUpperCase());
                    titulo.setIcon(R.drawable.ic_location);
                    titulo.show();
                }
            });

            mImgViewMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tittle=mTextViewNombrePDV.getText().toString();
                    Uri location = Uri.parse("geo:0,0?q="+tittle);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                    try {
                        itemView.getContext().startActivity(mapIntent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(itemView.getContext(), "No se puede encontrar ninguna aplicacion", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
