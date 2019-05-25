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
    String retador, contra,nomR;
    boolean retando;
    Paint p = new Paint();
    int maxH,maxW;
    Imagen[] imagenes;
    private static final int SHAKE_THRESHOLD = 800;
    long pasada;
    float contar,x,y,z,xPasada,yPasada,zPasada;
    boolean otra,encontrado, cargado;
    Random rnd;
    int jugada,turnos;

    Thread esperarMovimiento;
    //**************************constructor**********************//
    public LienzoJuego(Context context, String retador, String contra,String nomR,boolean retando) {
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
        this.retador = retador;
        this.contra = contra;
        this.nomR = nomR;
        this.retando=retando;
        encontrado=true;
        c= new Controlador();
        cargado=true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        p.setColor(Color.BLACK);
        p.setTextSize(60);
        //jugador de arriba

            c.buscarRetas(retador, contra);
            c.buscarRetas(retador, contra);
//            System.err.println(retador+" si encontro  "+contra);
            if(c.r!=null){

                int movj1 =c.r.movj1;
                int movj2 = c.r.movj2;
                if(movj1>-1){
                    canvas.drawText("Jugador x",(maxW/2)-160,maxH/4,p);
                    imagenes[movj1].x=(maxW/2)-100;
                    imagenes[movj1].y=maxH/4-100;
                    imagenes[movj1].pintar(canvas,p);

                }

                //linea que separa
                canvas.drawLine(0,maxH/2,maxW,(maxH/2),p);
                if(movj2>-1){
                    //jugador de abajo
                    canvas.drawText("Jugador y",(maxW/2)-160,maxH*0.75f,p);
                    imagenes[movj2].x=(maxW/2)-100;
                    imagenes[movj2].y=maxH/4+100;
                    imagenes[movj2].pintar(canvas,p);
                }
                //        if(c.r.turnos==3){
                p.setColor(Color.BLUE);
                canvas.drawRect(0,maxH*0.9f,maxW,maxH,p);
                p.setColor(Color.WHITE);
                p.setTextSize(60);

                if(retando){
                    if(c.r.puntuacion1>c.r.puntuacion2){
                        canvas.drawText("Ganaste",(maxW/2)-160,maxH*0.97f,p);
                    }else{
                        canvas.drawText("Perdiste",(maxW/2)-160,maxH*0.97f,p);
                    }
                    //        la barrita de quien gano
                }else{
                    if(c.r.puntuacion1>c.r.puntuacion2){
                        canvas.drawText("Perdiste "+nomR,(maxW/2)-160,maxH*0.97f,p);
                    }else{
                        canvas.drawText("Ganaste",(maxW/2)-160,maxH*0.97f,p);
                    }
                }
                //        }
            }

    }

    @Override
    public void onSensorChanged(int sensor, float[] values) {

        if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            System.err.println(retando+"  soy otra - "+otra);
            if ((curTime - pasada) > 100) {
                long diferencia = (curTime - pasada);
                pasada = curTime;

                x = values[SensorManager.DATA_X];
                y = values[SensorManager.DATA_Y];
                z = values[SensorManager.DATA_Z];

                float speed = Math.abs(x+y+z - xPasada - yPasada - zPasada) / diferencia * 10000;
                c.buscarRetas(retador,contra);
                c.buscarRetas(retador,contra);
                jugada = rnd.nextInt(2);
                if(retando) {
                    if (speed > SHAKE_THRESHOLD && c.r.movj1 == -1) {
//
                        if (c.r.movj1 == -1) {
                            c.r.movj1 = jugada;
                            System.err.println("me cambie - " + otra);
                        }
                        validar();

                    }
                }
                if(!retando){
                    if (speed > SHAKE_THRESHOLD && c.r.movj2 == -1) {
                        if (c.r.movj2 == -1) {
                            c.r.movj2 = jugada;
                            System.err.println("me cambie - " + jugada);
                        }
                        validar();
                    }
                }
                xPasada = x;
                yPasada = y;
                zPasada = z;
            }
            invalidate();
        }

    }

    @Override
    public void onAccuracyChanged(int sensor, int accuracy) {

    }

    public void validar(){
        c.actualizarPartida(c.r.puntuacion1,c.r.puntuacion2,c.r.movj1,c.r.movj2,c.r.turnos,retador,contra);
        encontrado=true;
        esperarMovimiento = new Thread(){
            public void run(){
                while (encontrado){
                    c.buscarRetas(retador,contra);
                    c.buscarRetas(retador,contra);
                    if(c.r.movj1 >-1 && c.r.movj2>-1){
                        System.err.println("encontre al otro - "+retando);
                        if(c.r.movj1 != c.r.movj2){
                            c.r.turnos++;
                        }
                        if(c.r.movj1==0 && c.r.movj2==2){
                            c.r.puntuacion1++;
                        }
                        if(c.r.movj1==1 && c.r.movj2==0){
                            c.r.puntuacion1++;
                        }
                        if(c.r.movj1==2 && c.r.movj2==1){
                            c.r.puntuacion1++;
                        }
                        //JUGADOR 2
                        if(c.r.movj2==0 && c.r.movj1==2){
                            c.r.puntuacion2++;
                        }
                        if(c.r.movj2==1 && c.r.movj1==0){
                            c.r.puntuacion2++;
                        }
                        if(c.r.movj2==2 && c.r.movj1==1){
                            c.r.puntuacion2++;
                        }
                        otra=true;
                        c.r.movj1=-1;
                        c.r.movj2=-1;
                        c.actualizarPartida(c.r.puntuacion1,c.r.puntuacion2,c.r.movj1,c.r.movj2,c.r.turnos,retador,contra);
                        invalidate();
                        encontrado=false;

                    }
                    invalidate();
                    try {
                        sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        esperarMovimiento.start();
        otra=false;
        invalidate();
    }
}
