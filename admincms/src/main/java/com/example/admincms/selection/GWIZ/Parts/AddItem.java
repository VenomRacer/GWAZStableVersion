package com.example.admincms.selection.GWIZ.Parts;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class AddItem extends AppCompatActivity {

    ImageView image;
    EditText stepname,description;
    Button save;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    FirebaseStorage storage;
    Uri setImageUri;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        // Initialize ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving Data...");
        progressDialog.setCancelable(false); // Prevent dialog from being dismissed by touching outside
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Set style to spinner

        image = findViewById(R.id.image);
        stepname = findViewById(R.id.stepname);
        description = findViewById(R.id.description);
        save = findViewById(R.id.save);

        //passed reference
        String databaseReferencePath = getIntent().getStringExtra("databaseReferencePath");
        String storageReferencePath = getIntent().getStringExtra("storageReferencePath");
        // Convert databaseReferencePath back to a DatabaseReference object
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(databaseReferencePath);
        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(storageReferencePath);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 10);

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                // Initialize FirebaseStorage
                storage = FirebaseStorage.getInstance();


                // Get the updated values from EditText fields
                String sname = stepname.getText().toString(); // for root folder (parent)
                String desc = description.getText().toString(); // for t1

                // Set the storage reference with the desired name
                StorageReference imageRef = storageReference.child(sname + ".jpg");

                // Check if an image is selected
                if (setImageUri != null) {



                    // Upload the image to Firebase Storage
                    imageRef.putFile(setImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // Image uploaded successfully, get the download URL
                                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // Save the download URL to the Firebase Realtime Database
                                            databaseReference.child(sname).child("t1").setValue(desc);
                                            databaseReference.child(sname).child("t2").setValue(uri.toString());
                                            // Display a success message or perform any other actions
                                            Toast.makeText(AddItem.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                            finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Handle any errors that may occur while retrieving the download URL
                                            Toast.makeText(AddItem.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                            finish();
                                        }
                                    });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle any errors that may occur while uploading the image
                                    Toast.makeText(AddItem.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    finish();
                                }
                            });
                } else {
                    // No image selected
                    Toast.makeText(AddItem.this, "Please select an image", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    finish();
                }
            }

        });
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