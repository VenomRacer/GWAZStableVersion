package com.av.Gwaz.homepage.AMPLIZ.Add;

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
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.av.Gwaz.R;
import com.av.Gwaz.chat.chatwindo;
import com.av.Gwaz.homepage.AMPLIZ.AllSettings.EffectsView;
import com.av.Gwaz.homepage.AMPLIZ.AllSettings.SettingsView;
import com.av.Gwaz.homepage.AMPLIZ.Comments.RateAndComment;
import com.av.Gwaz.homepage.AMPLIZ.Comments.Reviews;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class AmpView extends AppCompatActivity {

    private TextView ampName,genreName,userN,ampUsed,guitarUsed,pickupsUsed,description, rateTxt;

    private ImageView image;
    private ImageButton playButton, pauseButton;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private Handler handler;
    private ProgressDialog progressDialog;
    private Button viewamp, vieweffects, reviews;
    private RatingBar ratingBar;
    private String userName;
    private LinearLayout ratingLayout;
    private CardView sendMessage;




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
        ratingBar = findViewById(R.id.ratingBar);
        ratingLayout = findViewById(R.id.ratingLayout);
        reviews = findViewById(R.id.reviews);
        rateTxt = findViewById(R.id.rateTxt);
        sendMessage = findViewById(R.id.sendMessage);

        //retrieve main info
        String setName = getIntent().getStringExtra("setName");
        String genre = getIntent().getStringExtra("genre");
        String guitar = getIntent().getStringExtra("guitar");
        String pickups = getIntent().getStringExtra("pickups");
        String by = getIntent().getStringExtra("by");
        String userId = getIntent().getStringExtra("uid");
        String profilePic = getIntent().getStringExtra("profilePic");
        String amp = getIntent().getStringExtra("ampUsed");
        String desc = getIntent().getStringExtra("description");
        String img = getIntent().getStringExtra("imageUrl");
        String aud = getIntent().getStringExtra("audioUrl");
        String key = getIntent().getStringExtra("key");

        // Setting rating progress
        float rating = getIntent().getFloatExtra("rating", 0.0f); // Default value 0.0f if key "rating" is not found
        ratingBar.setRating(rating);

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

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("user").child(uid);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        userName = dataSnapshot.child("userName").getValue(String.class);
                        String realname = userName; // Move this assignment here
                        if (!by.equals("GWAZ") && !by.equals(realname)) {
                            sendMessage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ratingBar.setVisibility(View.VISIBLE);
                                    rateTxt.setVisibility(View.VISIBLE);

                                    Intent intent = new Intent(AmpView.this, chatwindo.class);
                                    intent.putExtra("nameeee", by);
                                    intent.putExtra("reciverImg", profilePic);
                                    intent.putExtra("uid", userId);
                                    startActivity(intent);
                                }
                            });
                        } else {
                            ratingBar.setVisibility(View.GONE);
                            rateTxt.setVisibility(View.GONE);

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle onCancelled
                }
            });
        }

        ratingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the next activity and pass the changed progress
                Intent intent = new Intent(AmpView.this, RateAndComment.class);
                intent.putExtra("key", key);
                startActivity(intent);
            }
        });



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

        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (AmpView.this, Reviews.class);
                intent.putExtra("key", key);
                intent.putExtra("setName", setName);
                startActivity(intent);

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