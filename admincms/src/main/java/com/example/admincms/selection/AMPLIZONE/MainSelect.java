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
import com.example.admincms.selection.AMPLIZONE.PopularSettings.PopularSettings;

public class MainSelect extends AppCompatActivity {

    CardView genre,pop, all;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_select);

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
                startActivity(new Intent(MainSelect.this, AllSettings.class));
            }
        });

        pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainSelect.this, PopularSettings.class));
            }
        });




    }
}