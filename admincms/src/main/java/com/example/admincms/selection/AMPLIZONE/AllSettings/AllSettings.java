package com.example.admincms.selection.AMPLIZONE.AllSettings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.admincms.R;
import com.example.admincms.selection.AMPLIZONE.Add.AddAmp;

public class AllSettings extends AppCompatActivity {

    ImageView Add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_settings);

        Add = findViewById(R.id.Add);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AllSettings.this, AddAmp.class));

            }
        });
    }
}