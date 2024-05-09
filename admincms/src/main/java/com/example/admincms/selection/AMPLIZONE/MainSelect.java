package com.example.admincms.selection.AMPLIZONE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.admincms.R;
import com.example.admincms.selection.AMPLIZONE.AllSettings.AllSettings;
import com.example.admincms.selection.AMPLIZONE.Genre.GenreSelection;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainSelect extends AppCompatActivity {

    CardView genre,pop, all;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_select);

        //all settings reference
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Service").child("AMPLIZONE").child("Amplifier");
        storageReference = FirebaseStorage.getInstance().getReference().child("gwazPic").child("AMPLIZONE").child("Amplifier");

        genre = findViewById(R.id.genre);
        pop = findViewById(R.id.pop);
        all = findViewById(R.id.all);

        genre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainSelect.this, GenreSelection.class));
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainSelect.this, AllSettings.class);
                intent.putExtra("databaseReference", databaseReference.toString());
                intent.putExtra("storageReference", storageReference.toString());
                intent.putExtra("AllTag", "All Settings");
                startActivity(intent);
            }
        });






    }
}