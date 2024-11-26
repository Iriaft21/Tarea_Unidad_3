package com.example.tarea_unidad_3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class CartaAdapter extends RecyclerView.Adapter<CartaAdapter.CartaViewHolder>{

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    private ArrayList<Carta> coleccion;
    private OnItemClickListener itemClickListener;
    public boolean isClickable= true;

    public CartaAdapter(ArrayList<Carta> coleccion, OnItemClickListener itemClickListener) {
        this.coleccion = coleccion;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public CartaAdapter.CartaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartaAdapter.CartaViewHolder cartaViewHolder =
                new CartaViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.carta, parent, false)
                );
        return cartaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartaAdapter.CartaViewHolder holder, int position) {
        Carta carta = coleccion.get(position);
        holder.imageViewReverso.setImageResource(carta.getImagenReverso());
        holder.imageViewCara.setImageResource(carta.getImagenCara());
        holder.imageViewCara.setVisibility(ImageView.INVISIBLE);
        carta.setIvReverso(holder.imageViewReverso);
        carta.setIvCara(holder.imageViewCara);
    }

    @Override
    public int getItemCount() {
        return coleccion.size();
    }

    public class CartaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageViewReverso;
        ImageView imageViewCara;

        public CartaViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageViewReverso = itemView.findViewById(R.id.reverso);
            imageViewCara = itemView.findViewById(R.id.cara);
        }

        @Override
        public void onClick(View view) {
            imageViewCara.setVisibility(View.VISIBLE);
            imageViewReverso.setVisibility(View.INVISIBLE);

            if(!isClickable) {
                return;
            }

            itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
