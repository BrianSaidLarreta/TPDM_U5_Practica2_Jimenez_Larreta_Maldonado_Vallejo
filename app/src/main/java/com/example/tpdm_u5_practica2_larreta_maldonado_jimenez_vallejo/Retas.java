package com.example.tpdm_u5_practica2_larreta_maldonado_jimenez_vallejo;

public class Retas {

    int puntuacion1, puntuacion2;
    int turnos;

    public Retas(){}

    public Retas(int n1, int n2){
        puntuacion1 = n1;
        puntuacion2 = n2;
        turnos = 0;
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
