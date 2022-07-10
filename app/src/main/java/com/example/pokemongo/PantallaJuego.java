package com.example.pokemongo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PantallaJuego extends AppCompatActivity implements LocationListener {

    static final long TIEMPO_MIN = 10 * 1000 ; // 10 segundos
    static final long DISTANCIA_MIN = 1 ; // 1 metro
    static double y = 0;
    static double x = 0;
    static String nombre, tipo;

    String proveedor;
    Button verPokemons, cazar;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_juego);

        verPokemons = findViewById(R.id.button);
        cazar = findViewById(R.id.btCazar);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //criterio para obtener localizacion
        Criteria criterio = new Criteria();
        criterio .setCostAllowed( false );
        criterio .setAltitudeRequired( false );
        criterio .setAccuracy( Criteria . ACCURACY_FINE );
        proveedor = locationManager .getBestProvider( criterio , true );

        //pokedex
        verPokemons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PantallaListaPokemonPropios.class);
                startActivity(intent);
            }
        });
        cazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //si he insertado el pokemon correctamente, lo muestro, y lo elimino de la base de datos del mundo
                DataBasePokemonPropios db = new DataBasePokemonPropios(PantallaJuego.this);
                db.insertarPokemonPropio(nombre.toString(), tipo.toString());
                Toast.makeText(getApplicationContext(), "Has capturado " + nombre + " tipo " + tipo, Toast.LENGTH_SHORT).show();

                DataBasePokemonsMundo dbMundo = new DataBasePokemonsMundo(PantallaJuego.this);
                SQLiteDatabase dbWritable = dbMundo.getWritableDatabase();
                String[] args = new String[]{nombre,tipo};
                dbWritable.execSQL("DELETE FROM PokemonsMundo where nombre=? and tipo=?",args);
                /*
                String consulta = "DELETE FROM " + DataBasePokemonsMundo.TABLE_POKEMONS_MUNDO + " WHERE nombre = '"+nombre+"'" ;
                Cursor cursor = dbWritable.rawQuery(consulta, null);

                    while (cursor.moveToNext()) {
                        Toast.makeText(getApplicationContext(), "Se ha eliminado del mundo a " + nombre + " de tipo " + tipo, Toast.LENGTH_SHORT).show();

                    }

                 */
            }

        });


    }

    // Métodos del ciclo de vida de la actividad
    @Override
    protected void onResume () {
        super .onResume();
        if ( ActivityCompat. checkSelfPermission ( this , Manifest. permission . ACCESS_FINE_LOCATION ) !=
                PackageManager. PERMISSION_GRANTED && ActivityCompat . checkSelfPermission ( this ,
                Manifest . permission . ACCESS_COARSE_LOCATION ) != PackageManager . PERMISSION_GRANTED ) {
            return ;
        }
        locationManager.requestLocationUpdates( proveedor , TIEMPO_MIN , DISTANCIA_MIN , this );
    }
    @Override protected void onPause () {
        super.onPause();
        locationManager.removeUpdates( this );
    }
    // Métodos de la interfaz LocationListener
    @Override public void onLocationChanged ( Location location) {
      Toast.makeText(getApplicationContext(), "Nueva localizacion:\n" + muestraLocaliz(location), Toast.LENGTH_LONG).show();
        y = location.getLongitude();
        x = location.getLatitude();
      comprobarPokemonsCerca();
    }
    @Override public void onProviderDisabled ( String proveedor) {
        Toast.makeText(getApplicationContext(), "Proveedor deshabilitado:", Toast.LENGTH_SHORT).show();
    }
    @Override public void onProviderEnabled ( String proveedor) {
        Toast.makeText(getApplicationContext(), "Proveedor habilitado:", Toast.LENGTH_SHORT).show();
    }


    private String muestraLocaliz ( Location localizacion) {
        if (localizacion == null )
            return "Localización desconocida";
        else{
            return String.valueOf("Longitud -> " + y + "\nLatitud-> " + x);
        }
    }
    private void comprobarPokemonsCerca(){
        //compruebo si hay un pokemon cerca, y habilito boton cazar

        //obtengo los pokemons del mundo para conocer su ubicacion
        DataBasePokemonsMundo dbPokemonsMundo = new DataBasePokemonsMundo(this);
        SQLiteDatabase db = dbPokemonsMundo.getWritableDatabase();
        ArrayList<Pokemon> listaPokemons = dbPokemonsMundo.mostrarPokemons();

        for (Pokemon p: listaPokemons) {
            //creo un radio de accion en el que detecto si estoy cerca del pokemon
            if(p.getX() > (x-0.01) & p.getX() < (x+0.01) & p.getY() > (y-0.01) & p.getY() >  (y+0.01)){
                nombre = p.getNombre();
                Toast.makeText(getApplicationContext(), "Cerca hay un " + nombre, Toast.LENGTH_SHORT).show();
                tipo = p.getTipo();
                cazar.setEnabled(true);

                //si estoy lo suficientemente cerca como para cazarlo, activo el boton
                //if(p.getX() > (x-0.001) & p.getX() < (x+0.001) & p.getY() > (y-0.001) & p.getY() >  (y+0.001))
                //cazar.setEnabled(true);
            }else{
                cazar.setEnabled(false);
            }
        }
    }

}