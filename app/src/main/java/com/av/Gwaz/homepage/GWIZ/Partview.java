package com.av.Gwaz.homepage.GWIZ;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.av.Gwaz.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Partview extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private List<PartGet> stepList;
    private PartAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    ImageView Add;
    TextView ttl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partview);

        // Retrieve databaseReference from intent extras
        String TITLE = getIntent().getStringExtra("TITLE");
        String databaseReferencePath = getIntent().getStringExtra("datab");
        String storageReferencePath = getIntent().getStringExtra("store");
        if (databaseReferencePath != null) {
            // Initialize databaseReference using the path retrieved from intent
            databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(databaseReferencePath);
            storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(storageReferencePath);
        } else {
            Toast.makeText(Partview.this, "Error fetching data.", Toast.LENGTH_SHORT).show();
        }

        // Initialization of elements
        recyclerView = findViewById(R.id.rvt);
        ttl = findViewById(R.id.TITLE);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout); // Initialize SwipeRefreshLayout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        stepList = new ArrayList<>();
        adapter = new PartAdapter(stepList, databaseReference, storageReference);
        recyclerView.setAdapter(adapter);

        ttl.setText(TITLE);





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


        ValueEventListener stepListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                stepList.clear();
                for (DataSnapshot stepSnapshot : dataSnapshot.getChildren()) {
                    PartGet step = stepSnapshot.getValue(PartGet.class);
                    step.setT3(stepSnapshot.getKey()); // Set the step name using setT3 method
                    stepList.add(step);
                }

                // Reverse the list to display in descending order
                //Collections.reverse(stepList);
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