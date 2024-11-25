package com.example.tarea_unidad_3;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements CartaAdapter.OnItemClickListener{

    private ArrayList<Carta> cartaArrayList;
    private final ArrayList<Carta> cartasSeleccionadas = new ArrayList<>();
    private int parejasHechas;
    //private  ImageView felicitacion;
    private boolean puedeVoltear = true;
    private Chronometer cronometro;

    public boolean getPuedeVoltear() {
        return this.puedeVoltear;
    }

    public void setPuedeVoltear(boolean puedeVoltear) {
        this.puedeVoltear = puedeVoltear;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //se crean las cartas
        cartaArrayList = new ArrayList<>(Arrays.asList(
                new Carta(R.drawable.reverso, R.drawable.tinkaton, "tinkaton"),
                new Carta(R.drawable.reverso, R.drawable.tinkaton, "tinkaton"),
                new Carta(R.drawable.reverso, R.drawable.phantump, "phantump"),
                new Carta(R.drawable.reverso, R.drawable.phantump, "phantump"),
                new Carta(R.drawable.reverso, R.drawable.cramorant, "cramorant"),
                new Carta(R.drawable.reverso, R.drawable.cramorant, "cramorant"),
                new Carta(R.drawable.reverso, R.drawable.deoxys, "deoxys"),
                new Carta(R.drawable.reverso, R.drawable.deoxys, "deoxys"),
                new Carta(R.drawable.reverso, R.drawable.lapras, "lapras"),
                new Carta(R.drawable.reverso, R.drawable.lapras, "lapras"),
                new Carta(R.drawable.reverso, R.drawable.houndoom, "houndoom"),
                new Carta(R.drawable.reverso, R.drawable.houndoom, "houndoom"),
                new Carta(R.drawable.reverso, R.drawable.ninetales, "ninetales"),
                new Carta(R.drawable.reverso, R.drawable.ninetales, "ninetales"),
                new Carta(R.drawable.reverso, R.drawable.charizard, "charizard"),
                new Carta(R.drawable.reverso, R.drawable.charizard, "charizard")));

        Collections.shuffle(cartaArrayList);

        CartaAdapter cartaAdapter = new CartaAdapter(cartaArrayList, this);
        RecyclerView rvCartas = findViewById(R.id.rvCartas);
//        felicitacion = findViewById(R.id.partida_ganada);
//        felicitacion.setVisibility(View.INVISIBLE);
        botonSalir();
        nuevaPartida();
        toggleCronometro();

        rvCartas.setLayoutManager(new GridLayoutManager(this,4));
        rvCartas.setAdapter(cartaAdapter);
    }

    public void botonSalir(){
        Button btn_salir = findViewById(R.id.btn_salir);
        Log.i("Debug", "Se ha pulsado el boton de salir");
        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAndRemoveTask();
            }
        });
    }

    public void nuevaPartida(){
        Log.i("Debug", "Se ha pulsado el boton de nueva partida");
        Button btn_nueva_partida = findViewById(R.id.btn_nuevaPartida);
        btn_nueva_partida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generarNuevaPartida();
            }
        });
    }

    public void toggleCronometro(){
        //se pone por defecto un cronometro invisible
        ToggleButton tg_cronometro = findViewById(R.id.toggleButton);
        cronometro = findViewById(R.id.cronometro);
        cronometro.setVisibility(View.INVISIBLE);

        tg_cronometro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(tg_cronometro.isChecked()){
                    Log.i("Debug", "Cronometro activado");
                    cronometro.setBase(SystemClock.elapsedRealtime());
                    cronometro.setVisibility(View.VISIBLE);
                    cronometro.start();
                }else{
                    Log.i("Debug", "Cronometro desactivado");
                    cronometro.setVisibility(View.INVISIBLE);
                    cronometro.setBase(SystemClock.elapsedRealtime());
                    cronometro.stop();
                }
            }
        });
    }

    private void generarNuevaPartida() {
        // Se pone a cero las parejas encontradas así como el cronometro
        parejasHechas = 0;
        cronometro.setBase(SystemClock.elapsedRealtime());
        cronometro.stop();
        cronometro.setBase(SystemClock.elapsedRealtime());
        cronometro.start();

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
        for (Carta carta : cartaArrayList) {
            carta.getIvCara().setVisibility(View.INVISIBLE);
            carta.getIvReverso().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Carta carta = cartaArrayList.get(position);

        if (!this.getPuedeVoltear() || carta.isParejaEncontrada()) {
            return;
        }
        
        Log.i("Debug", "Se ha seleccionado una carta");

        cartasSeleccionadas.add(carta);

        String acierto = getResources().getString(R.string.toast_acierto);
        String fallo = getResources().getString(R.string.toast_diferencia);

        Log.i("Debug", "Carta añadida: " + carta.getValor());
        Log.i("Debug", "Tamaño de cartasSeleccionadas: " + cartasSeleccionadas.size());

        if(cartasSeleccionadas.size()==2){
            Log.i("Debug", "He entrado en el if de seleccionar dos cartas");
            if(cartasSeleccionadas.get(0).getValor().equals(cartasSeleccionadas.get(1).getValor())){
                Log.i("Debug", "Son dos cartas iguales");
                Toast.makeText(view.getContext(), acierto,Toast.LENGTH_SHORT).show();
                parejasHechas++;
                cartasSeleccionadas.get(0).setParejaEncontrada(true);
                cartasSeleccionadas.get(1).setParejaEncontrada(true);

                if (parejasHechas == 8){
                    //felicitacion.setVisibility(View.VISIBLE);
                    Log.i("Debug", "Se han encontrado todas las parejas");
                    //felicitacion.setVisibility(View.INVISIBLE);
                    generarNuevaPartida();
                }
                cartasSeleccionadas.clear();
            } else{

                Log.i("Debug", "Son dos cartas diferentes");
                this.setPuedeVoltear(false);
                Toast.makeText(view.getContext(), fallo,Toast.LENGTH_SHORT).show();

                MainActivity activity = this;

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        activity.setPuedeVoltear(true);
                        for (Carta c : cartasSeleccionadas) {
                            ImageView cara = c.getIvCara();
                            ImageView reverso = c.getIvReverso();

                            if (cara != null && reverso != null) {
                                cara.setVisibility(View.INVISIBLE);
                                reverso.setVisibility(View.VISIBLE);
                            } else {
                                Log.e("Debug", "No se encontraron las vistas con los IDs proporcionados.");
                            }
                        }
                        cartasSeleccionadas.clear();
                    }
                }, 1000);
            }
        }
    }
}