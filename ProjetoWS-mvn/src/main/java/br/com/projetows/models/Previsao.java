package br.com.projetows.models;

import java.util.Date;

/**
 *
 * @author Tuanny
 */
public class Previsao {

    private Date dia;
    private String tempo;
    private int maxima;
    private int minima;
    private double iuv;

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public int getMaxima() {
        return maxima;
    }

    public void setMaxima(int maxima) {
        this.maxima = maxima;
    }

    public int getMinima() {
        return minima;
    }

    public void setMinima(int minima) {
        this.minima = minima;
    }

    public double getIuv() {
        return iuv;
    }

    public void setIuv(double iuv) {
        this.iuv = iuv;
    }
}
