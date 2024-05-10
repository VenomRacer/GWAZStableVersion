package com.example.admincms.selection.GWIZ.Parts;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    @SuppressLint("MissingInflatedId")
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
            // Handle the case where databaseReference is not provided
            // You may want to display an error message or handle it according to your app's logic
        }

        // Initialization of elements
        recyclerView = findViewById(R.id.rvt);
        Add = findViewById(R.id.Add);
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


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the AddItem activity
                Intent intent = new Intent(Partview.this, AddItem.class);

                // Pass the databaseReference path as an extra to the AddItem activity
                String databaseReferencePath = databaseReference.toString();
                String storageReferencePath = storageReference.toString();
                intent.putExtra("databaseReferencePath", databaseReferencePath);
                intent.putExtra("storageReferencePath", storageReferencePath);


                // Start the AddItem activity
                startActivity(intent);

            }
        });
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