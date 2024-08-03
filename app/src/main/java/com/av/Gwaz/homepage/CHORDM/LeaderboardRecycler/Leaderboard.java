package com.av.Gwaz.homepage.CHORDM.LeaderboardRecycler;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.av.Gwaz.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Leaderboard extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LeadAdapter adapter;
    private List<LeadGet> leadList;
    private TextView modename, rankeonename, ranktwoname, rankthreename;
    private ImageView firstRank, secondRank, thirdRank, back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        firstRank = findViewById(R.id.firstRank);
        secondRank = findViewById(R.id.secondRank);
        thirdRank = findViewById(R.id.thirdRank);
        rankeonename = findViewById(R.id.rankeonename);
        ranktwoname = findViewById(R.id.ranktwoname);
        rankthreename = findViewById(R.id.rankthreename);

        recyclerView = findViewById(R.id.recyclerView);
        modename = findViewById(R.id.mode);
        back = findViewById(R.id.back);

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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Service")
                .child("CHORDM")
                .child("Leaderboard")
                .child(mode);


        Query topThreeScoresQuery = databaseReference.orderByChild("score").limitToLast(3);

        topThreeScoresQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<DataSnapshot> topThree = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    topThree.add(snapshot);
                }

                // Since the query returns the results in ascending order, reverse the list to get descending order
                Collections.reverse(topThree);

                if (topThree.size() > 0) {
                    DataSnapshot firstSnapshot = topThree.get(0);
                    String firstUserPicUrl = firstSnapshot.child("profilepic").getValue(String.class);
                    String firstUsername = firstSnapshot.child("userName").getValue(String.class);

                    if (firstUserPicUrl != null && !firstUserPicUrl.isEmpty()) {
                        Picasso.get().load(firstUserPicUrl).into(firstRank);
                    }
                    if (firstUsername != null && !firstUsername.isEmpty()) {
                        rankeonename.setText(firstUsername);
                    }
                }

                if (topThree.size() > 1) {
                    DataSnapshot secondSnapshot = topThree.get(1);
                    String secondUserPicUrl = secondSnapshot.child("profilepic").getValue(String.class);
                    String secondUsername = secondSnapshot.child("userName").getValue(String.class);

                    if (secondUserPicUrl != null && !secondUserPicUrl.isEmpty()) {
                        Picasso.get().load(secondUserPicUrl).into(secondRank);
                    }
                    if (secondUsername != null && !secondUsername.isEmpty()) {
                        ranktwoname.setText(secondUsername);
                    }
                }

                if (topThree.size() > 2) {
                    DataSnapshot thirdSnapshot = topThree.get(2);
                    String thirdUserPicUrl = thirdSnapshot.child("profilepic").getValue(String.class);
                    String thirdUsername = thirdSnapshot.child("userName").getValue(String.class);

                    if (thirdUserPicUrl != null && !thirdUserPicUrl.isEmpty()) {
                        Picasso.get().load(thirdUserPicUrl).into(thirdRank);
                    }
                    if (thirdUsername != null && !thirdUsername.isEmpty()) {
                        rankthreename.setText(thirdUsername);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Leaderboard.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }
}