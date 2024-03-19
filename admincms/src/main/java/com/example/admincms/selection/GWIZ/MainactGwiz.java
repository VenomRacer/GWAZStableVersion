package com.example.admincms.selection.GWIZ;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.admincms.R;
import com.example.admincms.selection.GWIZ.Parts.Strings.Strings;

public class MainactGwiz extends AppCompatActivity {

    CardView strings,fretboard,nut,bridge,pegs,pickups,body, rod;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainact_gwiz);

        strings = findViewById(R.id.strings);
        fretboard = findViewById(R.id.fretboard);
        nut = findViewById(R.id.nut);
        bridge = findViewById(R.id.bridge);
        pegs = findViewById(R.id.pegs);
        pickups = findViewById(R.id.pickups);
        body = findViewById(R.id.body);
        rod = findViewById(R.id.rod);

        strings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainactGwiz.this, Strings.class));
            }
        });

    }
}