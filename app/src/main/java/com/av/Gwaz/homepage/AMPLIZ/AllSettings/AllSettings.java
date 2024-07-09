package com.av.Gwaz.homepage.AMPLIZ.AllSettings;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.av.Gwaz.R;
import com.av.Gwaz.homepage.AMPLIZ.Add.AmpView;
import com.av.Gwaz.homepage.AMPLIZ.Genre.Genre;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.Comparator;
import java.util.List;

public class AllSettings extends Fragment implements AllAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private List<AllGet> ampList;
    private AllAdapter adapter;
    private SearchView searchView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView pop, metal, rock, jazz, blues, funk, reggae, country, clean;
    private ImageView loadingCenter, nonet, openGenre, profile;
    private Vibrator vibrator;
    private AllAdapter.OnItemClickListener listener;
    private Dialog loadingDialog;
    private ScrollView scroll1;
    String bass, drive, gain, gainstage, mid, presence, reverb, tone, treble,
            chorus, compressor, delay, distortion, flanger, fuzz, overdrive, phaser, reverb1, tremolo, wah;
    private TextView nothingTxt;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_all_settings, container, false);

        // Initialize the custom loading dialog
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.dialog_loading);
        loadingDialog.setCancelable(false);

        ImageView loadingImageView = loadingDialog.findViewById(R.id.loadingImageView);
        Glide.with(this).asGif().load(R.drawable.loading_ic).into(loadingImageView);

        // Initialization
        vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        rock = view.findViewById(R.id.rock);
        pop = view.findViewById(R.id.pop);
        metal = view.findViewById(R.id.metal);
        blues = view.findViewById(R.id.blues);
        jazz = view.findViewById(R.id.jazz);
        country = view.findViewById(R.id.country);
        clean = view.findViewById(R.id.clean);
        funk = view.findViewById(R.id.funk);
        reggae = view.findViewById(R.id.reggae);
        searchView = view.findViewById(R.id.searchView);
        loadingCenter = view.findViewById(R.id.loadingCenter);
        nonet = view.findViewById(R.id.noNet);
        openGenre = view.findViewById(R.id.openGenre);
        scroll1 = view.findViewById(R.id.scroll1);
        profile = view.findViewById(R.id.profile);
        nothingTxt = view.findViewById(R.id.nothingTxt);

        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.black));
        searchEditText.setHintTextColor(getResources().getColor(R.color.black));

        Glide.with(this).asGif().load(R.drawable.loading_ic2).into(loadingCenter);
        nonet.setImageResource(R.drawable.nonet);

        // Initialize the listener
        listener = this;

        //for profile image
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("upload/" + currentUser.getUid());
        imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Use BitmapFactory to decode the byte array to a bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                profile.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors that occurred while fetching the image
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Service").child("AMPLIZONE").child("Amplifier");
        storageReference = FirebaseStorage.getInstance().getReference().child("gwazPic").child("AMPLIZONE").child("Amplifier");

        recyclerView = view.findViewById(R.id.rvt2);
        int numberOfColumns = 1;
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        ampList = new ArrayList<>();
        adapter = new AllAdapter(ampList, databaseReference, storageReference, listener);
        recyclerView.setAdapter(adapter);
        fetchData();

        // In your activity or fragment, load the animations
        final Animation slideDown = AnimationUtils.loadAnimation(getActivity(), R.anim.slidedown);
        final Animation slideUp = AnimationUtils.loadAnimation(getActivity(), R.anim.slideup);


        // Your existing onClick method with added animations
        openGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scroll1.getVisibility() == View.GONE) {
                    scroll1.setVisibility(View.VISIBLE);
                    scroll1.startAnimation(slideDown);
                    openGenre.setImageResource(R.drawable.downarrow);
                } else {
                    scroll1.startAnimation(slideUp);
                    slideUp.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            scroll1.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    openGenre.setImageResource(R.drawable.uparrow);
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

        setUpGenreClickListeners();

        return view;
    }

    private void setUpGenreClickListeners() {
        pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Genre.class);
                intent.putExtra("Genre", "Pop");
                startActivity(intent);
            }
        });

        metal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Genre.class);
                intent.putExtra("Genre", "Metal");
                startActivity(intent);
            }
        });

        rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Genre.class);
                intent.putExtra("Genre", "Rock");
                startActivity(intent);
            }
        });

        blues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Genre.class);
                intent.putExtra("Genre", "Blues");
                startActivity(intent);
            }
        });

        jazz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Genre.class);
                intent.putExtra("Genre", "Jazz");
                startActivity(intent);
            }
        });

        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Genre.class);
                intent.putExtra("Genre", "Country");
                startActivity(intent);
            }
        });

        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Genre.class);
                intent.putExtra("Genre", "Clean");
                startActivity(intent);
            }
        });

        funk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Genre.class);
                intent.putExtra("Genre", "Funk");
                startActivity(intent);
            }
        });

        reggae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Genre.class);
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

                    // Sort the list by the "date" field
                    Collections.sort(ampList, new Comparator<AllGet>() {
                        @Override
                        public int compare(AllGet o1, AllGet o2) {
                            // Assuming the date field is of type String
                            // and that it is formatted in a way that allows for lexicographical comparison
                            return o2.getDate().compareTo(o1.getDate());
                        }
                    });

                    adapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e(TAG, "onCancelled: ", databaseError.toException());
                }
            };

            // Retrieve items from Firebase
            Query query = databaseReference.orderByChild("ampname");
            query.addListenerForSingleValueEvent(ampListener);

            loadingCenter.setVisibility(View.VISIBLE); // Show loading dialog
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    loadingDialog.dismiss(); // Hide loading dialog when data is loaded
                    ampList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        AllGet amp = snapshot.getValue(AllGet.class);
                        ampList.add(amp);
                    }

                    // Sort the list by the "date" field
                    Collections.sort(ampList, new Comparator<AllGet>() {
                        @Override
                        public int compare(AllGet o1, AllGet o2) {
                            // Assuming the date field is of type String
                            // and that it is formatted in a way that allows for lexicographical comparison
                            return o2.getDate().compareTo(o1.getDate());
                        }
                    });

                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    loadingDialog.dismiss(); // Hide loading dialog if an error occurs
                    Log.e(TAG, "onCancelled: ", databaseError.toException());
                }
            });
        } else {
            loadingCenter.setVisibility(View.GONE);
            nonet.setVisibility(View.VISIBLE);
        }
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void search(String query) {
        List<AllGet> filteredList = new ArrayList<>();
        for (AllGet item : ampList) {
            if (item.getSetName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);

        // Set visibility of nothingTxt based on whether there are results
        if (filteredList.isEmpty()) {
            nothingTxt.setVisibility(View.VISIBLE);
        } else {
            nothingTxt.setVisibility(View.GONE);
        }
    }



    @Override
    public void onItemClick(AllGet item) {

        loadingDialog.show();

        // Define a timeout duration in milliseconds
        final int TIMEOUT_DURATION = 10000; // 10 seconds

        // Create a Handler and a Runnable for the timeout
        Handler timeoutHandler = new Handler();
        boolean[] isTimeout = {false};

        Runnable timeoutRunnable = new Runnable() {
            @Override
            public void run() {
                if (loadingDialog.isShowing()) {
                    isTimeout[0] = true;
                    loadingDialog.dismiss();
                    Toast.makeText(getActivity(), "There is something wrong, check your internet connection.", Toast.LENGTH_LONG).show();
                }
            }
        };

        // Post the timeout Runnable
        timeoutHandler.postDelayed(timeoutRunnable, TIMEOUT_DURATION);

        // Assuming each item has a unique key generated by Firebase
        Query query = databaseReference.orderByChild("key").equalTo(item.getKey()).limitToFirst(1);
        ValueEventListener itemListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (isTimeout[0]) return; // Check if the timeout has already occurred

                // Cancel the timeout if data loads successfully
                timeoutHandler.removeCallbacks(timeoutRunnable);

                // Iterate through the child nodes to find the appropriate subfolder
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DatabaseReference itemFolderRef = snapshot.getRef();

                    // Retrieve data from the "Settings" folder
                    itemFolderRef.child("Settings").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot settingsSnapshot) {
                            if (isTimeout[0]) return; // Check if the timeout has already occurred

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
                                        if (isTimeout[0]) return; // Check if the timeout has already occurred

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
                                        Intent intent = new Intent(getActivity(), AmpView.class);
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
                                        intent.putExtra("uid", item.getUid());
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

                                        // effects
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

                                        getActivity().startActivity(intent);
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
        };

        // Attach the listener to the query
        query.addListenerForSingleValueEvent(itemListener);
    }




}