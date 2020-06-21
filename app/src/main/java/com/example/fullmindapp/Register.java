package com.example.fullmindapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private Button Bregistrar;
    private Button Bcancelar;

    private EditText ETnombre;
    private EditText ETapellido;
    private EditText ETusuario;
    private EditText ETpass;

    private String name="";
    private String apellido="";
    private String user="";
    private String pass="";

    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    private FirebaseAuth.AuthStateListener mAuthState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_usuario);

        mAuth = FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        ETnombre = (EditText)findViewById(R.id.et_nm);
        ETapellido = (EditText)findViewById(R.id.et_ap);
        ETusuario = (EditText)findViewById(R.id.et_nu);
        ETpass = (EditText)findViewById(R.id.et_pu);

        Bregistrar = (Button)findViewById(R.id.rregistrar);
        Bregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = ETnombre.getText().toString().trim();
                apellido = ETapellido.getText().toString().trim();
                user = ETusuario.getText().toString().trim();
                pass = ETpass.getText().toString().trim();

                if(!name.isEmpty() && !apellido.isEmpty() && !user.isEmpty() && !pass.isEmpty()){
                    if(pass.length()>=6){
                        registrarUsuario();
                    }else{
                        Toast.makeText(Register.this,"El password debe tener al menos 6 caracteres.",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(Register.this,"Complete los campos necesarios.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        Bcancelar = (Button)findViewById(R.id.cancelar);
        Bcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void registrarUsuario() {
        mAuth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String id = mAuth.getCurrentUser().getUid();
                    Toast.makeText(Register.this,"Registro correcto de usuario.",Toast.LENGTH_SHORT).show();
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("ape", apellido);
                    map.put("email", user);
                    map.put("password", pass);
                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                Toast.makeText(Register.this,"Registo correcto de datos.",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                Toast.makeText(Register.this,"No se pudo registrar datos."+task2.getException(),Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }
                else{
                    Toast.makeText(Register.this,"No se pudo registrar este Usuario.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
