package com.example.mastermind;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InterfaceObjetoResultado} interface
 * to handle interaction events.
 * Use the {@link FragmentTablero#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTablero extends Fragment implements View.OnClickListener {


    ////////////COLORES DEL RESULTADO///////////
    //BLANCO==Acierto
    //ROJO==Emoji está en la combinacion Aleatoria
    //NEGRO==Fallo


    //atributos
    private InterfaceObjetoResultado mListener;
    private ImageButton ib1Combinacion, ib2Combinacion, ib3Combinacion, ib4Combinacion, ib1Fila, ib2Fila, ib3Fila, ib4Fila;
    private List<ImageButton> listaImageButtonCombinacionAleatoria = new ArrayList<>();
    private List<Integer> listaIntegerCombinacionAleatoria = new ArrayList<>();
    private List<Integer> listaIdBotonesFila = new ArrayList<>();
    private List<Integer> listaIdEmojisRecibidos = new ArrayList<>();
    private List<Resultado> listaObjetosResultado = new ArrayList<>();
    private int ibRecibido, contadorFila, puntuacionFila, puntuacionTotal, contadorImage, idDrawableib1Fila, idDrawableib2Fila, idDrawableib3Fila, idDrawableib4Fila, idJugador,contadorIntentos;
    private View fila1, fila2, fila3, fila4, fila5, fila6, fila7, fila8, fila9, fila10, filaActual;
    private ImageView ivCirculo1, ivCirculo2, ivCirculo3, ivCirculo4, ivCirculoActual;
    private TextView tvIntentos;
    private Resultado objetoResultado;
    private Context contexto;
    private View vista;
    private Intent intent;


    //constructor
    public FragmentTablero() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentTablero.
     */

    public static FragmentTablero newInstance() {
        FragmentTablero fragment = new FragmentTablero();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contadorFila = 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflo el layout de éste fragment
        View v = inflater.inflate(R.layout.fragment_tablero, container, false);
        vista = v;
        contexto = getActivity();
        obtenerReferencias(v);
        onClickBotonesFila();
        generaCombinacionAdivinar();
        mostrarCombinacionOculta();
        Toast.makeText(getActivity(), "COLOCA BIEN TU EMOJI", Toast.LENGTH_LONG).show();
        Toast.makeText(getActivity(), "NO PODRAS VOLVER A CAMBIARLOO!!!", Toast.LENGTH_LONG).show();
        tvIntentos.setVisibility(View.INVISIBLE);
        tvIntentos.setText(String.valueOf(contadorFila));
        return v;
    }


    //metodo que comprueba los aciertos de cada boton de la fila
    //BLANCO==Acierto
    //ROJO==Emoji está en la combinacion
    //NEGRO==Fallo

    //metodo que comprueba si el emoji existe en la combinacion, y su posicion en la fila
    //y asigna el drawable a las imagen view, comprueba basicamente los aciertos
    public void asignarAciertosImageView() {
        if (listaIdEmojisRecibidos.size() == 4) {
            contadorImage = 0;
            puntuacionFila = 0;
            tvIntentos.setVisibility(View.VISIBLE);
            //cada vez que entra en el metodo recojo los nuevos tag de cada boton
            obtenerListaTags();
            ivCirculoActual = devuelveCirculoActual(contadorImage);
            for (int i = 0; i < listaIdBotonesFila.size(); i++) {
                int num = listaIdBotonesFila.get(i);
                if (listaIntegerCombinacionAleatoria.contains(num)) {
                    if (listaIntegerCombinacionAleatoria.get(i) == num) {
                        ivCirculoActual.setImageResource(R.drawable.circuloblanco16);
                        puntuacionTotal += 10;
                        puntuacionFila += 10;
                    } else {
                        ivCirculoActual.setImageResource(R.drawable.circulorojo16);
                    }
                }
                contadorImage++;
                ivCirculoActual = devuelveCirculoActual(contadorImage);
            }
            ///compruebo si se ha finalizado el juego, si no se finalizo ,actualizo todos los datos para la siguiente fila
            finJuego();
            contadorFila++;
            contadorIntentos++;
            offOnClick();
            filaActual = devuelveFilaActual(contadorFila);
            obtenerNuevasReferencias();
            onClickBotonesFila();
            tvIntentos.setText(String.valueOf(contadorFila));
        } else {
            Toast.makeText(getActivity(), "No hay 4 EMOJIS!!!: ", Toast.LENGTH_SHORT).show();
        }
    }

    //cuando esta clickado el checkbox de comprobar,compruebo los aciertos y realizo las operaciones necesarias
    public void comprobarAciertos() {
        asignarAciertosImageView();
    }

    public void finJuego() {
        if (contadorFila == 10 || puntuacionFila == 40) {
            mostrarCombinacionVerdadera();
            if (contadorFila == 10) {
                Toast.makeText(getActivity(), "HAS AGOTADO LOS INTENTOS", Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), "OTRA VEZ SERAAA!!!", Toast.LENGTH_LONG).show();
            } else if (puntuacionFila == 40) {
                Toast.makeText(getActivity(), "FELICIDADES CAMPEOOON!!!", Toast.LENGTH_LONG).show();
            }
            contadorFila = 0;
            cuadroDialogo(contexto);
        }
    }

    //cuando acaba la partida creo el objeto con los datos para mostrarlo en el ranking
    public void datosFinJuego() {
        //fecha y hora actual
        String date = new SimpleDateFormat("dd-MM-yy").format(new Date());
        objetoResultado = new Resultado(puntuacionTotal, contadorIntentos, date);
        ClaseResultadoRanking.anhadirJugador(objetoResultado);
        intent = new Intent(getActivity(), ActivityRanking.class);
        startActivity(intent);
        // mListener.recibirObjetoResultado(objetoResultado);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InterfaceObjetoResultado) {
            mListener = (InterfaceObjetoResultado) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement InterfaceObjetoResultado");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //obtengo las referencias de cada botón y los meto en una lista
    public void obtenerReferencias(View v) {
        //botones Combinación Aleatoria
        ib1Combinacion = v.findViewById(R.id.ib1Combinacion);
        listaImageButtonCombinacionAleatoria.add(ib1Combinacion);
        ib2Combinacion = v.findViewById(R.id.ib2Combinacion);
        listaImageButtonCombinacionAleatoria.add(ib2Combinacion);
        ib3Combinacion = v.findViewById(R.id.ib3Combinacion);
        listaImageButtonCombinacionAleatoria.add(ib3Combinacion);
        ib4Combinacion = v.findViewById(R.id.ib4Combinacion);
        listaImageButtonCombinacionAleatoria.add(ib4Combinacion);
        //filas
        fila1 = v.findViewById(R.id.fila1);
        fila2 = v.findViewById(R.id.fila2);
        fila3 = v.findViewById(R.id.fila3);
        fila4 = v.findViewById(R.id.fila4);
        fila5 = v.findViewById(R.id.fila5);
        fila6 = v.findViewById(R.id.fila6);
        fila7 = v.findViewById(R.id.fila7);
        fila8 = v.findViewById(R.id.fila8);
        fila9 = v.findViewById(R.id.fila9);
        fila10 = v.findViewById(R.id.fila10);
        filaActual = devuelveFilaActual(contadorFila);
        //botones de cada fila
        ib1Fila = filaActual.findViewById(R.id.ib1Fila);
        ib2Fila = filaActual.findViewById(R.id.ib2Fila);
        ib3Fila = filaActual.findViewById(R.id.ib3Fila);
        ib4Fila = filaActual.findViewById(R.id.ib4Fila);
        //imagen view de las filas
        ivCirculo1 = filaActual.findViewById(R.id.ivCirculo1Fila);
        ivCirculo2 = filaActual.findViewById(R.id.ivCirculo2Fila);
        ivCirculo3 = filaActual.findViewById(R.id.ivCirculo3Fila);
        ivCirculo4 = filaActual.findViewById(R.id.ivCirculo4Fila);
        //text view de las filas
        tvIntentos = filaActual.findViewById(R.id.tvNumeroFila);
    }

    //al cambiar la fila obtengo de nuevo las referencias a esa vista y reinicio las listas
    public void obtenerNuevasReferencias() {

        filaActual = devuelveFilaActual(contadorFila);
        //botones de cada fila
        ib1Fila = filaActual.findViewById(R.id.ib1Fila);
        ib2Fila = filaActual.findViewById(R.id.ib2Fila);
        ib3Fila = filaActual.findViewById(R.id.ib3Fila);
        ib4Fila = filaActual.findViewById(R.id.ib4Fila);
        //imagen view de las filas
        ivCirculo1 = filaActual.findViewById(R.id.ivCirculo1Fila);
        ivCirculo2 = filaActual.findViewById(R.id.ivCirculo2Fila);
        ivCirculo3 = filaActual.findViewById(R.id.ivCirculo3Fila);
        ivCirculo4 = filaActual.findViewById(R.id.ivCirculo4Fila);
        //tv de las filas
        tvIntentos = filaActual.findViewById(R.id.tvNumeroFila);
        //renuevo la lista de los Id de los botones
        listaIdBotonesFila = new ArrayList<>();
        listaIdEmojisRecibidos = new ArrayList<>();
    }

    //quito el onClick de los botones de la fila una vez que se gasta el intento
    public void offOnClick() {
        ib1Fila.setClickable(false);
        ib2Fila.setClickable(false);
        ib3Fila.setClickable(false);
        ib4Fila.setClickable(false);
    }

    //método que devuelve el id del drawable de cada image button para la combinacion aleatoria
    public int devuelveIdEmojiCombinacion(int numero) {
        switch (numero) {
            case 1:
                return R.drawable.enamorado32;
            case 2:
                return R.drawable.guinio32;
            case 3:
                return R.drawable.guay32;
            case 4:
                return R.drawable.lengua32;
            case 5:
                return R.drawable.lloron32;
            case 6:
                return R.drawable.enojado32;
        }
        return -1;
    }

    //método que devuelve el id del drawable de cada image button recibido del fragment emoji para insertar en la fila
    public int devuelveIdElegidoFragmentEmoji(int idImageButton) {
        switch (idImageButton) {
            case R.id.ibEnamorado:
                return R.drawable.enamorado32;
            case R.id.ibGuinio:
                return R.drawable.guinio32;
            case R.id.ibGuay:
                return R.drawable.guay32;
            case R.id.ibLengua:
                return R.drawable.lengua32;
            case R.id.ibLloron:
                return R.drawable.lloron32;
            case R.id.ibEnojado:
                return R.drawable.enojado32;
        }
        return -1;
    }

    //selecciono la fila de intentos que estoi actualmente, irá aumentando con el contador
    public View devuelveFilaActual(int contador) {
        switch (contador) {
            case 1:
                return fila1;
            case 2:
                return fila2;
            case 3:
                return fila3;
            case 4:
                return fila4;
            case 5:
                return fila5;
            case 6:
                return fila6;
            case 7:
                return fila7;
            case 8:
                return fila8;
            case 9:
                return fila9;
            case 10:
                return fila10;
        }
        return null;
    }

    //Implementar onClick a los botones
    public void onClickBotonesFila() {
        ib1Fila.setOnClickListener(this);
        ib2Fila.setOnClickListener(this);
        ib3Fila.setOnClickListener(this);
        ib4Fila.setOnClickListener(this);
    }

    //genera la combinación aleatoria de 4 emojis que hay que adivinar, los meto en una lista
    public List generaCombinacionAdivinar() {
        int numero;
        int idEmoji;
        int contador = 0;
        while (contador != 4) {
            numero = (int) (Math.random() * 6 + 1);
            idEmoji = devuelveIdEmojiCombinacion(numero);
            if (!listaIntegerCombinacionAleatoria.contains(idEmoji)) {
                contador++;
                listaIntegerCombinacionAleatoria.add(idEmoji);
            }
        }
        return listaIntegerCombinacionAleatoria;
    }

    //Metodo que al final del juego muestra la combinacion que habia que adivinar
    public void mostrarCombinacionVerdadera() {
        ImageButton imageButton;
        for (int i = 0; i < listaImageButtonCombinacionAleatoria.size(); i++) {
            imageButton = listaImageButtonCombinacionAleatoria.get(i);
            imageButton.setImageResource(listaIntegerCombinacionAleatoria.get(i));
        }
    }

    //metodo que oculta la verdadera combinacion aleatoria
    public void mostrarCombinacionOculta() {
        ImageButton imageButton;
        for (int i = 0; i < listaImageButtonCombinacionAleatoria.size(); i++) {
            imageButton = listaImageButtonCombinacionAleatoria.get(i);
            imageButton.setImageResource(R.drawable.pregunta32);
        }
    }

    //recibo el botón pulsado del otro fragment y lo meto en una variable para trabajar con el
    public void recibeImageButton(int idImageButton) {
        ibRecibido = idImageButton;
    }

    //metodo que me devuelve una lista de tags
    public void obtenerListaTags() {
        //tag de los botones de cada fila
        idDrawableib1Fila = recibirIdTag(ib1Fila);
        listaIdBotonesFila.add(idDrawableib1Fila);
        idDrawableib2Fila = recibirIdTag(ib2Fila);
        listaIdBotonesFila.add(idDrawableib2Fila);
        idDrawableib3Fila = recibirIdTag(ib3Fila);
        listaIdBotonesFila.add(idDrawableib3Fila);
        idDrawableib4Fila = recibirIdTag(ib4Fila);
        listaIdBotonesFila.add(idDrawableib4Fila);
    }

    //metodo que devuelve la imagen view actual para rellenar de un circulo
    public ImageView devuelveCirculoActual(int contador) {
        switch (contador) {
            case 0:
                return ivCirculo1;
            case 1:
                return ivCirculo2;
            case 2:
                return ivCirculo3;
            case 3:
                return ivCirculo4;
        }
        return null;
    }

    //recibo el id del tag guardado en el image button
    public int recibirIdTag(ImageButton imageButton) {
        int idDrawableTag = (Integer) imageButton.getTag();
        return idDrawableTag;
    }

    //asigno a los botones de la fila de este fragment, el ID del drawable del boton pulsado en el fragment emoji
    @Override
    public void onClick(View v) {
        ImageButton imageButton = (ImageButton) v;
        int asignarIDRecibido = devuelveIdElegidoFragmentEmoji(ibRecibido);

        if (asignarIDRecibido != -1) {
            if (!listaIdEmojisRecibidos.contains(asignarIDRecibido) && imageButton.getTag() == null) {
                listaIdEmojisRecibidos.add(asignarIDRecibido);
                imageButton.setImageResource(asignarIDRecibido);
                //guardo en una etiqueta el ID del drawable asignado a cada boton de la fila, que es el mismo ID elegido en el otro fragmentEmoji
                imageButton.setTag(asignarIDRecibido);
            } else {
                Toast.makeText(getActivity(), "YA EXISTE ESE EMOJI!!!!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "ELIGE UN EMOJIIII!!!!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <mastermind1>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface InterfaceObjetoResultado {

        void recibirObjetoResultado(Resultado resultado);
    }

    //salta el cuadro de dialogo para ir al ranking
    public void cuadroDialogo(final Context contexto) {
        final Dialog dialogo = new Dialog(contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(false);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.cuadro_dialogo);

        ImageButton ibAceptar = dialogo.findViewById(R.id.ibAceptar);
        ibAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogo.dismiss();
                datosFinJuego();
            }
        });
        dialogo.show();
    }

}
