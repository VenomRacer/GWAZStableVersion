package com.av.Gwaz.chat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.av.Gwaz.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageWindow extends AppCompatActivity{

    FirebaseAuth auth;
    RecyclerView mainUserRecyclerView;
    UserAdpter  adapter;
    FirebaseDatabase database;
    ArrayList<Users> usersArrayList;
    ImageView imglogout;
    ImageView cumbut,setbut;
    ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_window);

        // Initialize ProgressDialog
        progressDialog = new ProgressDialog(MessageWindow.this);
        progressDialog.setMessage("Loading..."); // Set the message for the ProgressDialog
        progressDialog.setCancelable(false); // Set whether the ProgressDialog is cancelable
        progressDialog.show();



        database=FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        cumbut = findViewById(R.id.camBut);
        setbut = findViewById(R.id.settingBut);
        imglogout = findViewById(R.id.logoutimg);

        String currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference reference = database.getReference().child("user");

        usersArrayList = new ArrayList<>();

        mainUserRecyclerView = findViewById(R.id.mainUserRecyclerView);
        mainUserRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdpter(MessageWindow.this,usersArrayList);
        mainUserRecyclerView.setAdapter(adapter);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersArrayList.clear(); // Clear the list before adding users
                String currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                // Get the reference to the current user's userCommunicated node
                DataSnapshot currentUserCommunicatedSnapshot = snapshot.child(currentUserUid).child("userCommunicated");

                // Loop through all users
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Users user = dataSnapshot.getValue(Users.class);
                    String USERID = user.getUserId();
                    // Check if the current user has communicated with this user
                    DataSnapshot communicatedUserSnapshot = currentUserCommunicatedSnapshot.child(USERID);
                    if (communicatedUserSnapshot.exists()) {// If the user exists in the userCommunicated node of the current user, add it to the list
                        usersArrayList.add(user);
                    }
                }

                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });



    }
}