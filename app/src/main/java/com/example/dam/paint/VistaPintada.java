package com.example.dam.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.dam.paint.Clases.Circulo;
import com.example.dam.paint.Clases.Cuadrado;
import com.example.dam.paint.Clases.Libre;
import com.example.dam.paint.Clases.Linea;
import com.example.dam.paint.Clases.Oval;

import java.util.ArrayList;

public class VistaPintada extends View {

    ArrayList<Object> arrayObjetos = new ArrayList<>();
    ArrayList<Object> arrayRedo = new ArrayList<>();

    ArrayList<Linea> lineas = new ArrayList<>();

    private Bitmap mapaDeBits;
    private Canvas canvasFondo;

    public VistaPintada(Context context) {
        super(context);
    }

    public VistaPintada(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    Paint borrar() {
        Paint pincel = pincelPropiedades();

        pincel.setColor(Color.WHITE);

        return pincel;
    }

    Paint linea(Canvas canvas) {
        Paint pincel = pincelPropiedades();

        if (pintando) {
            canvas.drawLine(xi, yi, xf, yf, pincel);
        } else {
            arrayObjetos.add(new Linea(xi, yi, xf, yf, pincel));
            canvasFondo.drawLine(xi, yi, xf, yf, pincel);
        }

        return pincel;
    }

    Paint circulo(Canvas canvas) {
        Paint pincel = pincelPropiedades();

        if (pintando) {
            canvas.drawCircle(xi, yi, radio, pincel);
        } else {
            arrayObjetos.add(new Circulo(xi, yi, radio, pincel));
            canvasFondo.drawCircle(xi, yi, radio, pincel);
        }

        return pincel;
    }

    Paint rect(Canvas canvas) {
        Paint pincel = pincelPropiedades();

        pincel.setAntiAlias(true);  //suaviza los bordes
        if (pintando) {
            canvas.drawRect(xi, yi, xf, yf, pincel);
        } else {
            arrayObjetos.add(new Cuadrado(xi, yi, xf, yf, pincel));
            canvasFondo.drawRect(xi, yi, xf, yf, pincel);
        }

        return pincel;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    Paint oval(Canvas canvas) {
        Paint pincel = pincelPropiedades();

        if (pintando) {
            canvas.drawOval(xi, yi, xf, yf, pincel);
        } else {
            arrayObjetos.add(new Oval(xi, yi, xf, yf, pincel));
            canvasFondo.drawOval(xi, yi, xf, yf, pincel);
        }

        return pincel;
    }

    Paint pincelPropiedades() {
        Paint pincel = new Paint();

        pincel.setStrokeWidth(MainActivity.main.getsGrosor());
        pincel.setColor(MainActivity.main.getsColor());
        pincel.setStyle(MainActivity.main.getsRelleno());
        pincel.setAntiAlias(true);  //suaviza los bordes

        return pincel;
    }

    //Es donde yo puedo pintar
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint pincel = new Paint();
        arrayRedo = new ArrayList<>();

        if (MainActivity.main.borrar) {
            pincel = borrar();
        } else if (MainActivity.main.libre) {
            pincel = pincelPropiedades();
        } else if (MainActivity.main.linea) {
            pincel = linea(canvas);
        } else if (MainActivity.main.circulo) {
            pincel = circulo(canvas);
        } else if (MainActivity.main.rect) {
            pincel = rect(canvas);
        } else if (MainActivity.main.oval) {
            pincel = oval(canvas);
        }

        if (MainActivity.main.libre) {
            lineas.add(new Linea(xi, yi, xf, yf, pincel));
            canvasFondo.drawLine(xi, yi, xf, yf, pincel);
            if(!pintando && lineas.size() > 0){
                arrayObjetos.add(new Libre(lineas));
                lineas = new ArrayList<>();
            }
        }

        canvas.drawBitmap(mapaDeBits, 0, 0, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onClickUndo () {
        if(arrayObjetos.size() > 0){
            limpiarPantalla();
            arrayRedo.add(arrayObjetos.get(arrayObjetos.size()-1));
            arrayObjetos.remove(arrayObjetos.size()-1);
            for(int s = 0; s < arrayObjetos.size(); s++){
                if(arrayObjetos.get(s).getClass().equals(Libre.class)){
                    Libre libre = (Libre) arrayObjetos.get(s);
                    for(int z = 0; z < libre.arrayLineas.size(); z++){
                        Linea linea = libre.arrayLineas.get(z);
                        canvasFondo.drawLine(linea.xi, linea.yi, linea.xf, linea.yf, linea.pincel);
                    }
                }else if(arrayObjetos.get(s).getClass().equals(Linea.class)){
                    Linea linea = (Linea) arrayObjetos.get(s);
                    canvasFondo.drawLine(linea.xi, linea.yi, linea.xf, linea.yf, linea.pincel);
                }else if(arrayObjetos.get(s).getClass().equals(Circulo.class)){
                    Circulo circulo = (Circulo) arrayObjetos.get(s);
                    canvasFondo.drawCircle(circulo.xi, circulo.yi, circulo.radio, circulo.pincel);
                }else if(arrayObjetos.get(s).getClass().equals(Cuadrado.class)){
                    Cuadrado cuadrado = (Cuadrado) arrayObjetos.get(s);
                    canvasFondo.drawRect(cuadrado.xi, cuadrado.yi, cuadrado.xf, cuadrado.yf, cuadrado.pincel);
                }else if(arrayObjetos.get(s).getClass().equals(Oval.class)){
                    Oval oval = (Oval) arrayObjetos.get(s);
                    canvasFondo.drawOval(oval.xi, oval.yi, oval.xf, oval.yf, oval.pincel);
                }else if(arrayObjetos.get(s).equals(1)){
                    limpiarPantalla();
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onClickRedo(){
        if(arrayRedo.size() > 0){
            if(arrayRedo.get(arrayRedo.size()-1).getClass().equals(Libre.class)){
                Libre libre = (Libre) arrayRedo.get(arrayRedo.size()-1);
                for(int z = 0; z < libre.arrayLineas.size(); z++){
                    Linea linea = libre.arrayLineas.get(z);
                    canvasFondo.drawLine(linea.xi, linea.yi, linea.xf, linea.yf, linea.pincel);
                }
            }else if(arrayRedo.get(arrayRedo.size()-1).getClass().equals(Linea.class)){
                Linea linea = (Linea) arrayRedo.get(arrayRedo.size()-1);
                canvasFondo.drawLine(linea.xi, linea.yi, linea.xf, linea.yf, linea.pincel);
            }else if(arrayRedo.get(arrayRedo.size()-1).getClass().equals(Circulo.class)){
                Circulo circulo = (Circulo) arrayRedo.get(arrayRedo.size()-1);
                canvasFondo.drawCircle(circulo.xi, circulo.yi, circulo.radio, circulo.pincel);
            }else if(arrayRedo.get(arrayRedo.size()-1).getClass().equals(Cuadrado.class)){
                Cuadrado cuadrado = (Cuadrado) arrayRedo.get(arrayRedo.size()-1);
                canvasFondo.drawRect(cuadrado.xi, cuadrado.yi, cuadrado.xf, cuadrado.yf, cuadrado.pincel);
            }else if(arrayRedo.get(arrayRedo.size()-1).getClass().equals(Oval.class)){
                Oval oval = (Oval) arrayRedo.get(arrayRedo.size()-1);
                canvasFondo.drawOval(oval.xi, oval.yi, oval.xf, oval.yf, oval.pincel);
            }else if(arrayRedo.get(arrayRedo.size()-1).equals(1)){
                limpiarPantalla();
            }

            arrayObjetos.add(arrayRedo.get(arrayRedo.size()-1));
            arrayRedo.remove(arrayRedo.size()-1);
        }
    }

    public void agregarObjetoLimpiar(){
        arrayObjetos.add(1);
        limpiarPantalla();
    }

    void limpiarPantalla(){
        canvasFondo.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mapaDeBits = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvasFondo = new Canvas(mapaDeBits);
    }

    private float xi, yi, xf, yf, radio;
    boolean pintando = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: //acabo de pulsar
                pintando = true;
                xf = xi = x;
                yf = yi = y;
                break;
            case MotionEvent.ACTION_MOVE: //mantengo el dedo y lo muevo
                if (MainActivity.main.libre) {
                    xi = xf;
                    yi = yf;
                }
                xf = x;
                yf = y;
                break;
            case MotionEvent.ACTION_UP: //quito el dedo
                pintando = false;
                xf = x;
                yf = y;
                break;
        }
        radio = (float) Math.sqrt(Math.pow(xf - xi, 2) + Math.pow(yf - yi, 2));
        invalidate(); //vuelve a pintar, porque este método checkea si se ha pintado bien, y como se han cambiado las variables, lo nota como que está mal pintado y vuelve a llamar al método onDraw
        return true;
    }
}
