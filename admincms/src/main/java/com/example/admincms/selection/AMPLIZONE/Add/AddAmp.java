package com.example.admincms.selection.AMPLIZONE.Add;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.akaita.android.circularseekbar.CircularSeekBar;
import com.example.admincms.R;

import java.text.DecimalFormat;

public class AddAmp extends AppCompatActivity {

    private CircularSeekBar treble, gain, bass,drive,mid, presence;
    private TextView sampleTxt;
    private Vibrator vibrator;
    private Button nextBtn;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_amp);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        nextBtn = findViewById(R.id.nextBtn);

        treble = (CircularSeekBar) findViewById(R.id.treble);
        treble.setProgressTextFormat(new DecimalFormat("#"));

        gain = (CircularSeekBar) findViewById(R.id.gain);
        gain.setProgressTextFormat(new DecimalFormat("#"));

        bass = (CircularSeekBar) findViewById(R.id.bass);
        bass.setProgressTextFormat(new DecimalFormat("#"));

        drive = (CircularSeekBar) findViewById(R.id.drive);
        drive.setProgressTextFormat(new DecimalFormat("#"));

        mid = (CircularSeekBar) findViewById(R.id.mid);
        mid.setProgressTextFormat(new DecimalFormat("#"));

        presence = (CircularSeekBar) findViewById(R.id.presence);
        presence.setProgressTextFormat(new DecimalFormat("#"));


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the current progress values
                float prog1 = treble.getProgress();
                float prog2 = gain.getProgress();
                float prog3 = bass.getProgress();
                float prog4 = drive.getProgress();
                float prog5 = mid.getProgress();
                float prog6 = presence.getProgress();

                // Convert progress values to integers
                int int1 = (int) prog1;
                int int2 = (int) prog2;
                int int3 = (int) prog3;
                int int4 = (int) prog4;
                int int5 = (int) prog5;
                int int6 = (int) prog6;

                // Convert integers to strings
                String tb = String.valueOf(int1);
                String gn = String.valueOf(int2);
                String bs = String.valueOf(int3);
                String dv = String.valueOf(int4);
                String md = String.valueOf(int5);
                String pc = String.valueOf(int6);

                // Create intent to start the next activity
                Intent intent = new Intent(AddAmp.this, additionalKnobs.class);

                // Pass progress values as extras to the next activity
                intent.putExtra("treble", tb);
                intent.putExtra("gain", gn);
                intent.putExtra("bass", bs);
                intent.putExtra("drive", dv);
                intent.putExtra("mid", md);
                intent.putExtra("presence", pc);

                // Start the next activity
                vibrate();
                startActivity(intent);

            }
        });https://firebasestorage.googleapis.com/v0/b/messengerclone-5d6db.appspot.com/o/gwazPic%2FAMPLIZONE%2FAmplifier%2Famp%2Famp.mp3?alt=media&token=1d9c7892-1bd0-4f96-8af6-bf1b28283705





        //for treble
        treble.setOnCircularSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar seekBar, float progress, boolean fromUser) {

                if (progress < 5) {
                    treble.setRingColor(Color.GREEN);
                } else if (progress < 8) {
                    treble.setRingColor(Color.HSVToColor(new float[]{30.0f, 1.0f, 1.0f}));
                } else if (progress >= 8) {
                    treble.setRingColor(Color.RED);
                }
                int intProgress = (int) progress;
                treble.setProgressText(String.valueOf(intProgress));



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



        //for gain
        gain.setOnCircularSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar seekBar, float progress, boolean fromUser) {
                if (progress < 5) {
                    gain.setRingColor(Color.GREEN);
                } else if (progress < 8) {
                    gain.setRingColor(Color.HSVToColor(new float[]{30.0f, 1.0f, 1.0f}));
                } else if (progress >= 8) {
                    gain.setRingColor(Color.RED);
                }
                int intProgress = (int) progress;
                gain.setProgressText(String.valueOf(intProgress));
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

        //for bass
        bass.setOnCircularSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar seekBar, float progress, boolean fromUser) {
                if (progress < 5) {
                    bass.setRingColor(Color.GREEN);
                } else if (progress < 8) {
                    bass.setRingColor(Color.HSVToColor(new float[]{30.0f, 1.0f, 1.0f}));
                } else if (progress >= 8) {
                    bass.setRingColor(Color.RED);
                }
                int intProgress = (int) progress;
                bass.setProgressText(String.valueOf(intProgress));
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

        //for drive
        drive.setOnCircularSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar seekBar, float progress, boolean fromUser) {
                if (progress < 5) {
                    drive.setRingColor(Color.GREEN);
                } else if (progress < 8) {
                    drive.setRingColor(Color.HSVToColor(new float[]{30.0f, 1.0f, 1.0f}));
                } else if (progress >= 8) {
                    drive.setRingColor(Color.RED);
                }
                int intProgress = (int) progress;
                drive.setProgressText(String.valueOf(intProgress));
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

        //for mid
        mid.setOnCircularSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar seekBar, float progress, boolean fromUser) {
                if (progress < 5) {
                    mid.setRingColor(Color.GREEN);
                } else if (progress < 8) {
                    mid.setRingColor(Color.HSVToColor(new float[]{30.0f, 1.0f, 1.0f}));
                } else if (progress >= 8) {
                    mid.setRingColor(Color.RED);
                }
                int intProgress = (int) progress;
                mid.setProgressText(String.valueOf(intProgress));
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

        //for presence
        presence.setOnCircularSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar seekBar, float progress, boolean fromUser) {
                if (progress < 5) {
                    presence.setRingColor(Color.GREEN);
                } else if (progress < 8) {
                    presence.setRingColor(Color.HSVToColor(new float[]{30.0f, 1.0f, 1.0f}));
                } else if (progress >= 8) {
                    presence.setRingColor(Color.RED);
                }
                int intProgress = (int) progress;
                presence.setProgressText(String.valueOf(intProgress));
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