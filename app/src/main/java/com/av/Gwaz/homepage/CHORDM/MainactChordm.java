package com.av.Gwaz.homepage.CHORDM;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.av.Gwaz.R;
import com.av.Gwaz.homepage.CHORDM.Classic.LevelSelec;
import com.av.Gwaz.homepage.CHORDM.Diagram.DLevelSelec;
import com.av.Gwaz.homepage.CHORDM.LeaderboardRecycler.Leaderboard;
import com.av.Gwaz.homepage.CHORDM.Timed.TimeSelect;

public class MainactChordm extends AppCompatActivity {

    private ImageView classicmode,timedmode,diagrammode;
    private ImageView back;
    private ImageView classicpic,timedpic,diagrampic;
    private TextView classicuser,timeduser,diagramuser;
    private ImageView leaderClassic,leaderTimed,leaderDiagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainact_chordm);

        classicmode = findViewById(R.id.classicmode);
        leaderClassic = findViewById(R.id.leaderClassic);


        timedmode = findViewById(R.id.timedmode);
        leaderTimed = findViewById(R.id.leaderTimed);


        diagrammode = findViewById(R.id.diagrammode);
        leaderDiagram = findViewById(R.id.leaderDiag);

        back = findViewById(R.id.back);






        classicmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainactChordm.this, LevelSelec.class));

            }
        });

        leaderClassic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainactChordm.this, Leaderboard.class);
                intent.putExtra("mode", "Classic");
                startActivity(intent);

            }
        });

        timedmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainactChordm.this, TimeSelect.class));
            }
        });

        leaderTimed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainactChordm.this, Leaderboard.class);
                intent.putExtra("mode", "Timed");
                startActivity(intent);

            }
        });

        diagrammode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainactChordm.this, DLevelSelec.class));
            }
        });

        leaderDiagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainactChordm.this, Leaderboard.class);
                intent.putExtra("mode", "Diagram");
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




    }
}