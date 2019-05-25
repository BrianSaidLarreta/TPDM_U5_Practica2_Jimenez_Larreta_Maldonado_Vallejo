package com.example.tpdm_u5_practica2_larreta_maldonado_jimenez_vallejo;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    EditText sobrenombre,telefono;
    Button entrar;
    Controlador c;
    String usuarioActual,nombreUA;
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
                usuarioActual=telefono.getText().toString();
                nombreUA = sobrenombre.getText().toString();
                entrar(nombreUA,usuarioActual);
            }
        });
    }

    public MainActivity(){
        c = new Controlador();
    }

    public void entrar(String sobrenombre,String telefono){

        //c.insertarUsuario(sobrenombre,telefono);
        //c.insertarReta(sobrenombre,telefono);
        //c.insertarPendientes(sobrenombre,telefono);
        //c.buscarPendiente(telefono);
        //c.buscarRetas(sobrenombre,telefono);
        //c.eliminarPendiente(telefono);
        //c.eliminarReta(sobrenombre,telefono);
        Intent e = new Intent(this,Retar.class);
        e.putExtra("usuarioActual",usuarioActual);
        e.putExtra("nombreUA",nombreUA);
        startActivity(e);
    }

    public Controlador getControlador(){
        return c;
    }


}
