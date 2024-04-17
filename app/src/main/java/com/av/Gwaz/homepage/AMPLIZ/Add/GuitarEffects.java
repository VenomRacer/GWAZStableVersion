package com.av.Gwaz.homepage.AMPLIZ.Add;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.av.Gwaz.R;
import com.av.Gwaz.chat.setting;

public class GuitarEffects extends AppCompatActivity {

    private Button nxtButton;
    private CheckBox overdriveBox, distortionBox, fuzzBox, delayBox, reverbBox, chorusBox, flangerBox, phaserBox, tremoloBox, wahBox, compressorBox;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guitar_effects);

        nxtButton = findViewById(R.id.nxtButton);

        //initialize checkBoxes
        // Initialize checkboxes and button
        overdriveBox = findViewById(R.id.overdriveBox);
        distortionBox = findViewById(R.id.distortionBox);
        fuzzBox = findViewById(R.id.fuzzBox);
        delayBox = findViewById(R.id.delayBox);
        reverbBox = findViewById(R.id.reverbBox);
        chorusBox = findViewById(R.id.chorusBox);
        flangerBox = findViewById(R.id.flangerBox);
        phaserBox = findViewById(R.id.phaserBox);
        tremoloBox = findViewById(R.id.tremoloBox);
        wahBox = findViewById(R.id.wahBox);
        compressorBox = findViewById(R.id.compressorBox);

        //Passed data from previous activity
        Intent intent = getIntent();
        String trebleValue = intent.getStringExtra("treble");
        String gainValue = intent.getStringExtra("gain");
        String bassValue = intent.getStringExtra("bass");
        String driveValue = intent.getStringExtra("drive");
        String midValue = intent.getStringExtra("mid");
        String presenceValue = intent.getStringExtra("presence");
        String reverbValue = intent.getStringExtra("reverb");
        String toneValue = intent.getStringExtra("tone");
        String gainstageValue = intent.getStringExtra("gainstage");

        nxtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a Bundle to store all data
                Intent intent = getIntent();
                Bundle bundle = new Bundle();

                // Put checkbox values in the Bundle
                bundle.putBoolean("overdrive", overdriveBox.isChecked());
                bundle.putBoolean("distortion", distortionBox.isChecked());
                bundle.putBoolean("fuzz", fuzzBox.isChecked());
                bundle.putBoolean("delay", delayBox.isChecked());
                bundle.putBoolean("reverbp", reverbBox.isChecked());
                bundle.putBoolean("chorus", chorusBox.isChecked());
                bundle.putBoolean("flanger", flangerBox.isChecked());
                bundle.putBoolean("phaser", phaserBox.isChecked());
                bundle.putBoolean("tremolo", tremoloBox.isChecked());
                bundle.putBoolean("wah", wahBox.isChecked());
                bundle.putBoolean("compressor", compressorBox.isChecked());

                // Passed data from previous activity

                bundle.putString("treble", intent.getStringExtra("treble"));
                bundle.putString("gain", intent.getStringExtra("gain"));
                bundle.putString("bass", intent.getStringExtra("bass"));
                bundle.putString("drive", intent.getStringExtra("drive"));
                bundle.putString("mid", intent.getStringExtra("mid"));
                bundle.putString("presence", intent.getStringExtra("presence"));
                bundle.putString("reverbk", intent.getStringExtra("reverb"));
                bundle.putString("tone", intent.getStringExtra("tone"));
                bundle.putString("gainstage", intent.getStringExtra("gainstage"));

                // Start the next activity and pass the Bundle
                Intent nextIntent = new Intent(GuitarEffects.this, FinalizeAmp.class);
                nextIntent.putExtras(bundle);
                startActivity(nextIntent);
                finish();
            }
        });



    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GuitarEffects.this, setting.class));
        super.onBackPressed();
        finish();
    }
}