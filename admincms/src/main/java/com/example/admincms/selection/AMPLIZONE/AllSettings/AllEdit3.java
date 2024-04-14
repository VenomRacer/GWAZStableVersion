package com.example.admincms.selection.AMPLIZONE.AllSettings;

import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.example.admincms.R;

public class AllEdit3 extends AppCompatActivity {

    private Button nxtButton;
    private CheckBox overdriveBox, distortionBox, fuzzBox, delayBox, reverbBox, chorusBox, flangerBox, phaserBox, tremoloBox, wahBox, compressorBox;
    private Vibrator vibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_edit3);

        nxtButton = findViewById(R.id.nxtButton);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        //initialize checkBoxes

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

        //retrieve main info
        String setName = getIntent().getStringExtra("setName");
        String genre = getIntent().getStringExtra("genre");
        String by = getIntent().getStringExtra("by");
        String amp = getIntent().getStringExtra("ampUsed");
        String desc = getIntent().getStringExtra("description");
        String img = getIntent().getStringExtra("imageUrl");
        String aud = getIntent().getStringExtra("audioUrl");
        String key = getIntent().getStringExtra("key");

        //retrieve knobs 9
        String bass = getIntent().getStringExtra("bass");
        String drive = getIntent().getStringExtra("drive");
        String gain = getIntent().getStringExtra("gain");
        String gainstage = getIntent().getStringExtra("gainstage");
        String mid = getIntent().getStringExtra("mid");
        String presence = getIntent().getStringExtra("presence");
        String reverb = getIntent().getStringExtra("reverb");
        String tone = getIntent().getStringExtra("tone");
        String treble = getIntent().getStringExtra("treble");

        // Retrieve effects
        boolean chorus = Boolean.parseBoolean(getIntent().getStringExtra("chorus"));
        boolean compressor = Boolean.parseBoolean(getIntent().getStringExtra("compressor"));
        boolean delay = Boolean.parseBoolean(getIntent().getStringExtra("delay"));
        boolean distortion = Boolean.parseBoolean(getIntent().getStringExtra("distortion"));
        boolean flanger = Boolean.parseBoolean(getIntent().getStringExtra("flanger"));
        boolean fuzz = Boolean.parseBoolean(getIntent().getStringExtra("fuzz"));
        boolean overdrive = Boolean.parseBoolean(getIntent().getStringExtra("overdrive"));
        boolean phaser = Boolean.parseBoolean(getIntent().getStringExtra("phaser"));
        boolean reverb1 = Boolean.parseBoolean(getIntent().getStringExtra("reverb1"));
        boolean tremolo = Boolean.parseBoolean(getIntent().getStringExtra("tremolo"));
        boolean wah = Boolean.parseBoolean(getIntent().getStringExtra("wah"));

        // Set checkbox states based on passed data for effects
        chorusBox.setChecked(chorus);
        compressorBox.setChecked(compressor);
        delayBox.setChecked(delay);
        distortionBox.setChecked(distortion);
        flangerBox.setChecked(flanger);
        fuzzBox.setChecked(fuzz);
        overdriveBox.setChecked(overdrive);
        phaserBox.setChecked(phaser);
        reverbBox.setChecked(reverb1);
        tremoloBox.setChecked(tremolo);
        wahBox.setChecked(wah);

        nxtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to start the next activity
                Intent intent = new Intent(AllEdit3.this, AllEdit4.class);
                intent.putExtra("treble", treble);
                intent.putExtra("gain", gain);
                intent.putExtra("bass", bass);
                intent.putExtra("drive", drive);
                intent.putExtra("mid", mid);
                intent.putExtra("presence", presence);
                intent.putExtra("reverb",reverb );
                intent.putExtra("tone", tone);
                intent.putExtra("gainstage", gainstage);

                // Pass effects as strings
                intent.putExtra("chorus", String.valueOf(chorusBox.isChecked()));
                intent.putExtra("compressor", String.valueOf(compressorBox.isChecked()));
                intent.putExtra("delay", String.valueOf(delayBox.isChecked()));
                intent.putExtra("distortion", String.valueOf(distortionBox.isChecked()));
                intent.putExtra("flanger", String.valueOf(flangerBox.isChecked()));
                intent.putExtra("fuzz", String.valueOf(fuzzBox.isChecked()));
                intent.putExtra("overdrive", String.valueOf(overdriveBox.isChecked()));
                intent.putExtra("phaser", String.valueOf(phaserBox.isChecked()));
                intent.putExtra("reverb1", String.valueOf(reverbBox.isChecked()));
                intent.putExtra("tremolo", String.valueOf(tremoloBox.isChecked()));
                intent.putExtra("wah", String.valueOf(wahBox.isChecked()));

                // Pass info
                intent.putExtra("setName", setName);
                intent.putExtra("by", by);
                intent.putExtra("imageUrl", img);
                intent.putExtra("audioUrl", aud);
                intent.putExtra("ampUsed", amp);
                intent.putExtra("description", genre);
                intent.putExtra("genre", desc);
                intent.putExtra("key", key);

                vibrate();
                startActivity(intent);
            }
        });
    }

    private void vibrate() {
        // Vibrate for 100 milliseconds
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(100);
        }
    }
}