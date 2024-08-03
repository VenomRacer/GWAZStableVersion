package com.av.Gwaz.homepage.CHORDM.Timed;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.av.Gwaz.R;
import com.av.Gwaz.homepage.CHORDM.Timed.Basic.sixty.fifteen.fifteenb;
import com.av.Gwaz.homepage.CHORDM.Timed.Basic.sixty.sixty.sixtyb;
import com.av.Gwaz.homepage.CHORDM.Timed.Basic.sixty.thirty.thirtyb;

public class TimeSelect extends AppCompatActivity {

    private CardView b60,b30,b15;
    private ImageView back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_select);

        b60 = findViewById(R.id.b60);
        b30 = findViewById(R.id.b30);
        b15 = findViewById(R.id.b15);

        back = findViewById(R.id.back);

        b60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimeSelect.this, sixtyb.class));

            }
        });

        b30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimeSelect.this, thirtyb.class));

            }
        });

        b15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TimeSelect.this, fifteenb.class));

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