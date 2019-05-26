package com.example.tpdm_u5_practica2_larreta_maldonado_jimenez_vallejo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Juego extends AppCompatActivity {
    public static Juego context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String retador = getIntent().getExtras().getString("retador");
        String contra = getIntent().getExtras().getString("contra");
        boolean retando = getIntent().getExtras().getBoolean("retando");
        setContentView(new LienzoJuego(this,retador,contra,retando));
    }
    public static Juego getInstance(){
        return context;
    }

    @Override
    protected void onStart() {
        super.onStart();
        context = this;
    }
}
