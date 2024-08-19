package com.av.Gwaz.homepage.TUNER;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.av.Gwaz.R;

public class OverlayView4Fragment extends Fragment {

    private ImageView imageView;
    private View overlayView4;
    private MediaPlayer d1,a1,d2,g1,a2,d3,dadgadstrum;
    private static final int MOVE_THRESHOLD = 10; // Adjust this value as needed
    private float startX;
    private float startY;

    public OverlayView4Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overlay_view4, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.imageView);
        overlayView4 = view.findViewById(R.id.overlayView4);

        // Initialize MediaPlayer with the audio file
        d1 = MediaPlayer.create(getContext(), R.raw.d1);
        a1 = MediaPlayer.create(getContext(),R.raw.a1);
        d2 = MediaPlayer.create(getContext(), R.raw.d2);
        g1 = MediaPlayer.create(getContext(), R.raw.g1);
        a2 = MediaPlayer.create(getContext(), R.raw.a2);
        d3 = MediaPlayer.create(getContext(), R.raw.d3);
        dadgadstrum = MediaPlayer.create(getContext(), R.raw.dadgadstrum);



        overlayView4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return handleTouch(event);
            }
        });
    }

    private boolean handleTouch(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        // Convert touch coordinates to percentage of image view width and height
        float percentageX = x / imageView.getWidth();
        float percentageY = y / imageView.getHeight();

        float pixelX = calculatePixelX(percentageX, imageView);
        float pixelY = calculatePixelY(percentageY, imageView);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = x;
                startY = y;
                break;
            case MotionEvent.ACTION_UP:
                handleTouchUp(pixelX, pixelY);
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = Math.abs(x - startX);
                float deltaY = Math.abs(y - startY);
                if (deltaX > MOVE_THRESHOLD || deltaY > MOVE_THRESHOLD) {
                    if (dadgadstrum != null) {
                        dadgadstrum.release();
                    }
                    // Create a new instance of MediaPlayer and start playing the audio
                    dadgadstrum = MediaPlayer.create(getContext(), R.raw.dadgadstrum);
                    dadgadstrum.start();
                }
                break;
        }
        return true;
    }

    private void handleTouchUp(float x, float y) {
        // Define the percentage-based coordinates of the clicked spot
        float clickedXPercent = x / imageView.getWidth() * 100;
        float clickedYPercent = y / imageView.getHeight() * 100;

        // Show a toast message with the percentages
        String message1 = String.format("Clicked at: X=%.2f%% of imageView width, Y=%.2f%% of imageView height", clickedXPercent, clickedYPercent);
        //Toast.makeText(getContext(), message1, Toast.LENGTH_SHORT).show();



        // D
        float target1XPercent = 0.261f;
        float target1YPercent = 0.647f;
        float tolerance1 = 0.05f;

        // A
        float target2XPercent = 0.285f;
        float target2YPercent = 0.566f;
        float tolerance2 = 0.05f;

        // D
        float target3XPercent = 0.328f;
        float target3YPercent = 0.474f;
        float tolerance3 = 0.05f;

        // G
        float target4XPercent = 0.350f;
        float target4YPercent = 0.396f;
        float tolerance4 = 0.05f;

        // A
        float target5XPercent = 0.376f;
        float target5YPercent = 0.314f;
        float tolerance5 = 0.05f;

        // D
        float target6XPercent = 0.408f;
        float target6YPercent = 0.220f;
        float tolerance6 = 0.05f;



        if (isWithinTarget(x, y, target1XPercent, target1YPercent, tolerance1)) {
            // Release the previous instance of MediaPlayer if it exists
            // Show a toast message with the percentages
            String message = String.format("D");
            //Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            if (d1 != null) {
                d1.release();
            }
            // Create a new instance of MediaPlayer and start playing the audio
            d1 = MediaPlayer.create(getContext(), R.raw.d1);
            d1.start();
        }

        if (isWithinTarget(x, y, target2XPercent, target2YPercent, tolerance2)) {
            // Release the previous instance of MediaPlayer if it exists
            String message = String.format("A");
            //Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            if (a1 != null) {
                a1.release();
            }
            // Create a new instance of MediaPlayer and start playing the audio
            a1 = MediaPlayer.create(getContext(), R.raw.a1);
            a2.start();
        }
        if (isWithinTarget(x, y, target3XPercent, target3YPercent, tolerance3)) {
            // Release the previous instance of MediaPlayer if it exists
            String message = String.format("D");
            //Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            if (d2 != null) {
                d2.release();
            }
            // Create a new instance of MediaPlayer and start playing the audio
            d2 = MediaPlayer.create(getContext(), R.raw.d2);
            d2.start();
        }
        if (isWithinTarget(x, y, target4XPercent, target4YPercent, tolerance4)) {
            // Release the previous instance of MediaPlayer if it exists
            String message = String.format("G");
            //Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            if (g1 != null) {
                g1.release();
            }
            // Create a new instance of MediaPlayer and start playing the audio
            g1 = MediaPlayer.create(getContext(), R.raw.g1);
            g1.start();
        }
        if (isWithinTarget(x, y, target5XPercent, target5YPercent, tolerance5)) {
            // Release the previous instance of MediaPlayer if it exists
            String message = String.format("A");
            //Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            if (a2 != null) {
                a2.release();
            }
            // Create a new instance of MediaPlayer and start playing the audio
            a2 = MediaPlayer.create(getContext(), R.raw.a2);
            a2.start();
        }
        if (isWithinTarget(x, y, target6XPercent, target6YPercent, tolerance6)) {
            // Release the previous instance of MediaPlayer if it exists
            String message = String.format("D");
            //Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            if (d3 != null) {
                d3.release();
            }
            // Create a new instance of MediaPlayer and start playing the audio
            d3 = MediaPlayer.create(getContext(), R.raw.d3);
            d3.start();
        }
    }

    // Method to play audio
    @Override
    public void onResume() {
        super.onResume();
        // Reset MediaPlayer state
        if (d1 != null) {
            d1.release();
            d1 = MediaPlayer.create(getContext(),R.raw.d1);
        }
        if (a1 != null) {
            a1.release();
            a1= MediaPlayer.create(getContext(),R.raw.a);
        }
        if (d2 != null) {
            d2.release();
            d2 = MediaPlayer.create(getContext(),R.raw.d2);
        }
        if (g1 != null) {
            g1.release();
            g1 = MediaPlayer.create(getContext(),R.raw.g1);
        }
        if (a2 != null) {
            a2.release();
            a2 = MediaPlayer.create(getContext(),R.raw.a2);
        }
        if (d3 != null) {
            d3.release();
            d3 = MediaPlayer.create(getContext(),R.raw.d3);
        }
        if (dadgadstrum != null){
            dadgadstrum.release();
            dadgadstrum = MediaPlayer.create(getContext(), R.raw.dadgadstrum);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // Release MediaPlayer resources when activity is destroyed
        if (d1 != null) {
            d1.release();
            d1 = null;
        }
        if (a1 != null) {
            a1.release();
            a1 = null;
        }
        if (d2 != null) {
            d2.release();
            d2 = null;
        }
        if (g1 != null) {
            g1.release();
            g1 = null;
        }
        if (a2 != null) {
            a2.release();
            a2 = null;
        }
        if (d3 != null) {
            d3.release();
            d3 = null;
        }
        if (dadgadstrum != null){
            dadgadstrum.release();
            dadgadstrum = null;
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
