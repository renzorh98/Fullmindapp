package com.example.fullmindapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Inicio extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Button Bexit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario);

        mAuth = FirebaseAuth.getInstance();

        Bexit = (Button)findViewById(R.id.exit);
        Bexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();

            }
        });
    }
}
