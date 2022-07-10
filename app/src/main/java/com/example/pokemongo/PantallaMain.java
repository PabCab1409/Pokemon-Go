package com.example.pokemongo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PantallaMain extends AppCompatActivity {
Button btCazar,btInsertar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btCazar = (Button) findViewById(R.id.bt_empezar_a_cazar);
        btInsertar = (Button) findViewById(R.id.bt_insertar_pokemon);

        //empiezo el juego
        btCazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaMain.this,PantallaJuego.class);
                startActivity(intent);
            }
        });

        //proporciono interfaz para añadir pokemon, creo base de datos pokemons mundo
        btInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cambio actividad
                Intent intent = new Intent(PantallaMain.this,PantallaInsercionPokemonMundo.class);
                startActivity(intent);
                //creo la base de datos
                DataBasePokemonsMundo dbPokemonsMundo = new DataBasePokemonsMundo(PantallaMain.this);
                SQLiteDatabase db = dbPokemonsMundo.getWritableDatabase();
            }
        });


    }

}