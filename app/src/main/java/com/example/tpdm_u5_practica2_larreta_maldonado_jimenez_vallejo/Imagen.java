package com.example.tpdm_u5_practica2_larreta_maldonado_jimenez_vallejo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Imagen {
    int x,y;
    Bitmap icono;

    public Imagen(int x, int y,int imagen,LienzoJuego l) {
        this.x = x;
        this.y = y;
        icono = BitmapFactory.decodeResource(l.getResources(),imagen);
    }

    public void pintar(Canvas c, Paint p){
        c.drawBitmap(icono,x,y,p);
    }
}
