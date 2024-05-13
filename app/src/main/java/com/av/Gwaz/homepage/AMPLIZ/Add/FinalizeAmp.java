package com.av.Gwaz.homepage.AMPLIZ.Add;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.av.Gwaz.R;
import com.av.Gwaz.chat.setting;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FinalizeAmp extends AppCompatActivity {

    private CardView addImg,addSound;
    private Vibrator vibrator;
    private EditText setName,ampUsed;
    private TextInputEditText description;
    private Button back,save;
    private ImageView image1, soundpreview;
    private ImageButton playButton, pauseButton;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private Uri audioUri, imageUrl;
    private TextView audioTitle;
    private Spinner genreSpinner, guitarSpinner, pickupsSpinner;
    private String treble,gain,bass,drive,mid,presence,reverb2,tone,gainstage,userName,userId,profilepic;
    private boolean overdrive,distortion,fuzz,delay,reverb1,chorus,flanger,phaser,tremolo,wah,compressor;

    // Firebase Database reference
    private DatabaseReference mainRef, settingsRef, effectsRef,commentsRef;
    private StorageReference audioStorageRef, imageStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalize_amp);

        addImg = findViewById(R.id.addImg);
        image1 = findViewById(R.id.image1);
        addSound = findViewById(R.id.addSound);
        soundpreview = findViewById(R.id.soundpreview);
        playButton = findViewById(R.id.playButton);
        pauseButton = findViewById(R.id.pauseButton);
        audioTitle = findViewById(R.id.audioTitle);
        back = findViewById(R.id.back);
        save = findViewById(R.id.save);
        setName = findViewById(R.id.setName);
        ampUsed = findViewById(R.id.ampUsed);
        genreSpinner = findViewById(R.id.genreSpinner);
        guitarSpinner = findViewById(R.id.guitarSpinner);
        pickupsSpinner = findViewById(R.id.pickupsSpinner);
        description = findViewById(R.id.description);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        // Retrieve the array of genre options from strings.xml
        String[] genresArray = getResources().getStringArray(R.array.genre_array);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, genresArray);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Find the Spinner view
        Spinner spinner1 = findViewById(R.id.genreSpinner);
        // Apply the adapter to the spinner
        spinner1.setAdapter(adapter1);

        // Retrieve the array of genre options from strings.xml
        String[] guitarArray = getResources().getStringArray(R.array.guitar_array);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, guitarArray);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Find the Spinner view
        Spinner spinner2 = findViewById(R.id.guitarSpinner);
        // Apply the adapter to the spinner
        spinner2.setAdapter(adapter2);

        // Retrieve the array of genre options from strings.xml
        String[] pickupsArray = getResources().getStringArray(R.array.pickups_array);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pickupsArray);
        // Specify the layout to use when the list of choices appears
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Find the Spinner view
        Spinner spinner3 = findViewById(R.id.pickupsSpinner);
        // Apply the adapter to the spinner
        spinner3.setAdapter(adapter3);

        //getting username
        // Assuming you have Firebase authentication set up and the user is logged in
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("user").child(uid);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                         userName = dataSnapshot.child("userName").getValue(String.class);
                         userId = dataSnapshot.child("userId").getValue(String.class);
                         profilepic = dataSnapshot.child("profilepic").getValue(String.class);
                        // Now you have the username

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Display the creatorString as a toast message
                    Toast.makeText(getApplicationContext(), "Errors retrieving username.", Toast.LENGTH_SHORT).show();
                }
            });
        }



        //retrieve values from previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            overdrive = extras.getBoolean("overdrive", false);
            distortion = extras.getBoolean("distortion", false);
            fuzz = extras.getBoolean("fuzz", false);
            delay = extras.getBoolean("delay", false);
            reverb1 = extras.getBoolean("reverbp", false);
            chorus = extras.getBoolean("chorus", false);
            flanger = extras.getBoolean("flanger", false);
            phaser = extras.getBoolean("phaser", false);
            tremolo = extras.getBoolean("tremolo", false);
            wah = extras.getBoolean("wah", false);
            compressor = extras.getBoolean("compressor", false);

            treble = extras.getString("treble");
            gain = extras.getString("gain");
            bass = extras.getString("bass");
            drive = extras.getString("drive");
            mid = extras.getString("mid");
            presence = extras.getString("presence");
            reverb2 = extras.getString("reverbk");
            tone = extras.getString("tone");
            gainstage = extras.getString("gainstage");


            // Use the received data as needed
        }




        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch an Intent to open a file picker or camera app to select/capture an image
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        addSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch an intent to pick an audio file
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/*");
                startActivityForResult(intent, 2);
            }
        });
        // Initialize MediaPlayer
        mediaPlayer = new MediaPlayer();

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrate();
                // Play the audio
                if (audioUri != null) {
                    playAudio(audioUri);
                } else {
                    Toast.makeText(FinalizeAmp.this, "No audio selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrate();
                // Pause the audio
                if (audioUri != null) {
                    pauseAudio(audioUri);
                } else {
                    Toast.makeText(FinalizeAmp.this, "No audio selected", Toast.LENGTH_SHORT).show();
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToDatabase();
            }
        });







    }

    private void saveDataToDatabase() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving data...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String setNameValue = setName.getText().toString().trim();
        String ampUsedValue = ampUsed.getText().toString().trim();
        String descriptionValue = description.getText().toString().trim();
        String genreValue = genreSpinner.getSelectedItem().toString();
        String guitarValue = guitarSpinner.getSelectedItem().toString();
        String pickupsValue = pickupsSpinner.getSelectedItem().toString();

        // Check for network connectivity
        if (!isNetworkConnected()) {
            Toast.makeText(this, "No internet connection. Please check your network.", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }

        if (TextUtils.isEmpty(setNameValue) || TextUtils.isEmpty(ampUsedValue) || TextUtils.isEmpty(descriptionValue) || audioUri == null || imageUrl == null) {
            Toast.makeText(this, "Please fill in all fields and select an image and audio file", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }

        // Initialize Firebase Database reference (main)
        DatabaseReference mainRef = FirebaseDatabase.getInstance().getReference().child("Service").child("AMPLIZONE").child("Amplifier");

        // Check if the setName value already exists in the database
        mainRef.child("Key"+setNameValue).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    progressDialog.dismiss();
                    Toast.makeText(FinalizeAmp.this, "Amp with this name already exists. Please choose a different name.", Toast.LENGTH_SHORT).show();
                } else {
                    // Create a HashMap to hold the data
                    Map<String, Object> ampData = new HashMap<>();
                    ampData.put("setName", setNameValue);
                    ampData.put("ampUsed", ampUsedValue);
                    ampData.put("description", descriptionValue);
                    ampData.put("genre", genreValue);
                    ampData.put("guitar", guitarValue);
                    ampData.put("pickups", pickupsValue);
                    ampData.put("by", userName);
                    ampData.put("uid",userId);
                    ampData.put("profilePic",profilepic);
                    ampData.put("key", "Key"+setNameValue);

                    // Create a HashMap to hold the data
                    Map<String, Object> settingsData = new HashMap<>();
                    settingsData.put("treble", treble);
                    settingsData.put("gain", gain);
                    settingsData.put("bass", bass);
                    settingsData.put("drive", drive);
                    settingsData.put("mid", mid);
                    settingsData.put("presence", presence);
                    settingsData.put("reverb", reverb2);
                    settingsData.put("tone", tone);
                    settingsData.put("gainstage", gainstage);

                    // Create a HashMap to hold the data
                    Map<String, Object> effectsData = new HashMap<>();
                    effectsData.put("overdrive", overdrive);
                    effectsData.put("distortion", distortion);
                    effectsData.put("fuzz", fuzz);
                    effectsData.put("delay", delay);
                    effectsData.put("reverb1", reverb1);
                    effectsData.put("chorus", chorus);
                    effectsData.put("flanger ", flanger);
                    effectsData.put("phaser", phaser);
                    effectsData.put("tremolo", tremolo);
                    effectsData.put("wah", wah);
                    effectsData.put("compressor", compressor);

                    // Initialize Firebase Storage reference
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference imageStorageRef = storage.getReference().child("gwazPic").child("AMPLIZONE").child("Amplifier").child("Key"+setNameValue).child(setNameValue + ".jpg");
                    StorageReference audioStorageRef = storage.getReference().child("gwazPic").child("AMPLIZONE").child("Amplifier").child("Key"+setNameValue).child(setNameValue + ".mp3");

                    DatabaseReference finalMainRef = mainRef.child("Key"+ setNameValue);
                    DatabaseReference finalSettingsRef = mainRef.child("Key"+ setNameValue).child("Settings");
                    DatabaseReference finalEffectsRef = mainRef.child("Key"+ setNameValue).child("Effects");

                    // Use a Firebase transaction to ensure atomicity
                    finalMainRef.runTransaction(new Transaction.Handler() {
                        @NonNull
                        @Override
                        public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                            // Save the amp data
                            mutableData.setValue(ampData);
                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, boolean committed, @Nullable DataSnapshot dataSnapshot) {
                            if (committed) {
                                // Save the settings data
                                finalSettingsRef.setValue(settingsData)
                                        .addOnSuccessListener(aVoid -> {
                                            // Save the effects data
                                            finalEffectsRef.setValue(effectsData)
                                                    .addOnSuccessListener(aVoid1 -> {
                                                        // Upload image to Firebase Storage
                                                        imageStorageRef.putFile(imageUrl)
                                                                .addOnSuccessListener(taskSnapshot -> {
                                                                    // Image uploaded successfully, get the download URL
                                                                    imageStorageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                                                        finalMainRef.child("imageUrl").setValue(uri.toString());
                                                                        progressDialog.dismiss();
                                                                        startActivity(new Intent(FinalizeAmp.this, setting.class));
                                                                        finish();

                                                                    });
                                                                })
                                                                .addOnFailureListener(e -> {
                                                                    // Handle failed upload
                                                                    progressDialog.dismiss();
                                                                    Toast.makeText(FinalizeAmp.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                });
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        progressDialog.dismiss();
                                                        Toast.makeText(FinalizeAmp.this, "Failed to save data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    });
                                        })
                                        .addOnFailureListener(e -> {
                                            progressDialog.dismiss();
                                            Toast.makeText(FinalizeAmp.this, "Failed to save data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        });

                                // Upload audio to Firebase Storage
                                audioStorageRef.putFile(audioUri)
                                        .addOnSuccessListener(taskSnapshot -> {
                                            // Audio uploaded successfully, get the download URL
                                            audioStorageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                                finalMainRef.child("audioUrl").setValue(uri.toString());
                                            });
                                        })
                                        .addOnFailureListener(e -> {
                                            // Handle failed upload
                                            progressDialog.dismiss();
                                            Toast.makeText(FinalizeAmp.this, "Failed to upload audio: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        });
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(FinalizeAmp.this, "Transaction failed. Please try again.", Toast.LENGTH_SHORT).show();
                                // If the transaction fails, delete the partially saved data
                                finalMainRef.removeValue();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(FinalizeAmp.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Get the image URL from the intent data
            imageUrl = data.getData();

            // Do something with the imageUrl, such as displaying it or saving it
            Toast.makeText(FinalizeAmp.this, "Image Selected ", Toast.LENGTH_SHORT).show();

            image1.setImageURI(imageUrl);
        }

        if (requestCode == 2 && resultCode == RESULT_OK) {
            // Get the audio file URI
            audioUri = data.getData();

            // Extract the file name from the URI
            String audioFileName = getFileName(audioUri);

            // Set the file name as the title
            audioTitle.setText(audioFileName);

            // Do something with the audio URI, such as displaying it or playing it
            Toast.makeText(FinalizeAmp.this, "Audio Selected", Toast.LENGTH_SHORT).show();



        }
    }


    private void playAudio(Uri audioUri) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(getApplicationContext(), audioUri);
            mediaPlayer.prepare();
            mediaPlayer.start();
            isPlaying = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private void pauseAudio(Uri audioUri) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPlaying = false;
        }
    }

    private void resumeAudio() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            isPlaying = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    // Method to get the file name from the URI
    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    // Method to vibrate the device
    private void vibrate() {
        // Check if the device supports vibration and if permission is granted
        if (vibrator != null && vibrator.hasVibrator()) {
            // Vibrate for 100 milliseconds
            vibrator.vibrate(100);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(FinalizeAmp.this, setting.class));
        vibrate();
        super.onBackPressed();
        finish();
    }


}