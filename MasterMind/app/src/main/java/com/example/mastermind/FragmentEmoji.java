package com.example.mastermind;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnBotonEmojiListener} interface
 * to handle interaction events.
 * Use the {@link FragmentEmoji#newInstance} factory method to
 * create an instance of this fragment.
 */

//implemento la interface 'OnClickListener' a la clase y así ya todos los botones tienen implementado el OnClick
public class FragmentEmoji extends Fragment implements View.OnClickListener {

    //declaro la instancia del interface y los botones
    private OnBotonEmojiListener mListener;
    private ImageButton ibEnamorado, ibEnojado, ibGuinio, ibLloron, ibLengua, ibGuay;
    private CheckBox cbAciertos;

    //constructor vacío
    public FragmentEmoji() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentEmoji.
     */
    // los fragments no se instancian con el constructor se instancian a través de éste método, si
    //hubiera parámetros se los métemos, éste método ya usa el constructor y nos devuelve el fragment
    //instanciado, ésto es debido para pasarle los argumentos nada más crearse el fragment si los hubiere
    public static FragmentEmoji newInstance() {
        FragmentEmoji fragment = new FragmentEmoji();
        return fragment;
    }

    //para inicializar el fragment con listas y demás, pero sin ser vistas es decir sin ser views
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //con éste método debemos inflar el xml e instanciar las vistas
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflamos el layout del fragment, los parametros son el layout a inflar, su contenedor que es
        // la vista padre donde queremos inflarlo y, en los fragments en el inflador el último parametro
        // siempre es 'false'
        View v = inflater.inflate(R.layout.fragment_emoji, container, false);
        obtenerReferencias(v);
        implementarListener();
        onComprobarAciertos();
        return v;
    }

    //cuando se haga click en cualquier botón obtengo su id y se lo paso a la activity
    @Override
    public void onClick(View v) {
        int idEmoji = v.getId();
        mListener.onBotonEmoji(idEmoji);

    }

    //Los fragment no tienen contexto entonces debemos indicarle donde encontrar las referencias
    //a las vistas dentro de la vista inflada, así ya nos reconoce el metodo findViewById
    public void obtenerReferencias(View v) {
        ibEnamorado = v.findViewById(R.id.ibEnamorado);
        ibEnojado = v.findViewById(R.id.ibEnojado);
        ibGuinio = v.findViewById(R.id.ibGuinio);
        ibLloron = v.findViewById(R.id.ibLloron);
        ibLengua = v.findViewById(R.id.ibLengua);
        ibGuay = v.findViewById(R.id.ibGuay);
        cbAciertos = v.findViewById(R.id.cbAciertos);

    }

    //Implementar listener a los botones
    public void implementarListener() {
        ibEnamorado.setOnClickListener(this);
        ibEnojado.setOnClickListener(this);
        ibGuinio.setOnClickListener(this);
        ibLloron.setOnClickListener(this);
        ibLengua.setOnClickListener(this);
        ibGuay.setOnClickListener(this);

    }

    //con éste método le digo a la activity que le diga al fragment que mire cuantos aciertos hay
    public void onComprobarAciertos() {
        //asignamos el escuchador que comprueba si el checkbox está activado o no
        cbAciertos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    int comprobarAciertos = -1;
                    mListener.onBotonEmoji(comprobarAciertos);
                }
            }
        });
    }


    //metodo que enlaza nuestro fragment con la activity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //aqui preguntamos si la activity tiene implementado el interface
        if (context instanceof OnBotonEmojiListener) {
            //si lo tiene implementado creamos una referencia al interface, y asi ya se puede
            //comunicar el fragment con la activity a través del interface
            mListener = (OnBotonEmojiListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBotonEmojiListener");
        }
    }

    //metodo que desenlaza el fragment de la activity
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnBotonEmojiListener {

        void onBotonEmoji(int idEmoji);
    }
}
