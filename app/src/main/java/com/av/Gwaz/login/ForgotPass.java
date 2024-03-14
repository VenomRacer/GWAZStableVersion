package com.av.Gwaz.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.av.Gwaz.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPass extends AppCompatActivity {

    TextInputEditText email;
    Button reset;

    FirebaseAuth mAuth;
    String strEmail;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        //Initialization
        email = findViewById(R.id.txtEmail);
        reset = findViewById(R.id.btnReset);

        mAuth = FirebaseAuth.getInstance();

        // Reset button
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strEmail = email.getText().toString().trim();
                if (!TextUtils.isEmpty(strEmail)) {
                    ResetPassword();


                }

                else {
                    email.setError("Email field can't be empty");
                }
            }
        });
    }

    private void ResetPassword() {
        reset.setVisibility(View.INVISIBLE);

        mAuth.sendPasswordResetEmail(strEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ForgotPass.this, "Reset password link has been sent to your email.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ForgotPass.this, login.class);
                startActivity(intent);
                finish();


            }
        }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ForgotPass.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                reset.setVisibility(View.VISIBLE);

            }
        });
    }
}