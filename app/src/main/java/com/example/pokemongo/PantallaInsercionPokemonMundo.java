package com.example.pokemongo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PantallaInsercionPokemonMundo extends AppCompatActivity {
EditText txtNombre,txtTipo,txtX,txtY;
Button insertar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_insercion_pokemon_mundo);

        txtNombre = findViewById(R.id.txtNombreMundo);
        txtTipo = findViewById(R.id.txtTipoMundo);
        txtX = findViewById(R.id.txtXMundo);
        txtY = findViewById(R.id.txtYMundo);
        insertar = (Button) findViewById(R.id.bt_insertar_pokemon);

        //guardar pokemon
    }

    private void limpiar(){
    txtNombre.setText("");
    txtTipo.setText("");
    txtX.setText("");
    txtY.setText("");

    }


    public void onClick(View v){

        //me aseguro de que los datos sen correctos
        boolean datoCorrectos = true;
        if( txtNombre.getText().toString().compareTo("") == 0){
            Toast.makeText(getApplicationContext(), "El nombre no puede estar vacío y tiene que ser un String", Toast.LENGTH_SHORT).show();
            datoCorrectos = false;
        }
        if( txtTipo.getText().toString().compareTo("") == 0){
            Toast.makeText(getApplicationContext(), "El tipo no puede estar vacío y tiene que ser un String", Toast.LENGTH_SHORT).show();
            datoCorrectos = false;
        }
        if( txtX.getText().toString().compareTo("") == 0){
            Toast.makeText(getApplicationContext(), "La coordenada X no puede estar vacía y tiene que ser un Double", Toast.LENGTH_SHORT).show();
            datoCorrectos = false;
        }
        if( txtY.getText().toString().compareTo("") == 0){
            Toast.makeText(getApplicationContext(), "La coordenada Y no puede estar vacía y tiene que ser un Double", Toast.LENGTH_SHORT).show();
            datoCorrectos = false;
        }

        //hago la insercion con los datos que está correctos
        if(datoCorrectos){
            DataBasePokemonsMundo db = new DataBasePokemonsMundo(PantallaInsercionPokemonMundo.this);

            String nombre = txtNombre.getText().toString();
            String tipo = txtTipo.getText().toString();
            double x = Double.valueOf(txtX.getText().toString());
            double y = Double.valueOf(txtY.getText().toString());

            long id = db.insertarPokemonMundo(nombre,tipo,x, y);

            //si el id es mayor que 0 el registro se ha insertado
            if(id > 0){
                Toast.makeText(getApplicationContext(), "Registro insertado", Toast.LENGTH_SHORT).show();
                limpiar();
            }else{
                Toast.makeText(getApplicationContext(), "Registro no insertado", Toast.LENGTH_SHORT).show();
            }
        }



    }
}