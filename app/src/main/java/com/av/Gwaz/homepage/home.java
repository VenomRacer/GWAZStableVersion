package com.av.Gwaz.homepage;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.av.Gwaz.R;
import com.av.Gwaz.chat.MessageWindow;
import com.av.Gwaz.chat.setting;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class home extends AppCompatActivity {

    ImageView gwiz, amplizone,  chordmaster, tuner, profileBtn,chatBtn,chatNotify;
    ImageView downArrow,upArrow;
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



        profileBtn = findViewById(R.id.profileBtn);
        chatBtn = findViewById(R.id.chatBtn);
        chatNotify = findViewById(R.id.chatNotify);
        downArrow = findViewById(R.id.downArrow);
        upArrow = findViewById(R.id.upArrow);




        // Initialize MediaPlayer with the sound file
        sound1 = MediaPlayer.create(this, R.raw.strum);
        sound2 = MediaPlayer.create(this, R.raw.plug);
        sound3 = MediaPlayer.create(this,R.raw.chord);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        int[] images = {R.drawable.guitarwiz2, R.drawable.ampliz2, R.drawable.chordm2, R.drawable.tuner2};
        ImageAdapter adapter = new ImageAdapter(this, images);
        recyclerView.setAdapter(adapter);

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

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

    @Override
    public void onBackPressed(){
        Dialog dialog = new Dialog(home.this,R.style.dialoge);
        dialog.setContentView(R.layout.exit_dialog);
        Button no,yes;
        yes = dialog.findViewById(R.id.yesbnt);
        no = dialog.findViewById(R.id.nobnt);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home.super.onBackPressed();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}