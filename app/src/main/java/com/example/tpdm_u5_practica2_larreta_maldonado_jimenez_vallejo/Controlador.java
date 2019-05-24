package com.example.tpdm_u5_practica2_larreta_maldonado_jimenez_vallejo;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Controlador {

    private DatabaseReference mDatabase;

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

   /* private void mostrar(String i){
        FirebaseDatabase.getInstance().getReference().child("hospitales").child(i)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Hospitales h = dataSnapshot.getValue(Hospitales.class);

                        if(h!=null) {
                            id.setText(h.id);
                            nombre.setText(h.nombre);
                            capacidad.setText(h.capacidad);
                            domicilio.setText(h.domicilio);
                            anio.setText(h.anio);
                            eliminar.setEnabled(true);
                            actualizar.setEnabled(true);
                        } else {
                            mensaje("Error","No se encontró hospital a mostrar");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }*/
}
