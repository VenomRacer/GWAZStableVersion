package com.av.Gwaz.homepage.CHORDM.Timed.Basic.sixty.thirty;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

public class thirtyb extends AppCompatActivity {

    private ImageView speaker;
    private MediaPlayer mediaPlayer,missnote,rightnote,allright;
    private ImageView chordImage1;
    private ImageView chordImage2;
    private String userName,profilepic;

    private String[] chords = {"achord", "bchord", "cchord", "dchord", "echord", "fchord", "gchord", "emchord"};
    private int currentlyPlayingChordIndex = -1;
    private TextView timerText, targetCount;
    private static final long TIMER_DURATION = 31000; // 60 seconds
    private CountDownTimer countDownTimer;
    private int score = 0, target = 15;
    private Vibrator vibrator;
    private boolean chordGuessed = false;
    ProgressDialog progressDialog;
    boolean buttonClicked = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixtyb);

        // Initialize views
        speaker = findViewById(R.id.speaker);
        chordImage1 = findViewById(R.id.first);
        chordImage2 = findViewById(R.id.second);
        timerText = findViewById(R.id.timerText);
        targetCount = findViewById(R.id.targetCount);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.setCancelable(false);

        targetCount.setText(String.valueOf(target));

        // Start the game
        playRandomChord();
        startTimer();

        speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();

            }
        });

        // Set OnClickListener for chord choices
        chordImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the checkGuess method with the chord name if chordGuessed is false
                if (!buttonClicked) {
                    buttonClicked = true; // Set chordGuessed to true to disable further clicks
                    checkGuess((String) chordImage1.getTag());
                }
            }
        });
        chordImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the checkGuess method with the chord name if chordGuessed is false
                if (!buttonClicked) {
                    buttonClicked = true; // Set chordGuessed to true to disable further clicks
                    checkGuess((String) chordImage2.getTag());
                }
            }
        });
    }

    private void playRandomChord() {
        Random random = new Random();
        currentlyPlayingChordIndex = random.nextInt(chords.length); // Update currentlyPlayingChordIndex to a valid index
        int correctChordIndex = random.nextInt(2); // 0 or 1 to determine which ImageView will display the correct chord

        int chordSoundResID = getResources().getIdentifier(chords[currentlyPlayingChordIndex], "raw", getPackageName());
        int chordImageResID = getResources().getIdentifier(chords[currentlyPlayingChordIndex], "drawable", getPackageName());

        if (chordSoundResID != 0 && chordImageResID != 0) {
            mediaPlayer = MediaPlayer.create(this, chordSoundResID);
            if (mediaPlayer != null) {
                mediaPlayer.start();

                // Assign the correct chord to one ImageView and a random chord to the other
                if (correctChordIndex == 0) {
                    chordImage1.setImageResource(chordImageResID);
                    chordImage1.setTag(chords[currentlyPlayingChordIndex]);

                    int randomIndex2;
                    do {
                        randomIndex2 = random.nextInt(chords.length);
                    } while (randomIndex2 == currentlyPlayingChordIndex); // Make sure it's not the same as the correct chord
                    chordImage2.setImageResource(getResources().getIdentifier(chords[randomIndex2], "drawable", getPackageName()));
                    chordImage2.setTag(chords[randomIndex2]);
                } else {
                    chordImage2.setImageResource(chordImageResID);
                    chordImage2.setTag(chords[currentlyPlayingChordIndex]);

                    int randomIndex2;
                    do {
                        randomIndex2 = random.nextInt(chords.length);
                    } while (randomIndex2 == currentlyPlayingChordIndex); // Make sure it's not the same as the correct chord
                    chordImage1.setImageResource(getResources().getIdentifier(chords[randomIndex2], "drawable", getPackageName()));
                    chordImage1.setTag(chords[randomIndex2]);
                }
            } else {
                // Handle MediaPlayer creation failure
                Toast.makeText(this, "Failed to create MediaPlayer", Toast.LENGTH_SHORT).show();
                Log.e("MediaPlayer", "Failed to create MediaPlayer for resource: " + chords[currentlyPlayingChordIndex]);
            }
        } else {
            // Handle resource ID not found
            Toast.makeText(this, "Resource ID not found", Toast.LENGTH_SHORT).show();
            Log.e("MediaPlayer", "Resource ID not found for chord: " + chords[currentlyPlayingChordIndex]);
        }
        waveAnim();
    }

    // Method to check the user's guess
    private void checkGuess(String guessedChord) {
        try {
            // Check if the guessed chord matches the currently playing chord
            if (currentlyPlayingChordIndex != -1 && guessedChord.equals(chords[currentlyPlayingChordIndex])) {
                // Correct guess
                score += 10;
                target -= 1;
                targetCount.setText(String.valueOf(target));
                chordGuessed = true;
                playCorrectGuessSound();

                // Check if the target is zero and call postgame if true
                if (target == 0) {
                    reachSound();
                    postgame(score);
                    countDownTimer.cancel();
                }
            } else {
                // Incorrect guess
                chordGuessed = false;
                playRandomChord();
                missNote ();
                vibrate();

            }
            // After processing the guess, enable the button click again after a delay
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    buttonClicked = false; // Set chordGuessed to false to enable clicks again
                }
            }, 1000); // Delay of 1000 milliseconds (adjust as needed)
        } catch (ArrayIndexOutOfBoundsException e) {
            // Log any errors
            Log.e("CheckGuess", "Error checking guess: " + e.getMessage());
        }
    }
    // Method to play a sound indicating correct guess
    private void playCorrectGuessSound() {
        // Initialize MediaPlayer if not already initialized
        if (rightnote == null) {
            rightnote = MediaPlayer.create(thirtyb.this, R.raw.right); // Replace R.raw.right with the actual sound resource
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

    private void reachSound () {

        // Initialize MediaPlayer if not already initialized
        if (allright == null) {
            allright = MediaPlayer.create(thirtyb.this, R.raw.allguess); // Replace R.raw.right with the actual sound resource
        }
        allright.start();

    }

    private void missNote () {

        // Initialize MediaPlayer if not already initialized
        if (missnote == null) {
            missnote = MediaPlayer.create(thirtyb.this, R.raw.wrong); // Replace R.raw.right with the actual sound resource
        }
        missnote.start();

    }

    private void postgame2 () {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.postgamedialogtimed);
        dialog.setCancelable(false);


        Button exit = dialog.findViewById(R.id.exit);

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });



        dialog.show();
    }

    private void postgame(final int score) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.postgamedialogtimedtarget);
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
                                .child("Timed")
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
                                                        Toast.makeText(thirtyb.this, "Score uploaded successfully", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(thirtyb.this, "Failed to upload score", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                    upload.setClickable(false);
                                } else {
                                    userRef2.child("score").setValue(score);
                                    userRef2.child("userId").setValue(userId);
                                    userRef2.child("userName").setValue(userName);
                                    userRef2.child("profilepic").setValue(profilepic);
                                    Toast.makeText(thirtyb.this, "Score uploaded successfully", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    upload.setClickable(false);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Handle errors
                                progressDialog.dismiss();
                                Toast.makeText(thirtyb.this, "Failed to retrieve score: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        // User is not signed in
                        progressDialog.dismiss();
                        Toast.makeText(thirtyb.this, "User is not signed in", Toast.LENGTH_SHORT).show();
                    }
                }

            });

        } else {
            // If there's no network connectivity, display a toast
            Toast.makeText(thirtyb.this, "No network connection", Toast.LENGTH_SHORT).show();
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

    // Method to start the timer
    private void startTimer() {
        countDownTimer = new CountDownTimer(TIMER_DURATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timer text with remaining time
                long secondsRemaining = millisUntilFinished / 1000;
                timerText.setText(String.valueOf(secondsRemaining));
            }

            @Override
            public void onFinish() {
                // Timer finished, perform necessary actions
                timerText.setText("0");
                handleTimerFinish(); // You can define this method for what happens when timer finishes
            }
        }.start();
    }

    // Method to handle actions when the timer finishes
    private void handleTimerFinish() {

        postgame2();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release the MediaPlayer resources
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        if (rightnote != null) {
            rightnote.release();
            rightnote = null;
        }

        if (allright != null) {
            allright.release();
            allright = null;
        }
        // Cancel the timer to prevent memory leaks
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void waveAnim() {

        // Load the animation
        Animation soundwaveAnimation = AnimationUtils.loadAnimation(thirtyb.this, R.anim.soundwave_anim);

        // Start the animation
        speaker.startAnimation(soundwaveAnimation);

    }

}