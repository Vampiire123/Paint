package com.example.dam.paint;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static MainActivity main;

    private VistaPintada vistapintada;

    public Spinner sGrosor, sColor, sRelleno;

    Button bLimpiar, bGoma, bLibre, bLinea, bCirculo, bRect, bOval, bUndo, bRedo;
    public boolean borrar, libre, linea, circulo, rect, oval;

    public void init(){
        main = this;

        vistapintada = findViewById(R.id.viewDraw);

        Spinners();

        booleanFalse();
        linea = true;

        bUndo = findViewById(R.id.bUndo);
        bUndo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                vistapintada.onClickUndo();
            }
        });

        bRedo = findViewById(R.id.bRedo);
        bRedo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vistapintada.onClickRedo();
            }
        });

        bOval = findViewById(R.id.bOval);
        bOval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                booleanFalse();
                oval = true;
            }
        });

        bRect = findViewById(R.id.bRect);
        bRect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                booleanFalse();
                rect = true;
            }
        });

        bCirculo = findViewById(R.id.bCirculo);
        bCirculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                booleanFalse();
                circulo = true;
            }
        });

        bLinea = findViewById(R.id.bLinea);
        bLinea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                booleanFalse();
                linea = true;
            }
        });

        bGoma = findViewById(R.id.bGoma);
        bGoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                booleanFalse();
                borrar = true;
                libre = true;
            }
        });

        bLibre = findViewById(R.id.bLibre);
        bLibre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                booleanFalse();
                libre = true;
            }
        });

        bLimpiar = findViewById(R.id.bLimpiar);
        bLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vistapintada.agregarObjetoLimpiar();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setContentView(new VistaPintada(this));
        init();
    }

    void booleanFalse(){
        borrar = false;
        libre = false;
        linea = false;
        rect = false;
        circulo = false;
        oval = false;
    }

    void Spinners(){
        sGrosor = findViewById(R.id.sGrosor);
        ArrayAdapter<String> adapter;
        List<String> list;

        list = new ArrayList<String>();
        list.add("10");
        list.add("15");
        list.add("20");
        list.add("25");
        list.add("30");
        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sGrosor.setAdapter(adapter);


        sColor = findViewById(R.id.sColor);

        list = new ArrayList<String>();
        list.add("Rojo");
        list.add("Azul");
        list.add("Amarillo");
        list.add("Cyan");
        list.add("Negro");
        list.add("Verde");
        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sColor.setAdapter(adapter);

        sRelleno = findViewById(R.id.sRelleno);

        list = new ArrayList<String>();
        list.add("Relleno");
        list.add("Sin relleno");
        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sRelleno.setAdapter(adapter);
    }

    public int getsGrosor(){
        return Integer.parseInt(sGrosor.getSelectedItem().toString());
    }

    public int getsColor(){
        if(sColor.getSelectedItem().toString().equals("Rojo")){
            return Color.RED;
        }else if(sColor.getSelectedItem().toString().equals("Azul")){
            return Color.BLUE;
        }else if(sColor.getSelectedItem().toString().equals("Amarillo")){
            return Color.YELLOW;
        }else if(sColor.getSelectedItem().toString().equals("Cyan")){
            return Color.CYAN;
        }else if(sColor.getSelectedItem().toString().equals("Negro")){
            return Color.BLACK;
        }else if(sColor.getSelectedItem().toString().equals("Verde")){
            return Color.GREEN;
        }

        return 0;
    }

    public Paint.Style getsRelleno(){
        if(sRelleno.getSelectedItem().toString().equals("Relleno")){
            return Paint.Style.FILL;
        }else{
            return Paint.Style.STROKE;
        }
    }
}