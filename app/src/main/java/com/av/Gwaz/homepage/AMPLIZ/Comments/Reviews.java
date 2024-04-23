package com.av.Gwaz.homepage.AMPLIZ.Comments;

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
import java.util.List;

public class Reviews extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RevAdapter adapter;
    private List<RegGet> reviewList;
    private TextView ampName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        ampName = findViewById(R.id.ampName);



        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewList = new ArrayList<>();
        adapter = new RevAdapter(reviewList);
        recyclerView.setAdapter(adapter);

        // Retrieve the key passed from the previous activity
        String key = getIntent().getStringExtra("key");
        String setName = getIntent().getStringExtra("setName");

        ampName.setText(setName);

        // Reference to the Firebase database path based on the key
        DatabaseReference commentsRef = FirebaseDatabase.getInstance()
                .getReference()
                .child("Service")
                .child("AMPLIZONE")
                .child("Comments")
                .child(key);

        // Retrieve reviews from Firebase database
        commentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Get review data
                    RegGet reget = snapshot.getValue(RegGet.class);

                    reviewList.add(reget);
                }

                // Notify the adapter of changes
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }
}