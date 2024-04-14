package com.example.admincms.selection.AMPLIZONE.AllSettings;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.akaita.android.circularseekbar.CircularSeekBar;
import com.example.admincms.R;

import java.text.DecimalFormat;

public class AllEdit2 extends AppCompatActivity {

    private CircularSeekBar reverbc,tonec,gainstagec;
    private Vibrator vibrator;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_edit2);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        nextBtn = findViewById(R.id.nextBtn);

        reverbc = (CircularSeekBar) findViewById(R.id.reverb);
        reverbc.setProgressTextFormat(new DecimalFormat("#"));

        tonec = (CircularSeekBar) findViewById(R.id.tone);
        tonec.setProgressTextFormat(new DecimalFormat("#"));

        gainstagec = (CircularSeekBar) findViewById(R.id.gainstage);
        gainstagec.setProgressTextFormat(new DecimalFormat("#"));

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

        //retrieve effects 11
        String chorus = getIntent().getStringExtra("chorus");
        String compressor = getIntent().getStringExtra("compressor");
        String delay = getIntent().getStringExtra("delay");
        String distortion = getIntent().getStringExtra("distortion");
        String flanger = getIntent().getStringExtra("flanger");
        String fuzz = getIntent().getStringExtra("fuzz");
        String overdrive = getIntent().getStringExtra("overdrive");
        String phaser = getIntent().getStringExtra("phaser");
        String reverb1 = getIntent().getStringExtra("reverb1");
        String tremolo = getIntent().getStringExtra("tremolo");
        String wah = getIntent().getStringExtra("wah");

        gainstagec.setProgress(Float.parseFloat(gainstage));
        reverbc.setProgress(Float.parseFloat(reverb));
        tonec.setProgress(Float.parseFloat(tone));

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Retrieve the current progress values
                float prog1 = reverbc.getProgress();
                float prog2 = tonec.getProgress();
                float prog3 = gainstagec.getProgress();

                // Convert progress values to integers
                int int1 = (int) prog1;
                int int2 = (int) prog2;
                int int3 = (int) prog3;

                // Convert integers to strings
                String reverbValue = String.valueOf(int1);
                String toneValue = String.valueOf(int2);
                String gainstageValue = String.valueOf(int3);

                // Create intent to start the next activity
                Intent intent = new Intent(AllEdit2.this, AllEdit3.class);
                intent.putExtra("treble", treble);
                intent.putExtra("gain", gain);
                intent.putExtra("bass", bass);
                intent.putExtra("drive", drive);
                intent.putExtra("mid", mid);
                intent.putExtra("presence", presence);
                intent.putExtra("reverb",reverbValue );
                intent.putExtra("tone", toneValue);
                intent.putExtra("gainstage", gainstageValue);

                // Pass effects
                intent.putExtra("chorus", chorus);
                intent.putExtra("compressor", compressor);
                intent.putExtra("delay", delay);
                intent.putExtra("distortion", distortion);
                intent.putExtra("flanger", flanger);
                intent.putExtra("fuzz", fuzz);
                intent.putExtra("overdrive", overdrive);
                intent.putExtra("phaser", phaser);
                intent.putExtra("reverb1", reverb1);
                intent.putExtra("tremolo", tremolo);
                intent.putExtra("wah", wah);

                // Pass info
                intent.putExtra("setName", setName);
                intent.putExtra("by", by);
                intent.putExtra("imageUrl", img);
                intent.putExtra("audioUrl", aud);
                intent.putExtra("ampUsed", amp);
                intent.putExtra("description", genre);
                intent.putExtra("genre", desc);
                intent.putExtra("key",key);

                vibrate();
                startActivity(intent);


            }
        });

        reverbc.setOnCircularSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar seekBar, float progress, boolean fromUser) {

                if (progress < 5) {
                    reverbc.setRingColor(Color.GREEN);
                } else if (progress < 8) {
                    reverbc.setRingColor(Color.HSVToColor(new float[]{30.0f, 1.0f, 1.0f}));
                } else if (progress >= 8) {
                    reverbc.setRingColor(Color.RED);
                }
                int intProgress = (int) progress;
                reverbc.setProgressText(String.valueOf(intProgress));

            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {
                vibrate();
            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {
                vibrate();
            }
        });

        //for reverb
        tonec.setOnCircularSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar seekBar, float progress, boolean fromUser) {

                if (progress < 5) {
                    tonec.setRingColor(Color.GREEN);
                } else if (progress < 8) {
                    tonec.setRingColor(Color.HSVToColor(new float[]{30.0f, 1.0f, 1.0f}));
                } else if (progress >= 8) {
                    tonec.setRingColor(Color.RED);
                }
                int intProgress = (int) progress;
                tonec.setProgressText(String.valueOf(intProgress));

            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {
                vibrate();
            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {
                vibrate();
            }
        });

        gainstagec.setOnCircularSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar seekBar, float progress, boolean fromUser) {

                if (progress < 5) {
                    gainstagec.setRingColor(Color.GREEN);
                } else if (progress < 8) {
                    gainstagec.setRingColor(Color.HSVToColor(new float[]{30.0f, 1.0f, 1.0f}));
                } else if (progress >= 8) {
                    gainstagec.setRingColor(Color.RED);
                }
                int intProgress = (int) progress;
                gainstagec.setProgressText(String.valueOf(intProgress));

            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {
                vibrate();
            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {
                vibrate();
            }
        });
    }

    private void vibrate() {
        // Vibrate for 50 milliseconds
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(100);
        }
    }
}