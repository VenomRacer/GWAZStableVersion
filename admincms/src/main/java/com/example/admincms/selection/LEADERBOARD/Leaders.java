package com.example.admincms.selection.LEADERBOARD;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.admincms.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Leaders extends AppCompatActivity {

    private ImageView classicpic,timedpic,diagrampic;
    private TextView classicuser,timeduser,diagramuser;
    private FrameLayout leaderClassic,leaderTimed,leaderDiagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaders);


        classicpic = findViewById(R.id.classicpic);
        classicuser = findViewById(R.id.classicuser);
        leaderClassic = findViewById(R.id.leaderClassic);
        classicpic.setImageResource(R.drawable.profile);


        timedpic = findViewById(R.id.timedpic);
        timeduser = findViewById(R.id.timeduser);
        leaderTimed = findViewById(R.id.leaderTimed);
        timedpic.setImageResource(R.drawable.profile);


        diagrampic =  findViewById(R.id.diagpic);
        diagramuser = findViewById(R.id.diaguser);
        leaderDiagram = findViewById(R.id.leaderDiag);
        diagrampic.setImageResource(R.drawable.profile);



        leaderClassic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Leaders.this, Leaderboard.class);
                intent.putExtra("mode", "Classic");
                startActivity(intent);

            }
        });



        leaderTimed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Leaders.this, Leaderboard.class);
                intent.putExtra("mode", "Timed");
                startActivity(intent);

            }
        });



        leaderDiagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Leaders.this, Leaderboard.class);
                intent.putExtra("mode", "Diagram");
                startActivity(intent);
            }
        });





        // for classic leaderboard
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Service")
                .child("CHORDM")
                .child("Leaderboard")
                .child("Classic");

        // Query to get the highest scoring user
        Query highestScoreQuery = databaseReference.orderByChild("score").limitToLast(1);

        highestScoreQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Retrieve the highest scoring user's data
                    String highestScoreUserPicUrl = snapshot.child("profilepic").getValue(String.class);
                    String highestScoreUsername = snapshot.child("userName").getValue(String.class);

                    // Set the retrieved data to your image view and text view
                    // You can use libraries like Picasso or Glide to load images from URLs
                    // For simplicity, I'll assume you have the URLs ready and directly set them
                    if (highestScoreUserPicUrl != null && !highestScoreUserPicUrl.isEmpty()) {
                        Picasso.get().load(highestScoreUserPicUrl).into(classicpic);
                    }
                    if (highestScoreUsername != null && !highestScoreUsername.isEmpty()) {
                        classicuser.setText(highestScoreUsername);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Leaders.this, "An error occured", Toast.LENGTH_SHORT).show();
            }
        });

        // for timed leaderboard
        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference()
                .child("Service")
                .child("CHORDM")
                .child("Leaderboard")
                .child("Timed");

        // Query to get the highest scoring user
        Query highestScoreQuery2 = databaseReference2.orderByChild("score").limitToLast(1);

        highestScoreQuery2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Retrieve the highest scoring user's data
                    String highestScoreUserPicUrl = snapshot.child("profilepic").getValue(String.class);
                    String highestScoreUsername = snapshot.child("userName").getValue(String.class);

                    // Set the retrieved data to your image view and text view
                    // You can use libraries like Picasso or Glide to load images from URLs
                    // For simplicity, I'll assume you have the URLs ready and directly set them
                    if (highestScoreUserPicUrl != null && !highestScoreUserPicUrl.isEmpty()) {
                        Picasso.get().load(highestScoreUserPicUrl).into(timedpic);
                    }
                    if (highestScoreUsername != null && !highestScoreUsername.isEmpty()) {
                        timeduser.setText(highestScoreUsername);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Leaders.this, "An error occured", Toast.LENGTH_SHORT).show();
            }
        });

        // for timed leaderboard
        DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference()
                .child("Service")
                .child("CHORDM")
                .child("Leaderboard")
                .child("Diagram");

        // Query to get the highest scoring user
        Query highestScoreQuery3 = databaseReference3.orderByChild("score").limitToLast(1);

        highestScoreQuery3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Retrieve the highest scoring user's data
                    String highestScoreUserPicUrl = snapshot.child("profilepic").getValue(String.class);
                    String highestScoreUsername = snapshot.child("userName").getValue(String.class);

                    // Set the retrieved data to your image view and text view
                    // You can use libraries like Picasso or Glide to load images from URLs
                    // For simplicity, I'll assume you have the URLs ready and directly set them
                    if (highestScoreUserPicUrl != null && !highestScoreUserPicUrl.isEmpty()) {
                        Picasso.get().load(highestScoreUserPicUrl).into(diagrampic);
                    }
                    if (highestScoreUsername != null && !highestScoreUsername.isEmpty()) {
                        diagramuser.setText(highestScoreUsername);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Leaders.this, "An error occured", Toast.LENGTH_SHORT).show();
            }
        });
    }
}