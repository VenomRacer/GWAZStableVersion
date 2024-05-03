package com.av.Gwaz.homepage.CHORDM.Classic.Leaderboards.LeaderboardRecycler;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.av.Gwaz.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Leaderboard extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LeadAdapter adapter;
    private List<LeadGet> leadList;
    private TextView modename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        recyclerView = findViewById(R.id.recyclerView);
        modename = findViewById(R.id.mode);

        String mode = getIntent().getStringExtra("mode");
        modename.setText(mode);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        leadList = new ArrayList<>();
        adapter = new LeadAdapter(leadList);
        recyclerView.setAdapter(adapter);

        DatabaseReference leadRef = FirebaseDatabase.getInstance()
                .getReference()
                .child("Service")
                .child("CHORDM")
                .child("Leaderboard")
                .child(mode);

        // Retrieve reviews from Firebase database
        leadRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                leadList.clear(); // Clear the list before adding sorted items
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Get review data
                    LeadGet leadget = snapshot.getValue(LeadGet.class);
                    leadList.add(leadget);
                }

                // Sort the leadList based on score
                Collections.sort(leadList, new Comparator<LeadGet>() {
                    @Override
                    public int compare(LeadGet lead1, LeadGet lead2) {
                        // Compare scores in descending order
                        return Integer.compare(lead2.getScore(), lead1.getScore());
                    }
                });

                // Notify the adapter of changes
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}