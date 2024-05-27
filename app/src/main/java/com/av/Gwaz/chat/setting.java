    package com.av.Gwaz.chat;

    import android.Manifest;
    import android.annotation.SuppressLint;
    import android.app.Dialog;
    import android.content.Context;
    import android.content.Intent;
    import android.content.pm.PackageManager;
    import android.net.ConnectivityManager;
    import android.net.NetworkInfo;
    import android.net.Uri;
    import android.os.Bundle;
    import android.provider.Settings;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.app.ActivityCompat;
    import androidx.core.content.ContextCompat;

    import com.av.Gwaz.R;
    import com.av.Gwaz.homepage.AMPLIZ.Add.AddAmp;
    import com.av.Gwaz.homepage.AMPLIZ.MyAmp.MyAmp;
    import com.av.Gwaz.login.login;
    import com.bumptech.glide.Glide;
    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.android.gms.tasks.Task;
    import com.google.android.material.floatingactionbutton.FloatingActionButton;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;
    import com.google.firebase.storage.FirebaseStorage;
    import com.google.firebase.storage.StorageReference;
    import com.google.firebase.storage.UploadTask;
    import com.squareup.picasso.Picasso;

    public class setting extends AppCompatActivity {
        ImageView setprofile, myAmp, mailIC, nameIC,statusIC;
        EditText setname, setstatus;
        TextView displayText, ampCount, Email;
        Button donebut, logoutBtn;
        FloatingActionButton Add;
        FirebaseAuth auth;
        FirebaseDatabase database;
        FirebaseStorage storage;
        Uri setImageUri;
        String email,password,oldname;
        Dialog loadingDialog;

        private static final int PERMISSION_REQUEST_CODE = 123;



        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_setting);
            auth = FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance();
            storage = FirebaseStorage.getInstance();
            setprofile = findViewById(R.id.settingprofile);
            setname = findViewById(R.id.settingname);
            setstatus = findViewById(R.id.settingstatus);
            donebut = findViewById(R.id.donebutt);
            logoutBtn = findViewById(R.id.logoutBtn);
            displayText = findViewById(R.id.displayName);
            Add = findViewById(R.id.Add);
            myAmp = findViewById(R.id.myAmp);
            ampCount = findViewById(R.id.ampCount);
            Email = findViewById(R.id.Email);
            mailIC = findViewById(R.id.mailIC);
            nameIC = findViewById(R.id.nameIC);
            statusIC = findViewById(R.id.statusIC);

            Glide.with(this).asGif().load(R.drawable.profile_loading).into(setprofile);
            Glide.with(this).asGif().load(R.drawable.loading_yellow).into(mailIC);
            Glide.with(this).asGif().load(R.drawable.loading_yellow).into(nameIC);
            Glide.with(this).asGif().load(R.drawable.loading_yellow).into(statusIC);



            // Initialize the custom loading dialog
            loadingDialog = new Dialog(this);
            loadingDialog.setContentView(R.layout.dialog_loading);
            loadingDialog.setCancelable(false);

            ImageView loadingImageView = loadingDialog.findViewById(R.id.loadingImageView);
            Glide.with(this).asGif().load(R.drawable.loading_ic).into(loadingImageView);

            // Check for network connectivity
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();




            DatabaseReference reference = database.getReference().child("user").child(auth.getUid());
            StorageReference storageReference = storage.getReference().child("upload").child(auth.getUid());reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    email = snapshot.child("mail").getValue().toString();

                    String name = snapshot.child("userName").getValue().toString();
                    oldname = name;

                    String profile = snapshot.child("profilepic").getValue().toString();
                    String status = snapshot.child("status").getValue().toString();
                    Email.setText(email);
                    setname.setText(name);
                    displayText.setText(name);
                    setstatus.setText(status);
                    Picasso.get().load(profile).into(setprofile);
                    mailIC.setImageResource(R.drawable.mail_icon);
                    nameIC.setImageResource(R.drawable.baseline_person_24);
                    statusIC.setImageResource(R.drawable.info_ic);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            DatabaseReference amplifierRef = FirebaseDatabase.getInstance().getReference().child("Service").child("AMPLIZONE").child("Amplifier");

            amplifierRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int count = 0;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.hasChild("by") && snapshot.child("by").getValue(String.class).equals(oldname)) {
                            count++;
                        }
                    }
                    // Now count holds the number of children with "by" as "Venom"
                    String countString = String.valueOf(count);

                    // Set the count as text in the ampCount TextView
                    ampCount.setText(countString);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle error
                }
            });

            myAmp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(setting.this, MyAmp.class));
                }
            });

            logoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(setting.this,R.style.dialoge);
                    dialog.setContentView(R.layout.dialog_layout);
                    Button no,yes;
                    yes = dialog.findViewById(R.id.yesbnt);
                    no = dialog.findViewById(R.id.nobnt);
                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(setting.this, login.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                }
            });

            Add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Check and request permissions if needed
                    if (!checkPermissions()) {
                        requestPermissions();
                    }

                    else {

                        if (networkInfo != null && networkInfo.isConnected()) {
                            // If there is network connectivity, execute the code
                            startActivity(new Intent(setting.this, AddAmp.class));
                            finish();
                        } else {
                            // If there's no network connectivity, display a toast
                            Toast.makeText(setting.this, "No network connection", Toast.LENGTH_SHORT).show();
                        }

                    }



                }
            });

            setprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!checkPermissions()) {
                        requestPermissions();
                    } else {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 10);
                    }

                }
            });



            donebut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadingDialog.show();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        // If there is network connectivity, execute the code


                        // Get a reference to your Firebase database
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("user");
                        // Assuming userUid is already obtained during authentication
                        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();



                        String name = setname.getText().toString();
                        String Status = setstatus.getText().toString();


                        DatabaseReference amplifierRef = FirebaseDatabase.getInstance().getReference().child("Service").child("AMPLIZONE").child("Amplifier");
                        amplifierRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ampSnapshot : dataSnapshot.getChildren()) {
                                    String byValue = ampSnapshot.child("by").getValue(String.class);
                                    if (byValue.equals(oldname)) {
                                        // Update the "by" value to the new username
                                        ampSnapshot.getRef().child("by").setValue(name);
                                    }


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Handle error
                            }
                        });

                        DatabaseReference leaderboard = FirebaseDatabase.getInstance().getReference().child("Service").child("CHORDM").child("Leaderboard").child("Classic");
                        leaderboard.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot leadSnap : dataSnapshot.getChildren()) {
                                    String byValue = leadSnap.child("userName").getValue(String.class);
                                    if (byValue.equals(oldname)) {
                                        // Update the "by" value to the new username
                                        leadSnap.getRef().child("userName").setValue(name);


                                    }


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Handle error
                            }
                        });

                        DatabaseReference leaderboardtimed = FirebaseDatabase.getInstance().getReference().child("Service").child("CHORDM").child("Leaderboard").child("Timed");
                        leaderboardtimed.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot leadSnap : dataSnapshot.getChildren()) {
                                    String byValue = leadSnap.child("userName").getValue(String.class);
                                    if (byValue.equals(oldname)) {
                                        // Update the "by" value to the new username
                                        leadSnap.getRef().child("userName").setValue(name);


                                    }


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Handle error
                            }
                        });

                        DatabaseReference leaderboarddiag = FirebaseDatabase.getInstance().getReference().child("Service").child("CHORDM").child("Leaderboard").child("Diagram");
                        leaderboarddiag.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot leadSnap : dataSnapshot.getChildren()) {
                                    String byValue = leadSnap.child("userName").getValue(String.class);
                                    if (byValue.equals(oldname)) {
                                        // Update the "by" value to the new username
                                        leadSnap.getRef().child("userName").setValue(name);


                                    }


                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Handle error
                            }
                        });



                        if (setImageUri!=null){
                            storageReference.putFile(setImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String finalImageUri = uri.toString();

                                            // Set values
                                            myRef.child(userUid).child("userName").setValue(name)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {

                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(setting.this, "Something wrong", Toast.LENGTH_SHORT);

                                                        }
                                                    });
                                            myRef.child(userUid).child("status").setValue(Status)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {

                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(setting.this, "Something wrong", Toast.LENGTH_SHORT);

                                                        }
                                                    });
                                            myRef.child(userUid).child("profilepic").setValue(finalImageUri)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {

                                                            loadingDialog.dismiss();

                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(setting.this, "Something wrong", Toast.LENGTH_SHORT);

                                                        }
                                                    });

                                            DatabaseReference leaderboard = FirebaseDatabase.getInstance().getReference().child("Service").child("CHORDM").child("Leaderboard").child("Classic");
                                            leaderboard.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot leadSnap : dataSnapshot.getChildren()) {
                                                        String byValue = leadSnap.child("userName").getValue(String.class);
                                                        if (byValue.equals(oldname)) {

                                                            leadSnap.getRef().child("profilepic").setValue(finalImageUri);

                                                        }


                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    // Handle error
                                                }
                                            });

                                            DatabaseReference leaderboardtimed = FirebaseDatabase.getInstance().getReference().child("Service").child("CHORDM").child("Leaderboard").child("Timed");
                                            leaderboardtimed.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot leadSnap : dataSnapshot.getChildren()) {
                                                        String byValue = leadSnap.child("userName").getValue(String.class);
                                                        if (byValue.equals(oldname)) {

                                                            leadSnap.getRef().child("profilepic").setValue(finalImageUri);

                                                        }


                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    // Handle error
                                                }
                                            });

                                            DatabaseReference leaderboardDiag = FirebaseDatabase.getInstance().getReference().child("Service").child("CHORDM").child("Leaderboard").child("Diagram");
                                            leaderboardDiag.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    for (DataSnapshot leadSnap : dataSnapshot.getChildren()) {
                                                        String byValue = leadSnap.child("userName").getValue(String.class);
                                                        if (byValue.equals(oldname)) {

                                                            leadSnap.getRef().child("profilepic").setValue(finalImageUri);

                                                        }


                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    // Handle error
                                                }
                                            });


                                        }
                                    });
                                }
                            });




                        }else {
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String finalImageUri = uri.toString();

                                    // Set values
                                    myRef.child(userUid).child("userName").setValue(name)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(setting.this, "Something wrong", Toast.LENGTH_SHORT);

                                                }
                                            });
                                    myRef.child(userUid).child("status").setValue(Status)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(setting.this, "Something wrong", Toast.LENGTH_SHORT);

                                                }
                                            });
                                    myRef.child(userUid).child("profilepic").setValue(finalImageUri)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(setting.this, "Something wrong", Toast.LENGTH_SHORT);

                                                }
                                            });
                                    loadingDialog.dismiss();

                                    DatabaseReference leaderboard = FirebaseDatabase.getInstance().getReference().child("Service").child("CHORDM").child("Leaderboard").child("Classic");
                                    leaderboard.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot leadSnap : dataSnapshot.getChildren()) {
                                                String byValue = leadSnap.child("userName").getValue(String.class);
                                                if (byValue.equals(oldname)) {
                                                    String name = setname.getText().toString();
                                                    // Update the "by" value to the new username
                                                    leadSnap.getRef().child("userName").setValue(name);
                                                    Toast.makeText(setting.this, byValue,Toast.LENGTH_SHORT).show();

                                                }


                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            // Handle error
                                        }
                                    });

                                    DatabaseReference leaderboardtimed = FirebaseDatabase.getInstance().getReference().child("Service").child("CHORDM").child("Leaderboard").child("Timed");
                                    leaderboardtimed.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot leadSnap : dataSnapshot.getChildren()) {
                                                String byValue = leadSnap.child("userName").getValue(String.class);
                                                if (byValue.equals(oldname)) {
                                                    String name = setname.getText().toString();
                                                    // Update the "by" value to the new username
                                                    leadSnap.getRef().child("userName").setValue(name);
                                                    Toast.makeText(setting.this, byValue,Toast.LENGTH_SHORT).show();

                                                }


                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            // Handle error
                                        }
                                    });

                                    DatabaseReference leaderboardDiag = FirebaseDatabase.getInstance().getReference().child("Service").child("CHORDM").child("Leaderboard").child("Diagram");
                                    leaderboardDiag.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot leadSnap : dataSnapshot.getChildren()) {
                                                String byValue = leadSnap.child("userName").getValue(String.class);
                                                if (byValue.equals(oldname)) {

                                                    leadSnap.getRef().child("profilepic").setValue(finalImageUri);

                                                }


                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            // Handle error
                                        }
                                    });
                                    loadingDialog.dismiss();

                                }
                            });
                        }

                    } else {
                        // If there's no network connectivity, display a toast
                        loadingDialog.dismiss();
                        Toast.makeText(setting.this, "No network connection", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 10) {
                if (data != null) {
                    setImageUri = data.getData();
                    setprofile.setImageURI(setImageUri);
                }
            }


        }

        @Override
        public void onBackPressed() {
            // Call finish() to close the current activity and return to the previous activity
            super.onBackPressed();
            finish();
        }

        private boolean checkPermissions() {
            int permissionNetwork = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            int permissionReadMediaAudio = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO);
            int permissionReadMediaImages = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES);
            return (permissionNetwork == PackageManager.PERMISSION_GRANTED ||
                    permissionReadMediaAudio == PackageManager.PERMISSION_GRANTED) &&
                    permissionReadMediaImages == PackageManager.PERMISSION_GRANTED;


        }

        // Request permissions
        private void requestPermissions() {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_MEDIA_AUDIO,
                    Manifest.permission.READ_MEDIA_IMAGES
            }, PERMISSION_REQUEST_CODE);
            Toast.makeText(setting.this, "Please allow permissions \n for media access.", Toast.LENGTH_SHORT).show();

        }



        // Function to redirect to app settings
        // Function to redirect to app permissions settings
        private void redirectToAppSettings() {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        }


    }