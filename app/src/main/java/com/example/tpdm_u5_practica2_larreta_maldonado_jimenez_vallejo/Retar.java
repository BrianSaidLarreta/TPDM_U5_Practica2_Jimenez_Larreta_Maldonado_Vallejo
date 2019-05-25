package com.example.tpdm_u5_practica2_larreta_maldonado_jimenez_vallejo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Retar extends AppCompatActivity {

    Controlador c;
    String usuarioActual,nombreUA;
    int clics=0;
    MainActivity principal;
    Thread teEstanRetando,esperandoContrincante;
    boolean teRetan,esperaContrincante,respuesta;


    Button larretar;
    EditText contra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retar);
        principal = new MainActivity();

        usuarioActual = getIntent().getExtras().getString("usuarioActual");
        nombreUA = getIntent().getExtras().getString("nombreUA");
        c = principal.getControlador();
        System.err.println(usuarioActual+" sasdasda  "+nombreUA);
        larretar = findViewById(R.id.retar_btnRetar);
        contra = findViewById(R.id.retar_telefono);
        teRetan=true;
        esperaContrincante= true;
        if(clics==0) {
            larretar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (contra.getText().toString().isEmpty()) {
                        return;
                    }
                    c.insertarPendientes(usuarioActual, contra.getText().toString());
                    esperandoContrincante.start();
                    clics++;
                    larretar.setEnabled(false);
                }
            });

        }

        teEstanRetando = new Thread(){
            public void run(){
                while (teRetan && clics==0){
                    c.buscarPendiente(usuarioActual);
                    if(c.p!=null){

                        teRetan=false;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    System.err.println(usuarioActual+" dd que esta pasando  ");
                                    aceptarReta();
                                }
                            });


                    }
                    try {
                        sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        esperandoContrincante = new Thread(){
            public void run(){
                while (esperaContrincante){
                    c.buscarPendiente(contra.getText().toString());
                    if(c.buscandoRetador){
                        c.eliminarPendiente(contra.getText().toString());
                        System.err.println(" si encontro  ");
                        esperaContrincante=false;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                c.insertarReta(usuarioActual,contra.getText().toString());
                                Intent r = new Intent(Retar.this,Juego.class);

                                r.putExtra("retador",usuarioActual);
                                r.putExtra("nombreRetador",nombreUA);
                                r.putExtra("contra",contra.getText().toString());
                                r.putExtra("retando",true);
                                startActivity(r);
                            }
                        });

                    }
                    try {
                        sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        teEstanRetando.start();

    }
    public void aceptarReta(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(Retar.this);
        alerta.setTitle("ATENCION")
                .setMessage("Se ha encontrado un retador")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        c.actualizarReta(c.p.numero1,usuarioActual);
                        Intent r = new Intent(Retar.this, Juego.class);
                        r.putExtra("retador",c.p.numero1 );
//                        c.buscarUsuario(c.p.numero1);
                        r.putExtra("nombreRetador","");
                        r.putExtra("contra",usuarioActual);
                        r.putExtra("retando",false);
                        startActivity(r);
                    }
                })
                .setNegativeButton("Rechazar", null)
                .show();
    }
    public Controlador getControlador(){
        return c;
    }
}
