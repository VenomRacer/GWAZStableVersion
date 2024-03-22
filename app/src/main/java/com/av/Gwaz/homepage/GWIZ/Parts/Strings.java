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

public class Strings extends AppCompatActivity {

    CardView traditional, nylon;
    DatabaseReference trastring,nystring;
    StorageReference trastore, nystore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strings);

        // element initialization
        traditional = findViewById(R.id.traditional);
        nylon = findViewById(R.id.nylon);

        // For nylon string
        nystring = FirebaseDatabase.getInstance().getReference().child("Service").child("GWIZ")
                .child("Strings").child("NylonStrings");
        nystore = FirebaseStorage.getInstance().getReference().child("gwazPic").child("GWIZ")
                .child("Strings").child("NylonStrings");


        // For traditional string
        trastring = FirebaseDatabase.getInstance().getReference().child("Service").child("GWIZ")
                .child("Strings").child("TraditionalStrings");
        trastore = FirebaseStorage.getInstance().getReference().child("gwazPic").child("GWIZ")
                .child("Strings").child("TraditionalStrings");

        traditional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass databaseReference to Traditional activity
                Intent intent = new Intent(Strings.this, Partview.class);
                intent.putExtra("TITLE","Traditional");
                intent.putExtra("datab", trastring.toString());
                intent.putExtra("store", trastore.toString());
                startActivity(intent);
            }
        });

        nylon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Strings.this, Partview.class);
                intent.putExtra("TITLE","Nylon");
                intent.putExtra("datab", nystring.toString());
                intent.putExtra("store", nystore.toString());
                startActivity(intent);

            }
        });
    }
}