package com.example.appgestor.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appgestor.R;
import com.example.appgestor.bd.DbProductos;
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

            mEditTextCostoProd.setTag("app");
            mEditTextCostoProd.setText("100");
            mEditTextCostoProd.setTag(null);

            mEditTextMayorProd.setTag("app");
            mEditTextMayorProd.setText("100");
            mEditTextMayorProd.setTag(null);

            mEditTextStockProd.setTag("app");
            mEditTextStockProd.setText("100");
            mEditTextStockProd.setTag(null);

            mEditTextStockProd.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (mEditTextStockProd.getTag() == null) {
                        if(id==0){
                        }else{
                            DbProductos dbProductos=new DbProductos(itemView.getContext());
                            dbProductos.updateStock(id,Double.parseDouble(mEditTextStockProd.getText().toString()));
                        }
                    } else {
                        Toast.makeText(itemView.getContext(),"Modificado por el programa",Toast.LENGTH_SHORT).show();
                    }
                }
            });


            mEditTextMayorProd.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (mEditTextMayorProd.getTag() == null) {
                        if(id==0){
                        }else{
                            DbProductos dbProductos=new DbProductos(itemView.getContext());
                            dbProductos.updateMayor(id,Double.parseDouble(mEditTextMayorProd.getText().toString()));
                        }
                    } else {
                        Toast.makeText(itemView.getContext(),"Modificado por el programa",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            mEditTextCostoProd.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    //diferenciar quien ha sido
                    if (mEditTextCostoProd.getTag() == null) {
                        if(id==0){

                        }else{
                            DbProductos dbProductos=new DbProductos(itemView.getContext());
                            dbProductos.updateCosto(id,Double.parseDouble(mEditTextCostoProd.getText().toString()));
                            //Toast.makeText(itemView.getContext(),""+id,Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //Modificado por el programa
                        Toast.makeText(itemView.getContext(),"Modificado por el programa",Toast.LENGTH_SHORT).show();
                    }

                }
            });


        }
    }
}
