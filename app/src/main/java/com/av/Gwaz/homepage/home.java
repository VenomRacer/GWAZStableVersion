package com.av.Gwaz.homepage;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.av.Gwaz.R;
import com.av.Gwaz.chat.MessageWindow;
import com.av.Gwaz.chat.setting;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class home extends Fragment {

    ImageView gwiz, amplizone,  chordmaster, tuner, profileBtn, chatBtn, chatNotify;
    ImageView downArrow, upArrow;
    MediaPlayer sound1, sound2, sound3;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home, container, false);

        // Initialize Firebase variables
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("user").child(mAuth.getCurrentUser().getUid()).child("userCommunicated");

        profileBtn = view.findViewById(R.id.profileBtn);
        chatBtn = view.findViewById(R.id.chatBtn);
        chatNotify = view.findViewById(R.id.chatNotify);
        downArrow = view.findViewById(R.id.downArrow);
        upArrow = view.findViewById(R.id.upArrow);

        // Initialize MediaPlayer with the sound file
        sound1 = MediaPlayer.create(getContext(), R.raw.strum);
        sound2 = MediaPlayer.create(getContext(), R.raw.plug);
        sound3 = MediaPlayer.create(getContext(), R.raw.chord);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        int[] images = {R.drawable.guitarwiz2, R.drawable.chordm2, R.drawable.tuner2}; //, R.drawable.ampliz2, (backup)
        ImageAdapter adapter = new ImageAdapter(getContext(), images);
        recyclerView.setAdapter(adapter);

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        /*// Set up ValueEventListener to listen for changes in communication status
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
        });*/

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), setting.class));
            }
        });

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MessageWindow.class));
            }
        });

        chatNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MessageWindow.class));
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Release MediaPlayer resources when fragment view is destroyed
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

    // Remove onBackPressed and other activity specific methods
}