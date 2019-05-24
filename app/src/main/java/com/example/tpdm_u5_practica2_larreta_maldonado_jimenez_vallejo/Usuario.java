package com.example.tpdm_u5_practica2_larreta_maldonado_jimenez_vallejo;

public class Usuario {

    String sobrenombre, telefono;

    public Usuario(){}

    public Usuario(String s, String t){
        sobrenombre = s;
        telefono = t;
    }

    public String getSobrenombre() {
        return sobrenombre;
    }

    public void setSobrenombre(String sobrenombre) {
        this.sobrenombre = sobrenombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
