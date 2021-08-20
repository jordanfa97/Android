package com.example.mastermind;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

//le damos a la bombilla para quitar todos los fallos
public class AdaptadorResultados extends RecyclerView.Adapter<AdaptadorResultados.ResultadoViewHolder> {

    //la lista de los resultados ke vamos a mostrar
    private List<Resultado> listaResultados;
    private View.OnClickListener listener;

    //constructor
    public AdaptadorResultados(List<Resultado> listaResultados) {
        this.listaResultados = listaResultados;
    }

    @NonNull
    @Override//metodo que enlaza el adaptador al XML item-Resultado
    public ResultadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resultado, parent, false);
        itemView.setOnClickListener(listener);
        ResultadoViewHolder resultadoViewHolder = new ResultadoViewHolder((itemView));
        return resultadoViewHolder;
    }

    //este metodo enlaza el adaptador con la clase viewHolder
    @Override
    public void onBindViewHolder(@NonNull ResultadoViewHolder resultadoViewHolder, int posicion) {
        //cogemos un resultado de la lista
        Resultado resultado = listaResultados.get(posicion);
        //le pasamos datos al ViewHolder del resultado obtenido
        resultadoViewHolder.asignarDatos(resultado);
    }

    //nos devuelve el tamaño de la lista de resultados que nos llega
    @Override
    public int getItemCount() {
        return listaResultados.size();
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    //////VIEW HOLDER/////
    //la clase que va a mostrar los datos elegidos en el xml item-Resultado
    public class ResultadoViewHolder extends RecyclerView.ViewHolder {

        private TextView tvIdJugador, tvPuntuacion, tvIntentos, tvFecha;
        private SimpleDateFormat sdf = new SimpleDateFormat();

        //constructor
        public ResultadoViewHolder(@NonNull View itemView) {
            super(itemView);
            ///obtenemos las referencias a los componentes
            tvIdJugador = itemView.findViewById(R.id.tvJugador);
            tvPuntuacion = itemView.findViewById(R.id.tvPuntuacion);
            tvIntentos = itemView.findViewById(R.id.tvIntentos);
            tvFecha = itemView.findViewById(R.id.tvFecha);

        }

        //metodo que asigna los datos en la clase Resultado
        public void asignarDatos(Resultado resultado) {
            tvIdJugador.setText(String.valueOf(resultado.getIdJugador()));
            tvPuntuacion.setText(String.valueOf(resultado.getPuntos()));
            tvIntentos.setText(String.valueOf(resultado.getIntentos()));
            tvFecha.setText(String.valueOf(resultado.getFecha()));

        }
    }
}
