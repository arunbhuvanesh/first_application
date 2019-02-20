package com.example.admin.inventory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.inventory.activites.MainActivity;

import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

public class splash_Activity extends AppCompatActivity {
    private Handler handler;
    private Runnable runnable;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        progressBar=findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        runnable=new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        };

        handler= new Handler();
        handler.postDelayed(runnable,2500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(handler!=null&&runnable!=null)
        handler.removeCallbacks(runnable);
    }
}
