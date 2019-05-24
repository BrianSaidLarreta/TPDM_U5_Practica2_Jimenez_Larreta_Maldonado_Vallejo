package com.example.tpdm_u5_practica2_larreta_maldonado_jimenez_vallejo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Juego extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new LienzoJuego(this));
    }
}
