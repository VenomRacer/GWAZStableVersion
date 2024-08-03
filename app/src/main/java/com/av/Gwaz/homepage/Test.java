package com.av.Gwaz.homepage;

import android.Manifest;
import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.av.Gwaz.R;
import com.av.Gwaz.chat.MessageWindow;
import com.av.Gwaz.chat.setting;
import com.av.Gwaz.homepage.AMPLIZ.Add.AddAmp;
import com.av.Gwaz.homepage.AMPLIZ.AllSettings.AllSettings;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Target;


public class Test extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton fab;
    private Target profileTarget;
    private static final int PERMISSION_REQUEST_CODE = 123;
    private String NOTIF_KEY = "JULY10";
    private String LINK = "https://novalichessti-my.sharepoint.com/:f:/g/personal/calongcagong_241612_novaliches_sti_edu_ph/ErJtL1qthPFOsD1w0b58EhwB8BQ-Md2i98tsIDrVEBOESA?e=gBNT78";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlayout);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        Dialog ndialog = new Dialog(Test.this,R.style.dialoge);
        ndialog.setContentView(R.layout.notif_dialog);
        ndialog.setCancelable(false);

        FirebaseDatabase ndatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = ndatabase.getReference("updateKey");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get the value of updateKey
                String updateKeyValue = dataSnapshot.getValue(String.class);
                if (!updateKeyValue.equals(NOTIF_KEY)){
                    Toast.makeText(Test.this, " "+updateKeyValue, (Toast.LENGTH_SHORT)).show();
                    ndialog.show();
                    Button update,exit;
                    update = ndialog.findViewById(R.id.updateBtn);
                    exit = ndialog.findViewById(R.id.exitBtn);

                    update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(LINK)));
                            finish();
                            return;

                        }
                    });

                    exit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();

                        }
                    });

                } else {
                    ndialog.dismiss();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle possible errors
            }
        });

        replaceFragment(new AllSettings());



        bottomNavigationView = findViewById(R.id.bottom_nav);
        fab = findViewById(R.id.fab);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.home:
                        selectedFragment = new AllSettings();
                        fab.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .alpha(1f)
                                .setDuration(500) // Animation duration in milliseconds
                                .setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                        fab.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {
                                    }
                                })
                                .start();
                        break;
                    case R.id.tools:
                        selectedFragment = new home();
                        fab.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .alpha(1f)
                                .setDuration(500) // Animation duration in milliseconds
                                .setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                        fab.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {
                                    }
                                })
                                .start();
                        break;
                    case R.id.profile:
                        selectedFragment = new setting();
                        fab.animate()
                                .scaleX(0f)
                                .scaleY(0f)
                                .alpha(0f)
                                .setDuration(500) // Animation duration in milliseconds
                                .setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        fab.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {
                                    }
                                })
                                .start();
                        break;
                    case R.id.message:
                        selectedFragment = new MessageWindow();
                        fab.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .alpha(1f)
                                .setDuration(500) // Animation duration in milliseconds
                                .setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                        fab.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {
                                    }
                                })
                                .start();
                        break;
                }
                if (selectedFragment != null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    // Set custom animations
                    // Set custom animations
                    transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                            android.R.anim.fade_in, android.R.anim.fade_out);
                    transaction.replace(R.id.fragment_container, selectedFragment);
                    transaction.commit();
                }
                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check for network connectivity
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                // Check and request permissions if needed
                if (!checkPermissions()) {
                    requestPermissions();
                }

                else {

                    if (networkInfo != null && networkInfo.isConnected()) {
                        // If there is network connectivity, execute the code
                        Intent intent = new Intent(Test.this, AddAmp.class);
                        // Apply the custom slide-up animation
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_up, 0);
                        // Remove the Fragment from the back stack

                    } else {
                        // If there's no network connectivity, display a toast
                        Toast.makeText(Test.this, "No network connection", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }


    private Bitmap getCircularBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        paint.setAntiAlias(true);
        paint.setShader(shader);

        float radius = Math.min(bitmap.getWidth() / 2f, bitmap.getHeight() / 2f);
        canvas.drawCircle(bitmap.getWidth() / 2f, bitmap.getHeight() / 2f, radius, paint);

        return output;
    }


    private void replaceFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

    }

    private boolean checkPermissions() {
        int permissionNetwork = ContextCompat.checkSelfPermission(Test.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionReadMediaAudio = ContextCompat.checkSelfPermission(Test.this, Manifest.permission.READ_MEDIA_AUDIO);
        int permissionReadMediaImages = ContextCompat.checkSelfPermission(Test.this, Manifest.permission.READ_MEDIA_IMAGES);
        return (permissionNetwork == PackageManager.PERMISSION_GRANTED ||
                permissionReadMediaAudio == PackageManager.PERMISSION_GRANTED) ||
                permissionReadMediaImages == PackageManager.PERMISSION_GRANTED;


    }

    // Request permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(Test.this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_IMAGES
        }, PERMISSION_REQUEST_CODE);
        Toast.makeText(Test.this, "Please allow permissions \n for media access.", Toast.LENGTH_SHORT).show();

    }
}