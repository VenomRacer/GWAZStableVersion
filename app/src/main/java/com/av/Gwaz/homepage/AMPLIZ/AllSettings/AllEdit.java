package com.av.Gwaz.homepage.AMPLIZ.AllSettings;

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
import com.av.Gwaz.R;

import java.text.DecimalFormat;

public class AllEdit extends AppCompatActivity {

    private CircularSeekBar treblec, gainc, bassc,drivec,midc, presencec;
    private TextView sampleTxt;
    private Vibrator vibrator;
    private Button nextBtn;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_edit);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        nextBtn = findViewById(R.id.nextBtn);

        treblec = (CircularSeekBar) findViewById(R.id.treble);
        treblec.setProgressTextFormat(new DecimalFormat("#"));

        gainc = (CircularSeekBar) findViewById(R.id.gain);
        gainc.setProgressTextFormat(new DecimalFormat("#"));

        bassc = (CircularSeekBar) findViewById(R.id.bass);
        bassc.setProgressTextFormat(new DecimalFormat("#"));

        drivec = (CircularSeekBar) findViewById(R.id.drive);
        drivec.setProgressTextFormat(new DecimalFormat("#"));

        midc = (CircularSeekBar) findViewById(R.id.mid);
        midc.setProgressTextFormat(new DecimalFormat("#"));

        presencec = (CircularSeekBar) findViewById(R.id.presence);
        presencec.setProgressTextFormat(new DecimalFormat("#"));

        //retrieve main info
        String setName = getIntent().getStringExtra("setName");
        String genre = getIntent().getStringExtra("genre");
        String by = getIntent().getStringExtra("by");
        String amp = getIntent().getStringExtra("ampUsed");
        String guitar = getIntent().getStringExtra("guitar");
        String pickups = getIntent().getStringExtra("pickups");
        String desc = getIntent().getStringExtra("description");
        String img = getIntent().getStringExtra("imageUrl");
        String aud = getIntent().getStringExtra("audioUrl");
        String key = getIntent().getStringExtra("key");
        String uid = getIntent().getStringExtra("uid");

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

        //initialize
        treblec.setProgress(Float.parseFloat(treble));
        gainc.setProgress(Float.parseFloat(gain));
        bassc.setProgress(Float.parseFloat(bass));
        drivec.setProgress(Float.parseFloat(drive));
        midc.setProgress(Float.parseFloat(mid));
        presencec.setProgress(Float.parseFloat(presence));

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the current progress values
                float prog1 = treblec.getProgress();
                float prog2 = gainc.getProgress();
                float prog3 = bassc.getProgress();
                float prog4 = drivec.getProgress();
                float prog5 = midc.getProgress();
                float prog6 = presencec.getProgress();

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
                Intent intent = new Intent(AllEdit.this, AllEdit2.class);

                // Pass knobs
                intent.putExtra("treble", tb);
                intent.putExtra("gain", gn);
                intent.putExtra("bass", bs);
                intent.putExtra("drive", dv);
                intent.putExtra("mid", md);
                intent.putExtra("presence", pc);
                intent.putExtra("gainstage", gainstage);
                intent.putExtra("reverb", reverb);
                intent.putExtra("tone", tone);

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
                intent.putExtra("guitar", guitar);
                intent.putExtra("pickups", pickups);
                intent.putExtra("description", desc);
                intent.putExtra("genre", genre);
                intent.putExtra("key",key);
                intent.putExtra("uid", uid);

                // Start the next activity
                vibrate();
                startActivity(intent);
                finish();

            }
        });

        //for treble
        treblec.setOnCircularSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar seekBar, float progress, boolean fromUser) {

                if (progress < 5) {
                    treblec.setRingColor(Color.GREEN);
                } else if (progress < 8) {
                    treblec.setRingColor(Color.HSVToColor(new float[]{30.0f, 1.0f, 1.0f}));
                } else if (progress >= 8) {
                    treblec.setRingColor(Color.RED);
                }
                int intProgress = (int) progress;
                treblec.setProgressText(String.valueOf(intProgress));



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
        gainc.setOnCircularSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar seekBar, float progress, boolean fromUser) {
                if (progress < 5) {
                    gainc.setRingColor(Color.GREEN);
                } else if (progress < 8) {
                    gainc.setRingColor(Color.HSVToColor(new float[]{30.0f, 1.0f, 1.0f}));
                } else if (progress >= 8) {
                    gainc.setRingColor(Color.RED);
                }
                int intProgress = (int) progress;
                gainc.setProgressText(String.valueOf(intProgress));
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
        bassc.setOnCircularSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar seekBar, float progress, boolean fromUser) {
                if (progress < 5) {
                    bassc.setRingColor(Color.GREEN);
                } else if (progress < 8) {
                    bassc.setRingColor(Color.HSVToColor(new float[]{30.0f, 1.0f, 1.0f}));
                } else if (progress >= 8) {
                    bassc.setRingColor(Color.RED);
                }
                int intProgress = (int) progress;
                bassc.setProgressText(String.valueOf(intProgress));
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
        drivec.setOnCircularSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar seekBar, float progress, boolean fromUser) {
                if (progress < 5) {
                    drivec.setRingColor(Color.GREEN);
                } else if (progress < 8) {
                    drivec.setRingColor(Color.HSVToColor(new float[]{30.0f, 1.0f, 1.0f}));
                } else if (progress >= 8) {
                    drivec.setRingColor(Color.RED);
                }
                int intProgress = (int) progress;
                drivec.setProgressText(String.valueOf(intProgress));
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
        midc.setOnCircularSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar seekBar, float progress, boolean fromUser) {
                if (progress < 5) {
                    midc.setRingColor(Color.GREEN);
                } else if (progress < 8) {
                    midc.setRingColor(Color.HSVToColor(new float[]{30.0f, 1.0f, 1.0f}));
                } else if (progress >= 8) {
                    midc.setRingColor(Color.RED);
                }
                int intProgress = (int) progress;
                midc.setProgressText(String.valueOf(intProgress));
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
        presencec.setOnCircularSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar seekBar, float progress, boolean fromUser) {
                if (progress < 5) {
                    presencec.setRingColor(Color.GREEN);
                } else if (progress < 8) {
                    presencec.setRingColor(Color.HSVToColor(new float[]{30.0f, 1.0f, 1.0f}));
                } else if (progress >= 8) {
                    presencec.setRingColor(Color.RED);
                }
                int intProgress = (int) progress;
                presencec.setProgressText(String.valueOf(intProgress));
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