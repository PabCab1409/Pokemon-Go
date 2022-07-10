package com.example.pokemongo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PantallaListaPokemonPropios extends AppCompatActivity {
private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_lista_pokemon_propios);

        listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, obtenerPokemonsPropios());
        listView.setAdapter(adapter);

    }

    private ArrayList<String> obtenerPokemonsPropios(){
        ArrayList<String> pokemonsString = new ArrayList<>();
        DataBasePokemonPropios dbPokemonsPropios = new DataBasePokemonPropios(this);
        SQLiteDatabase db = dbPokemonsPropios.getWritableDatabase();
        ArrayList<Pokemon> listaPokemons = dbPokemonsPropios.mostrarPokemons();
        for (Pokemon p:
             listaPokemons) {
            pokemonsString.add(p.getNombre() + " " + p.getTipo());
        }
        return pokemonsString;
    }
}