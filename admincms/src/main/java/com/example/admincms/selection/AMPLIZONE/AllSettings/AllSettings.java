package com.example.admincms.selection.AMPLIZONE.AllSettings;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.admincms.R;
import com.example.admincms.selection.AMPLIZONE.Add.AddAmp;
import com.example.admincms.selection.AMPLIZONE.Add.AmpView;
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
import java.util.Comparator;
import java.util.List;

public class AllSettings extends AppCompatActivity implements AllAdapter.OnItemClickListener {


    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private List<AllGet> ampList;
    private AllAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView Add;
    private SearchView searchView;
    private TextView TITLE;
    private Vibrator vibrator;
    private AllAdapter.OnItemClickListener listener;
    private ProgressDialog progressDialog; // Declare progressDialog here
    String bass,drive,gain,gainstage,mid,presence,reverb,tone,treble,
            chorus,compressor,delay,distortion,flanger,fuzz,overdrive,phaser,reverb1,tremolo,wah;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_settings);

        //Initialization
        Add = findViewById(R.id.Add);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        TITLE = findViewById(R.id.TITLE);
        searchView = findViewById(R.id.searchView);



        // Initialize the listener
        listener = this;

        // All settings intent
        String databaseReferenceStr = getIntent().getStringExtra("databaseReference");
        String storageReferenceStr = getIntent().getStringExtra("storageReference");
        String tag = getIntent().getStringExtra("AllTag");

        // Assignment
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(databaseReferenceStr);
        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(storageReferenceStr);
        TITLE.setText(tag);


        recyclerView = findViewById(R.id.rvt2);
        int numberOfColumns = 2;
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Trigger search
                search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter as you type
                search(newText);
                return true;
            }
        });
    }

    private void fetchData() {
        // Check for internet connectivity
        if (isConnected()) {
            ValueEventListener ampListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ampList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        AllGet allGet = snapshot.getValue(AllGet.class);
                        ampList.add(allGet);
                    }
                    // Sort the list by the "date" field
                    Collections.sort(ampList, new Comparator<AllGet>() {
                        @Override
                        public int compare(AllGet o1, AllGet o2) {
                            // Assuming "date" is a field of type long in AllGet class
                            return Long.compare(o2.getDate(), o1.getDate());
                        }
                    });
                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                }
            };

            databaseReference.addListenerForSingleValueEvent(ampListener);
        } else {
            // Display a message to the user indicating the lack of internet connectivity
            // You can show a Snackbar, Toast, or any other UI element here
            // For example:
            Toast.makeText(this, "No internet connection. Please check your connection and try again.", Toast.LENGTH_SHORT).show();
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
    public void onItemClick(AllGet item) {


        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();

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
                                        Intent intent = new Intent(AllSettings.this, AmpView.class);
                                        intent.putExtra("setName", item.getSetName());
                                        intent.putExtra("by", item.getBy());
                                        intent.putExtra("imageUrl", item.getImageUrl());
                                        intent.putExtra("audioUrl", item.getAudioUrl());
                                        intent.putExtra("ampUsed", item.getAmpUsed());
                                        intent.putExtra("description", item.getDescription());
                                        intent.putExtra("genre", item.getGenre());
                                        intent.putExtra("guitar", item.getGuitar());
                                        intent.putExtra("pickups", item.getPickups());
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
                                        progressDialog.dismiss();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Log.e(TAG, "Error fetching effects data: " + databaseError.getMessage());
                                        progressDialog.dismiss();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e(TAG, "Error fetching settings data: " + databaseError.getMessage());
                            progressDialog.dismiss();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error fetching item data: " + databaseError.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void search(String query) {
        List<AllGet> filteredList = new ArrayList<>();

        for (AllGet item : ampList) {
            if (item.getSetName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);
    }




    @Override
    protected void onResume() {
        super.onResume();
        // Dismiss the progress dialog if it's showing
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    // Method to check internet connectivity
    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


}