package com.av.Gwaz.homepage.TUNER;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.av.Gwaz.R;
import com.av.Gwaz.homepage.home;

public class TunerTrainer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuner_trainer);

        // Open the OverlayView1Fragment when the activity is created
        openOverlayView1Fragment();

        Button btnOpenMenu = findViewById(R.id.btn_open_menu);
        btnOpenMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMenu();
            }
        });
    }



    // Method to open OverlayView1Fragment
    private void openOverlayView1Fragment() {
        OverlayView1Fragment overlayFragment = new OverlayView1Fragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.overlay_container, overlayFragment, "overlay_fragment")
                .commit();
    }

    private void openMenu() {
        PullUpMenuFragment menuFragment = new PullUpMenuFragment();
        menuFragment.show(getSupportFragmentManager(), "pull_up_menu");
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TunerTrainer.this, home.class));
        finish();
    }




}