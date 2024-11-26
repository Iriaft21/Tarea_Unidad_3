package com.example.tarea_unidad_3;

import android.widget.ImageView;

public class Carta {

    //atributos de la clase
    private int imagenReverso;
    private int imagenCara;
    private String valor;
    private ImageView ivReverso;
    private ImageView ivCara;
    private boolean parejaEncontrada;

    //constructor de la clase
    public Carta(int imagenReverso, int imagenCara, String valor) {
        this.imagenReverso = imagenReverso;
        this.imagenCara = imagenCara;
        this.valor = valor;
    }

    //getters y setters de las clase
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

    //para comprobar si se la carta esta ya emparejada
    public boolean isParejaEncontrada() {
        return parejaEncontrada;
    }

    public void setParejaEncontrada(boolean parejaEncontrada) {
        this.parejaEncontrada = parejaEncontrada;
    }

    //metodo toString que nos muestra los datos de la clase
    @Override
    public String toString() {
        return "Carta imagendel reverso " + imagenReverso + ", imagen de la cara " + imagenCara + ", valor" + valor;
    }
}
