package com.example.tarea_unidad_3;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity implements CartaAdapter.OnItemClickListener{

    private ArrayList<Carta> cartaArrayList;
    private final ArrayList<Carta> cartasSeleccionadas = new ArrayList<>();
    private int parejasHechas;

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

        //asignacion random de imagenes y valores
        //extraes el nombre del elemento y lo pones como valor(?)

//        ArrayList<String> valoresCarta = new ArrayList<>(Arrays.asList(
//                "tinkaton", "ninetales", "phantump", "cramorant", "lapras", "ninetales", "houndoom", "deoxys",
//                "cramorant", "houdoom", "charizard", "tinkaton", "phantump", "charizard", "deoxys", "lapras"
//                ));



        cartaArrayList = new ArrayList<>(Arrays.asList(
                new Carta(R.drawable.reverso, R.drawable.tinkaton, "tinkaton"),
                new Carta(R.drawable.reverso, R.drawable.tinkaton, "tinkaton"),
                new Carta(R.drawable.reverso, R.drawable.phantump, "phantump"),
                new Carta(R.drawable.reverso, R.drawable.cramorant, "cramorant"),
                new Carta(R.drawable.reverso, R.drawable.deoxys, "deoxys"),
                new Carta(R.drawable.reverso, R.drawable.lapras, "lapras"),
                new Carta(R.drawable.reverso, R.drawable.deoxys, "deoxys"),
                new Carta(R.drawable.reverso, R.drawable.cramorant, "cramorant"),
                new Carta(R.drawable.reverso, R.drawable.houndoom, "houndoom"),
                new Carta(R.drawable.reverso, R.drawable.phantump, "phantump"),
                new Carta(R.drawable.reverso, R.drawable.charizard, "charizard"),
                new Carta(R.drawable.reverso, R.drawable.houndoom, "houndoom"),
                new Carta(R.drawable.reverso, R.drawable.ninetales, "ninetales"),
                new Carta(R.drawable.reverso, R.drawable.lapras, "lapras"),
                new Carta(R.drawable.reverso, R.drawable.ninetales, "ninetales"),
                new Carta(R.drawable.reverso, R.drawable.charizard, "charizard")));
        CartaAdapter cartaAdapter = new CartaAdapter(cartaArrayList, this);
        int idcara1 = cartaArrayList.get(0).getImagenCara();
        int idReverso1 = cartaArrayList.get(0).getImagenCara();
        int idcara3 = cartaArrayList.get(2).getImagenCara();
        int idReverso3 = cartaArrayList.get(2).getImagenCara();

        Log.i("Debug","id de la cara de la carta 1 " + idcara1 + " id del reverso " + idReverso1);
        Log.i("Debug","id de la cara de la carta 3 " + idcara3 + " id del reverso " + idReverso3);

        RecyclerView rvCartas = findViewById(R.id.rvCartas);

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
        //funcionalidad de nueva partida. Empezar todoo de nuevo
        Button btn_nueva_partida = findViewById(R.id.btn_nuevaPartida);
    }

    public void toggleCronometro(){
        ToggleButton tg_cronometro = findViewById(R.id.toggleButton);
        Chronometer cronometro = findViewById(R.id.cronometro);
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

    @Override
    public void onItemClick(View view, int position) {
        Log.i("Debug", "Se ha seleccionado una carta");
        Carta carta = cartaArrayList.get(position);
        cartasSeleccionadas.add(carta);

        String acierto = getResources().getString(R.string.toast_acierto);
        String fallo = getResources().getString(R.string.toast_diferencia);

        Log.i("Debug", "Carta añadida: " + carta.getValor());
        Log.i("Debug", "Tamaño de cartasSeleccionadas: " + cartasSeleccionadas.size());

        if(cartasSeleccionadas.size()==2){
            if(cartasSeleccionadas.get(0).getValor().equals(cartasSeleccionadas.get(1).getValor())){
                Toast.makeText(view.getContext(), acierto,Toast.LENGTH_SHORT).show();
                parejasHechas++;
            } else{
                Toast.makeText(view.getContext(), fallo,Toast.LENGTH_SHORT).show();
                for (Carta c : cartasSeleccionadas) {
                    int caraId = c.getImagenCara();
                    int reversoId = c.getImagenReverso();
                    Log.d("Debug", "Id de la cara de la carta" + caraId);
                    Log.d("Debug", "Id del reverso de la carta" + reversoId);

                    ImageView cara = findViewById(caraId);
                    ImageView reverso = findViewById(reversoId);

                    if (cara != null && reverso != null) {
                        cara.setVisibility(View.INVISIBLE);
                        reverso.setVisibility(View.VISIBLE);
                    } else {
                        Log.e("Debug", "No se encontraron las vistas con los IDs proporcionados.");
                    }
                }
            }
            cartasSeleccionadas.clear();
        }
    }
}