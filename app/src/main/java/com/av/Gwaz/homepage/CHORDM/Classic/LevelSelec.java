package com.av.Gwaz.homepage.CHORDM.Classic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.av.Gwaz.R;
import com.av.Gwaz.homepage.CHORDM.Classic.Levels.Level1;
import com.av.Gwaz.homepage.CHORDM.Classic.Levels.Level2;
import com.av.Gwaz.homepage.CHORDM.Classic.Levels.Level3;
import com.av.Gwaz.homepage.CHORDM.Classic.Levels.Level4;
import com.av.Gwaz.homepage.CHORDM.Classic.Levels.Level5;

public class LevelSelec extends AppCompatActivity {
    private CardView level1,level2,level3,level4,level5;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selec);

        level1 = findViewById(R.id.level1);
        level2 = findViewById(R.id.level2);
        level3 = findViewById(R.id.level3);
        level4 = findViewById(R.id.level4);
        level5 = findViewById(R.id.level5);

        level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LevelSelec.this, Level1.class));
            }
        });

        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LevelSelec.this, Level2.class));
            }
        });

        level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (LevelSelec.this, Level3.class));
            }
        });

        level4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (LevelSelec.this, Level4.class));
            }
        });

        level5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LevelSelec.this, Level5.class));
            }
        });


    }
}