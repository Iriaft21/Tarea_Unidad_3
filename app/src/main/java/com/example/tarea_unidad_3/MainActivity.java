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
    private ArrayList<Carta> cartasSeleccionadas = new ArrayList<>();
    private int parejasHechas;
    private  ImageView felicitacion;
    private boolean puedeVoltear = true;
    private Chronometer cronometro;
    private RecyclerView rvCartas;
    private CartaAdapter cartaAdapter;

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

        //se barajan las cartas, poniendolas en posiciones aleatorias
        Collections.shuffle(cartaArrayList);

        //se crea el adaptador, se busca el RecyclerView y la imagen de partida ganada se pone invisble
        cartaAdapter = new CartaAdapter(cartaArrayList, this);
        rvCartas = findViewById(R.id.rvCartas);
        felicitacion = findViewById(R.id.partida_ganada);
        felicitacion.setVisibility(View.INVISIBLE);
        //metodo asociado a botones o toggleButton
        botonSalir();
        nuevaPartida();
        toggleCronometro();

        //se escoge el formato del RecyclerView y se le pasa el adaptador
        rvCartas.setLayoutManager(new GridLayoutManager(this,4));
        rvCartas.setAdapter(cartaAdapter);
    }

    public void botonSalir(){
        //se busca el boton de salir en el xml
        Button btn_salir = findViewById(R.id.btn_salir);
        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hace que se salga de la aplicacion
                finishAndRemoveTask();
            }
        });
    }

    public void nuevaPartida(){
        //se busca el boton de nueva partida en el xml
        Button btn_nueva_partida = findViewById(R.id.btn_nuevaPartida);
        btn_nueva_partida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //se llama a otro método
                generarNuevaPartida();
            }
        });
    }

    public void toggleCronometro(){
        //se busca el toggleButton y el cronometro en el xml
        ToggleButton tg_cronometro = findViewById(R.id.toggleButton);
        cronometro = findViewById(R.id.cronometro);
        //cronometro por defecto invisible
        cronometro.setVisibility(View.INVISIBLE);

        tg_cronometro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //sei se activa el toggleButton
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

    private void generarNuevaPartida() {
        // Se pone a cero las parejas encontradas y se vacia el ArrayList que contiene las cartas seleccionadas
        cartasSeleccionadas.clear();
        parejasHechas = 0;

        //se pone a cero el cronometro
        cronometro.setBase(SystemClock.elapsedRealtime());
        cronometro.stop();
        cronometro.setBase(SystemClock.elapsedRealtime());
        cronometro.start();

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

    @Override
    public void onItemClick(View view, int position) {
        Carta carta = cartaArrayList.get(position);

        //se comrpueba que la carta se pueda voltear, que se pueda clicar, que este ya emparejada o que ya este seleccionada de antes en esa tanda
        if (!getPuedeVoltear() || !CartaAdapter.puedeClicar || carta.isParejaEncontrada() || cartasSeleccionadas.contains(carta)) {
            Log.i("Error", "Click ignorado por condiciones no cumplidas");
            CartaAdapter.puedeClicar = true;
            return;
        }
        //se añade al arrayList de cartas seleccionadas
        cartasSeleccionadas.add(carta);

        //se extrae de los recursos los strings para los Toast
        String acierto = getResources().getString(R.string.toast_acierto);
        String fallo = getResources().getString(R.string.toast_diferencia);

        //se comprueba que el tamaño sea siempre dos
        if(cartasSeleccionadas.size()==2) {
            //comprobamos que ambas cartas tenga el mismo valor
            if (cartasSeleccionadas.get(0).getValor().equals(cartasSeleccionadas.get(1).getValor())) {
                aciertoPareja(view, acierto);
            } else {
                falloPareja(view, fallo);
            }
        } else{
            CartaAdapter.puedeClicar = true;
        }
    }

    public void aciertoPareja(View view, String acierto){
        //se envia un Toast avisando del acierto
        Toast.makeText(view.getContext(), acierto, Toast.LENGTH_SHORT).show();
        parejasHechas++;

        //se anotan las cartas como ya emparejadas
        cartasSeleccionadas.get(0).setParejaEncontrada(true);
        cartasSeleccionadas.get(1).setParejaEncontrada(true);

        //se vacia el ArrayList
        cartasSeleccionadas.clear();

        //se estan las 8 parejas encontradas se llama al metodo finalizarJuego
        if (parejasHechas == 8) {
            finalizarJuego();
        }
        CartaAdapter.puedeClicar= true;
    }

    public void falloPareja(View view, String fallo){
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
                setPuedeVoltear(true);
                CartaAdapter.puedeClicar = true;
            }
        }, 1000);

    }

    public void finalizarJuego(){
        //handler para que salga la felicitación después de unos segundos
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                felicitacion.setVisibility(View.VISIBLE);
                //otro handler para que la felicitacion permanezca visible durante unos segundos
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //se vuelve a hacer la felicitacion invisible y se genera nueva partida
                        felicitacion.setVisibility(View.INVISIBLE);
                        try {
                            generarNuevaPartida();
                        } catch (Exception e) {
                            Log.e("Error", "Error al generar nueva partida: " + e.getMessage());
                        }
                        CartaAdapter.puedeClicar = true;
                    }
                }, 2000);
            }
        }, 2000);
    }
}