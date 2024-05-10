package com.example.admincms.selection.GWIZ.Parts.Fretboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.admincms.R;
import com.example.admincms.selection.GWIZ.Parts.Partview;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Fretboard extends AppCompatActivity {

    CardView rosewood, maple;
    DatabaseReference rosestring,mapstring;
    StorageReference rosestore, mapstore;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fretboard);

        //Element initialization
        rosewood = findViewById(R.id.rosewood);
        maple = findViewById(R.id.maple);


        // For rosewood
        rosestring = FirebaseDatabase.getInstance().getReference().child("Service").child("GWIZ")
                .child("Fretboard").child("Rosewood");
        rosestore = FirebaseStorage.getInstance().getReference().child("gwazPic").child("GWIZ")
                .child("Fretboard").child("Rosewood");

        // For maple
        mapstring = FirebaseDatabase.getInstance().getReference().child("Service").child("GWIZ")
                .child("Fretboard").child("Maple");
        mapstore = FirebaseStorage.getInstance().getReference().child("gwazPic").child("GWIZ")
                .child("Fretboard").child("Maple");



        rosewood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass databaseReference to Traditional activity
                Intent intent = new Intent(Fretboard.this, Partview.class);
                intent.putExtra("TITLE","Rosewood");
                intent.putExtra("datab", rosestring.toString());
                intent.putExtra("store", rosestore.toString());
                startActivity(intent);
            }
        });

        maple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass databaseReference to Traditional activity
                Intent intent = new Intent(Fretboard.this, Partview.class);
                intent.putExtra("TITLE","Maple");
                intent.putExtra("datab", mapstring.toString());
                intent.putExtra("store", mapstore.toString());
                startActivity(intent);
            }
        });



    }
}