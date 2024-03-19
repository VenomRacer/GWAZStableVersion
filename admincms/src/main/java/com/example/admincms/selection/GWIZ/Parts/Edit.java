package com.example.admincms.selection.GWIZ.Parts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.admincms.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Edit extends AppCompatActivity {

    ImageView image;
    EditText stepname,description;
    Button save;
    TextView title;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri setImageUri;

    // Firebase database reference
    DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Service").child("GWIZ").child("Strings").child("TraditionalStrings");
        // Initialize FirebaseStorage
        storage = FirebaseStorage.getInstance();
        // Storage reference
        StorageReference storageReference = storage.getReference().child("gwizPic");

        image = findViewById(R.id.image);
        stepname = findViewById(R.id.stepname);
        description = findViewById(R.id.description);
        save = findViewById(R.id.save);
        title = findViewById(R.id.title);



        // Retrieve data from intent extras
        Intent intent = getIntent();
        if (intent != null) {
            String stepName = intent.getStringExtra("StepName");
            String descriptionText = intent.getStringExtra("Description");
            String imageUrl = intent.getStringExtra("Img");

            // Now you can use these values as needed
            // For example, set them to your ImageView and EditText fields
            stepname.setText(stepName);
            title.setText(stepName);
            description.setText(descriptionText);
            // Set the image using Picasso or any other method you prefer
            Picasso.get().load(imageUrl).into(image);

            // Save button onClick listener
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the updated values from EditText fields
                    String updatedStepName = stepname.getText().toString();
                    String updatedDescription = description.getText().toString();
                    String existingName = title.getText().toString();

                    // Remove the old data if the stepName has changed
                    if (!updatedStepName.equals(stepName)) {
                        databaseReference.child(stepName).removeValue(); // Use updatedStepName instead of existingName
                    }

                    uploadImage(updatedStepName, updatedDescription);
                    databaseReference.child(updatedStepName).child("t1").setValue(updatedDescription);
                    databaseReference.child(updatedStepName).child("t2").setValue(imageUrl);


                    // Finish the activity after saving changes
                    finish();
                }
            });



            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 10);

                }
            });
        }


    }

    private void uploadImage(final String stepName, final String description) {
        if (setImageUri != null) {
            // Initialize FirebaseStorage
            storage = FirebaseStorage.getInstance();

            // Storage reference
            StorageReference imageRef = storage.getReference().child("gwazPic").child("GWIZ").child("Strings").child("TraditionalStrings").child(stepName + ".jpg");

            // Upload image to Firebase Storage
            imageRef.putFile(setImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get the download URL of the uploaded image
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Store the download URL in the Realtime Database under t2
                                    String imageUrl = uri.toString();
                                    databaseReference.child(stepName).child("t2").setValue(imageUrl);


                                    // Finish the activity after saving changes
                                    finish();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle unsuccessful upload
                            Toast.makeText(Edit.this, "Image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            // No image selected, update other fields only
            databaseReference.child(stepName).child("t1").setValue(description);
            finish();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (data != null) {
                setImageUri = data.getData();
                image.setImageURI(setImageUri);
            }
        }


    }
}