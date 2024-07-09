package com.av.Gwaz.homepage.TUNER;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.av.Gwaz.R;

public class OverlayView2Fragment extends Fragment {

    private ImageView imageView;
    private View overlayView2;
    private MediaPlayer Eb,Ab,Db,Gb,Bb,eb,halfstepstrum;

    public OverlayView2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overlay_view2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.imageView);
        overlayView2 = view.findViewById(R.id.overlayView2);

        // Initialize MediaPlayer with the audio file
        Eb = MediaPlayer.create(getContext(), R.raw.loweb);
        Ab = MediaPlayer.create(getContext(),R.raw.ab);
        Db = MediaPlayer.create(getContext(), R.raw.db);
        Gb = MediaPlayer.create(getContext(), R.raw.gb);
        Bb = MediaPlayer.create(getContext(), R.raw.bb);
        eb = MediaPlayer.create(getContext(), R.raw.higheb);
        halfstepstrum = MediaPlayer.create(getContext(), R.raw.halfstepstrum);



        overlayView2.setOnTouchListener(new View.OnTouchListener() {
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
            case MotionEvent.ACTION_UP:
                handleTouchUp(pixelX, pixelY);
                break;
            case MotionEvent.ACTION_MOVE:
                if (halfstepstrum != null) {
                    halfstepstrum.release();
                }
                // Create a new instance of MediaPlayer and start playing the audio
                halfstepstrum = MediaPlayer.create(getContext(), R.raw.halfstepstrum);
                halfstepstrum.start();



        }
        return true;
    }

    private void handleTouchUp(float x, float y) {
        // Define the percentage-based coordinates of the clicked spot
        float clickedXPercent = x / imageView.getWidth() * 100;
        float clickedYPercent = y / imageView.getHeight() * 100;

        // Show a toast message with the percentages
        String message1 = String.format("Clicked at: X=%.2f%% of imageView width, Y=%.2f%% of imageView height", clickedXPercent, clickedYPercent);
        Toast.makeText(getContext(), message1, Toast.LENGTH_SHORT).show();



        // low Eb
        float target1XPercent = 0.262f;
        float target1YPercent = 0.552f;
        float tolerance1 = 0.05f;

        // Ab
        float target2XPercent = 0.254f;
        float target2YPercent = 0.406f;
        float tolerance2 = 0.05f;

        // Db
        float target3XPercent = 0.246f;
        float target3YPercent = 0.256f;
        float tolerance3 = 0.05f;

        // Gb
        float target4XPercent = 0.730f;
        float target4YPercent = 0.268f;
        float tolerance4 = 0.05f;

        // Bb
        float target5XPercent = 0.733f;
        float target5YPercent = 0.401f;
        float tolerance5 = 0.05f;

        // high Eb
        float target6XPercent = 0.747f;
        float target6YPercent = 0.542f;
        float tolerance6 = 0.05f;



        if (isWithinTarget(x, y, target1XPercent, target1YPercent, tolerance1)) {
            // Release the previous instance of MediaPlayer if it exists
            // Show a toast message with the percentages
            String message = String.format("Eb");
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            if (Eb != null) {
                Eb.release();
            }
            // Create a new instance of MediaPlayer and start playing the audio
            Eb = MediaPlayer.create(getContext(), R.raw.loweb);
            Eb.start();
        }

        if (isWithinTarget(x, y, target2XPercent, target2YPercent, tolerance2)) {
            // Release the previous instance of MediaPlayer if it exists
            String message = String.format("Ab");
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            if (Ab != null) {
                Ab.release();
            }
            // Create a new instance of MediaPlayer and start playing the audio
            Ab = MediaPlayer.create(getContext(), R.raw.ab);
            Ab.start();
        }
        if (isWithinTarget(x, y, target3XPercent, target3YPercent, tolerance3)) {
            // Release the previous instance of MediaPlayer if it exists
            String message = String.format("Db");
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            if (Db != null) {
                Db.release();
            }
            // Create a new instance of MediaPlayer and start playing the audio
            Db = MediaPlayer.create(getContext(), R.raw.db);
            Db.start();
        }
        if (isWithinTarget(x, y, target4XPercent, target4YPercent, tolerance4)) {
            // Release the previous instance of MediaPlayer if it exists
            String message = String.format("Gb");
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            if (Gb != null) {
                Gb.release();
            }
            // Create a new instance of MediaPlayer and start playing the audio
            Gb = MediaPlayer.create(getContext(), R.raw.gb);
            Gb.start();
        }
        if (isWithinTarget(x, y, target5XPercent, target5YPercent, tolerance5)) {
            // Release the previous instance of MediaPlayer if it exists
            String message = String.format("Bb");
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            if (Bb != null) {
                Bb.release();
            }
            // Create a new instance of MediaPlayer and start playing the audio
            Bb = MediaPlayer.create(getContext(), R.raw.bb);
            Bb.start();
        }
        if (isWithinTarget(x, y, target6XPercent, target6YPercent, tolerance6)) {
            // Release the previous instance of MediaPlayer if it exists
            String message = String.format("eb");
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            if (eb != null) {
                eb.release();
            }
            // Create a new instance of MediaPlayer and start playing the audio
            eb = MediaPlayer.create(getContext(), R.raw.higheb);
            eb.start();
        }
    }

    // Method to play audio
    @Override
    public void onResume() {
        super.onResume();
        // Reset MediaPlayer state
        if (Eb != null) {
            Eb.release();
            Eb = MediaPlayer.create(getContext(),R.raw.loweb);
        }
        if (Ab != null) {
            Ab.release();
            Ab = MediaPlayer.create(getContext(),R.raw.ab);
        }
        if (Db != null) {
            Db.release();
            Db = MediaPlayer.create(getContext(),R.raw.db);
        }
        if (Gb != null) {
            Gb.release();
            Gb = MediaPlayer.create(getContext(),R.raw.gb);
        }
        if (Bb != null) {
            Bb.release();
            Bb = MediaPlayer.create(getContext(),R.raw.bb);
        }
        if (eb != null) {
            eb.release();
            eb = MediaPlayer.create(getContext(),R.raw.higheb);
        }
        if (halfstepstrum != null){
            halfstepstrum.release();
            halfstepstrum = MediaPlayer.create(getContext(), R.raw.halfstepstrum);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // Release MediaPlayer resources when activity is destroyed
        if (Eb != null) {
            Eb.release();
            Eb = null;
        }
        if (Ab != null) {
            Ab.release();
            Ab = null;
        }
        if (Db != null) {
            Db.release();
            Db = null;
        }
        if (Gb != null) {
            Gb.release();
            Gb = null;
        }
        if (Bb != null) {
            Bb.release();
            Bb = null;
        }
        if (eb != null) {
            eb.release();
            eb = null;
        }
        if (halfstepstrum != null){
            halfstepstrum.release();
            halfstepstrum = null;
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
