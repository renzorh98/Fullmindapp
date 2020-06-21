package com.example.fullmindapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private Button BLogin;
    private TextView BOlvidaste;

    private TextView user;
    private TextView pass;

    private FirebaseAuth mAuth;
    private String email;
    private String password;

    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        user = (TextView)findViewById(R.id.et_nm);
        pass = (TextView)findViewById(R.id.et_contrasena);

        BLogin = (Button)findViewById(R.id.login);
        BOlvidaste = (TextView) findViewById(R.id.remember);

        BLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = user.getText().toString().trim();
                password = pass.getText().toString().trim();
                if(!email.isEmpty() && !password.isEmpty()){
                    LoginUsuario();
                }else{
                    Toast.makeText(Login.this,"Complete los campos necesarios.",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private void LoginUsuario() {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(Login.this,Inicio.class));
                    finish();
                }else{
                    Toast.makeText(Login.this,"Error en Email o Contraseña, compruebe los datos.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
