package com.example.tpdm_u5_practica2_larreta_maldonado_jimenez_vallejo;

public class Pendientes {

    String numero1;
    boolean estado;
    public Pendientes(){}

    public Pendientes(String n1,boolean est){
        numero1 = n1;
        estado=est;
    }

    public String getNumero1() {
        return numero1;
    }

    public void setNumero1(String numero1) {
        this.numero1 = numero1;
    }
}
