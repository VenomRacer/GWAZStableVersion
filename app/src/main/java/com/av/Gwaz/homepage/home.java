package com.av.Gwaz.homepage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.av.Gwaz.R;
import com.av.Gwaz.chat.MessageWindow;
import com.av.Gwaz.chat.setting;
import com.av.Gwaz.homepage.AMPLIZ.AllSettings.AllSettings;
import com.av.Gwaz.homepage.CHORDM.MainactChordm;
import com.av.Gwaz.homepage.GWIZ.MainactGwiz;
import com.av.Gwaz.homepage.TUNER.TunerTrainer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class home extends AppCompatActivity {

    ImageView gwiz, amplizone,  chordmaster, tuner, profileBtn,chatBtn,chatNotify;
    MediaPlayer sound1, sound2, sound3;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize Firebase variables
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("user").child(mAuth.getCurrentUser().getUid()).child("userCommunicated");


        gwiz = findViewById(R.id.gwiz);
        amplizone = findViewById(R.id.amplizone);
        chordmaster = findViewById(R.id.chordmaster);
        tuner = findViewById(R.id.tuner);
        profileBtn = findViewById(R.id.profileBtn);
        chatBtn = findViewById(R.id.chatBtn);
        chatNotify = findViewById(R.id.chatNotify);


        // Initialize MediaPlayer with the sound file
        sound1 = MediaPlayer.create(this, R.raw.strum);
        sound2 = MediaPlayer.create(this, R.raw.plug);
        sound3 = MediaPlayer.create(this,R.raw.chord);

        // Set up ValueEventListener to listen for changes in communication status
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean hasUnseenCommunication = false;

                for (DataSnapshot communicationSnapshot : dataSnapshot.getChildren()) {
                    Boolean seen = communicationSnapshot.child("seen").getValue(Boolean.class);
                    if (seen != null && !seen) {
                        hasUnseenCommunication = true;
                        break;
                    }
                }

                // Update visibility based on communication status
                if (hasUnseenCommunication) {
                    chatNotify.setVisibility(View.VISIBLE);
                    chatBtn.setVisibility(View.GONE);
                } else {
                    chatNotify.setVisibility(View.GONE);
                    chatBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
            }
        });

        gwiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sound1.start();
                startActivity(new Intent(home.this, MainactGwiz.class));
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);

            }
        });

        amplizone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sound2.start();
                startActivity(new Intent(home.this, AllSettings.class));
            }
        });

        chordmaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sound3.start();
                startActivity(new Intent(home.this, MainactChordm.class));
            }
        });

        tuner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this, TunerTrainer.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this, setting.class));

            }
        });

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this, MessageWindow.class));
            }
        });

        chatNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(home.this, MessageWindow.class));

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reset MediaPlayer state
        if (sound1 != null) {
            sound1.release();
            sound1 = MediaPlayer.create(this, R.raw.strum);
        }

        if (sound2 != null) {
            sound2.release();
            sound2 = MediaPlayer.create(this, R.raw.plug);
        }

        if (sound3 != null) {
            sound3.release();
            sound3 = MediaPlayer.create(this, R.raw.chord);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release MediaPlayer resources when activity is destroyed
        if (sound1 != null) {
            sound1.release();
            sound1 = null;
        }

        if (sound2 != null) {
            sound2.release();
            sound2 = null;
        }

        if (sound3 != null) {
            sound3.release();
            sound3 = null;
        }
    }
}