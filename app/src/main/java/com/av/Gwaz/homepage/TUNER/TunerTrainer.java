package com.av.Gwaz.homepage.TUNER;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.av.Gwaz.R;
import com.av.Gwaz.homepage.home;
import com.bumptech.glide.Glide;

public class TunerTrainer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuner_trainer);


        // Open the OverlayView1Fragment when the activity is created
        openOverlayView1Fragment();

        instruction();

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

    public void instruction() {
        Dialog dialog = new Dialog(TunerTrainer.this, R.style.dialoge);
        dialog.setContentView(R.layout.tuner_instruction);


        ImageView tap = dialog.findViewById(R.id.tap);

        Glide.with(TunerTrainer.this).asGif().load(R.drawable.tuner_instruction2).into(tap);

        final boolean[] isFirstClick = {true};

        tap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirstClick[0]) {
                    Glide.with(TunerTrainer.this).asGif().load(R.drawable.tuner_intruction1).into(tap);
                    isFirstClick[0] = false;
                } else {
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
    }




}