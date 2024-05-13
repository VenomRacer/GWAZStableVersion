package com.example.admincms;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable offline persistence globally
        FirebaseDatabase.getInstance().setPersistenceEnabled(false);

        // Enable persistence only for the specific path "Service/GWIZ"
        DatabaseReference gwizRef = FirebaseDatabase.getInstance().getReference().child("Service").child("GWIZ");
        gwizRef.keepSynced(true);

        // Enable persistence only for the specific path "Service/GWIZ"
        DatabaseReference ampRef = FirebaseDatabase.getInstance().getReference().child("Service").child("AMPLIZONE");
        ampRef.keepSynced(false);


    }
}