package com.example.admincms.selection.AMPLIZONE.AllSettings;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.admincms.R;

public class SettingsView extends AppCompatActivity {

    private TextView trebleText,gainText,bassText,driveText,midText,presenceText,reverbText,toneText,gainstagingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_view);

        trebleText = findViewById(R.id.trebleText);
        gainText = findViewById(R.id.gainText);
        bassText = findViewById(R.id.bassText);
        driveText = findViewById(R.id.driveText);
        midText = findViewById(R.id.midText);
        presenceText = findViewById(R.id.presenceText);
        reverbText = findViewById(R.id.reverbText);
        toneText = findViewById(R.id.toneText);
        gainstagingText = findViewById(R.id.gainstagingText);

        //retrieve knobs 9
        String bass = getIntent().getStringExtra("BASS");
        String drive = getIntent().getStringExtra("DRIVE");
        String gain = getIntent().getStringExtra("GAIN");
        String gainstage = getIntent().getStringExtra("GAINSTAGE");
        String mid = getIntent().getStringExtra("MID");
        String presence = getIntent().getStringExtra("PRESENCE");
        String reverb = getIntent().getStringExtra("REVERB");
        String tone = getIntent().getStringExtra("TONE");
        String treble = getIntent().getStringExtra("TREBLE");
        //setting text
        trebleText.setText(treble);
        gainText.setText(gain);
        bassText.setText(bass);
        driveText.setText(drive);
        midText.setText(mid);
        presenceText.setText(presence);
        reverbText.setText(reverb);
        toneText.setText(tone);
        gainstagingText.setText(gainstage);
    }
}