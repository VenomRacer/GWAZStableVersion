package com.example.admincms.selection.GWIZ.Parts.Strings;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.admincms.R;
import com.example.admincms.selection.GWIZ.Parts.Strings.Sub.Traditional.Traditional;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Strings extends AppCompatActivity {

    CardView traditional, nylon;
    DatabaseReference trastring;
    StorageReference imageRef;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strings);

        // element initialization
        traditional = findViewById(R.id.traditional);

        // For traditional string
        trastring = FirebaseDatabase.getInstance().getReference().child("Service").child("GWIZ").child("Strings").child("TraditionalStrings");
        // Storage reference
        // Initialize FirebaseStorage
        imageRef = FirebaseStorage.getInstance().getReference().child("gwazPic").child("GWIZ")
                .child("Strings").child("TraditionalStrings");



        traditional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass databaseReference to Traditional activity
                Intent intent = new Intent(Strings.this, Traditional.class);
                intent.putExtra("trastring", trastring.toString());
                intent.putExtra("trastorage", imageRef.toString());
                startActivity(intent);
            }
        });
    }
}