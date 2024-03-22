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

public class TuningPegs extends AppCompatActivity {

    CardView conventional, locking;
    DatabaseReference constring,lockstring;
    StorageReference constore, lockstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuning_pegs);

        // element initialization
        conventional = findViewById(R.id.conventional);
        locking = findViewById(R.id.locking);

        // For conventional
        constring = FirebaseDatabase.getInstance().getReference().child("Service").child("GWIZ")
                .child("TuningPegs").child("Conventional");
        constore = FirebaseStorage.getInstance().getReference().child("gwazPic").child("GWIZ")
                .child("TuningPegs").child("Conventional");

        // For locking
        lockstring = FirebaseDatabase.getInstance().getReference().child("Service").child("GWIZ")
                .child("TuningPegs").child("Locking");
        lockstore = FirebaseStorage.getInstance().getReference().child("gwazPic").child("GWIZ")
                .child("TuningPegs").child("Locking");

        conventional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass databaseReference to Traditional activity
                Intent intent = new Intent(TuningPegs.this, Partview.class);
                intent.putExtra("TITLE","Conventional");
                intent.putExtra("datab", constring.toString());
                intent.putExtra("store", constore.toString());
                startActivity(intent);
            }
        });

        locking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass databaseReference to Traditional activity
                Intent intent = new Intent(TuningPegs.this, Partview.class);
                intent.putExtra("TITLE","Locking");
                intent.putExtra("datab", lockstring.toString());
                intent.putExtra("store", lockstore.toString());
                startActivity(intent);
            }
        });
    }
}