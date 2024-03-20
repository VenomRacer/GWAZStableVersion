package com.example.admincms.selection.GWIZ;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.admincms.R;
import com.example.admincms.selection.GWIZ.Parts.Bridge.Bridge;
import com.example.admincms.selection.GWIZ.Parts.Fretboard.Fretboard;
import com.example.admincms.selection.GWIZ.Parts.Partview;
import com.example.admincms.selection.GWIZ.Parts.Strings.Strings;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainactGwiz extends AppCompatActivity {

    CardView strings,fretboard,nut,bridge,pegs,pickups,body, rod;

    DatabaseReference nutstring,rodstring;
    StorageReference nutstore, rodstore;

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

        // For nylon string
        nutstring = FirebaseDatabase.getInstance().getReference().child("Service").child("GWIZ")
                .child("Nut");
        nutstore = FirebaseStorage.getInstance().getReference().child("gwazPic").child("GWIZ")
                .child("Nut");

        strings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainactGwiz.this, Strings.class));
            }
        });

        fretboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainactGwiz.this, Fretboard.class));
            }
        });

        nut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainactGwiz.this, Partview.class);
                intent.putExtra("TITLE","Nut");
                intent.putExtra("datab", nutstring.toString());
                intent.putExtra("store", nutstore.toString());
                startActivity(intent);
            }
        });

        bridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainactGwiz.this, Bridge.class));
            }
        });

    }
}