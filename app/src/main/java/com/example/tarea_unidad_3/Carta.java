package com.example.tarea_unidad_3;

import static java.security.AccessController.getContext;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.annotation.NonNull;

public class Carta implements Parcelable {

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


    protected Carta(Parcel in){
        imagenReverso = in.readInt();
        imagenCara = in.readInt();
        valor = in.readString();
        int resourceId = in.readInt();
        if (resourceId != 0) {
            ivReverso = new ImageView(getContext());
            ivReverso.setImageResource(resourceId);
            ivReverso.setTag(resourceId);
        }
        parejaEncontrada = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int i) {
        dest.writeInt(this.imagenReverso);
        dest.writeInt(this.imagenCara);
        dest.writeString(this.valor);
        dest.writeInt(ivReverso != null ? ((Integer) ivReverso.getTag()) : 0);
        dest.writeByte((byte) (this.parejaEncontrada ? 1 : 0));
    }

    public static final Creator<Carta> CREATOR = new Creator<Carta>() {
        @Override
        public Carta createFromParcel(Parcel parcel) {
            return new Carta(parcel);
        }

        @Override
        public Carta[] newArray(int i) {
            return new Carta[0];
        }
    };


    //metodo toString que nos muestra los datos de la clase
    @Override
    public String toString() {
        return "Carta imagendel reverso " + imagenReverso + ", imagen de la cara " + imagenCara + ", valor" + valor;
    }
}
