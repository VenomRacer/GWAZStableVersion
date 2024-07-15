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
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.av.Gwaz.R;
import com.av.Gwaz.homepage.AMPLIZ.Add.AddAmp;
import com.av.Gwaz.homepage.AMPLIZ.MyAmp.FaveAmp;
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

public class setting extends Fragment {
    ImageView setprofile, myAmp,faveAmp, mailIC, nameIC,statusIC;
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_setting, container, false);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        setprofile = view.findViewById(R.id.settingprofile);
        setname = view.findViewById(R.id.settingname);
        setstatus = view.findViewById(R.id.settingstatus);
        donebut = view.findViewById(R.id.donebutt);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        displayText = view.findViewById(R.id.displayName);
        Add = view.findViewById(R.id.Add);
        myAmp = view.findViewById(R.id.myAmp);
        faveAmp = view.findViewById(R.id.faveAmp);
        ampCount = view.findViewById(R.id.ampCount);
        Email = view.findViewById(R.id.Email);
        mailIC = view.findViewById(R.id.mailIC);
        nameIC = view.findViewById(R.id.nameIC);
        statusIC = view.findViewById(R.id.statusIC);

        ImageButton imageButton = view.findViewById(R.id.menu);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });





        Glide.with(this).asGif().load(R.drawable.profile_loading).into(setprofile);
        Glide.with(this).asGif().load(R.drawable.loading_yellow).into(mailIC);
        Glide.with(this).asGif().load(R.drawable.loading_yellow).into(nameIC);
        Glide.with(this).asGif().load(R.drawable.loading_yellow).into(statusIC);



        // Initialize the custom loading dialog
        loadingDialog = new Dialog(getActivity());
        loadingDialog.setContentView(R.layout.dialog_loading);
        loadingDialog.setCancelable(false);

        ImageView loadingImageView = loadingDialog.findViewById(R.id.loadingImageView);
        Glide.with(this).asGif().load(R.drawable.loading_ic).into(loadingImageView);

        // Check for network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
                startActivity(new Intent(getActivity(), MyAmp.class));
            }
        });

        faveAmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FaveAmp.class));
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity(),R.style.dialoge);
                dialog.setContentView(R.layout.dialog_layout);
                Button no,yes;
                yes = dialog.findViewById(R.id.yesbnt);
                no = dialog.findViewById(R.id.nobnt);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getActivity(), login.class);
                        startActivity(intent);
                        // Remove the Fragment from the back stack
                        getActivity().getSupportFragmentManager().popBackStack();
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
                        Intent intent = new Intent(getActivity(), AddAmp.class);
                        // Apply the custom slide-up animation
                        startActivity(intent);
                        requireActivity().overridePendingTransition(R.anim.slide_up, 0);
                        // Remove the Fragment from the back stack

                    } else {
                        // If there's no network connectivity, display a toast
                        Toast.makeText(getActivity(), "No network connection", Toast.LENGTH_SHORT).show();
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
                    // Get a reference to your Firebase database
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference userRef = database.getReference("user");
                    // Assuming userUid is already obtained during authentication
                    String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    String name = setname.getText().toString();
                    String status = setstatus.getText().toString();

                    // Check if the username already exists
                    userRef.orderByChild("userName").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            boolean nameExists = false;
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                if (!snapshot.getKey().equals(userUid)) {
                                    nameExists = true;
                                    break;
                                }
                            }

                            if (nameExists) {
                                loadingDialog.dismiss();
                                Toast.makeText(getActivity(), "Username already exists", Toast.LENGTH_SHORT).show();
                            } else {
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
                                                                    Toast.makeText(getActivity(), "Something wrong", Toast.LENGTH_SHORT);

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
                                                                    Toast.makeText(getActivity(), "Something wrong", Toast.LENGTH_SHORT);

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
                                                                    Toast.makeText(getActivity(), "Something wrong", Toast.LENGTH_SHORT);

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
                                                            Toast.makeText(getActivity(), "Something wrong", Toast.LENGTH_SHORT);

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
                                                            Toast.makeText(getActivity(), "Something wrong", Toast.LENGTH_SHORT);

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
                                                            Toast.makeText(getActivity(), "Something wrong", Toast.LENGTH_SHORT);

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
                                                            Toast.makeText(getActivity(), byValue,Toast.LENGTH_SHORT).show();

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
                                                            Toast.makeText(getActivity(), byValue,Toast.LENGTH_SHORT).show();

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
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            loadingDialog.dismiss();
                            Toast.makeText(getActivity(), "Error checking username", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    // If there's no network connectivity, display a toast
                    loadingDialog.dismiss();
                    Toast.makeText(getActivity(), "No network connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }


    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (data != null) {
                setImageUri = data.getData();
                setprofile.setImageURI(setImageUri);
            }
        }


    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(getActivity(), v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());

        // Set item click listener for the popup menu
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Logout:

                        Dialog dialog = new Dialog(getActivity(),R.style.dialoge);
                        dialog.setContentView(R.layout.dialog_layout);
                        Button no,yes;
                        yes = dialog.findViewById(R.id.yesbnt);
                        no = dialog.findViewById(R.id.nobnt);
                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(getActivity(), login.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the back stack
                                startActivity(intent);
                                requireActivity().finish(); // Finish the current activity
                            }
                        });
                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        // Handle Settings menu item click
                        // Example: startActivity(new Intent(getActivity(), SettingsActivity.class));
                        return true;

                    default:
                        return false;
                }
            }
        });


        popup.show();
    }



    private boolean checkPermissions() {
        int permissionNetwork = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionReadMediaAudio = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_MEDIA_AUDIO);
        int permissionReadMediaImages = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_MEDIA_IMAGES);
        return (permissionNetwork == PackageManager.PERMISSION_GRANTED ||
                permissionReadMediaAudio == PackageManager.PERMISSION_GRANTED) ||
                permissionReadMediaImages == PackageManager.PERMISSION_GRANTED;


    }

    // Request permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_IMAGES
        }, PERMISSION_REQUEST_CODE);
        Toast.makeText(getActivity(), "Please allow permissions \n for media access.", Toast.LENGTH_SHORT).show();

    }







    // Function to redirect to app settings
    // Function to redirect to app permissions settings



}