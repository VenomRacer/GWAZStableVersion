package com.av.Gwaz.homepage.CHORDM.Classic.Levels;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.av.Gwaz.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class Level4 extends AppCompatActivity {

    private ImageView speaker, g_press, em_press,c_press,dm_press, am_press, b_press;
    private MediaPlayer mediaPlayer,missnote,rightnote,allright;
    private String[] chords = {"gchord", "emchord", "cchord","dmchord", "amchord", "bchord"};
    private boolean[] chordGuessed = {false, false, false, false, false, false};
    private int currentChordIndex = 0;
    private int score = 0;
    private int prevsore;
    private Vibrator vibrator;
    private String userName,profilepic;
    ProgressDialog progressDialog;
    private TextView errors;
    private int wrongGuessCount = 0;
    private static final int MAX_WRONG_GUESSES = 5;

    private FirebaseUser currentUser;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "LevelPrefs";
    private static final String KEY_HIGHEST_UNLOCKED_LEVEL = "highestUnlockedLevel";




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level4);

        speaker = findViewById(R.id.speaker);
        g_press = findViewById(R.id.g_press);
        em_press = findViewById(R.id.em_press);
        c_press = findViewById(R.id.c_press);
        dm_press = findViewById(R.id.dm_press);
        am_press = findViewById(R.id.am_press);
        b_press = findViewById(R.id.b_press);

        errors = findViewById(R.id.errors);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.setCancelable(false);

        prevsore = getIntent().getIntExtra("prevscore", 0);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference()
                    .child("user")
                    .child(currentUser.getUid())
                    .child("Levels")
                    .child("Classic");


        }




        showChordDialog();

        speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();

            }
        });


        // Set OnClickListener for the option ImageViews
        g_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGuess("gchord");
            }
        });

        em_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGuess("emchord");
            }
        });

        c_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGuess("cchord");
            }
        });

        dm_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGuess("dmchord");
            }
        });

        am_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGuess("amchord");
            }
        });

        b_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGuess("bchord");
            }
        });
    }


    // Method to show dialog displaying the chords and their sounds
    private void showChordDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.pregamedialog4);
        dialog.setCancelable(false);

        ImageView firstchord = dialog.findViewById(R.id.firstchord);
        ImageView secondchord = dialog.findViewById(R.id.secondchord);

        TextView next = dialog.findViewById(R.id.next);

        // Get the current user's ID
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            final String userId = currentUser.getUid();

            // Reference to the user's node in the database
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference()
                    .child("user")
                    .child(userId);

            // Add a ValueEventListener to retrieve the userName
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Check if the dataSnapshot exists and contains the userName
                    if (dataSnapshot.exists() && dataSnapshot.hasChild("userName")) {
                        userName = dataSnapshot.child("userName").getValue(String.class);

                    } else {
                        // The userName is not available in the database
                        Log.d("UserName", "User name not found");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors
                    Log.e("Firebase", "Error fetching userName: " + databaseError.getMessage());
                }
            });
        } else {
            // User is not signed in
            Log.d("CurrentUser", "User is not signed in");
        }


        // Set click listener for the chord image views to play their sounds
        firstchord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playChordSound(chords[4]);

            }
        });

        secondchord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playChordSound(chords[3]);

            }
        });



        // Set click listener for the start game button
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // Dismiss the dialog
                // Start the game by playing a random chord
                playRandomChord();

            }
        });

        dialog.show();
    }

    // Method to play the sound of a chord
    private void playChordSound(String chord) {
        int resID = getResources().getIdentifier(chord, "raw", getPackageName());
        mediaPlayer = MediaPlayer.create(this, resID);
        mediaPlayer.start();
    }

    private void postgame(final int score) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.postgamedialog);
        dialog.setCancelable(false);

        // Check for network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // Get the current user's ID
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            final String userId = currentUser.getUid();

            // Reference to the user's node in the database
            DatabaseReference userRef1 = FirebaseDatabase.getInstance().getReference()
                    .child("user")
                    .child(userId);

            // Add a ValueEventListener to retrieve the userName
            userRef1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Check if the dataSnapshot exists and contains the userName
                    if (dataSnapshot.exists() && dataSnapshot.hasChild("userName")||
                            dataSnapshot.exists() && dataSnapshot.hasChild("profilepic")) {
                        userName = dataSnapshot.child("userName").getValue(String.class);
                        profilepic = dataSnapshot.child("profilepic").getValue(String.class);

                    } else {
                        // The userName is not available in the database
                        Log.d("UserName", "User name not found");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors
                    Log.e("Firebase", "Error fetching userName: " + databaseError.getMessage());
                }
            });
        } else {
            // User is not signed in
            Log.d("CurrentUser", "User is not signed in");
        }

        TextView scoredisplay = dialog.findViewById(R.id.scoreDisplay);
        Button yes = dialog.findViewById(R.id.yes);
        Button exit = dialog.findViewById(R.id.exit);
        Button upload = dialog.findViewById(R.id.uploadScore);

        scoredisplay.setText(String.valueOf(score + prevsore));
        int firstScore = score + prevsore;

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Level4.this, Level5.class);
                intent.putExtra("prevscore", firstScore);
                startActivity(intent);
                finish();
            }
        });

        if (networkInfo != null && networkInfo.isConnected()) {
            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the current user's ID
                    saveProgress(5);
                    progressDialog.show();
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (currentUser != null) {
                        final String userId = currentUser.getUid();

                        // Reference to the user's node in the database
                        DatabaseReference userRef2 = FirebaseDatabase.getInstance().getReference()
                                .child("Service")
                                .child("CHORDM")
                                .child("Leaderboard")
                                .child("Classic")
                                .child(userId);

                        // Retrieve the current score from the database
                        userRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists() && dataSnapshot.hasChild("score")) {
                                    // Get the current score
                                    long currentScore = dataSnapshot.child("score").getValue(Long.class);

                                    // Add the new score to the current score
                                    long updatedScore = currentScore + score + prevsore;

                                    // Update the score in the database
                                    dataSnapshot.getRef().child("score").setValue(updatedScore)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        progressDialog.dismiss();
                                                        Toast.makeText(Level4.this, "Score uploaded successfully", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(Level4.this, "Failed to upload score", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                    upload.setClickable(false);
                                } else {
                                    userRef2.child("score").setValue(score);
                                    userRef2.child("userId").setValue(userId);
                                    userRef2.child("userName").setValue(userName);
                                    userRef2.child("profilepic").setValue(profilepic);
                                    Toast.makeText(Level4.this, "Score uploaded successfully", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    upload.setClickable(false);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Handle errors
                                progressDialog.dismiss();
                                Toast.makeText(Level4.this, "Failed to retrieve score: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        // User is not signed in
                        progressDialog.dismiss();
                        Toast.makeText(Level4.this, "User is not signed in", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            // If there's no network connectivity, display a toast
            Toast.makeText(Level4.this, "No network connection", Toast.LENGTH_SHORT).show();
        }



        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
    }

    private void fivemistakepostgame(final int score) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.postgamedialog_fivemistakes);
        dialog.setCancelable(false);

        // Check for network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // Get the current user's ID
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            final String userId = currentUser.getUid();

            // Reference to the user's node in the database
            DatabaseReference userRef1 = FirebaseDatabase.getInstance().getReference()
                    .child("user")
                    .child(userId);

            // Add a ValueEventListener to retrieve the userName
            userRef1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Check if the dataSnapshot exists and contains the userName
                    if (dataSnapshot.exists() && dataSnapshot.hasChild("userName")||
                            dataSnapshot.exists() && dataSnapshot.hasChild("profilepic")) {
                        userName = dataSnapshot.child("userName").getValue(String.class);
                        profilepic = dataSnapshot.child("profilepic").getValue(String.class);

                    } else {
                        // The userName is not available in the database
                        Log.d("UserName", "User name not found");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors
                    Log.e("Firebase", "Error fetching userName: " + databaseError.getMessage());
                }
            });
        } else {
            // User is not signed in
            Log.d("CurrentUser", "User is not signed in");
        }

        TextView scoredisplay = dialog.findViewById(R.id.scoreDisplay);
        Button exit = dialog.findViewById(R.id.exit);
        Button upload = dialog.findViewById(R.id.uploadScore);

        scoredisplay.setText(String.valueOf(score));


        if (networkInfo != null && networkInfo.isConnected()) {
            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the current user's ID
                    progressDialog.show();
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (currentUser != null) {
                        final String userId = currentUser.getUid();

                        // Reference to the user's node in the database
                        DatabaseReference userRef2 = FirebaseDatabase.getInstance().getReference()
                                .child("Service")
                                .child("CHORDM")
                                .child("Leaderboard")
                                .child("Classic")
                                .child(userId);

                        // Retrieve the current score from the database
                        userRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists() && dataSnapshot.hasChild("score")) {
                                    // Get the current score
                                    long currentScore = dataSnapshot.child("score").getValue(Long.class);

                                    // Add the new score to the current score
                                    long updatedScore = currentScore + score;

                                    // Update the score in the database
                                    dataSnapshot.getRef().child("score").setValue(updatedScore)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        progressDialog.dismiss();
                                                        Toast.makeText(Level4.this, "Score uploaded successfully", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(Level4.this, "Failed to upload score", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                    upload.setClickable(false);
                                } else {
                                    userRef2.child("score").setValue(score);
                                    userRef2.child("userId").setValue(userId);
                                    userRef2.child("userName").setValue(userName);
                                    userRef2.child("profilepic").setValue(profilepic);
                                    Toast.makeText(Level4.this, "Score uploaded successfully", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    upload.setClickable(false);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Handle errors
                                progressDialog.dismiss();
                                Toast.makeText(Level4.this, "Failed to retrieve score: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        // User is not signed in
                        progressDialog.dismiss();
                        Toast.makeText(Level4.this, "User is not signed in", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            // If there's no network connectivity, display a toast
            Toast.makeText(Level4.this, "No network connection", Toast.LENGTH_SHORT).show();
        }



        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
    }


    // Method to play a random chord
    private void playRandomChord() {
        Random random = new Random();
        int randomIndex;
        do {
            randomIndex = random.nextInt(chords.length);
        } while (chordGuessed[randomIndex]); // Keep generating a new random index until we find a chord that hasn't been guessed
        currentChordIndex = randomIndex;
        int resID = getResources().getIdentifier(chords[currentChordIndex], "raw", getPackageName());
        mediaPlayer = MediaPlayer.create(this, resID);
        mediaPlayer.start();
    }

    // Method to check the user's guess
    private void checkGuess(String guessedChord) {
        if (guessedChord.equals(chords[currentChordIndex])) {
            // Correct guess
            if (!isChordGuessed(currentChordIndex)) {
                score += 10;
                setChordGuessed(currentChordIndex, true);
                if (allChordsGuessed()) {
                    // User has guessed both chords
                    unlockNextLevel(5);
                    playAllGuess();
                    postgame(score);
                } else {
                    // Play the next chord
                    playCorrectGuessSound();
                }
            }
        } else {
            vibrate();
            playIncorrectGuessSound();
            wrongGuessCount++;
            if (wrongGuessCount >= MAX_WRONG_GUESSES) {
                // User reached maximum wrong guesses, calculate score
                score -= (wrongGuessCount - MAX_WRONG_GUESSES); // Deduct extra wrong guesses from the score
                fivemistakepostgame(score);
            } else {
                mediaPlayer.start();
            }
        }
        // Update the remaining errors TextView
        int remainingErrors = MAX_WRONG_GUESSES - wrongGuessCount;
        errors.setText(String.valueOf(remainingErrors));
    }

    // Method to play a sound indicating incorrect or right guess
    private void playIncorrectGuessSound() {
        // Initialize MediaPlayer if not already initialized
        if (missnote == null) {
            missnote = MediaPlayer.create(Level4.this, R.raw.wrong); // Replace R.raw.incorrect_guess_sound with the actual sound resource
        }
        // Start playing the sound
        missnote.start();
    }

    // Method to play a sound indicating correct guess
    private void playCorrectGuessSound() {
        // Initialize MediaPlayer if not already initialized
        if (rightnote == null) {
            rightnote = MediaPlayer.create(Level4.this, R.raw.right); // Replace R.raw.right with the actual sound resource
        }

        // Set an OnCompletionListener to play the random chord after the correct guess sound finishes
        rightnote.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playRandomChord();
            }
        });

        // Start playing the sound
        rightnote.start();
    }

    private void playAllGuess() {
        // Initialize MediaPlayer if not already initialized
        if (allright == null) {
            allright = MediaPlayer.create(Level4.this, R.raw.allguess); // Replace R.raw.incorrect_guess_sound with the actual sound resource
        }
        // Start playing the sound
        allright.start();
    }

    // Method to check if a specific chord has been guessed
    private boolean isChordGuessed(int index) {
        return chordGuessed[index];
    }

    // Method to set a specific chord as guessed
    private void setChordGuessed(int index, boolean guessed) {
        chordGuessed[index] = guessed;
    }

    // Method to check if all chords have been guessed
    private boolean allChordsGuessed() {
        for (boolean guessed : chordGuessed) {
            if (!guessed) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release the MediaPlayer resources
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        if (missnote != null) {
            missnote.release();
            missnote = null;
        }

        if (rightnote != null) {
            rightnote.release();
            rightnote = null;
        }

        if (allright != null) {
            allright.release();
            allright = null;
        }
    }

    // Method to vibrate the device
    private void vibrate() {
        // Initialize Vibrator if not already initialized
        if (vibrator == null) {
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        }
        // Vibrate for 500 milliseconds
        if (vibrator != null) {
            vibrator.vibrate(500);
        }
    }

    private void saveProgress(int nextLevel) {

        SharedPreferences sharedPreferences = getSharedPreferences("LevelPrefs", Context.MODE_PRIVATE);
        int highestUnlockedLevel = sharedPreferences.getInt("highestUnlockedLevel", 1);

        if (nextLevel >= highestUnlockedLevel) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(KEY_HIGHEST_UNLOCKED_LEVEL, nextLevel);
            editor.apply();

            if (currentUser != null && databaseReference != null) {
                databaseReference.child(KEY_HIGHEST_UNLOCKED_LEVEL).setValue(highestUnlockedLevel)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("Firebase", "Progress saved to Firebase");
                                } else {
                                    Log.e("Firebase", "Failed to save progress: " + task.getException().getMessage());
                                }
                            }
                        });
            }

        }

    }

    private void unlockNextLevel(int nextLevel) {
        SharedPreferences sharedPreferences = getSharedPreferences("LevelPrefs", Context.MODE_PRIVATE);
        int highestUnlockedLevel = sharedPreferences.getInt("highestUnlockedLevel", 1);

        if (nextLevel > highestUnlockedLevel) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("highestUnlockedLevel", nextLevel);
            editor.apply();
        }
    }





}