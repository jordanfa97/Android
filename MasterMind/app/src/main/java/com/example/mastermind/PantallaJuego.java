package com.example.mastermind;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PantallaJuego extends AppCompatActivity implements  FragmentTablero.InterfaceObjetoResultado,FragmentEmoji.OnBotonEmojiListener {

    //ATRIBUTOS
    private Intent intent;
    private FragmentTablero fragmentTablero;
    private Resultado objetoResultado;
    private List<Resultado> listaObjetosResultado = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_juego);
        fragmentTablero = (FragmentTablero) getSupportFragmentManager().findFragmentById(R.id.fragmentTablero);
    }

    ////////IMPLEMENTAR EL MENU//////////
    //vamos a inflar el menu para que se pueda ver. sino, no se ve. Cogemos el menu_principal.xml, lo inflamos
    //y lo metemos en el menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_juego, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //segun el id q hayas pulsado
        switch (item.getItemId()) {

            case R.id.action_resultados:
                intent = new Intent(PantallaJuego.this, ActivityRanking.class);
                startActivity(intent);
                break;
            case R.id.action_preferencias:
                intent = new Intent(PantallaJuego.this, PantallaPreferencias.class);
                startActivity(intent);
                break;

            //En el caso que pulsemos en el icono de home iremos a la pantalla inicial
            case R.id.action_home:
                finish();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    //recibo el botón pulsado del fragment y se lo paso al otro fragment
    @Override
    public void onBotonEmoji(int idEmoji) {
        if (idEmoji != -1) {
            fragmentTablero.recibeImageButton(idEmoji);
        } else {
            fragmentTablero.comprobarAciertos();
        }

    }


    //recibo el objetoResultado del fragment y lo meto en una lista
    //si hubiera una base de datos es como deberia hacerse no con metodos estaticos
    @Override
    public void recibirObjetoResultado(Resultado resultado) {
        objetoResultado = resultado;
        listaObjetosResultado.add(objetoResultado);
        Gson gson = new Gson();
        String myJsonResultado = gson.toJson(objetoResultado);
        intent = new Intent(PantallaJuego.this, ActivityRanking.class);
        intent.putExtra("objetoResultado", myJsonResultado);
        startActivity(intent);
    }


}
