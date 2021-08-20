package com.example.mastermind;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;


import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityRanking extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Resultado> listaResultados;
    private AdaptadorResultados adaptadorResultados;
    private SimpleDateFormat sdf = new SimpleDateFormat();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        Gson gson = new Gson();
        Resultado objetoResultado = gson.fromJson(getIntent().getStringExtra("objetoResultado"), Resultado.class);
        listaResultados=ClaseResultadoRanking.getListaResultadoRanking();
        asignarId();

        //obtengo la referencia del recyclerView
        recyclerView = findViewById(R.id.rvListaResultados);
        //decido que muestre los items en vertical
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //creo una instancia del adaptador y le paso la lista de datos que quiero que se muestre
        adaptadorResultados = new AdaptadorResultados(listaResultados);

        //le asigno el adaptador
        recyclerView.setAdapter(adaptadorResultados);

        //le implemento el listener para cuando hago click en un item me muestre los datos
        adaptadorResultados.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hay que indicarle el nombre de la clase
                Toast.makeText(ActivityRanking.this, "Has clickado: "
                        + recyclerView.getChildAdapterPosition(v), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void asignarId(){
        for (int i = 0; i <listaResultados.size() ; i++) {
            int id=i+1;
            listaResultados.get(i).setIdJugador(id);
        }
    }

    private void iniciarDatosEjemplo() {
            listaResultados.add(new Resultado(1, 80, 7, "02-02-19"));
            listaResultados.add(new Resultado(2, 70, 5, "02-05-19"));
            listaResultados.add(new Resultado(3, 60, 3, "03-04-19"));
            listaResultados.add(new Resultado(4, 50, 4, "02-02-19"));
            listaResultados.add(new Resultado(2, 40, 2, "01-07-19"));
            listaResultados.add(new Resultado(4, 30, 3, "09-04-19"));

    }
}
