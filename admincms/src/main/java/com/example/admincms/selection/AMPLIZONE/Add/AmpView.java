package com.example.admincms.selection.AMPLIZONE.Add;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.admincms.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class AmpView extends AppCompatActivity {

    private TextView ampName,genreName,userN,ampUsed,description;
    private ImageView image;
    private ImageButton playButton, pauseButton;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private Handler handler;
    private ProgressDialog progressDialog;




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
        description = findViewById(R.id.description);
        image = findViewById(R.id.image);
        playButton = findViewById(R.id.playButton);
        pauseButton = findViewById(R.id.pauseButton);
        seekBar = findViewById(R.id.seekBar);

        String setName = getIntent().getStringExtra("setName");
        String genre = getIntent().getStringExtra("genre");
        String by = getIntent().getStringExtra("by");
        String amp = getIntent().getStringExtra("ampUsed");
        String desc = getIntent().getStringExtra("description");
        String img = getIntent().getStringExtra("imageUrl");
        String aud = getIntent().getStringExtra("audioUrl");

        ampName.setText(setName);
        genreName.setText(genre);
        userN.setText(by);
        ampUsed.setText(amp);
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