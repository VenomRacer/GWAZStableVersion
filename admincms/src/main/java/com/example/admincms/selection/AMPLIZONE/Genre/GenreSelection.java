package com.example.admincms.selection.AMPLIZONE.Genre;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.admincms.R;

public class GenreSelection extends AppCompatActivity {

    private CardView pop,rock,blues,metal,jazz,country,clean,funk,reggae;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_selection);

        pop = findViewById(R.id.pop);
        rock = findViewById(R.id.rock);
        blues = findViewById(R.id.blues);
        metal =findViewById(R.id.metal);
        jazz =  findViewById(R.id.jazz);
        country = findViewById(R.id.country);
        clean  = findViewById(R.id.clean);
        funk= findViewById(R.id.funk);
        reggae = findViewById(R.id.reggae);

        pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenreSelection.this, Genre.class);
                intent.putExtra("Genre", "Pop");
                startActivity(intent);
            }
        });

        rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenreSelection.this, Genre.class);
                intent.putExtra("Genre", "Rock");
                startActivity(intent);
            }
        });

        blues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenreSelection.this, Genre.class);
                intent.putExtra("Genre", "Blues");
                startActivity(intent);
            }
        });

        metal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenreSelection.this, Genre.class);
                intent.putExtra("Genre", "Metal");
                startActivity(intent);
            }
        });

        jazz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenreSelection.this, Genre.class);
                intent.putExtra("Genre", "Jazz");
                startActivity(intent);
            }
        });

        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenreSelection.this, Genre.class);
                intent.putExtra("Genre", "Country");
                startActivity(intent);
            }
        });

        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenreSelection.this, Genre.class);
                intent.putExtra("Genre", "Clean");
                startActivity(intent);
            }
        });

        funk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenreSelection.this, Genre.class);
                intent.putExtra("Genre", "Funk");
                startActivity(intent);
            }
        });

        reggae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenreSelection.this, Genre.class);
                intent.putExtra("Genre", "Reggae");
                startActivity(intent);
            }
        });


    }
}