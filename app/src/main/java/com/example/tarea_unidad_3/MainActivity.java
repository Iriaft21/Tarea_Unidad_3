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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity /*implements CartaAdapter.OnItemClickListener*/{

    //atributos de la clase
    private ArrayList<Carta> cartaArrayList;
    public  ImageView felicitacion;
    public Chronometer cronometro;
    private FragmentoCartas fragmentoCartas;

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

        felicitacion = findViewById(R.id.partida_ganada);
        felicitacion.setVisibility(View.INVISIBLE);
        cronometro = findViewById(R.id.cronometro);

        //se crean las cartas
        cartaArrayList = new ArrayList<>(Arrays.asList(
                new Carta(R.drawable.reverso, R.drawable.tinkaton, "tinkaton",this),
                new Carta(R.drawable.reverso, R.drawable.tinkaton, "tinkaton", this),
                new Carta(R.drawable.reverso, R.drawable.phantump, "phantump", this),
                new Carta(R.drawable.reverso, R.drawable.phantump, "phantump", this),
                new Carta(R.drawable.reverso, R.drawable.cramorant, "cramorant", this),
                new Carta(R.drawable.reverso, R.drawable.cramorant, "cramorant", this),
                new Carta(R.drawable.reverso, R.drawable.deoxys, "deoxys", this),
                new Carta(R.drawable.reverso, R.drawable.deoxys, "deoxys", this),
                new Carta(R.drawable.reverso, R.drawable.lapras, "lapras", this),
                new Carta(R.drawable.reverso, R.drawable.lapras, "lapras", this),
                new Carta(R.drawable.reverso, R.drawable.houndoom, "houndoom", this),
                new Carta(R.drawable.reverso, R.drawable.houndoom, "houndoom", this),
                new Carta(R.drawable.reverso, R.drawable.ninetales, "ninetales", this),
                new Carta(R.drawable.reverso, R.drawable.ninetales, "ninetales", this),
                new Carta(R.drawable.reverso, R.drawable.charizard, "charizard", this),
                new Carta(R.drawable.reverso, R.drawable.charizard, "charizard", this)));

        //se barajan las cartas, poniendolas en posiciones aleatorias
        Collections.shuffle(cartaArrayList);

        fragmentoCartas = FragmentoCartas.newInstance(cartaArrayList);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView, fragmentoCartas);
        fragmentTransaction.commit();

        //metodos asociado a botones o toggleButton
        botonSalir();
        nuevaPartida();
        toggleCronometro();
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
                fragmentoCartas.generarNuevaPartida();

            }
        });
    }

    public void toggleCronometro(){
        //se busca el toggleButton y el cronometro en el xml
        ToggleButton tg_cronometro = findViewById(R.id.toggleButton);
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