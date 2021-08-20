package com.example.mastermind;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //atributos
    private Button btJugar,btnPreferencia;
    private Intent pantallaJuego,pantallaPreferencias;
    public ClaseResultadoRanking claseResultadoRanking=new ClaseResultadoRanking();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //obtengo la referencia al boton del xml
        btJugar=findViewById(R.id.btJugar);
        btnPreferencia=findViewById(R.id.btnPreferencia);

        //inicializo el intent de abrir la pantalla del juego
        pantallaJuego = new Intent(this,PantallaJuego.class);
        pantallaPreferencias = new Intent(this,PantallaPreferencias.class);
       //asigno el escuchador al boton para que me abra la pantalla del juego cuando pulso
        //el botón de jugar
        btJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //abre la pantalla del juego
              startActivity(pantallaJuego);
            }
        });

        btnPreferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //abre la pantalla preferencias
                startActivity(pantallaPreferencias);
            }
        });
    }

    //metodo de las preferencias
    public void saberPreferencias(){
        //objeto sharedPref
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        //usamos getBoolean....etc para recoger los datos del xml de sharedPref
        //boolean miniaturasPref = sharedPref.getBoolean("miniaturas", true);

        //editor para escribir en el xml de sharedPref
        SharedPreferences.Editor editor=sharedPref.edit();
        /*
        editor.putBoolean("jubilado",true);
        editor.putString("nombre","ana");
        editor.commit();*/

    }
}
