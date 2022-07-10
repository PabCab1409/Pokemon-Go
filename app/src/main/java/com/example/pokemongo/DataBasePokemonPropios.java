package com.example.pokemongo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBasePokemonPropios extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "pokemonsPropios.db";
    private static final String TABLE_POKEMONS_POPIOS = "PokemonsPropios";

    public DataBasePokemonPropios(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_POKEMONS_POPIOS + "(nombre TEXT PRIMARY KEY, tipo TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //cuando quiera modificar la estrcutura de mi base, este metodo será usado
        db.execSQL("DROP TABLE " + TABLE_POKEMONS_POPIOS);
        onCreate(db);
    }

    public long insertarPokemonPropio(String nombre, String tipo){
        long id = 0;
        try{
            SQLiteDatabase  db = this.getWritableDatabase();

            //agregar
            ContentValues values = new ContentValues();
            values.put("nombre",nombre);
            values.put("tipo",tipo);


            //insert
            id = db.insert(TABLE_POKEMONS_POPIOS,null,values);
        }catch (Exception e){
            e.toString();
        }
        return id;
    }

    public ArrayList<Pokemon> mostrarPokemons(){
        SQLiteDatabase db = this.getWritableDatabase();

        //objetos para mapear
        ArrayList<Pokemon> listaPokemons = new ArrayList<>();
        Pokemon pokemon = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_POKEMONS_POPIOS , null);

        //recorro consulta
        if(cursor.moveToFirst()){
            do{
                pokemon = new Pokemon();
                pokemon.setNombre(cursor.getString(0));
                pokemon.setTipo(cursor.getString(1));
                listaPokemons.add(pokemon);
            }while(cursor.moveToNext());
        }

        cursor.close();
        return listaPokemons;
    }
}
