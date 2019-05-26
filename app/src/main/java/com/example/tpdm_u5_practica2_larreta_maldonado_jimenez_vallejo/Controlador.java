package com.example.tpdm_u5_practica2_larreta_maldonado_jimenez_vallejo;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Controlador {

    private DatabaseReference mDatabase;
    public boolean buscandoRetador,buscandoReta;
    public Pendientes p;
    public Retas r;
    public Usuario u;
    public Controlador(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        buscandoReta=true;
    }

    public  void insertarUsuario(final String sobrenombre,final String telefono){
        final Usuario u = new Usuario(sobrenombre,telefono);

        mDatabase.child("usuarios").child(u.telefono).setValue(u);
    }

    public  void insertarReta(final String numero1,final String numero2){
        final Retas r = new Retas();

        mDatabase.child("retas").child(numero1 +"-"+ numero2).setValue(r);
    }

    public  void insertarPendientes(final String numero1,final String numero2){
        Pendientes p = new Pendientes(numero1,false);

        mDatabase.child("pendientes").child(numero2).setValue(p);
    }
    public void buscarUsuario(String numero){
        mDatabase.child("usuarios").child(numero)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        u = dataSnapshot.getValue(Usuario.class);

                        if(u!=null) {
                            System.err.println(u.sobrenombre+"  asdasd");
                        } else {
                            buscandoReta=false;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("se cancelo");
                    }
                });
    }

   public void buscarRetas(final String numero1, final String numero2, final boolean retando){
       mDatabase.child("retas").child(numero1+"-"+numero2)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                         r = dataSnapshot.getValue(Retas.class);

                        if(r!=null) {
                            Juego context = Juego.getInstance();
                            LienzoJuego lj = new LienzoJuego(context);

                            if(r.movj1 > -1 || r.movj2 > -1){
                                lj.postInvalidate();
                            }
                            if (r.movj1 > -1 && r.movj2 > -1 && retando) {
                                if (r.movj1 !=r.movj2) {
                                    r.turnos++;
                                }
                                if (r.movj1 == 0 && r.movj2 == 2) {
                                    r.puntuacion1++;
                                }
                                if (r.movj1 == 1 && r.movj2 == 0) {
                                    r.puntuacion1++;
                                }
                                if (r.movj1 == 2 && r.movj2 == 1) {
                                    r.puntuacion1++;
                                }
                                //JUGADOR 2
                                if (r.movj2 == 0 && r.movj1 == 2) {
                                    r.puntuacion2++;
                                }
                                if (r.movj2 == 1 && r.movj1 == 0) {
                                    r.puntuacion2++;
                                }
                                if (r.movj2 == 2 && r.movj1 == 1) {
                                    r.puntuacion2++;
                                }
                                Thread hilo = new Thread(){
                                    public void run(){
                                        int x=0;
                                        while (true){
                                            if(x>0)
                                                break;
                                            x++;
                                            try {
                                                sleep(3000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            r.movj1 = -1;
                                            r.movj2 = -1;
                                            actReta(numero1,numero2);
                                        }
                                    }
                                };
                                hilo.start();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
    public void buscarPendiente(final String numero2){
        mDatabase.child("pendientes").child(numero2)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                         p = dataSnapshot.getValue(Pendientes.class);
                        if(p!=null) {
                           buscandoRetador=p.estado;
//                            System.out.println(" te encontre   "+numero2);

                        } else {
//                            System.out.println("NEL PASTEL");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void eliminarReta(final String numero1,final String numero2){
        mDatabase.child("retas").child(numero1+"-"+numero2).removeValue();
    }

    public void eliminarPendiente(final String numero2){
        mDatabase.child("pendientes").child(numero2).removeValue();
    }

    public void actualizarPendiente(String numero1,String numero2){
        Pendientes p = new Pendientes(numero1,true);
        mDatabase.child("pendientes").child(numero2).setValue(p);
    }
    public void actMovJ1(String n1,String n2, int mov){
        Map<String,Object> actJ1 = new HashMap<>();
        actJ1.put("movj1",mov);
        mDatabase.child("retas").child(n1+"-"+n2).updateChildren(actJ1);
    }
    public void actMovJ2(String n1,String n2, int mov){
        Map<String,Object> actJ2 = new HashMap<>();
        actJ2.put("movj2",mov);
        mDatabase.child("retas").child(n1+"-"+n2).updateChildren(actJ2);
    }
    public void actReta(String n1,String n2){
        if(r!=null){
            mDatabase.child("retas").child(n1 +"-"+ n2).setValue(r);
            r=null;
        }
    }

    public void actualizarUsuario(String num,String nom){
        Map<String,Object> act = new HashMap<>();
        act.put("sobrenombre",nom);
        mDatabase.child("usuarios").child(num).updateChildren(act);
    }

}
