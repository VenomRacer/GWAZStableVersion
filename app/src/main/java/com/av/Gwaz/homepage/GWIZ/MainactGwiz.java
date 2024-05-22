package com.av.Gwaz.homepage.GWIZ;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.av.Gwaz.R;
import com.av.Gwaz.homepage.GWIZ.Parts.Bridge;
import com.av.Gwaz.homepage.GWIZ.Parts.Fretboard;
import com.av.Gwaz.homepage.GWIZ.Parts.Strings;
import com.av.Gwaz.homepage.GWIZ.Parts.TuningPegs;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class MainactGwiz extends AppCompatActivity {
    ImageView imageView;
    View overlayView1, overlayView2;

    DatabaseReference nutstring,rodstring,bodystring,pickupsstring;
    StorageReference nutstore, rodstore, bodystore, pickupsstore;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainact_gwiz);

        // Initialize imageView and overlayView after setContentView
        imageView = findViewById(R.id.imageView);
        overlayView1 = findViewById(R.id.overlayView1);
        overlayView2 = findViewById(R.id.overlayView2);

        // For nut
        nutstring = FirebaseDatabase.getInstance().getReference().child("Service").child("GWIZ")
                .child("Nut");
        nutstore = FirebaseStorage.getInstance().getReference().child("gwazPic").child("GWIZ")
                .child("Nut");

        // For truss rod
        rodstring = FirebaseDatabase.getInstance().getReference().child("Service").child("GWIZ")
                .child("TrussRod");
        rodstore = FirebaseStorage.getInstance().getReference().child("gwazPic").child("GWIZ")
                .child("TrussRod");

        // For body
        bodystring = FirebaseDatabase.getInstance().getReference().child("Service").child("GWIZ")
                .child("Body").child("ElectricGuitar");
        bodystore = FirebaseStorage.getInstance().getReference().child("gwazPic").child("GWIZ")
                .child("Body").child("ElectricGuitar");

        // For pickups
        pickupsstring = FirebaseDatabase.getInstance().getReference().child("Service").child("GWIZ")
                .child("Pickups").child("ElectricGuitar");
        pickupsstore = FirebaseStorage.getInstance().getReference().child("gwazPic").child("GWIZ")
                .child("Pickups").child("ElectricGuitar");

        overlayView1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return handleTouch(1, event);
            }
        });

        overlayView2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return handleTouch(2, event);
            }
        });
    }

    private boolean handleTouch(int overlayIndex, MotionEvent event) {
        View overlayView = overlayIndex == 1 ? overlayView1 : overlayView2;

        float x = event.getX();
        float y = event.getY();

        float percentageX = x / imageView.getWidth();
        float percentageY = y / imageView.getHeight();

        float pixelX = calculatePixelX(percentageX, imageView);
        float pixelY = calculatePixelY(percentageY, imageView);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handleTouchDown(overlayIndex, pixelX, pixelY);
                break;
            case MotionEvent.ACTION_MOVE:
                handleTouchMove(overlayIndex, pixelX, pixelY);
                break;
            case MotionEvent.ACTION_UP:
                handleTouchUp(overlayIndex, pixelX, pixelY);
                break;
        }
        return true;
    }

    private void handleTouchDown(int overlayIndex, float x, float y) {
        // Handle touch down event
    }

    private void handleTouchMove(int overlayIndex, float x, float y) {
        // Handle touch move event
    }

    private void handleTouchUp(int overlayIndex, float x, float y) {
        // Define the percentage-based coordinates of the clicked spot
        float clickedXPercent = x / imageView.getWidth() * 100;
        float clickedYPercent = y / imageView.getHeight() * 100;

        // Show a toast message with the percentages
        //String message = String.format("Clicked at: X=%.2f%% of imageView width, Y=%.2f%% of imageView height", clickedXPercent, clickedYPercent);
        //Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        // for overlay1
        if (overlayIndex == 1) {

            //for fretboard
            float target1XPercent = 0.467f; // Percentage of imageView width for target 1
            float target1YPercent = 0.627f; // Percentage of imageView height for target 1
            float tolerance1 = 0.05f; // Tolerance as percentage of imageView width/height for target 1



            float target10XPercent = 0.468f;
            float target10YPercent = 0.545f;
            float tolerance10 = 0.05f;

            float target11XPercent = 0.472f;
            float target11YPercent = 0.692f;
            float tolerance11 = 0.1f;

            float target12XPercent = 0.476f;
            float target12YPercent = 0.758f;
            float tolerance12 = 0.1f;

            float target13XPercent = 0.464f;
            float target13YPercent = 0.819f;
            float tolerance13 = 0.1f;

            float target14XPercent = 0.482f;
            float target14YPercent = 0.880f;
            float tolerance14 = 0.1f;

            float target15XPercent = 0.475f;
            float target15YPercent = 0.939f;
            float tolerance15 = 0.1f;

            float target16XPercent = 0.476f;
            float target16YPercent = 0.975f;
            float tolerance16 = 0.1f;



            // nut
            float target2XPercent = 0.455f; // Percentage of imageView width for target 2
            float target2YPercent = 0.419f; // Percentage of imageView height for target 2
            float tolerance2 = 0.05f; // Tolerance as percentage of imageView width/height for target 2

            // tuningpegs
            float target3XPercent = 0.342f;
            float target3YPercent = 0.149f;
            float tolerance3 = 0.1f;

            float target4XPercent = 0.36f;
            float target4YPercent = 0.225f;
            float tolerance4 = 0.1f;

            float target5XPercent = 0.356f;
            float target5YPercent = 0.295f;
            float tolerance5 = 0.1f;

            float target6XPercent = 0.657f;
            float target6YPercent = 0.12f;
            float tolerance6 = 0.1f;

            float target7XPercent = 0.651f;
            float target7YPercent = 0.217f;
            float tolerance7 = 0.1f;

            float target8XPercent = 0.647f;
            float target8YPercent = 0.288f;
            float tolerance8 = 0.1f;

            //for truss rod
            float target17XPercent = 0.489f;
            float target17YPercent = 0.298f;
            float tolerance17 = 0.1f;

            float target18XPercent = 0.480f;
            float target18YPercent = 0.244f;
            float tolerance18 = 0.1f;



            // Check if the click occurred within the desired regions for overlayIndex 1
            if (isWithinTarget(x, y, target1XPercent, target1YPercent, tolerance1) ||
                    isWithinTarget(x, y, target10XPercent, target10YPercent, tolerance10) ||
                    isWithinTarget(x, y, target11XPercent, target11YPercent, tolerance11) ||
                    isWithinTarget(x, y, target12XPercent, target12YPercent, tolerance12) ||
                    isWithinTarget(x, y, target13XPercent, target13YPercent, tolerance13) ||
                    isWithinTarget(x, y, target14XPercent, target14YPercent, tolerance14) ||
                    isWithinTarget(x, y, target15XPercent, target15YPercent, tolerance15) ||
                    isWithinTarget(x, y, target16XPercent, target16YPercent, tolerance16)) {
                Intent intent = new Intent(MainactGwiz.this, Fretboard.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);

            } else if (isWithinTarget(x, y, target2XPercent, target2YPercent, tolerance2)) {

                Intent intent = new Intent(MainactGwiz.this, Partview.class);
                intent.putExtra("TITLE","Nut");
                intent.putExtra("datab", nutstring.toString());
                intent.putExtra("store", nutstore.toString());
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
            }

            else if (isWithinTarget(x, y, target17XPercent, target17YPercent, tolerance17)||
                    isWithinTarget(x, y, target18XPercent, target18YPercent, tolerance17)) {

                Intent intent = new Intent(MainactGwiz.this, Partview.class);
                intent.putExtra("TITLE","Truss Rod");
                intent.putExtra("datab", rodstring.toString());
                intent.putExtra("store", rodstore.toString());
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
            }

            else if (isWithinTarget(x, y, target3XPercent, target3YPercent, tolerance3) ||
                    isWithinTarget(x, y, target4XPercent, target4YPercent, tolerance4) ||
                    isWithinTarget(x, y, target5XPercent, target5YPercent, tolerance5) ||
                    isWithinTarget(x, y, target6XPercent, target6YPercent, tolerance6) ||
                    isWithinTarget(x, y, target7XPercent, target7YPercent, tolerance7) ||
                    isWithinTarget(x, y, target8XPercent, target8YPercent, tolerance8)) {
                Intent intent = new Intent(MainactGwiz.this, TuningPegs.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
            }


        //overlay2
        } else if (overlayIndex == 2) {

            //for fretboard
            float target2XPercent = 0.484f;
            float target2YPercent = 0.119f;
            float tolerance2 = 0.1f;

            float target3XPercent = 0.478f;
            float target3YPercent = 0.176f;
            float tolerance3 = 0.1f;

            float target4XPercent = 0.482f;
            float target4YPercent = 0.225f;
            float tolerance4 = 0.1f;

            //for strings
            float target1XPercent = 0.503f;
            float target1YPercent = 0.456f;
            float tolerance1 = 0.05f;

            //for bridge/saddle
            float target5XPercent = 0.500f;
            float target5YPercent = 0.606f;
            float tolerance5 = 0.05f;

            float target6XPercent = 0.485f;
            float target6YPercent = 0.694f;
            float tolerance6 = 0.05f;

            //for pickups
            float target7XPercent = 0.500f;
            float target7YPercent = 0.370f;
            float tolerance7 = 0.05f;

            float target8XPercent = 0.495f;
            float target8YPercent = 0.544f;
            float tolerance8 = 0.05f;

            //for body
            float target9XPercent = 0.472f;
            float target9YPercent = 0.832f;
            float tolerance9 = 0.2f;

            // Check if the click occurred within the desired regions for overlayIndex 1
            if (isWithinTarget(x, y, target1XPercent, target1YPercent, tolerance1)) {
                // Clicked within target 1 region for overlayIndex 1, navigate to Fretboard.class
                Intent intent = new Intent(MainactGwiz.this, Strings.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
            }

            else if (isWithinTarget(x, y, target3XPercent, target3YPercent, tolerance3) ||
                    isWithinTarget(x, y, target2XPercent, target2YPercent, tolerance2) ||
                    isWithinTarget(x, y, target4XPercent, target4YPercent, tolerance4)) {
                Intent intent = new Intent(MainactGwiz.this, Fretboard.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
            }

            else if (isWithinTarget(x, y, target5XPercent, target5YPercent, tolerance5)||
                    isWithinTarget(x, y, target6XPercent, target6YPercent, tolerance6)) {
                Intent intent = new Intent(MainactGwiz.this, Bridge.class);
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
            }

            else if (isWithinTarget(x, y, target7XPercent, target7YPercent, tolerance7)||
                    isWithinTarget(x, y, target8XPercent, target8YPercent, tolerance8)) {
                Intent intent = new Intent(MainactGwiz.this, Partview.class);
                intent.putExtra("TITLE","Pickups");
                intent.putExtra("datab", pickupsstring.toString());
                intent.putExtra("store", pickupsstore.toString());
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
            }

            else if (isWithinTarget(x, y, target9XPercent, target9YPercent, tolerance9)) {
                Intent intent = new Intent(MainactGwiz.this, Partview.class);
                intent.putExtra("TITLE","Body");
                intent.putExtra("datab", bodystring.toString());
                intent.putExtra("store", bodystore.toString());
                startActivity(intent);
                overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
            }



        }
    }

    // Method to check if the click occurred within the desired target region
    private boolean isWithinTarget(float x, float y, float targetXPercent, float targetYPercent, float tolerance) {
        // Calculate pixel coordinates based on percentage and view size
        float targetX = calculatePixelX(targetXPercent, imageView);
        float targetY = calculatePixelY(targetYPercent, imageView);

        // Check if the click occurred within the desired region
        return (Math.abs(x - targetX) <= tolerance * imageView.getWidth() &&
                Math.abs(y - targetY) <= tolerance * imageView.getHeight());
    }

    // Calculate pixel coordinates based on percentage and view size
    private float calculatePixelX(float percentageX, View view) {
        return percentageX * view.getWidth();
    }

    private float calculatePixelY(float percentageY, View view) {
        return percentageY * view.getHeight();
    }
}