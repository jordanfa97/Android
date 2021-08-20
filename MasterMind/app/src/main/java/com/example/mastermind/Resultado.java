package com.example.mastermind;


import java.io.Serializable;

public class Resultado implements Serializable, Comparable<Resultado> {
    private int idJugador=1;
    private int puntos, intentos;
    private String fecha;

    public Resultado(int idJugador, int puntos, int intentos, String fecha) {
        this.idJugador = idJugador;
        this.puntos = puntos;
        this.intentos = intentos;
        this.fecha = fecha;
    }

    public Resultado(int puntos, int intentos, String fecha) {
        this.puntos = puntos;
        this.intentos = intentos;
        this.fecha = fecha;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Resultado{" +
                "idJugador=" + idJugador +
                ", puntos=" + puntos +
                ", intentos=" + intentos +
                ", fecha='" + fecha + '\'' +
                '}';
    }

    //metodo para ordenar
    @Override
    public int compareTo(Resultado o) {
        if (puntos < o.getPuntos()) {
            return -1;
        }
        if (puntos > o.getPuntos()) {
            return 1;
        }
        return 0;
    }
}
