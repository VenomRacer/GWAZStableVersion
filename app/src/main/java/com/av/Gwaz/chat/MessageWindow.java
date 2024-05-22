package com.av.Gwaz.chat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.av.Gwaz.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MessageWindow extends AppCompatActivity{

    FirebaseAuth auth;
    RecyclerView mainUserRecyclerView;
    UserAdpter  adapter;
    FirebaseDatabase database;
    ArrayList<Users> usersArrayList;
    ImageView imglogout;
    ImageView cumbut,setbut;
    Dialog loadingDialog;
    Handler handler = new Handler();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_window);

        // Initialize the custom loading dialog
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.dialog_loading);
        loadingDialog.setCancelable(false);

        ImageView loadingImageView = loadingDialog.findViewById(R.id.loadingImageView);
        Glide.with(this).asGif().load(R.drawable.loading_ic).into(loadingImageView);
        loadingDialog.show();

        // Dismiss the loading dialog after 10 seconds if not already dismissed
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                    Toast.makeText(MessageWindow.this, "Something went wrong, please try again later.", Toast.LENGTH_SHORT).show();
                }
            }
        }, 10000); // 10 seconds



        database=FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        cumbut = findViewById(R.id.camBut);
        setbut = findViewById(R.id.settingBut);
        imglogout = findViewById(R.id.logoutimg);

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

                // Create a list to hold the users along with their time
                List<Pair<Users, Long>> userListWithTime = new ArrayList<>();

                // Loop through all users
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Users user = dataSnapshot.getValue(Users.class);
                    String userId = user.getUserId();
                    // Check if the current user has communicated with this user
                    DataSnapshot communicatedUserSnapshot = currentUserCommunicatedSnapshot.child(userId);
                    if (communicatedUserSnapshot.exists()) {// If the user exists in the userCommunicated node of the current user, add it to the list
                        // Get the time of communication for this user
                        Long time = communicatedUserSnapshot.child("time").getValue(Long.class);
                        if (time != null) {
                            userListWithTime.add(new Pair<>(user, time));
                        }
                    }
                }

                // Sort the list based on the time in descending order (latest at the top)
                Collections.sort(userListWithTime, new Comparator<Pair<Users, Long>>() {
                    @Override
                    public int compare(Pair<Users, Long> o1, Pair<Users, Long> o2) {
                        return o2.second.compareTo(o1.second); // Compare based on time in descending order
                    }
                });

                // Clear the existing list
                usersArrayList.clear();

                // Add the sorted users to the usersArrayList
                for (Pair<Users, Long> pair : userListWithTime) {
                    usersArrayList.add(pair.first);
                }

                adapter.notifyDataSetChanged();
                loadingDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


}