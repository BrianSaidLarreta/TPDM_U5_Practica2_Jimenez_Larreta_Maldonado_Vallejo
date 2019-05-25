package com.example.tpdm_u5_practica2_larreta_maldonado_jimenez_vallejo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Juego extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String retador = getIntent().getExtras().getString("retador");
        String contra = getIntent().getExtras().getString("contra");
        String nomR = getIntent().getExtras().getString("nombreRetador");
        boolean retando = getIntent().getExtras().getBoolean("retando");
        Retar r = new Retar();
        setContentView(new LienzoJuego(this,retador,contra,nomR,retando,r.getControlador()));
    }
}
