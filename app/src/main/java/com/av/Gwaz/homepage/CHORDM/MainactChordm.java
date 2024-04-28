package com.av.Gwaz.homepage.CHORDM;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.av.Gwaz.R;
import com.av.Gwaz.homepage.CHORDM.Classic.LevelSelec;

public class MainactChordm extends AppCompatActivity {

    ImageView classicmode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainact_chordm);

        classicmode = findViewById(R.id.classicmode);

        classicmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainactChordm.this, LevelSelec.class));

            }
        });
    }
}