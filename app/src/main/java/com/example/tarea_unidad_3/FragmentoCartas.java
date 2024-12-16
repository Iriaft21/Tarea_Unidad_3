package com.example.tarea_unidad_3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentoCartas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoCartas extends Fragment implements CartaAdapter.OnItemClickListener{

    private static final String ARG_CARTAS = "cartas";

    private ArrayList<Carta> cartasSeleccionadas = new ArrayList<>();
    private int parejasHechas;
    private boolean puedeVoltear = true;
    private ArrayList<Carta> cartaArrayList;
    private RecyclerView rvCartas;
    private CartaAdapter cartaAdapter;
    private MainFragment mainFragment;

    public boolean getPuedeVoltear() {
        return this.puedeVoltear;
    }

    public void setPuedeVoltear(boolean puedeVoltear) {
        this.puedeVoltear = puedeVoltear;
    }


    public FragmentoCartas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param cartas Parameter 1.
     * @return A new instance of fragment FragmentoCartas.
     */
    public static FragmentoCartas newInstance(ArrayList<Carta> cartas) {
        FragmentoCartas fragment = new FragmentoCartas();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_CARTAS, cartas);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cartaArrayList = getArguments().getParcelableArrayList(ARG_CARTAS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragmento_cartas, container, false);

        rvCartas = view.findViewById(R.id.rv_cartas);
        rvCartas.setLayoutManager(new GridLayoutManager(getContext(), 4));

        cartaAdapter = new CartaAdapter(cartaArrayList, this);
        rvCartas.setAdapter(cartaAdapter);

        mainFragment = (MainFragment) getParentFragment();
        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        Carta carta = cartaArrayList.get(position);

        //se comrpueba que la carta se pueda voltear, que este ya emparejada o que ya este seleccionada de antes en esa tanda
        if (!getPuedeVoltear()  || carta.isParejaEncontrada() || cartasSeleccionadas.contains(carta)) {
            Log.i("Error", "Click ignorado por condiciones no cumplidas");
            return;
        }
        //se añade al arrayList de cartas seleccionadas
        cartasSeleccionadas.add(carta);

        //se extrae de los recursos los strings para los Toast
        String acierto = getResources().getString(R.string.toast_acierto);
        String fallo = getResources().getString(R.string.toast_diferencia);
        Log.i("Debug","Carta seleccionada" + carta.getValor());

        //se comprueba que el tamaño sea siempre dos
        if(cartasSeleccionadas.size()==2) {
            //comprobamos que ambas cartas tenga el mismo valor
            if (cartasSeleccionadas.get(0).getValor().equals(cartasSeleccionadas.get(1).getValor())) {
                aciertoPareja(view, acierto);
            } else {
                falloPareja(view, fallo);
            }
        } else{
            // Si solo se ha seleccionado una carta, se puede volver a clicar
            CartaAdapter.puedeClicar = true;
        }
    }

    private void aciertoPareja(View view, String acierto){
        //se envia un Toast avisando del acierto
        Toast.makeText(view.getContext(), acierto, Toast.LENGTH_SHORT).show();
        parejasHechas++;

        //se anotan las cartas como ya emparejadas
        cartasSeleccionadas.get(0).setParejaEncontrada(true);
        cartasSeleccionadas.get(1).setParejaEncontrada(true);

        //se vacia el ArrayList
        cartasSeleccionadas.clear();

        //si estan las 8 parejas encontradas se llama al metodo finalizarJuego
        if (parejasHechas == 8) {
            finalizarJuego();
        }
        //se permite volver a clicar
        CartaAdapter.puedeClicar= true;
    }

    private void falloPareja(View view, String fallo){
        //se deshabilita el voltear las cartas
        setPuedeVoltear(false);
        //toast avisando de que las cartas no son iguales
        Toast.makeText(view.getContext(), fallo, Toast.LENGTH_SHORT).show();

        //handler para hacer que se volteen las cartas
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (Carta c : cartasSeleccionadas) {
                    ImageView cara = c.getIvCara();
                    ImageView reverso = c.getIvReverso();
                    //comprobacion de que tenga imagen la carta y en caso afirmativo, se voltean las cartas
                    if (cara != null && reverso != null) {
                        cara.setVisibility(View.INVISIBLE);
                        reverso.setVisibility(View.VISIBLE);
                    } else {
                        Log.e("Error", "No se encontró imagen de cara o del reverso");
                    }
                }
                //se vacia el ArrayList
                cartasSeleccionadas.clear();
                //se vuelve a habilitar el volteo
                setPuedeVoltear(true);
                //se permite volver a clicar
                CartaAdapter.puedeClicar = true;
            }
        }, 1000);

    }

    private void finalizarJuego(){
        //handler para que salga la felicitación después de unos segundos
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //se muestra la felicitacion
                mainFragment.felicitacion.setVisibility(View.VISIBLE);
                //otro handler para que la felicitacion permanezca visible durante unos segundos
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //se vuelve a hacer la felicitacion invisible y se genera nueva partida
                        mainFragment.felicitacion.setVisibility(View.INVISIBLE);
                        try {
                            generarNuevaPartida();
                        } catch (Exception e) {
                            Log.e("Error", "Error al generar nueva partida: " + e.getMessage());
                        }
                        //se permite volver a clicar
                        CartaAdapter.puedeClicar = true;
                    }
                }, 2000);
            }
        }, 2000);
    }

    public void generarNuevaPartida() {
        // Se pone a cero las parejas encontradas y se vacia el ArrayList que contiene las cartas seleccionadas
        cartasSeleccionadas.clear();
        parejasHechas = 0;

        //se pone a cero el cronometro
        mainFragment.cronometro.setBase(SystemClock.elapsedRealtime());
        mainFragment.cronometro.stop();
        mainFragment.cronometro.setBase(SystemClock.elapsedRealtime());
        mainFragment.cronometro.start();

        //se vuelve a barajar las cartas
        Collections.shuffle(cartaArrayList);

        //se modifica el boolean de pareja encontrada, sino no tendran los eventos de ItemClick
        for (Carta carta : cartaArrayList) {
            carta.setParejaEncontrada(false);
        }

        // Notificar al adaptador que los datos han cambiado para que actualice la vista
        rvCartas.getAdapter().notifyDataSetChanged();

        // Se voltea boca abajo todas las cartas
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setPuedeVoltear(true);
                voltearTodasLasCartas();
            }
        }, 1000);
    }

    private void voltearTodasLasCartas() {
        //se recorren las cartas y se van poniendo boca abajo
        for (Carta carta : cartaArrayList) {
            if (carta.getIvCara() != null && carta.getIvReverso() != null) {
                carta.getIvCara().setVisibility(View.INVISIBLE);
                carta.getIvReverso().setVisibility(View.VISIBLE);
            } else {
                Log.e("Error", "No se encontró imagen de cara o del reverso");
            }
        }
    }
}