package com.av.Gwaz.homepage.AMPLIZ.Add;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.akaita.android.circularseekbar.CircularSeekBar;
import com.av.Gwaz.R;

import java.text.DecimalFormat;

public class AddAmp extends AppCompatActivity {

    private CircularSeekBar treble, gain, bass,drive,mid, presence;
    private TextView sampleTxt;
    private Vibrator vibrator;
    private Button nextBtn;
    private ImageView treblefact,gainfact,bassfact,drivefact,midfact,presencefact;



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

        treblefact = findViewById(R.id.treblefact);
        gainfact = findViewById(R.id.gainfact);
        bassfact = findViewById(R.id.bassfact);
        drivefact = findViewById(R.id.drivefact);
        midfact = findViewById(R.id.midfact);
        presencefact = findViewById(R.id.presencefact);

        treblefact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "Treble refers to the higher frequencies in sound, often associated with brightness and clarity. Increasing treble makes the sound sharper, brighter, and more cutting, helping the guitar stand out in a mix.";
                String title1 = "Treble";
                showFactDialog(text,title1);
            }
        });

        bassfact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "Boosting the bass makes the guitar sound fuller and deeper, emphasizing the low end and giving the tone more body.";
                String title1 = "Bass";
                showFactDialog(text,title1);
            }
        });

        midfact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "Adjusting the mids affects the clarity and presence of the guitar, making it more or less prominent in a band setting. More mids make the guitar sound fuller and more defined, while cutting mids can give a scooped or hollow sound.";
                String title1 = "Mid";
                showFactDialog(text,title1);
            }
        });

        gainfact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "This is what creates the crunchy, powerful tones used in rock, metal, and other genres. Essentially, gain determines how \"hot\" or strong the signal is, influencing the overall aggressiveness and sustain of the sound.";
                String title1 = "Gain";
                showFactDialog(text,title1);
            }
        });

        drivefact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "When you increase the drive, you push the amplifier's tubes or circuitry harder, causing the sound to break up and create a distorted tone. This adds warmth, sustain, and a gritty edge to the guitar sound. ";
                String title1 = "Drive";
                showFactDialog(text,title1);
            }
        });

        presencefact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "Adjusting presence makes the sound more or less sharp and defined, influencing how the guitar sits in a mix. Increasing presence gives the sound more bite and clarity, helping it cut through other instruments.  ";
                String title1 = "Presence";
                showFactDialog(text,title1);
            }
        });

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
                finish();

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

    private void showFactDialog(String fact, String title) {
        // Create an AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set the title of the dialog (optional)
        builder.setTitle(title);

        // Set the message of the dialog (the fact)
        builder.setMessage(fact);

        // Add an "OK" button to dismiss the dialog
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
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
        super.finish();
        overridePendingTransition(0,R.anim.slide_down);// Apply slide-down animation
    }

    /*@Override
    public void onBackPressed() {
        startActivity(new Intent(additionalKnobs.this, setting.class));
        super.onBackPressed();
        finish();
    }*/
}