package com.example.admincms.selection.AMPLIZONE.AllSettings;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.admincms.R;
import com.example.admincms.selection.AMPLIZONE.Add.AddAmp;
import com.example.admincms.selection.AMPLIZONE.Add.AmpView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllSettings extends AppCompatActivity implements AllAdapter.OnItemClickListener {


    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private List<AllGet> ampList;
    private AllAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView Add;
    private Vibrator vibrator;
    private AllAdapter.OnItemClickListener listener;
    private ProgressDialog progressDialog; // Declare progressDialog here


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_settings);

        //Initialization
        Add = findViewById(R.id.Add);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);



        // Initialize the listener
        listener = this;

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Service").child("AMPLIZONE").child("Amplifier");
        storageReference = FirebaseStorage.getInstance().getReference().child("gwazPic").child("AMPLIZONE").child("Amplifier");

        recyclerView = findViewById(R.id.rvt2);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ampList = new ArrayList<>();
        adapter = new AllAdapter(ampList, databaseReference, storageReference, listener);
        recyclerView.setAdapter(adapter);
        fetchData();

        // Set up refresh listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Fetch data again when swipe gesture is performed
                fetchData();
            }
        });


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vibrate();
                startActivity(new Intent(AllSettings.this, AddAmp.class));

            }
        });
    }

    private void fetchData() {
        ValueEventListener ampListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ampList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AllGet allGet = snapshot.getValue(AllGet.class);
                    ampList.add(allGet);
                }
                Collections.reverse(ampList);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };

        databaseReference.addListenerForSingleValueEvent(ampListener);
    }
    private void vibrate() {
        // Vibrate for 50 milliseconds
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(100);
        }
    }

    @Override
    public void onItemClick(AllGet item) {

        // Initialize progressDialog if it's null
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }
        // Show progress dialog
        progressDialog.show();

        // Handle item click here
        Intent intent = new Intent(AllSettings.this, AmpView.class);
        intent.putExtra("setName", item.getSetName()); // Example: Pass item name as intent extra
        intent.putExtra("by", item.getBy());
        intent.putExtra("imageUrl", item.getImageUrl());
        intent.putExtra("audioUrl", item.getAudioUrl());
        intent.putExtra("ampUsed", item.getAmpUsed());
        intent.putExtra("description", item.getDescription());
        intent.putExtra("genre", item.getGenre());
        // Pass other item data similarly if needed
        startActivity(intent);
        // Dismiss progress dialog when activity is started



    }


    @Override
    protected void onResume() {
        super.onResume();
        // Dismiss the progress dialog if it's showing
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


}