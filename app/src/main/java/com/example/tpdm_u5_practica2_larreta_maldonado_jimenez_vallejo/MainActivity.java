package com.example.tpdm_u5_practica2_larreta_maldonado_jimenez_vallejo;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    EditText sobrenombre,telefono;
    Button entrar;
    Controlador c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sobrenombre = findViewById(R.id.sobrenombre);
        telefono = findViewById(R.id.numero);
        entrar = findViewById(R.id.entrar);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entrar(sobrenombre.getText().toString(),telefono.getText().toString());
            }
        });
    }

    public void entrar(String sobrenombre,String telefono){
        c = new Controlador();
        c.insertarUsuario(sobrenombre,telefono);
    }


}
