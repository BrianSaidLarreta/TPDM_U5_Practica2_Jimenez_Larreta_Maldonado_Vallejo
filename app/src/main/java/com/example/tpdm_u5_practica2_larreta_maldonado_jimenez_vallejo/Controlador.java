package com.example.tpdm_u5_practica2_larreta_maldonado_jimenez_vallejo;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Controlador {

    private DatabaseReference mDatabase;
    public boolean buscandoRetador,buscandoReta;
    public Pendientes p;
    public Retas r;
    public Usuario u;
    public Controlador(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public  void insertarUsuario(final String sobrenombre,final String telefono){
        final Usuario u = new Usuario(sobrenombre,telefono);

        mDatabase.child("usuarios").child(u.telefono).setValue(u)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("EXITO");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("ERROR");
                    }
                });
    }

    public  void insertarReta(final String numero1,final String numero2){
        final Retas r = new Retas();

        mDatabase.child("retas").child(numero1 +"-"+ numero2).setValue(r)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("EXITO");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("ERROR");
                    }
                });
    }

    public  void insertarPendientes(final String numero1,final String numero2){
        Pendientes p = new Pendientes(numero1,false);

        mDatabase.child("pendientes").child(numero2).setValue(p)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("EXITO");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("ERROR");
                    }
                });
    }
    public void buscarUsuario(String numero){
        FirebaseDatabase.getInstance().getReference().child("usuarios").child(numero)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        u = dataSnapshot.getValue(Usuario.class);

                        if(u!=null) {
                            System.out.println("YES EN INGLES");

                        } else {
                            System.out.println("NEL PASTEL");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

   public void buscarRetas(String numero1,String numero2){
        FirebaseDatabase.getInstance().getReference().child("retas").child(numero1+"-"+numero2)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                         r = dataSnapshot.getValue(Retas.class);

                        if(r!=null) {
                            System.out.println("YES EN INGLES");

                        } else {
                            System.out.println("NEL PASTEL");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
    public void buscarPendiente(final String numero2){
        FirebaseDatabase.getInstance().getReference().child("pendientes").child(numero2)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                         p = dataSnapshot.getValue(Pendientes.class);

                        if(p!=null) {
                           buscandoRetador=p.estado;
                            System.out.println(" te encontre   "+numero2);

                        } else {
                            System.out.println("NEL PASTEL");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void eliminarReta(final String numero1,final String numero2){
        mDatabase.child("retas").child(numero1+"-"+numero2).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("SE ACABO LA RETA WE");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("NO SE BORRÓ WE");
                    }
                });
    }

    public void eliminarPendiente(final String numero2){
        mDatabase.child("pendientes").child(numero2).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("LERO LERO SE CANCELÓ");
                        System.out.println("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("SABE QUE PASÓ CON EL PENDIENTE WE");
                    }
                });
    }

    public void actualizarReta(String numero1,String numero2){
        Pendientes p = new Pendientes(numero1,true);

        mDatabase.child("pendientes").child(numero2).setValue(p)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }
    public void actualizarPartida(int punt1,int punt2,int mov1,int mov2,int turnos, String n1,String n2){
        Retas rr = new Retas(punt1,punt2,mov1,mov2,turnos);

        mDatabase.child("retas").child(n1+"-"+n2).setValue(rr)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

}
