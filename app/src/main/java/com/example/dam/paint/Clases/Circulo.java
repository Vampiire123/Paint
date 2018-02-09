package com.example.dam.paint.Clases;

import android.graphics.Paint;

public class Circulo {

    public float xi, yi, radio;
    public Paint pincel;

    public Circulo(float xi, float yi, float radio, Paint pincel){
        this.xi = xi;
        this.yi = yi;
        this.radio = radio;
        this.pincel = pincel;
    }
}
