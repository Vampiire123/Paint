package com.example.dam.paint.Clases;

import android.graphics.Paint;

public class Linea {
    public Paint pincel;
    public float xi, yi, xf, yf;

    public Linea(float xi, float yi, float xf, float yf, Paint pincel){
        this.pincel = pincel;
        this.xf = xf;
        this.xi = xi;
        this.yf = yf;
        this.yi = yi;
    }
}
