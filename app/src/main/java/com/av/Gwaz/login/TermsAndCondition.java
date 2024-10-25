package com.av.Gwaz.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.av.Gwaz.R;

public class TermsAndCondition extends AppCompatActivity {
    Button nextBtn;
    ImageView back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);

        nextBtn = findViewById(R.id.nextBtn);
        back = findViewById(R.id.back);


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermsAndCondition.this, TermsAndCondition2.class);
                startActivityForResult(intent, 2);  // Request code is 2 for Page 2

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK) {  // Result from Page 2
            boolean isChecked = data.getBooleanExtra("checkboxChecked", false);

            // Pass the result back to Registration Page
            Intent intent = new Intent();
            intent.putExtra("checkboxChecked", isChecked);
            setResult(RESULT_OK, intent);  // Set the result for the Registration Page
            finish();  // Go back to Registration Page
        }
    }
}