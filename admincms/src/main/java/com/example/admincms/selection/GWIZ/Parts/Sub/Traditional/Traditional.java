package com.example.admincms.selection.GWIZ.Parts.Sub.Traditional;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.admincms.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Traditional extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private List<TraGet> stepList;
    private TraAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traditional);

        recyclerView = findViewById(R.id.rvt);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout); // Initialize SwipeRefreshLayout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        stepList = new ArrayList<>();
        adapter = new TraAdapter(stepList);
        recyclerView.setAdapter(adapter);

        // Set up refresh listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Fetch data again when swipe gesture is performed
                fetchData();
            }
        });

        // Fetch data for the first time
        fetchData();
    }

    // Method to fetch data from Firebase Database
    private void fetchData() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Service").child("GWIZ").child("Strings").child("Traditional Strings");

        ValueEventListener stepListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                stepList.clear();
                for (DataSnapshot stepSnapshot : dataSnapshot.getChildren()) {
                    TraGet step = stepSnapshot.getValue(TraGet.class);
                    step.setT3(stepSnapshot.getKey()); // Set the step name using setT3 method
                    stepList.add(step);
                }
                adapter.notifyDataSetChanged();

                // Stop refreshing animation
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };

        databaseReference.addListenerForSingleValueEvent(stepListener);
    }
}