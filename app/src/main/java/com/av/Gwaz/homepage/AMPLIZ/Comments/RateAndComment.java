    package com.av.Gwaz.homepage.AMPLIZ.Comments;

    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.RatingBar;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;

    import com.av.Gwaz.R;
    import com.google.android.material.textfield.TextInputEditText;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;

    public class RateAndComment extends AppCompatActivity {

        private RatingBar ratingBar;
        private TextInputEditText comment;
        private String userName,dp;
        private Button feedback;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_rate_and_comment);

            ratingBar = findViewById(R.id.ratingBar);
            comment = findViewById(R.id.comment);
            feedback = findViewById(R.id.feedback);

            // get intent extras
            String key = getIntent().getStringExtra("key");

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String currentUserId = currentUser.getUid();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user").child(currentUserId);




            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    userName = snapshot.child("userName").getValue().toString();
                    dp = snapshot.child("profilepic").getValue().toString();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            feedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Construct the comment key using the username
                    String commentKey = "COMMENT" + userName;

                    // Retrieve the comment text from the TextInputEditText
                    String commentText = comment.getText().toString();

                    // Retrieve the rating from the RatingBar
                    float ratingValue = ratingBar.getRating();

                    // Store the comment text and the rating in the database under the specified path
                    DatabaseReference commentRef = FirebaseDatabase.getInstance().getReference()
                            .child("Service")
                            .child("AMPLIZONE")
                            .child("Comments")
                            .child(key) // This is the amp key
                            .child(commentKey);

                    // Set the comment text and the rating as values in the database
                    commentRef.child("comment").setValue(commentText);
                    commentRef.child("rating").setValue(ratingValue);
                    commentRef.child("user").setValue(userName);
                    commentRef.child("profilePic").setValue(dp);

                    // Calculate the average rating and store it in Amplifiers node
                    calculateAndStoreAverageRating(key);

                    // Optionally, you can show a toast message or perform any other action after sending
                    Toast.makeText(RateAndComment.this, "Rating sent successfully", Toast.LENGTH_SHORT).show();

                    // Finish the activity to go back
                    finish();
                }
            });
        }

        private void calculateAndStoreAverageRating(String ampKey) {
            DatabaseReference commentsRef = FirebaseDatabase.getInstance().getReference()
                    .child("Service")
                    .child("AMPLIZONE")
                    .child("Comments")
                    .child(ampKey);

            commentsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int totalRatings = 0;
                    float sumRatings = 0;

                    for (DataSnapshot commentSnapshot : dataSnapshot.getChildren()) {
                        float rating = commentSnapshot.child("rating").getValue(Float.class);
                        sumRatings += rating;
                        totalRatings++;
                    }

                    // Calculate the average rating
                    float averageRating = totalRatings > 0 ? sumRatings / totalRatings : 0;

                    // Store the average rating back to the Firebase Realtime Database under Amplifiers node
                    DatabaseReference ampRef = FirebaseDatabase.getInstance().getReference()
                            .child("Service")
                            .child("AMPLIZONE")
                            .child("Amplifier")
                            .child(ampKey)
                            .child("rating");

                    ampRef.setValue(averageRating);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle potential errors
                }
            });
        }
    }