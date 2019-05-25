package com.example.tpdm_u5_practica2_larreta_maldonado_jimenez_vallejo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class LienzoJuego extends View {

    Paint p = new Paint();
    int maxH,maxW;
    Imagen[] imagenes;
    //**************************constructor**********************//
    public LienzoJuego(Context context) {
        super(context);
        maxW= getResources().getSystem().getDisplayMetrics().widthPixels;
        maxH = getResources().getSystem().getDisplayMetrics().heightPixels-200;
        imagenes = new Imagen[]{
            new Imagen(0,0,R.drawable.piedra,LienzoJuego.this),
            new Imagen(0,0,R.drawable.papel2,LienzoJuego.this),
            new Imagen(0,0,R.drawable.tijeras,LienzoJuego.this),
        };
    }

    @Override
    protected void onDraw(Canvas canvas) {
        p.setColor(Color.BLACK);
        p.setTextSize(60);
        //jugador de arriba
        canvas.drawText("Jugador x",(maxW/2)-160,maxH/4,p);

        //linea que separa
        canvas.drawLine(0,maxH/2,maxW,(maxH/2),p);

        //jugador de abajo
        canvas.drawText("Jugador y",(maxW/2)-160,maxH*0.75f,p);


//        la barrita de quien gano
        p.setColor(Color.BLUE);
        canvas.drawRect(0,maxH*0.9f,maxW,maxH,p);
        p.setColor(Color.WHITE);
        p.setTextSize(60);
        canvas.drawText("Perdiste",(maxW/2)-160,maxH*0.97f,p);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int accion= event.getAction();
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (accion){
            case MotionEvent.ACTION_DOWN:

                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        return true;
    }
}
