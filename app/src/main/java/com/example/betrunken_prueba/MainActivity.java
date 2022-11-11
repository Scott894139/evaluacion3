package com.example.betrunken_prueba;

import androidx.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText correo;
    private EditText password;
    private EditText confirmacion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth= FirebaseAuth.getInstance();

        correo=findViewById(R.id.Correo);
        password=findViewById(R.id.Contrasena);
        confirmacion=findViewById(R.id.Confirmar);
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI (currentUser);
    }

    public void registrarUsuario(View view){

        if(password.getText().toString().equals(confirmacion.getText().toString())){

            mAuth.createUserWithEmailAndPassword(correo.getText().toString().trim(),password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                //Log.d(TAG,"createUserWithEmail:succes");
                                Toast.makeText(getApplicationContext(),"Usuario Creado",
                                        Toast.LENGTH_SHORT).show();

                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent i =new Intent(getApplicationContext(),Sesion.class);
                                startActivity(i);

                                //updateUI(user);
                            } else{
                                //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(),"Error en la Creacion de Usuario",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });

        }else{
            Toast.makeText(this,"Contraseñas no coinciden",
                    Toast.LENGTH_SHORT).show();
        }

    }
    public void IniciarSesion(View view){
        Intent i = new Intent(this,Sesion.class);
        startActivity(i);
    }

}