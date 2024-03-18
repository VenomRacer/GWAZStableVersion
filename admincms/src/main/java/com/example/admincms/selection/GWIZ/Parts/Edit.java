package com.example.admincms.selection.GWIZ.Parts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.admincms.R;
import com.squareup.picasso.Picasso;

public class Edit extends AppCompatActivity {

    ImageView image;
    EditText stepname,description;
    Button save;
    TextView title;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

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
        }


    }
}