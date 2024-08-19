package com.av.Gwaz.login;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.av.Gwaz.R;
import com.av.Gwaz.homepage.Test;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class  login extends AppCompatActivity {
    TextView logsignup;
    Button login, forgotPass;
    EditText email, password;
    FirebaseAuth auth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Dialog loadingDialog;
    //private static final int PERMISSION_REQUEST_CODE = 123;

    private String NOTIF_KEY = "AUG27";
    private String LINK = "https://novalichessti-my.sharepoint.com/:f:/g/personal/calongcagong_241612_novaliches_sti_edu_ph/ErJtL1qthPFOsD1w0b58EhwB8BQ-Md2i98tsIDrVEBOESA?e=gBNT78";

    private FirebaseUser currentUser;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "LevelPrefs";
    private static final String KEY_HIGHEST_UNLOCKED_LEVEL = "highestUnlockedLevel";
    private static final String KEY_HIGHEST_UNLOCKED_LEVEL_DIAGRAM = "highestUnlockedLevelDiagram";




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("updateKey");

        Dialog ndialog = new Dialog(login.this,R.style.dialoge);
        ndialog.setContentView(R.layout.notif_dialog);
        ndialog.setCancelable(false);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get the value of updateKey
                String updateKeyValue = dataSnapshot.getValue(String.class);

                if (!updateKeyValue.equals(NOTIF_KEY)) {
                    if (!login.this.isFinishing() && !login.this.isDestroyed()) {
                        ndialog.show();
                    }

                    Button update, exit;
                    update = ndialog.findViewById(R.id.updateBtn);
                    exit = ndialog.findViewById(R.id.exitBtn);

                    update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(LINK)));
                            finish();
                            return;
                        }
                    });

                    exit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                            ndialog.dismiss();
                        }
                    });

                } else {
                    if (ndialog.isShowing()) {
                        ndialog.dismiss();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle possible errors
            }
        });


        //classic
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);



        // Initialize the custom loading dialog
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.dialog_loading);
        loadingDialog.setCancelable(false);

        ImageView loadingImageView = loadingDialog.findViewById(R.id.loadingImageView);
        Glide.with(this).asGif().load(R.drawable.loading_ic).into(loadingImageView);



        auth = FirebaseAuth.getInstance();
        login = findViewById(R.id.logbutton);
        forgotPass = findViewById(R.id.forgotPass);
        email = findViewById(R.id.editTexLogEmail);
        password = findViewById(R.id.editTextLogPassword);
        logsignup = findViewById(R.id.logsignup);

        // Check if user is already logged in and email is verified
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null && currentUser.isEmailVerified()) {
            // User is already logged in and email is verified, redirect to home activity
            startActivity(new Intent(this, Test.class));
            finish(); // Finish the current activity to prevent user from coming back to login screen using back button
        }

        logsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this,registration.class);
                startActivity(intent);

            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, ForgotPass.class);
                startActivity(intent);
             }
        });




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.show();
                if (!isNetworkConnected()) {
                    Toast.makeText(login.this, "No internet connection. Please check your connection and try again.", Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
                    return;
                }

                String Email = email.getText().toString();
                String pass = password.getText().toString();

                if (TextUtils.isEmpty(Email)) {
                    loadingDialog.dismiss();
                    Toast.makeText(login.this, "Enter The Email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(pass)) {
                    loadingDialog.dismiss();
                    Toast.makeText(login.this, "Enter The Password", Toast.LENGTH_SHORT).show();
                } else if (!Email.matches(emailPattern)) {
                    loadingDialog.dismiss();
                    email.setError("Give Proper Email Address");
                } else if (password.length() < 6) {
                    loadingDialog.dismiss();
                    password.setError("More Than Six Characters");
                    Toast.makeText(login.this, "Password Needs To Be Longer Than Six Characters", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the email exists in the database
                    FirebaseDatabase.getInstance().getReference().child("user").orderByChild("mail").equalTo(Email).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // Email exists, proceed with login
                                auth.signInWithEmailAndPassword(Email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        loadingDialog.dismiss();

                                        if (task.isSuccessful()) {
                                            if (auth.getCurrentUser().isEmailVerified()) {
                                                syncProgressFromFirebaseClassic();
                                                syncProgressFromFirebaseDiagram();
                                                Intent intent = new Intent(login.this, Test.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(login.this, "Failed to Login. Verify Your Account First.", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(login.this, "Invalid Email or Password.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                // Email does not exist, show toast
                                loadingDialog.dismiss();
                                Toast.makeText(login.this, "This email is not registered.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            loadingDialog.dismiss();
                            Toast.makeText(login.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    private void syncProgressFromFirebaseClassic() {

        currentUser = FirebaseAuth.getInstance().getCurrentUser();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("user")
                .child(auth.getCurrentUser().getUid())
                .child("Levels")
                .child("Classic");

        if (auth.getCurrentUser() != null && databaseReference != null) {
            databaseReference.child(KEY_HIGHEST_UNLOCKED_LEVEL).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        int classiclevel = dataSnapshot.getValue(Integer.class);
                        SharedPreferences sharedPreferences = getSharedPreferences("LevelPrefs", Context.MODE_PRIVATE);
                        int highestUnlockedLevel = sharedPreferences.getInt("highestUnlockedLevel", 1);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(KEY_HIGHEST_UNLOCKED_LEVEL, classiclevel);
                        editor.apply();



                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(login.this, "Failed to sync game progress", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void syncProgressFromFirebaseDiagram() {

        currentUser = FirebaseAuth.getInstance().getCurrentUser();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("user")
                .child(auth.getCurrentUser().getUid())
                .child("Levels")
                .child("Diagram");

        if (auth.getCurrentUser() != null && databaseReference != null) {
            databaseReference.child(KEY_HIGHEST_UNLOCKED_LEVEL_DIAGRAM).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        int diagramlevel = dataSnapshot.getValue(Integer.class);
                        SharedPreferences sharedPreferences = getSharedPreferences("LevelPrefs", Context.MODE_PRIVATE);
                        int highestUnlockedLevel = sharedPreferences.getInt("highestUnlockedLevelDiagram", 1);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(KEY_HIGHEST_UNLOCKED_LEVEL_DIAGRAM, diagramlevel);
                        editor.apply();



                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(login.this, "Failed to sync game progress", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void onBackPressed() {
        // Prevent going back to the previous activity (which should be the fragment's host activity)
        finishAffinity(); // Finish this activity and all activities immediately below it in the current task
    }


    // Check if permissions are granted
    /*private boolean checkPermissions() {
        int permissionNetwork = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionVibrate = ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE);
        int permissionReadMediaAudio = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO);
        int permissionReadMediaImages = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES);
        return permissionNetwork == PackageManager.PERMISSION_GRANTED &&
                permissionVibrate == PackageManager.PERMISSION_GRANTED &&
                permissionReadMediaAudio == PackageManager.PERMISSION_GRANTED &&
                permissionReadMediaImages == PackageManager.PERMISSION_GRANTED;
    }

    // Request permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.VIBRATE,
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_IMAGES
        }, PERMISSION_REQUEST_CODE);
    }*/
}