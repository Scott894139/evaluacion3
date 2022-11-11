package com.example.betrunken_prueba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.betrunken_prueba.PasajerosBD.PasajeroBD;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Pasajero extends AppCompatActivity {

    private TextView nombre;
    private TextView rut;
    private TextView telefono;

    private Button Agregar;
    private Button Buscar;
    private Button eliminar;



    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef= database.getReference("Pasajeros");
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasajero);

        nombre=findViewById(R.id.Nombre);
        rut=findViewById(R.id.Rut);
        telefono=findViewById(R.id.Telefono);

        Agregar=findViewById(R.id.btn1);
        Buscar=findViewById(R.id.btn2);
        eliminar=findViewById(R.id.btn3);

        Agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgregarPasajero(nombre.getText().toString(),rut.getText().toString(),telefono.getText().toString());
            }
        });

        Buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarPasajero(nombre.getText().toString());
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarPasajero(nombre.getText().toString());
            }
        });



        auth=FirebaseAuth.getInstance();

    }

    public void buscarPasajero(String nombre){
        myRef.child(auth.getCurrentUser().getUid()).child(nombre).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PasajeroBD pasajeroBDtemporal=snapshot.getValue(PasajeroBD.class);
                if(pasajeroBDtemporal!=null){
                    rut.setText(pasajeroBDtemporal.getRut()+"");
                    telefono.setText(pasajeroBDtemporal.getTelefono()+"");
                }else{
                    Toast.makeText(Pasajero.this,"No existe pasajero",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void eliminarPasajero(String nombre){
        myRef.child(auth.getCurrentUser().getUid()).child(nombre).removeValue();
        Toast.makeText(Pasajero.this,"Pasajero Eliminado-a",Toast.LENGTH_SHORT).show();
    }


    public void AgregarPasajero(String pasajero,String rut,String telefono){
        PasajeroBD pasajeroBD=new PasajeroBD(pasajero,rut,telefono);
        if (auth.getCurrentUser()!=null){
            myRef.child(auth.getCurrentUser().getUid()).child(pasajero).setValue(pasajeroBD).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(Pasajero.this,"Pasajero Agregado",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Pasajero.this,"Error",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}