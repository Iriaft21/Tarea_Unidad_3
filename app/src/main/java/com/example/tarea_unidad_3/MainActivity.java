package com.example.tarea_unidad_3;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity{

    //atributos de la clase


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

        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_navegacion);
        Menu menu = bottomNavigationView.getMenu();
        menu.add(Menu.NONE, 1, Menu.NONE, "Juego").setIcon(android.R.drawable.ic_menu_compass);
        menu.add(Menu.NONE, 2, Menu.NONE, "Estadisticas").setIcon(android.R.drawable.ic_menu_sort_by_size);
        menu.add(Menu.NONE, 3, Menu.NONE, "Ajustes").setIcon(android.R.drawable.ic_menu_manage);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                int id = item.getItemId();
                MainFragment mainFragment = null;
                EstadisticasFragment estadisticasFragment= null;
                AjustesFragment ajustesFragment = null;

                switch (id) {
                    case 1:
                        mainFragment = new MainFragment();
                        fragmentTransaction.replace(R.id.fragmentContainerView2, mainFragment);
                        break;
                    case 2:
                        estadisticasFragment = new EstadisticasFragment();
                        fragmentTransaction.replace(R.id.fragmentContainerView2, estadisticasFragment);
                        break;
                    case 3:
                        ajustesFragment = new AjustesFragment();
                        fragmentTransaction.replace(R.id.fragmentContainerView2, ajustesFragment);
                        break;
                    default:
                        return false;
                }
                fragmentTransaction.commitAllowingStateLoss();

                return true;
            }
        });
    }
}