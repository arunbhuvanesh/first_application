package com.example.admin.inventory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.inventory.activites.HomeActivity;
import com.example.admin.inventory.activites.MainActivity;
import com.wang.avi.AVLoadingIndicatorView;

import android.os.Handler;
import android.view.View;


public class splash_Activity extends AppCompatActivity {
    private Handler handler;
    private Runnable runnable;
    private AVLoadingIndicatorView progressBar;
    SharedPreferences sharePreference;
    public static final String shared_prefer = "mypreference";
    public static final String key_username = "keyusername";
    public static final String key_password = "keypassword";
    public static final String key_id = "keyid";
    public static final String Susername = "supernameKey";
    public static final String Spassword = "superpassKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

//        sharePreference = getSharedPreferences(shared_prefer, Context.MODE_PRIVATE);


        String indicator = getIntent().getStringExtra("indicator");
        progressBar.setIndicator(indicator);

        runnable = new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        };

        handler = new Handler();
        handler.postDelayed(runnable, 1200);
      /*  if (sharePreference.contains(key_username) && sharePreference.contains(key_password)) {
            try {
                Intent intent = new Intent(splash_Activity.this, HomeActivity.class);
                startActivity(intent);
                finish();

            } catch (Exception e) {
                e.printStackTrace();
            }
*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(handler!=null&&runnable!=null)
        handler.removeCallbacks(runnable);
    }


    }

