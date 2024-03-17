package com.example.admincms.selection.GWIZ;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.admincms.R;

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

    }
}