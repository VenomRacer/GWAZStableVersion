package com.av.Gwaz.homepage.AMPLIZ.AllSettings;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.av.Gwaz.R;
import com.av.Gwaz.homepage.AMPLIZ.Add.AmpView;
import com.av.Gwaz.homepage.AMPLIZ.Genre.Genre;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AllSettings extends AppCompatActivity implements AllAdapter.OnItemClickListener {


    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private List<AllGet> ampList;
    private AllAdapter adapter;
    private SearchView searchView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView pop,metal,rock,jazz,blues,funk,reggae,country,clean;
    private ImageView loadingCenter,nonet, openGenre;
    private Vibrator vibrator;
    private AllAdapter.OnItemClickListener listener;
    private Dialog loadingDialog;
    private ScrollView scroll1;
    String bass,drive,gain,gainstage,mid,presence,reverb,tone,treble,
            chorus,compressor,delay,distortion,flanger,fuzz,overdrive,phaser,reverb1,tremolo,wah;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlayout);

        // Initialize the custom loading dialog
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.dialog_loading);
        loadingDialog.setCancelable(false);

        ImageView loadingImageView = loadingDialog.findViewById(R.id.loadingImageView);
        Glide.with(this).asGif().load(R.drawable.loading_ic).into(loadingImageView);

        //Initialization
        //Add = findViewById(R.id.Add);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        rock = findViewById(R.id.rock);
        pop = findViewById(R.id.pop);
        metal = findViewById(R.id.metal);
        blues = findViewById(R.id.blues);
        jazz = findViewById(R.id.jazz);
        country  = findViewById(R.id.country);
        clean = findViewById(R.id.clean);
        funk = findViewById(R.id.funk);
        reggae = findViewById(R.id.reggae);
        searchView = findViewById(R.id.searchView);
        loadingCenter = findViewById(R.id.loadingCenter);
        nonet = findViewById(R.id.noNet);
        openGenre = findViewById(R.id.openGenre);
        scroll1 = findViewById(R.id.scroll1);

        Glide.with(this).asGif().load(R.drawable.loading_ic2).into(loadingCenter);
        nonet.setImageResource(R.drawable.nonet);


        // Initialize the listener
        listener = this;

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Service").child("AMPLIZONE").child("Amplifier");
        storageReference = FirebaseStorage.getInstance().getReference().child("gwazPic").child("AMPLIZONE").child("Amplifier");

        recyclerView = findViewById(R.id.rvt2);
        int numberOfColumns = 2;
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        ampList = new ArrayList<>();
        adapter = new AllAdapter(ampList, databaseReference, storageReference, listener);
        recyclerView.setAdapter(adapter);
        fetchData();

        openGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scroll1.getVisibility() == View.GONE) {
                    scroll1.setVisibility(View.VISIBLE);
                } else {
                    scroll1.setVisibility(View.GONE);
                }

            }
        });

        // Set up refresh listener
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Fetch data again when swipe gesture is performed
                fetchData();
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


        pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllSettings.this, Genre.class);
                intent.putExtra("Genre", "Pop");
                startActivity(intent);

            }
        });

        metal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllSettings.this, Genre.class);
                intent.putExtra("Genre", "Metal");
                startActivity(intent);

            }
        });

        rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllSettings.this, Genre.class);
                intent.putExtra("Genre", "Rock");
                startActivity(intent);

            }
        });

        blues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllSettings.this, Genre.class);
                intent.putExtra("Genre", "Blues");
                startActivity(intent);

            }
        });

        jazz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllSettings.this, Genre.class);
                intent.putExtra("Genre", "Jazz");
                startActivity(intent);

            }
        });

        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllSettings.this, Genre.class);
                intent.putExtra("Genre", "Country");
                startActivity(intent);

            }
        });

        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllSettings.this, Genre.class);
                intent.putExtra("Genre", "Clean");
                startActivity(intent);

            }
        });

        funk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllSettings.this, Genre.class);
                intent.putExtra("Genre", "Funk");
                startActivity(intent);

            }
        });

        reggae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllSettings.this, Genre.class);
                intent.putExtra("Genre", "Reggae");
                startActivity(intent);

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
                        loadingCenter.setVisibility(View.GONE);
                    }

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

            //nonet.setVisibility(View.VISIBLE);
            //loadingCenter.setVisibility(View.GONE);
            Toast.makeText(this, "No internet connection. Please check your connection and try again.", Toast.LENGTH_SHORT).show();

            ValueEventListener ampListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ampList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        AllGet allGet = snapshot.getValue(AllGet.class);
                        ampList.add(allGet);
                        loadingCenter.setVisibility(View.GONE);
                    }

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
                                        intent.putExtra("key", item.getKey());
                                        intent.putExtra("rating", item.getRating());
                                        intent.putExtra("uid",item.getUid());
                                        intent.putExtra("profilePic", item.getProfilePic());
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

    private void search(String query) {
        List<AllGet> filteredList = new ArrayList<>();

        for (AllGet item : ampList) {
            if (item.getSetName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);
    }


    /*@Override
    protected void onResume() {
        super.onResume();
        // Dismiss the progress dialog if it's showing
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }*/

    // Method to check internet connectivity
    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


}