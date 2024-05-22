package com.av.Gwaz.homepage.AMPLIZ.MyAmp;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.av.Gwaz.R;
import com.av.Gwaz.homepage.AMPLIZ.Add.AmpView;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyAmp extends AppCompatActivity implements MyAmpAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private List<MyGet> myList;
    private MyAmpAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView Add;
    private Vibrator vibrator;
    private String userName;
    private MyAmpAdapter.OnItemClickListener listener;
    private Dialog loadingDialog;
    String bass,drive,gain,gainstage,mid,presence,reverb,tone,treble,
            chorus,compressor,delay,distortion,flanger,fuzz,overdrive,phaser,reverb1,tremolo,wah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_amp);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        // Initialize the listener
        listener = this;


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Service").child("AMPLIZONE").child("Amplifier");
        storageReference = FirebaseStorage.getInstance().getReference().child("gwazPic").child("AMPLIZONE").child("Amplifier");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = currentUser.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user").child(currentUserId);

        recyclerView = findViewById(R.id.rvt2);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        // Set up LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        myList = new ArrayList<>();
        adapter = new MyAmpAdapter(myList, databaseReference, storageReference, listener);
        recyclerView.setAdapter(adapter);
        //proper placement






        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userName = snapshot.child("userName").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Set up refresh listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Fetch data again when swipe gesture is performed
                fetchDataAndPopulateRecyclerView();
            }
        });

        fetchDataAndPopulateRecyclerView();


    }

    private void fetchDataAndPopulateRecyclerView() {
        // Check for internet connectivity
        if (isConnected()) {
            // Initialize the custom loading dialog
            loadingDialog = new Dialog(this);
            loadingDialog.setContentView(R.layout.dialog_loading);
            loadingDialog.setCancelable(false);

            ImageView loadingImageView = loadingDialog.findViewById(R.id.loadingImageView);
            Glide.with(this).asGif().load(R.drawable.loading_ic).into(loadingImageView);
            loadingDialog.show();

            ValueEventListener userListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Query query = databaseReference.orderByChild("by").equalTo(userName);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            myList.clear();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                MyGet myGet = snapshot.getValue(MyGet.class);
                                myList.add(myGet);
                            }
                            Collections.reverse(myList);
                            adapter.notifyDataSetChanged();
                            loadingDialog.dismiss();
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e(TAG, "Error fetching data: " + databaseError.getMessage());
                            loadingDialog.dismiss();
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e(TAG, "Error fetching user data: " + databaseError.getMessage());
                    loadingDialog.dismiss();
                }
            };

            // Assuming your database reference is pointing to the root of the database
            databaseReference.addListenerForSingleValueEvent(userListener);
        } else {
            // Display a message to the user indicating the lack of internet connectivity
            Toast.makeText(this, "No internet connection. Please check your connection and try again.", Toast.LENGTH_SHORT).show();
            // Dismiss swipeRefreshLayout if it's refreshing
            swipeRefreshLayout.setRefreshing(false);
        }
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
    public void onItemClick(MyGet item) {


        loadingDialog.show();

        // Assuming each item has a unique key generated by Firebase
        Query query = databaseReference.orderByChild("key").equalTo(item.getKey()).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Iterate through the child nodes to find the appropriate subfolder
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DatabaseReference itemFolderRef = snapshot.getRef();

                    // Retrieve data from the "Settings" folder
                    itemFolderRef.child("Settings").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot settingsSnapshot) {
                            // Handle settings data retrieval here
                            if (settingsSnapshot.exists()) {
                                // Retrieve bass value from the settings snapshot
                                bass = (String) settingsSnapshot.child("bass").getValue();
                                drive = (String) settingsSnapshot.child("drive").getValue();
                                gain = (String) settingsSnapshot.child("gain").getValue();
                                gainstage = (String)settingsSnapshot.child("gainstage").getValue();
                                mid = (String) settingsSnapshot.child("mid").getValue();
                                presence = (String) settingsSnapshot.child("presence").getValue();
                                reverb = (String) settingsSnapshot.child("reverb").getValue();
                                tone = (String) settingsSnapshot.child("tone").getValue();
                                treble = (String) settingsSnapshot.child("treble").getValue();
                                // You can retrieve other settings similarly

                                // Retrieve data from the "Effects" folder
                                itemFolderRef.child("Effects").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot effectsSnapshot) {
                                        // Handle effects data retrieval here
                                        // Example: Retrieve "chorus" value
                                        chorus = String.valueOf(effectsSnapshot.child("chorus").getValue(Boolean.class));
                                        compressor = String.valueOf(effectsSnapshot.child("compressor").getValue(Boolean.class));
                                        delay = String.valueOf(effectsSnapshot.child("delay").getValue(Boolean.class));
                                        distortion = String.valueOf(effectsSnapshot.child("distortion").getValue(Boolean.class));
                                        flanger = String.valueOf(effectsSnapshot.child("flanger").getValue(Boolean.class));
                                        fuzz = String.valueOf(effectsSnapshot.child("fuzz").getValue(Boolean.class));
                                        overdrive = String.valueOf(effectsSnapshot.child("overdrive").getValue(Boolean.class));
                                        phaser = String.valueOf(effectsSnapshot.child("phaser").getValue(Boolean.class));
                                        reverb1 = String.valueOf(effectsSnapshot.child("reverb1").getValue(Boolean.class));
                                        tremolo = String.valueOf(effectsSnapshot.child("tremolo").getValue(Boolean.class));
                                        wah = String.valueOf(effectsSnapshot.child("wah").getValue(Boolean.class));
                                        // You can retrieve other effects similarly

                                        // Start the AmpView activity with the retrieved data
                                        Intent intent = new Intent(MyAmp.this, AmpView.class);
                                        intent.putExtra("setName", item.getSetName());
                                        intent.putExtra("by", item.getBy());
                                        intent.putExtra("imageUrl", item.getImageUrl());
                                        intent.putExtra("audioUrl", item.getAudioUrl());
                                        intent.putExtra("ampUsed", item.getAmpUsed());
                                        intent.putExtra("description", item.getDescription());
                                        intent.putExtra("genre", item.getGenre());
                                        intent.putExtra("key", item.getKey());
                                        intent.putExtra("rating", item.getRating());
                                        // Pass the retrieved settings and effects data to the intent

                                        // knobs
                                        intent.putExtra("bass", bass);
                                        intent.putExtra("drive", drive);
                                        intent.putExtra("gain", gain);
                                        intent.putExtra("gainstage", gainstage);
                                        intent.putExtra("mid", mid);
                                        intent.putExtra("presence", presence);
                                        intent.putExtra("reverb", reverb);
                                        intent.putExtra("tone", tone);
                                        intent.putExtra("treble", treble);


                                        //effects
                                        intent.putExtra("chorus", chorus);
                                        intent.putExtra("compressor", compressor);
                                        intent.putExtra("delay", delay);
                                        intent.putExtra("distortion", distortion);
                                        intent.putExtra("flanger", flanger);
                                        intent.putExtra("fuzz", fuzz);
                                        intent.putExtra("overdrive", overdrive);
                                        intent.putExtra("phaser", phaser);
                                        intent.putExtra("reverb1", reverb1);
                                        intent.putExtra("tremolo", tremolo);
                                        intent.putExtra("wah", wah);


                                        startActivity(intent);
                                        loadingDialog.dismiss();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Log.e(TAG, "Error fetching effects data: " + databaseError.getMessage());
                                        loadingDialog.dismiss();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e(TAG, "Error fetching settings data: " + databaseError.getMessage());
                            loadingDialog.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error fetching item data: " + databaseError.getMessage());
                loadingDialog.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Dismiss the progress dialog if it's showing
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    // Method to check internet connectivity
    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}