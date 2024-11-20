package com.example.tarea_unidad_3;

public class Carta {

    private int imagenReverso;
    private int imagenCara;
    private String valor;

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

    @Override
    public String toString() {
        return "Carta imagendel reverso " + imagenReverso + ", imagen de la cara " + imagenCara + ", valor" + valor;
    }
}
