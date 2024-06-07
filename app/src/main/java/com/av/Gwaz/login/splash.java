package com.av.Gwaz.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.av.Gwaz.R;
import com.facebook.shimmer.ShimmerFrameLayout;

public class splash extends AppCompatActivity {
    ImageView logo, gwaztxt;
    TextView name, own1, own2;
    Animation topAnim, bottomAnim, logoAnimSet;
    MediaPlayer mediaPlayer;
    private ShimmerFrameLayout shimmerFrameLayout;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);

        logo = findViewById(R.id.logoimg);
        gwaztxt = findViewById(R.id.gwaztxt);
        own1 = findViewById(R.id.ownone);
        own2 = findViewById(R.id.owntwo);

        // Check if activity was launched from a notification click
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey("link")) {
            String url = getIntent().getStringExtra("link");
            if (url != null && !url.isEmpty()) {
                // Open the URL if available
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                finish();
                return;

            }
        }





        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        logoAnimSet = AnimationUtils.loadAnimation(this, R.anim.logo_animation_set);


        logo.setAnimation(logoAnimSet);
        own1.setAnimation(bottomAnim);
        own2.setAnimation(bottomAnim);



        mediaPlayer = MediaPlayer.create(this, R.raw.intro_track);
        mediaPlayer.start();

        // Start the shimmer effect after a delay of 2.5 seconds (2500 milliseconds)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                shimmerFrameLayout.startShimmer();

                // Stop the shimmer effect after one cycle (based on shimmer_duration)
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        shimmerFrameLayout.stopShimmer();
                        gwaztxt.setVisibility(View.VISIBLE);
                    }
                }, 1000); // Adjust this delay to match the shimmer_duration
            }
        }, 2500);





        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
              Intent intent = new Intent(splash.this, login.class);

              startActivity(intent);
              finish();
          }
      },4000);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release the MediaPlayer resources
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    @Override
    protected void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }





}