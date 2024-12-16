package com.example.tarea_unidad_3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    private ArrayList<Carta> cartaArrayList;
    public ImageView felicitacion;
    public Chronometer cronometro;
    private FragmentoCartas fragmentoCartas;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //se crean las cartas
        cartaArrayList = new ArrayList<>(Arrays.asList(
                new Carta(R.drawable.reverso, R.drawable.tinkaton, "tinkaton",getContext()),
                new Carta(R.drawable.reverso, R.drawable.tinkaton, "tinkaton", getContext()),
                new Carta(R.drawable.reverso, R.drawable.phantump, "phantump", getContext()),
                new Carta(R.drawable.reverso, R.drawable.phantump, "phantump", getContext()),
                new Carta(R.drawable.reverso, R.drawable.cramorant, "cramorant", getContext()),
                new Carta(R.drawable.reverso, R.drawable.cramorant, "cramorant", getContext()),
                new Carta(R.drawable.reverso, R.drawable.deoxys, "deoxys", getContext()),
                new Carta(R.drawable.reverso, R.drawable.deoxys, "deoxys", getContext()),
                new Carta(R.drawable.reverso, R.drawable.lapras, "lapras", getContext()),
                new Carta(R.drawable.reverso, R.drawable.lapras, "lapras", getContext()),
                new Carta(R.drawable.reverso, R.drawable.houndoom, "houndoom", getContext()),
                new Carta(R.drawable.reverso, R.drawable.houndoom, "houndoom", getContext()),
                new Carta(R.drawable.reverso, R.drawable.ninetales, "ninetales", getContext()),
                new Carta(R.drawable.reverso, R.drawable.ninetales, "ninetales", getContext()),
                new Carta(R.drawable.reverso, R.drawable.charizard, "charizard", getContext()),
                new Carta(R.drawable.reverso, R.drawable.charizard, "charizard", getContext())));

        //se barajan las cartas, poniendolas en posiciones aleatorias
        Collections.shuffle(cartaArrayList);

        fragmentoCartas = FragmentoCartas.newInstance(cartaArrayList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        felicitacion = view.findViewById(R.id.partida_ganada);
        felicitacion.setVisibility(View.INVISIBLE);
        cronometro = view.findViewById(R.id.cronometro);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragmentoCartas);
        fragmentTransaction.commit();

        //metodos asociado a botones o toggleButton
        botonSalir(view);
        nuevaPartida(view);
        toggleCronometro(view);
        return view;
    }

    public void botonSalir(View view){
        //se busca el boton de salir en el xml
        Button btn_salir = view.findViewById(R.id.btn_salir);
        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hace que se salga de la aplicacion
                getActivity().finishAndRemoveTask();
            }
        });
    }

    public void nuevaPartida(View view){
        //se busca el boton de nueva partida en el xml
        Button btn_nueva_partida = view.findViewById(R.id.btn_nuevaPartida);
        btn_nueva_partida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //se llama a otro método
                fragmentoCartas.generarNuevaPartida();

            }
        });
    }

    public void toggleCronometro(View view){
        //se busca el toggleButton y el cronometro en el xml
        ToggleButton tg_cronometro = view.findViewById(R.id.toggleButton);
        //cronometro por defecto invisible
        cronometro.setVisibility(View.INVISIBLE);

        tg_cronometro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //se activa el toggleButton
                if(tg_cronometro.isChecked()){
                    //Se muestra el cronometro y empieza a contar
                    cronometro.setBase(SystemClock.elapsedRealtime());
                    cronometro.setVisibility(View.VISIBLE);
                    cronometro.start();
                }else{
                    //se oculta el cronometro, se pone a cero y se para
                    cronometro.setVisibility(View.INVISIBLE);
                    cronometro.setBase(SystemClock.elapsedRealtime());
                    cronometro.stop();
                }
            }
        });
    }
}