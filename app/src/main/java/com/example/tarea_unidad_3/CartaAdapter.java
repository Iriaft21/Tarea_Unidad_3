package com.example.tarea_unidad_3;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;

public class CartaAdapter extends RecyclerView.Adapter<CartaAdapter.CartaViewHolder>{

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    private ArrayList<Carta> coleccion;
    private OnItemClickListener itemClickListener;

    public CartaAdapter(ArrayList<Carta> coleccion, OnItemClickListener itemClickListener) {
        this.coleccion = coleccion;
        this.itemClickListener = itemClickListener;
        Log.i("Debug","Saludos desde  el constructor");
    }

    @NonNull
    @Override
    public CartaAdapter.CartaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartaAdapter.CartaViewHolder cartaViewHolder =
                new CartaViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.carta, parent, false)
                );
        Log.i("Debug","Saludos desde onCreateViewHolder");
        return cartaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartaAdapter.CartaViewHolder holder, int position) {
        Carta carta = coleccion.get(position);
        holder.imageViewReverso.setImageResource(carta.getImagenReverso());
        holder.imageViewCara.setImageResource(carta.getImagenCara());
        holder.imageViewCara.setVisibility(ImageView.INVISIBLE);
        Log.i("Debug","Saludos desde onBindViewHolder");

        /*
        holder.imageViewReverso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.imageViewCara.setVisibility(View.VISIBLE);
                holder.imageViewReverso.setVisibility(View.INVISIBLE);
                Log.i("Debug","Saludos desde girar la carta a boca arriba");
            }
        });


        holder.imageViewCara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.imageViewReverso.setVisibility(View.VISIBLE);
                holder.imageViewCara.setVisibility(View.INVISIBLE);
                Log.i("Debug","Saludos desde girar la carta a boca abajo");
            }
        });*/
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
            //Log.i("Debug","Saludos desde CartaViewHolder");
        }


        //no entra aqui
        @Override
        public void onClick(View view) {
            imageViewCara.setVisibility(View.VISIBLE);
            imageViewReverso.setVisibility(View.INVISIBLE);
            //Log.i("Debug","Saludos desde girar la carta a boca arriba");

            itemClickListener.onItemClick(view, getAdapterPosition());
            //System.out.println("entra en el onclick de cartaViewHolder");
        }
    }
}
