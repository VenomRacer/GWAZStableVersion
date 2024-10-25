package com.av.Gwaz.homepage.CHORDM.Diagram;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.av.Gwaz.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DLevelSelec extends AppCompatActivity {
    private FrameLayout level1,level2,level3,level4,level5;
    private ImageView lock2, lock3, lock4, lock5,back;
    private TextView reset,modename;
    private ImageButton sync;

    SharedPreferences sharedPreferences;

    Dialog loadingDialog;

    private FirebaseUser currentUser;
    private DatabaseReference databaseReference;
    private static final String PREF_NAME = "LevelPrefs";
    private static final String KEY_HIGHEST_UNLOCKED_LEVEL_DIAGRAM = "highestUnlockedLevelDiagram";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dlevel_selec);

        level1 = findViewById(R.id.level1);
        level2 = findViewById(R.id.level2);
        level3 = findViewById(R.id.level3);
        level4 = findViewById(R.id.level4);
        level5 = findViewById(R.id.level5);
        sync = findViewById(R.id.sync);

        reset = findViewById(R.id.modename);
        lock2 = findViewById(R.id.lock2);
        lock3 = findViewById(R.id.lock3);
        lock4 = findViewById(R.id.lock4);
        lock5 = findViewById(R.id.lock5);

        back = findViewById(R.id.back);
        modename = findViewById(R.id.modename);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Initialize the custom loading dialog
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.dialog_loading);
        loadingDialog.setCancelable(true);

        ImageView loadingImageView = loadingDialog.findViewById(R.id.loadingImageView);
        Glide.with(this).asGif().load(R.drawable.loading_ic).into(loadingImageView);

        sharedPreferences = getSharedPreferences("LevelPrefs", Context.MODE_PRIVATE);
        int highestUnlockedLevelDiagram = sharedPreferences.getInt("highestUnlockedLevelDiagram", 1);
        setLevelAccess(highestUnlockedLevelDiagram);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference()
                    .child("user")
                    .child(currentUser.getUid())
                    .child("Levels")
                    .child("Diagram");


        }


        level1.setOnClickListener(v -> startActivity(new Intent(DLevelSelec.this, DLevel1.class)));
        level2.setOnClickListener(v -> {
            if (highestUnlockedLevelDiagram >= 2) {
                startActivity(new Intent(DLevelSelec.this, DLevel2.class));
            }
        });
        level3.setOnClickListener(v -> {
            if (highestUnlockedLevelDiagram >= 3) {
                startActivity(new Intent(DLevelSelec.this, DLevel3.class));
            }
        });
        level4.setOnClickListener(v -> {
            if (highestUnlockedLevelDiagram >= 4) {
                startActivity(new Intent(DLevelSelec.this, DLevel4.class));
            }
        });
        level5.setOnClickListener(v -> {
            if (highestUnlockedLevelDiagram >= 5) {
                startActivity(new Intent(DLevelSelec.this, DLevel5.class));
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        sync.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hadnleTouch(event);
                return true;
            }
        });


    }

    private void setLevelAccess(int highestUnlockedLevelDiagram) {
        if (highestUnlockedLevelDiagram >= 2) {
            lock2.setVisibility(View.GONE);
        }
        if (highestUnlockedLevelDiagram >= 3) {
            lock3.setVisibility(View.GONE);
        }
        if (highestUnlockedLevelDiagram >= 4) {
            lock4.setVisibility(View.GONE);
        }
        if (highestUnlockedLevelDiagram >= 5) {
            lock5.setVisibility(View.GONE);
        }
    }

    private void resetPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    private boolean hadnleTouch(MotionEvent event){
        // Check for network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                sync.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                break;

            case MotionEvent.ACTION_UP:
                sync.setBackgroundColor(Color.TRANSPARENT);
                if (networkInfo != null && networkInfo.isConnected()){
                    loadingDialog.show();
                    if (currentUser != null && databaseReference != null) {
                        databaseReference.child(KEY_HIGHEST_UNLOCKED_LEVEL_DIAGRAM).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if (dataSnapshot.exists()) {
                                    int diagramlevel = dataSnapshot.getValue(Integer.class);
                                    SharedPreferences sharedPreferences = getSharedPreferences("LevelPrefs", Context.MODE_PRIVATE);
                                    int highestUnlockedLevelDiagram = sharedPreferences.getInt("highestUnlockedLevelDiagram", 1);

                                    if(diagramlevel > highestUnlockedLevelDiagram){
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putInt(KEY_HIGHEST_UNLOCKED_LEVEL_DIAGRAM, diagramlevel);
                                        editor.apply();
                                        setLevelAccess(diagramlevel);
                                        loadingDialog.dismiss();

                                    } else if (diagramlevel <= highestUnlockedLevelDiagram) {
                                        databaseReference.child(KEY_HIGHEST_UNLOCKED_LEVEL_DIAGRAM).setValue(highestUnlockedLevelDiagram)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            loadingDialog.dismiss();
                                                            Log.d("Firebase", "Progress saved to Firebase");
                                                            Toast.makeText(DLevelSelec.this, "Game Synced", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Log.e("Firebase", "Failed to save progress: " + task.getException().getMessage());
                                                            Toast.makeText(DLevelSelec.this, "Failed to sync game progress", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });


                                    }


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(DLevelSelec.this, "Failed to sync game progress", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                } else {
                    Toast.makeText(DLevelSelec.this, "No network connection", Toast.LENGTH_SHORT).show();
                }
        }


        return true;

    }
}