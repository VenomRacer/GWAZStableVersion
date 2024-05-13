package com.av.Gwaz.homepage.AMPLIZ.AllSettings;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

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
import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AllEdit4 extends AppCompatActivity {

    private CardView addImg,addSound;
    private Vibrator vibrator;
    private EditText setNamee,ampUsede;
    private TextInputEditText descriptione;
    private Button back,save;
    private ImageView image1, soundpreview;
    private ImageButton playButton, pauseButton;
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private Uri audioUri, imageUrl;
    private TextView audioTitle;
    private Spinner genreSpinner, guitarSpinner, pickupsSpinner;
    private String treble,gain,bass,drive,mid,presence,reverb2,tone,gainstage;
    // Declare img and aud variables
    private String img;
    private String aud;
    private String userName;

    private boolean overdrive,distortion,fuzz,delay,reverb1,chorus,flanger,phaser,tremolo,wah,compressor;

    // Firebase Database reference
    private DatabaseReference mainRef, settingsRef, effectsRef,commentsRef;
    private StorageReference audioStorageRef, imageStorageRef;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_edit4);

        addImg = findViewById(R.id.addImg);
        image1 = findViewById(R.id.image1);
        addSound = findViewById(R.id.addSound);
        soundpreview = findViewById(R.id.soundpreview);
        playButton = findViewById(R.id.playButton);
        pauseButton = findViewById(R.id.pauseButton);
        audioTitle = findViewById(R.id.audioTitle);
        back = findViewById(R.id.back);
        save = findViewById(R.id.save);
        setNamee = findViewById(R.id.setName);
        ampUsede = findViewById(R.id.ampUsed);
        genreSpinner = findViewById(R.id.genreSpinner);
        guitarSpinner = findViewById(R.id.guitarSpinner);
        pickupsSpinner = findViewById(R.id.pickupsSpinner);
        descriptione = findViewById(R.id.description);

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

        //retrieve main info
        String setName = getIntent().getStringExtra("setName");
        String genre = getIntent().getStringExtra("genre");
        String by = getIntent().getStringExtra("by");
        String amp = getIntent().getStringExtra("ampUsed");
        String guitar = getIntent().getStringExtra("guitar");
        String pickups = getIntent().getStringExtra("pickups");
        String desc = getIntent().getStringExtra("description");
        String img = getIntent().getStringExtra("imageUrl");
        String aud = getIntent().getStringExtra("audioUrl");
        String key = getIntent().getStringExtra("key");

        //retrieve knobs 9
        String bass = getIntent().getStringExtra("bass");
        String drive = getIntent().getStringExtra("drive");
        String gain = getIntent().getStringExtra("gain");
        String gainstage = getIntent().getStringExtra("gainstage");
        String mid = getIntent().getStringExtra("mid");
        String presence = getIntent().getStringExtra("presence");
        String reverb = getIntent().getStringExtra("reverb");
        String tone = getIntent().getStringExtra("tone");
        String treble = getIntent().getStringExtra("treble");

        // Retrieve effects
        boolean chorus = getIntent().getStringExtra("chorus") != null && getIntent().getStringExtra("chorus").equals("true");
        boolean compressor = getIntent().getStringExtra("compressor") != null && getIntent().getStringExtra("compressor").equals("true");
        boolean delay = getIntent().getStringExtra("delay") != null && getIntent().getStringExtra("delay").equals("true");
        boolean distortion = getIntent().getStringExtra("distortion") != null && getIntent().getStringExtra("distortion").equals("true");
        boolean flanger = getIntent().getStringExtra("flanger") != null && getIntent().getStringExtra("flanger").equals("true");
        boolean fuzz = getIntent().getStringExtra("fuzz") != null && getIntent().getStringExtra("fuzz").equals("true");
        boolean overdrive = getIntent().getStringExtra("overdrive") != null && getIntent().getStringExtra("overdrive").equals("true");
        boolean phaser = getIntent().getStringExtra("phaser") != null && getIntent().getStringExtra("phaser").equals("true");
        boolean reverb1 = getIntent().getStringExtra("reverb1") != null && getIntent().getStringExtra("reverb1").equals("true");
        boolean tremolo = getIntent().getStringExtra("tremolo") != null && getIntent().getStringExtra("tremolo").equals("true");
        boolean wah = getIntent().getStringExtra("wah") != null && getIntent().getStringExtra("wah").equals("true");

        setNamee.setText(setName);
        ampUsede.setText(amp);
        descriptione.setText(desc);

        // displaying spinner current value
        int index1 = adapter1.getPosition(genre);
        spinner1.setSelection(index1);

        int index2 = adapter2.getPosition(guitar);
        spinner2.setSelection(index2);

        int index3 = adapter3.getPosition(pickups);
        spinner3.setSelection(index3);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = currentUser.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user").child(currentUserId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userName = snapshot.child("userName").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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
                } else  {

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
                ProgressDialog progressDialog = new ProgressDialog(AllEdit4.this);
                progressDialog.setMessage("Saving data...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                String setNameValue = setNamee.getText().toString().trim();
                String ampUsedValue = ampUsede.getText().toString().trim();
                String descriptionValue = descriptione.getText().toString().trim();
                String genreValue = genreSpinner.getSelectedItem().toString();
                String guitarValue = guitarSpinner.getSelectedItem().toString();
                String pickupsValue = pickupsSpinner.getSelectedItem().toString();

                // Check for network connectivity
                if (!isNetworkConnected()) {
                    Toast.makeText(AllEdit4.this, "No internet connection. Please check your network.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }

                if (TextUtils.isEmpty(setNameValue) || TextUtils.isEmpty(ampUsedValue) || TextUtils.isEmpty(descriptionValue)) {
                    Toast.makeText(AllEdit4.this, "Please fill in all fields and select an image and audio file", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }

                // Initialize Firebase Database reference (main)
                DatabaseReference mainRef = FirebaseDatabase.getInstance().getReference().child("Service").child("AMPLIZONE").child("Amplifier");
                mainRef.child("Key"+setName).removeValue();


                // Check if the setName value already exists in the database
                mainRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            // Create a HashMap to hold the data
                            Map<String, Object> ampData = new HashMap<>();
                            ampData.put("setName", setNameValue);
                            ampData.put("ampUsed", ampUsedValue);
                            ampData.put("description", descriptionValue);
                            ampData.put("genre", genreValue);
                            ampData.put("guitar", guitarValue);
                            ampData.put("pickups", pickupsValue);
                            ampData.put("by", userName);
                            ampData.put("imageUrl", img);
                            ampData.put("audioUrl", aud);
                            ampData.put("key", key);

                            // Create a HashMap to hold the data
                            Map<String, Object> settingsData = new HashMap<>();
                            settingsData.put("treble", treble);
                            settingsData.put("gain", gain);
                            settingsData.put("bass", bass);
                            settingsData.put("drive", drive);
                            settingsData.put("mid", mid);
                            settingsData.put("presence", presence);
                            settingsData.put("reverb", reverb);
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
                            StorageReference imageStorageRef = storage.getReference().child("gwazPic").child("AMPLIZONE").child("Amplifier").child(setNameValue).child(setNameValue + ".jpg");
                            StorageReference audioStorageRef = storage.getReference().child("gwazPic").child("AMPLIZONE").child("Amplifier").child(setNameValue).child(setNameValue + ".mp3");

                            DatabaseReference finalMainRef = mainRef.child(key);
                            DatabaseReference finalSettingsRef = mainRef.child(key).child("Settings");
                            DatabaseReference finalEffectsRef = mainRef.child(key).child("Effects");



                            // Use a Firebase transaction to ensure atomicity
                            finalMainRef.runTransaction(new Transaction.Handler() {
                                @NonNull
                                @Override
                                public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                                    // Save the amp data
                                    mutableData.setValue(ampData);
                                    finalSettingsRef.setValue(settingsData);
                                    finalEffectsRef.setValue(effectsData);
                                    return Transaction.success(mutableData);
                                }

                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, boolean committed, @Nullable DataSnapshot dataSnapshot) {

                                }
                            });

                            progressDialog.dismiss();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        Toast.makeText(AllEdit4.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                finish();


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
            Toast.makeText(AllEdit4.this, "Image Selected ", Toast.LENGTH_SHORT).show();

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
            Toast.makeText(AllEdit4.this, "Audio Selected", Toast.LENGTH_SHORT).show();



        }
    }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        vibrate(); // Vibrate for 200 milliseconds when back button is pressed
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

    // Function to delete a folder and its contents recursively
    private void deleteFolderContents(StorageReference ref) {
        ref.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item : listResult.getItems()) {
                    item.delete();
                }
                for (StorageReference prefix : listResult.getPrefixes()) {
                    deleteFolderContents(prefix);
                }
                ref.delete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Error deleting folder contents", e);
            }
        });
    }
}