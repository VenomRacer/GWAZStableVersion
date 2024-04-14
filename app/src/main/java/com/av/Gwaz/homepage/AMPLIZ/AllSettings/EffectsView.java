    package com.av.Gwaz.homepage.AMPLIZ.AllSettings;

    import android.os.Bundle;
    import android.view.View;
    import android.widget.LinearLayout;

    import androidx.appcompat.app.AppCompatActivity;

    import com.av.Gwaz.R;

    public class EffectsView extends AppCompatActivity {

        private LinearLayout chorusBox,compressorBox,delayBox,distortionBox,flangerBox,fuzzBox,overdriveBox,phaserBox,reverb1Box,tremoloBox,wahBox;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_effects_view);

            chorusBox = findViewById(R.id.chorusBox);
            compressorBox = findViewById(R.id.compressorBox);
            delayBox = findViewById(R.id.delayBox);
            distortionBox = findViewById(R.id.distortionBox);
            flangerBox = findViewById(R.id.flangerBox);
            fuzzBox = findViewById(R.id.fuzzBox);
            overdriveBox = findViewById(R.id.overdriveBox);
            phaserBox = findViewById(R.id.phaserBox);
            reverb1Box = findViewById(R.id.reverbBox);
            tremoloBox = findViewById(R.id.tremoloBox);
            wahBox = findViewById(R.id.wahBox);

            //retrieve effects 11
            String chorus = getIntent().getStringExtra("CHORUS");
            String compressor = getIntent().getStringExtra("COMPRESSOR");
            String delay = getIntent().getStringExtra("DELAY");
            String distortion = getIntent().getStringExtra("DISTORTION");
            String flanger = getIntent().getStringExtra("FLANGER");
            String fuzz = getIntent().getStringExtra("FUZZ");
            String overdrive = getIntent().getStringExtra("OVERDRIVE");
            String phaser = getIntent().getStringExtra("PHASER");
            String reverb1 = getIntent().getStringExtra("REVERB1");
            String tremolo = getIntent().getStringExtra("TREMOLO");
            String wah = getIntent().getStringExtra("WAH");

            //setting visibility
            if ("true".equals(chorus)){
                chorusBox.setVisibility(View.VISIBLE);

            }
            if ("true".equals(compressor)){
                compressorBox.setVisibility(View.VISIBLE);

            }
            if ("true".equals(delay)){
                delayBox.setVisibility(View.VISIBLE);

            }
            if ("true".equals(distortion)){
                distortionBox.setVisibility(View.VISIBLE);

            }
            if ("true".equals(flanger)){
                flangerBox.setVisibility(View.VISIBLE);

            }
            if ("true".equals(fuzz)){
                fuzzBox.setVisibility(View.VISIBLE);

            }
            if ("true".equals(overdrive)){
                overdriveBox.setVisibility(View.VISIBLE);

            }
            if ("true".equals(phaser)){
                phaserBox.setVisibility(View.VISIBLE);

            }
            if ("true".equals(reverb1)){
                reverb1Box.setVisibility(View.VISIBLE);

            }
            if ("true".equals(tremolo)){
                tremoloBox.setVisibility(View.VISIBLE);

            }
            if ("true".equals(wah)){
                wahBox.setVisibility(View.VISIBLE);

            }
        }
    }