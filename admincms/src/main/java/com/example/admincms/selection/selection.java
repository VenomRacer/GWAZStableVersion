package com.example.admincms.selection;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.admincms.R;
import com.example.admincms.login.login;
import com.example.admincms.selection.GWIZ.MainactGwiz;

public class selection extends AppCompatActivity {

    Button logout;
    ImageView guitarwiz, amplizone, leaderboard, users;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        logout  = findViewById(R.id.logout);
        guitarwiz = findViewById(R.id.guitarwiz);
        amplizone = findViewById(R.id.amplizone);
        leaderboard = findViewById(R.id.leaderboard);
        users = findViewById(R.id.users);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(selection.this, login.class));
            }
        });

        guitarwiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(selection.this, MainactGwiz.class));
            }
        });

    }
}