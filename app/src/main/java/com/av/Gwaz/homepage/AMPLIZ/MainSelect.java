package com.av.Gwaz.homepage.AMPLIZ;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.av.Gwaz.R;
import com.av.Gwaz.homepage.AMPLIZ.AllSettings.AllSettings;
import com.av.Gwaz.homepage.AMPLIZ.PopularSettings.PopularSettings;

public class MainSelect extends AppCompatActivity {

    private CardView genre,pop,all;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_select);

        genre = findViewById(R.id.genre);
        pop = findViewById(R.id.pop);
        all = findViewById(R.id.all);




        pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainSelect.this, PopularSettings.class));
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainSelect.this, AllSettings.class));
            }
        });


    }


}