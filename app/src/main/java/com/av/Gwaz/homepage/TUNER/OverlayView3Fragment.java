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

public class OverlayView3Fragment extends Fragment {

    private ImageView imageView;
    private View overlayView3;
    private MediaPlayer dropD,A,D,G,B,e,dropdstrum;

    public OverlayView3Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overlay_view3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.imageView);
        overlayView3 = view.findViewById(R.id.overlayView3);

        // Initialize MediaPlayer with the audio file
        dropD = MediaPlayer.create(getContext(), R.raw.dropd);
        A = MediaPlayer.create(getContext(),R.raw.a);
        D = MediaPlayer.create(getContext(), R.raw.d);
        G = MediaPlayer.create(getContext(), R.raw.g);
        B = MediaPlayer.create(getContext(), R.raw.b);
        e = MediaPlayer.create(getContext(), R.raw.highe);
        dropdstrum = MediaPlayer.create(getContext(), R.raw.dropdstrum);



        overlayView3.setOnTouchListener(new View.OnTouchListener() {
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
                if (dropdstrum != null) {
                    dropdstrum.release();
                }
                // Create a new instance of MediaPlayer and start playing the audio
                dropdstrum = MediaPlayer.create(getContext(), R.raw.dropdstrum);
                dropdstrum.start();
                Toast.makeText(getContext(), "clickable", Toast.LENGTH_SHORT).show();
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


        // Drop D
        float target1XPercent = 0.262f;
        float target1YPercent = 0.650f;
        float tolerance1 = 0.05f;

        // A
        float target2XPercent = 0.306f;
        float target2YPercent = 0.560f;
        float tolerance2 = 0.05f;

        // D
        float target3XPercent = 0.352f;
        float target3YPercent = 0.457f;
        float tolerance3 = 0.05f;

        // G
        float target4XPercent = 0.385f;
        float target4YPercent = 0.354f;
        float tolerance4 = 0.05f;

        // B
        float target5XPercent = 0.438f;
        float target5YPercent = 0.262f;
        float tolerance5 = 0.05f;

        // high E
        float target6XPercent = 0.467f;
        float target6YPercent = 0.159f;
        float tolerance6 = 0.05f;



        if (isWithinTarget(x, y, target1XPercent, target1YPercent, tolerance1)) {
            // Release the previous instance of MediaPlayer if it exists
            // Show a toast message with the percentages
            String message = String.format("D");
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            if (dropD != null) {
                dropD.release();
            }
            // Create a new instance of MediaPlayer and start playing the audio
            dropD = MediaPlayer.create(getContext(), R.raw.dropd);
            dropD.start();
        }

        if (isWithinTarget(x, y, target2XPercent, target2YPercent, tolerance2)) {
            // Release the previous instance of MediaPlayer if it exists
            String message = String.format("A");
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            if (A != null) {
                A.release();
            }
            // Create a new instance of MediaPlayer and start playing the audio
            A = MediaPlayer.create(getContext(), R.raw.a);
            A.start();
        }
        if (isWithinTarget(x, y, target3XPercent, target3YPercent, tolerance3)) {
            // Release the previous instance of MediaPlayer if it exists
            String message = String.format("D");
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            if (D != null) {
                D.release();
            }
            // Create a new instance of MediaPlayer and start playing the audio
            D = MediaPlayer.create(getContext(), R.raw.d);
            D.start();
        }
        if (isWithinTarget(x, y, target4XPercent, target4YPercent, tolerance4)) {
            // Release the previous instance of MediaPlayer if it exists
            String message = String.format("G");
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            if (G != null) {
                G.release();
            }
            // Create a new instance of MediaPlayer and start playing the audio
            G = MediaPlayer.create(getContext(), R.raw.g);
            G.start();
        }
        if (isWithinTarget(x, y, target5XPercent, target5YPercent, tolerance5)) {
            // Release the previous instance of MediaPlayer if it exists
            String message = String.format("B");
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            if (B != null) {
                B.release();
            }
            // Create a new instance of MediaPlayer and start playing the audio
            B = MediaPlayer.create(getContext(), R.raw.b);
            B.start();
        }
        if (isWithinTarget(x, y, target6XPercent, target6YPercent, tolerance6)) {
            // Release the previous instance of MediaPlayer if it exists
            String message = String.format("e");
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            if (e != null) {
                e.release();
            }
            // Create a new instance of MediaPlayer and start playing the audio
            e = MediaPlayer.create(getContext(), R.raw.highe);
            e.start();
        }
    }

    // Method to play audio
    @Override
    public void onResume() {
        super.onResume();
        // Reset MediaPlayer state
        if (dropD != null) {
            dropD.release();
            dropD = MediaPlayer.create(getContext(),R.raw.dropd);
        }
        if (A != null) {
            A.release();
            A = MediaPlayer.create(getContext(),R.raw.a);
        }
        if (D != null) {
            D.release();
            D = MediaPlayer.create(getContext(),R.raw.d);
        }
        if (G != null) {
            G.release();
            G = MediaPlayer.create(getContext(),R.raw.g);
        }
        if (B != null) {
            B.release();
            B = MediaPlayer.create(getContext(),R.raw.b);
        }
        if (e != null) {
            e.release();
            e = MediaPlayer.create(getContext(),R.raw.highe);
        }
        if (dropdstrum != null){
            dropdstrum.release();
            dropdstrum = MediaPlayer.create(getContext(), R.raw.dropdstrum);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // Release MediaPlayer resources when activity is destroyed
        if (dropD != null) {
            dropD.release();
            dropD = null;
        }
        if (A != null) {
            A.release();
            A = null;
        }
        if (D != null) {
            D.release();
            D = null;
        }
        if (G != null) {
            G.release();
            G = null;
        }
        if (B != null) {
            B.release();
            B = null;
        }
        if (e != null) {
            e.release();
            e = null;
        }
        if (dropdstrum != null){
            dropdstrum.release();
            dropdstrum = null;
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
