package com.example.pokemongo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Pokedex extends AppCompatActivity {
    private ListView listview;
    private ArrayList<String> pokemons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokedex);

        listview = (ListView) findViewById(R.id.lvPokemons);

        //listo los pokemons
        DataBasePokemonPropios dbPokemonsPropio = new DataBasePokemonPropios(Pokedex.this);
        SQLiteDatabase db = dbPokemonsPropio.getWritableDatabase();
        dbPokemonsPropio.insertarPokemonPropio("Charizard","fuego");
        //pokemons = dbPokemonsPropio.mostrarPokemons();
        //pokemons.add(new Pokemon());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pokemons);


    }
}