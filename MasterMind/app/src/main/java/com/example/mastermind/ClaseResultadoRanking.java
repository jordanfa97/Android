package com.example.mastermind;

import com.example.mastermind.Resultado;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClaseResultadoRanking implements Serializable {

    //atributo estatico para ir metiendo a los jugadores resultado para el ranking
    private static List<Resultado> listaResultadoRanking = new ArrayList<>();

    //getter y setter
    public static List<Resultado> getListaResultadoRanking() {
        return listaResultadoRanking;
    }

    public static void setListaResultadoRanking(List<Resultado> listaResultadoRanking) {
        ClaseResultadoRanking.listaResultadoRanking = listaResultadoRanking;
    }

    //metodo que añade los jugadores resultado
    public static void anhadirJugador(Resultado resultado) {
        listaResultadoRanking.add(resultado);
    }

    //metodo para ordenar la lista
    public static void ordenarRanking() {
        Collections.sort(listaResultadoRanking);
        Collections.reverse(listaResultadoRanking);
    }

    //tostring
    @Override
    public String toString() {
        return "ClaseResultadoRanking{}";
    }
}
