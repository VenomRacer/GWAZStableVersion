package com.av.Gwaz;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable offline persistence
        FirebaseDatabase.getInstance().setPersistenceEnabled(false);

        DatabaseReference gwizref = FirebaseDatabase.getInstance().getReference("Service").child("GWIZ");
        gwizref.keepSynced(true);

        DatabaseReference leaderboardref = FirebaseDatabase.getInstance().getReference("Service").child("CHORDM");
        leaderboardref.keepSynced(true);




    }
}
