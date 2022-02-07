package com.example.appgestor.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgestor.R;
import com.example.appgestor.entities.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>{

    ArrayList<Producto> listaProducto;
    ArrayList<Producto> listaOriginal;

    public ProductoAdapter(ArrayList<Producto>listaProducto){
        this.listaProducto=listaProducto;
        listaOriginal=new ArrayList<>();
        listaOriginal.addAll(listaProducto);
    }
    @NonNull
    @Override
    public ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_productos,null,false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoViewHolder holder, int position) {
        String costo = String.valueOf(listaProducto.get(position).getCosto());
        String p_mayor=String.valueOf(listaProducto.get(position).getP_mayor());
        String stock=Integer.toString(listaProducto.get(position).getStock());

        holder.mEditTextNombreProd.setText(listaProducto.get(position).getProducto());
        holder.mEditTextCostoProd.setText(costo);
        holder.mEditTextMayorProd.setText(p_mayor);
        holder.mEditTextStockProd.setText(stock);
        holder.id=listaProducto.get(position).getId();
        }

    public void filtrado(String txtBuscar){
        int longitud=txtBuscar.length();
        if(longitud==0){
            listaProducto.clear();
            listaProducto.addAll(listaOriginal);
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Producto> collection=listaProducto.stream().filter(i ->i.getProducto().toLowerCase()
                        .contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
                listaProducto.clear();
                listaProducto.addAll(collection);
            }else{
                for(Producto p: listaOriginal){
                    if(p.getProducto().toLowerCase().contains(txtBuscar.toLowerCase())){
                        listaProducto.add(p);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaProducto.size();
    }

    public class ProductoViewHolder extends RecyclerView.ViewHolder {
        EditText mEditTextCostoProd,mEditTextMayorProd,mEditTextStockProd;
        TextView mEditTextNombreProd;
        int id;
        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            mEditTextCostoProd=itemView.findViewById(R.id.editTextCostoProd);
            mEditTextNombreProd=itemView.findViewById(R.id.editTextNombreProd);
            mEditTextMayorProd=itemView.findViewById(R.id.editTextMayorProd);
            mEditTextStockProd=itemView.findViewById(R.id.editTextStockProd);

            mEditTextCostoProd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mEditTextCostoProd.setEnabled(true);
                    //Toast.makeText(v.getContext(), ""+id,Toast.LENGTH_SHORT).show();
                    //double costo=Double.parseDouble(mEditTextCostoProd.getText().toString().trim());
                }
            });
            mEditTextMayorProd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }
}
