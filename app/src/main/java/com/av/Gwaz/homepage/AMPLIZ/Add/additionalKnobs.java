package com.av.Gwaz.homepage.AMPLIZ.Add;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.akaita.android.circularseekbar.CircularSeekBar;
import com.av.Gwaz.R;
import com.av.Gwaz.chat.setting;

import java.text.DecimalFormat;

public class additionalKnobs extends AppCompatActivity {

    private CircularSeekBar reverb,tone,gainstage;
    private Vibrator vibrator;
    private Button nextBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_knobs);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        nextBtn = findViewById(R.id.nextBtn);

        reverb = (CircularSeekBar) findViewById(R.id.reverb);
        reverb.setProgressTextFormat(new DecimalFormat("#"));

        tone = (CircularSeekBar) findViewById(R.id.tone);
        tone.setProgressTextFormat(new DecimalFormat("#"));

        gainstage = (CircularSeekBar) findViewById(R.id.gainstage);
        gainstage.setProgressTextFormat(new DecimalFormat("#"));



        Intent intent = getIntent();
        String trebleValue = intent.getStringExtra("treble");
        String gainValue = intent.getStringExtra("gain");
        String bassValue = intent.getStringExtra("bass");
        String driveValue = intent.getStringExtra("drive");
        String midValue = intent.getStringExtra("mid");
        String presenceValue = intent.getStringExtra("presence");


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Retrieve the current progress values
                float prog1 = reverb.getProgress();
                float prog2 = tone.getProgress();
                float prog3 = gainstage.getProgress();

                // Convert progress values to integers
                int int1 = (int) prog1;
                int int2 = (int) prog2;
                int int3 = (int) prog3;

                // Convert integers to strings
                String reverbValue = String.valueOf(int1);
                String toneValue = String.valueOf(int2);
                String gainstageValue = String.valueOf(int3);

                // Create intent to start the next activity
                Intent intent = new Intent(additionalKnobs.this, GuitarEffects.class);
                intent.putExtra("treble", trebleValue);
                intent.putExtra("gain", gainValue);
                intent.putExtra("bass", bassValue);
                intent.putExtra("drive", driveValue);
                intent.putExtra("mid", midValue);
                intent.putExtra("presence", presenceValue);
                intent.putExtra("reverb",reverbValue );
                intent.putExtra("tone", toneValue);
                intent.putExtra("gainstage", gainstageValue);
                vibrate();
                startActivity(intent);
                finish();


            }
        });

        //for reverb
        reverb.setOnCircularSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar seekBar, float progress, boolean fromUser) {

                if (progress < 5) {
                    reverb.setRingColor(Color.GREEN);
                } else if (progress < 8) {
                    reverb.setRingColor(Color.HSVToColor(new float[]{30.0f, 1.0f, 1.0f}));
                } else if (progress >= 8) {
                    reverb.setRingColor(Color.RED);
                }
                int intProgress = (int) progress;
                reverb.setProgressText(String.valueOf(intProgress));

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
        tone.setOnCircularSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar seekBar, float progress, boolean fromUser) {

                if (progress < 5) {
                    tone.setRingColor(Color.GREEN);
                } else if (progress < 8) {
                    tone.setRingColor(Color.HSVToColor(new float[]{30.0f, 1.0f, 1.0f}));
                } else if (progress >= 8) {
                    tone.setRingColor(Color.RED);
                }
                int intProgress = (int) progress;
                tone.setProgressText(String.valueOf(intProgress));

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

        gainstage.setOnCircularSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar seekBar, float progress, boolean fromUser) {

                if (progress < 5) {
                    gainstage.setRingColor(Color.GREEN);
                } else if (progress < 8) {
                    gainstage.setRingColor(Color.HSVToColor(new float[]{30.0f, 1.0f, 1.0f}));
                } else if (progress >= 8) {
                    gainstage.setRingColor(Color.RED);
                }
                int intProgress = (int) progress;
                gainstage.setProgressText(String.valueOf(intProgress));

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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(additionalKnobs.this, setting.class));
        super.onBackPressed();
        finish();
    }
}