package com.example.dam.paint.Clases;

import android.graphics.Paint;

public class Oval {

    public float xi, yi, xf, yf;
    public Paint pincel;

    public Oval(float xi, float yi, float xf, float yf, Paint pincel){
        this.xi = xi;
        this.xf = xf;
        this.yf = yf;
        this.yi = yi;
        this.pincel = pincel;
    }
}
