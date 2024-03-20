package com.example.admincms.selection.GWIZ.Parts.Bridge;

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

public class Bridge extends AppCompatActivity {

    CardView electg, actg;
    DatabaseReference electstring,actstring;
    StorageReference electstore, actstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bridge);

        // Initialization
        electg = findViewById(R.id.electg);
        actg = findViewById(R.id.actg);

        // For eg bridge
        electstring = FirebaseDatabase.getInstance().getReference().child("Service").child("GWIZ")
                .child("Bridge").child("ElectricGuitar");
        electstore = FirebaseStorage.getInstance().getReference().child("gwazPic").child("GWIZ")
                .child("Bridge").child("ElectricGuitar");

        // For acoustic bridge
        actstring = FirebaseDatabase.getInstance().getReference().child("Service").child("GWIZ")
                .child("Bridge").child("AcousticGuitar");
        actstore = FirebaseStorage.getInstance().getReference().child("gwazPic").child("GWIZ")
                .child("Bridge").child("AcousticGuitar");

        electg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Bridge.this, Partview.class);
                intent.putExtra("TITLE","Electric Guitar");
                intent.putExtra("datab", electstring.toString());
                intent.putExtra("store", electstore.toString());
                startActivity(intent);
            }
        });


        actg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Bridge.this, Partview.class);
                intent.putExtra("TITLE","Acoustic Guitar");
                intent.putExtra("datab", actstring.toString());
                intent.putExtra("store", actstore.toString());
                startActivity(intent);
            }
        });

    }
}