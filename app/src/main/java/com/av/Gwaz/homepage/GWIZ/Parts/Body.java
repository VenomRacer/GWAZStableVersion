package com.av.Gwaz.homepage.GWIZ.Parts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.av.Gwaz.R;
import com.av.Gwaz.homepage.GWIZ.Partview;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Body extends AppCompatActivity {

    CardView actg, electg;
    DatabaseReference actstring,electstring;
    StorageReference actstore, electstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);

        actg = findViewById(R.id.actg);
        electg = findViewById(R.id.electg);

        // For acoustic
        actstring = FirebaseDatabase.getInstance().getReference().child("Service").child("GWIZ")
                .child("Body").child("AcousticGuitar");
        actstore = FirebaseStorage.getInstance().getReference().child("gwazPic").child("GWIZ")
                .child("Body").child("AcousticGuitar");

        // For electric
        electstring = FirebaseDatabase.getInstance().getReference().child("Service").child("GWIZ")
                .child("Body").child("ElectricGuitar");
        electstore = FirebaseStorage.getInstance().getReference().child("gwazPic").child("GWIZ")
                .child("Body").child("ElectricGuitar");

        actg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass databaseReference to Traditional activity
                Intent intent = new Intent(Body.this, Partview.class);
                intent.putExtra("TITLE","Acoustic Guitar");
                intent.putExtra("datab", actstring.toString());
                intent.putExtra("store", actstore.toString());
                startActivity(intent);
            }
        });

        electg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass databaseReference to Traditional activity
                Intent intent = new Intent(Body.this, Partview.class);
                intent.putExtra("TITLE","Electric Guitar");
                intent.putExtra("datab", electstring.toString());
                intent.putExtra("store", electstore.toString());
                startActivity(intent);
            }
        });
    }
}