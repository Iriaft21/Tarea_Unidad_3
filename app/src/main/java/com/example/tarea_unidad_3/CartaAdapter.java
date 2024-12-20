package com.example.tarea_unidad_3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class CartaAdapter extends RecyclerView.Adapter<CartaAdapter.CartaViewHolder>{

    public static boolean puedeClicar = true;

    //interfaz del listener OnItemClick
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    //atributos de la clase
    private ArrayList<Carta> coleccion;
    private OnItemClickListener itemClickListener;

    //constructor del adaptador
    public CartaAdapter(ArrayList<Carta> coleccion, OnItemClickListener itemClickListener) {
        this.coleccion = coleccion;
        this.itemClickListener = itemClickListener;
    }

    //se crea el viewHolder
    @NonNull
    @Override
    public CartaAdapter.CartaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Se infla la vista de la carta desde el archivo de diseño XML
        CartaAdapter.CartaViewHolder cartaViewHolder =
                new CartaViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.carta, parent, false)
                );
        return cartaViewHolder;
    }

    //se asocian los datos de cartas con los elementos del viewHolder
    @Override
    public void onBindViewHolder(@NonNull CartaAdapter.CartaViewHolder holder, int position) {
        Carta carta = coleccion.get(position);
        holder.imageViewReverso.setImageResource(carta.getImagenReverso());
        holder.imageViewCara.setImageResource(carta.getImagenCara());
        holder.imageViewCara.setVisibility(ImageView.INVISIBLE);
        carta.setIvReverso(holder.imageViewReverso);
        carta.setIvCara(holder.imageViewCara);
    }

    //obtener el numero de elementos que contiene el adaptador
    @Override
    public int getItemCount() {
        return coleccion.size();
    }

    public class CartaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageViewReverso;
        ImageView imageViewCara;

        public CartaViewHolder(@NonNull View itemView) {
            super(itemView);
            //se asocia el evento a una view
            itemView.setOnClickListener(this);
            //se asocian las imageView a los elementos de la interfaz
            imageViewReverso = itemView.findViewById(R.id.reverso);
            imageViewCara = itemView.findViewById(R.id.cara);
        }

        //evento onClick
        @Override
        public void onClick(View view) {
            //se comprueba si la carta se puede clicar
            if(!puedeClicar){
                // si no se puede clicar, se sale del método y no se realiza ninguna acción
                return;
            }
            //se deshabilita el poder clicar
            puedeClicar = false;
            //la cara de la carta se hace visible y el reverso invisible
            imageViewCara.setVisibility(View.VISIBLE);
            imageViewReverso.setVisibility(View.INVISIBLE);
            // se llama al método onItemClick del itemClickListener pasándole la vista y la posición de la carta clicada
            itemClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}
