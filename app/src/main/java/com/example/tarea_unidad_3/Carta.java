package com.example.tarea_unidad_3;

import android.widget.ImageView;

public class Carta {

    private int imagenReverso;
    private int imagenCara;
    private String valor;
    private ImageView ivReverso;
    private ImageView ivCara;
    private boolean parejaEncontrada;

    public Carta(int imagenReverso, int imagenCara, String valor) {
        this.imagenReverso = imagenReverso;
        this.imagenCara = imagenCara;
        this.valor = valor;
    }

    public int getImagenReverso() {
        return imagenReverso;
    }

    public void setImagenReverso(int imagenReverso) {
        this.imagenReverso = imagenReverso;
    }

    public int getImagenCara() {
        return imagenCara;
    }

    public void setImagenCara(int imagenCara) {
        this.imagenCara = imagenCara;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public ImageView getIvReverso() {
        return ivReverso;
    }

    public void setIvReverso(ImageView ivReverso) {
        this.ivReverso = ivReverso;
    }

    public ImageView getIvCara() {
        return ivCara;
    }

    public void setIvCara(ImageView ivCara) {
        this.ivCara = ivCara;
    }

    public boolean isParejaEncontrada() {
        return parejaEncontrada;
    }

    public void setParejaEncontrada(boolean parejaEncontrada) {
        this.parejaEncontrada = parejaEncontrada;
    }

    @Override
    public String toString() {
        return "Carta imagendel reverso " + imagenReverso + ", imagen de la cara " + imagenCara + ", valor" + valor;
    }
}
