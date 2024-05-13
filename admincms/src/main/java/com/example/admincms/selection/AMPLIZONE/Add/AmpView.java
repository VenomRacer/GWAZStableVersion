package com.example.admincms.selection.AMPLIZONE.Add;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.admincms.R;
import com.example.admincms.selection.AMPLIZONE.AllSettings.EffectsView;
import com.example.admincms.selection.AMPLIZONE.AllSettings.SettingsView;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class AmpView extends AppCompatActivity {

    private TextView ampName,genreName,userN,ampUsed,guitarUsed,pickupsUsed,description;

    private ImageView image;
    private ImageButton playButton, pauseButton;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private Handler handler;
    private ProgressDialog progressDialog;
    private Button viewamp, vieweffects;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amp_view);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.dismiss();

        ampName = findViewById(R.id.ampName);
        genreName = findViewById(R.id.genreName);
        userN = findViewById(R.id.userN);
        ampUsed = findViewById(R.id.ampUsed);
        guitarUsed = findViewById(R.id.guitarUsed);
        pickupsUsed = findViewById(R.id.pickupsUsed);
        description = findViewById(R.id.description);
        image = findViewById(R.id.image);
        playButton = findViewById(R.id.playButton);
        pauseButton = findViewById(R.id.pauseButton);
        seekBar = findViewById(R.id.seekBar);
        viewamp = findViewById(R.id.viewamp);
        vieweffects = findViewById(R.id.vieweffects);

        //retrieve main info
        String setName = getIntent().getStringExtra("setName");
        String genre = getIntent().getStringExtra("genre");
        String guitar = getIntent().getStringExtra("guitar");
        String pickups = getIntent().getStringExtra("pickups");
        String by = getIntent().getStringExtra("by");
        String amp = getIntent().getStringExtra("ampUsed");
        String desc = getIntent().getStringExtra("description");
        String img = getIntent().getStringExtra("imageUrl");
        String aud = getIntent().getStringExtra("audioUrl");

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

        viewamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AmpView.this, SettingsView.class);
                intent.putExtra("BASS", bass);
                intent.putExtra("DRIVE", drive);
                intent.putExtra("GAIN", gain);
                intent.putExtra("GAINSTAGE", gainstage);
                intent.putExtra("MID", mid);
                intent.putExtra("PRESENCE", presence);
                intent.putExtra("REVERB", reverb);
                intent.putExtra("TONE", tone);
                intent.putExtra("TREBLE", treble);
                startActivity(intent);


            }
        });

        vieweffects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AmpView.this, EffectsView.class);
                intent.putExtra("CHORUS", chorus);
                intent.putExtra("COMPRESSOR", compressor);
                intent.putExtra("DELAY", delay);
                intent.putExtra("DISTORTION", distortion);
                intent.putExtra("FLANGER", flanger);
                intent.putExtra("FUZZ", fuzz);
                intent.putExtra("OVERDRIVE", overdrive);
                intent.putExtra("PHASER", phaser);
                intent.putExtra("REVERB1", reverb1);
                intent.putExtra("TREMOLO", tremolo);
                intent.putExtra("WAH", wah);
                startActivity(intent);


            }
        });


        //placing values
        ampName.setText(setName);
        genreName.setText(genre);
        userN.setText(by);
        ampUsed.setText(amp);
        guitarUsed.setText(guitar);
        pickupsUsed.setText(pickups);
        description.setText(desc);
        Picasso.get().load(img).into(image);

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(aud);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        handler = new Handler();
        updateSeekBar();

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseAudio();
            }
        });
    }

    private void playAudio() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            playButton.setVisibility(View.GONE);
            pauseButton.setVisibility(View.VISIBLE);
        }
    }

    private void pauseAudio() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            pauseButton.setVisibility(View.GONE);
            playButton.setVisibility(View.VISIBLE);
        }
    }

    private void updateSeekBar() {
        if (mediaPlayer != null) {
            seekBar.setMax(mediaPlayer.getDuration());
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateSeekBar();
                }
            }, 1000); // Update seek bar every second

            // SeekBar listener for tracking user's seek
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser && mediaPlayer != null) {
                        mediaPlayer.seekTo(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // Nothing to implement here
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // Nothing to implement here
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        // Remove any callbacks from the handler
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Finish the activity to properly navigate back to AllSettings
        finish();
    }
}