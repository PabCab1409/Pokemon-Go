package com.example.pokemongo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBasePokemonsMundo extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "pokemonsMundo.db";
    public static final String TABLE_POKEMONS_MUNDO = "PokemonsMundo";

    public DataBasePokemonsMundo(@Nullable Context context) {
        super(context,DATABASE_NOMBRE,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_POKEMONS_MUNDO + "(nombre TEXT PRIMARY KEY, tipo TEXT, coordenadaX DOUBLE, coordenadaY DOUBLE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //cuando quiera modificar la estrcutura de mi base, este metodo será usado
        db.execSQL("DROP TABLE " + TABLE_POKEMONS_MUNDO);
        onCreate(db);
    }

    public long insertarPokemonMundo(String nombre, String tipo, double x, double y){
      long id = 0;
       try{
           SQLiteDatabase  db = this.getWritableDatabase();

           //agregar
           ContentValues values = new ContentValues();
           values.put("nombre",nombre);
           values.put("tipo",tipo);
           values.put("coordenadaX",x);
           values.put("coordenadaY",y);

           //insert
           id = db.insert(TABLE_POKEMONS_MUNDO,null,values);
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

        cursor = db.rawQuery("SELECT * FROM " + TABLE_POKEMONS_MUNDO , null);

        //recorro consulta
        if(cursor.moveToFirst()){
            do{
                pokemon = new Pokemon();
                pokemon.setNombre(cursor.getString(0));
                pokemon.setTipo(cursor.getString(1));
                pokemon.setX(cursor.getDouble(2));
                pokemon.setY(cursor.getDouble(3));
                listaPokemons.add(pokemon);
            }while(cursor.moveToNext());
        }

        cursor.close();

        return listaPokemons;
    }

}
