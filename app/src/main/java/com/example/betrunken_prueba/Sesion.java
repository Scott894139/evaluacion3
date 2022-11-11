package com.example.betrunken_prueba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Sesion extends AppCompatActivity {

    private EditText correo;
    private EditText contrasena;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion);

        mAuth= FirebaseAuth.getInstance();

        correo=findViewById(R.id.Correo);
        contrasena=findViewById(R.id.Contrasena);

    }


    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI (currentUser);
    }

    public void sesion(View view){
        mAuth.signInWithEmailAndPassword(correo.getText().toString().trim(),contrasena.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user= mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(),"Bienvenid@ Usuari@",
                                    Toast.LENGTH_SHORT).show();
                            Intent i= new Intent(getApplicationContext(),Pasajero.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(getApplicationContext(),"Error en el ingreso",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    public void irregistro(View view){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}