package com.example.tpdm_u5_practica2_larreta_maldonado_jimenez_vallejo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

import static android.content.Context.SENSOR_SERVICE;

public class LienzoJuego extends View implements SensorListener {

    Controlador c;
    String retador, contra;
    boolean retando;
    Paint p = new Paint();
    int maxH,maxW;
    Imagen[] imagenes;
    private static final int SHAKE_THRESHOLD = 800;
    long pasada;
    float contar,x,y,z,xPasada,yPasada,zPasada;
    boolean otra,encontrado;
    Random rnd;
    int jugada;

    Thread esperarMovimiento;
    //**************************constructor**********************//
    public LienzoJuego(Context context){super(context);}

    public LienzoJuego(Context context, final String retado, String contr, boolean retand) {
        super(context);
        maxW= getResources().getSystem().getDisplayMetrics().widthPixels;
        maxH = getResources().getSystem().getDisplayMetrics().heightPixels-200;
        imagenes = new Imagen[]{
            new Imagen(0,0,R.drawable.piedra,LienzoJuego.this),
            new Imagen(0,0,R.drawable.papel2,LienzoJuego.this),
            new Imagen(0,0,R.drawable.tijeras,LienzoJuego.this),
        };
        SensorManager sensorMgr = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        contar = 1;
        sensorMgr.registerListener(this,
                SensorManager.SENSOR_ACCELEROMETER,
                SensorManager.SENSOR_DELAY_GAME);
        otra=true;
        rnd = new Random();
        this.retador = retado;
        this.contra = contr;

        this.retando=retand;
        encontrado=true;
        c= new Controlador();
        c.buscarRetas(retador,contra,retando);

        esperarMovimiento = new Thread(){
            public void run(){
                while (true) {
                    postInvalidate();
                    try {
                        sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        esperarMovimiento.start();

    }
//***************** fin constructor **********************//
    @Override
    protected void onDraw(Canvas canvas) {

        p.setTextSize(60);
        if(c.r!=null){

                int movj1 =c.r.movj1;
                int movj2 = c.r.movj2;
                if(retando){

                    if(movj1>-1){
                        imagenes[movj1].x=(maxW/2)-200;
                        imagenes[movj1].y=maxH/2;
                        imagenes[movj1].pintar(canvas,p);
                    }
                    if(movj2>-1){
                        imagenes[movj2].x=(maxW/2)-200;
                        imagenes[movj2].y=maxH/4-200;
                        imagenes[movj2].pintar(canvas,p);
                    }
                }
                else{
                    if(movj1>-1){
                        imagenes[movj1].x=(maxW/2)-200;
                        imagenes[movj1].y=maxH/4-200;
                        imagenes[movj1].pintar(canvas,p);
                    }
                    if(movj2>-1){
                        //jugador de abajo
                        imagenes[movj2].x=(maxW/2)-200;
                        imagenes[movj2].y=maxH/2;
                        imagenes[movj2].pintar(canvas,p);
                    }
                }
                //linea que separa
                canvas.drawLine(0,maxH/2,maxW,(maxH/2),p);

            if(c.r.turnos==3){
                p.setColor(Color.BLUE);
                canvas.drawRect(0,maxH*0.9f,maxW,maxH,p);
                p.setColor(Color.WHITE);
                p.setTextSize(60);

                if(retando){
                    if(c.r.puntuacion1>c.r.puntuacion2){
                        canvas.drawText("Ganaste",(maxW/2)-160,maxH*0.96f,p);
                    }else{
                        canvas.drawText("Perdiste",(maxW/2)-160,maxH*0.96f,p);
                    }
                    //        la barrita de quien gano
                }else{
                    if(c.r.puntuacion1>c.r.puntuacion2){
                        canvas.drawText("Perdiste ",(maxW/2)-160,maxH*0.96f,p);
                    }else{
                        canvas.drawText("Ganaste",(maxW/2)-160,maxH*0.96f,p);
                    }
                }
//                c.eliminarReta(retador,contra);
            }
        }

    }

    @Override
    public void onSensorChanged(int sensor, float[] values) {

        if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
//            System.err.println(retando+"  soy otra - "+otra);
            if ((curTime - pasada) > 100) {
                long diferencia = (curTime - pasada);
                pasada = curTime;

                x = values[SensorManager.DATA_X];
                y = values[SensorManager.DATA_Y];
                z = values[SensorManager.DATA_Z];

                float speed = Math.abs(x+y+z - xPasada - yPasada - zPasada) / diferencia * 10000;

                jugada = rnd.nextInt(2);
                if(retando) {
                    if (speed > SHAKE_THRESHOLD && c.r.movj1 == -1) {
//
                        if (c.r.movj1 == -1) {
                            c.actMovJ1(retador,contra,jugada);
                        }
                    }
                }
                if(!retando){
                    if (speed > SHAKE_THRESHOLD && c.r.movj2 == -1) {
                        if (c.r.movj2 == -1) {
                            c.actMovJ2(retador,contra,jugada);
                        }
                    }
                }
                postInvalidate();
                xPasada = x;
                yPasada = y;
                zPasada = z;
            }
        }

    }

    @Override
    public void onAccuracyChanged(int sensor, int accuracy) {

    }

    public void iniciarHilo(){


    }
}
