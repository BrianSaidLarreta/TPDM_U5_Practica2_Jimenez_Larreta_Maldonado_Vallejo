package com.example.tpdm_u5_practica2_larreta_maldonado_jimenez_vallejo;

public class Retas {

    int puntuacion1, puntuacion2,movj1,movj2;
    int turnos;

    public Retas(){
        puntuacion1 = 0;
        puntuacion2 = 0;
        movj1 = -1;
        movj2 = -1;
        turnos = 0;
    }

    public Retas(int n1, int n2,int mov1,int mov2,int t){
        puntuacion1 = n1;
        puntuacion2 = n2;
        movj1 = mov1;
        movj2 = mov2;
        turnos = t;
    }

    public int getPuntuacion1() {
        return puntuacion1;
    }

    public void setPuntuacion1(int puntuacion1) {
        this.puntuacion1 = puntuacion1;
    }

    public int getPuntuacion2() {
        return puntuacion2;
    }

    public void setPuntuacion2(int puntuacion2) {
        this.puntuacion2 = puntuacion2;
    }

    public int getTurnos() {
        return turnos;
    }

    public void setTurnos(int turnos) {
        this.turnos = turnos;
    }
}
