package com.example.admin.inventory.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.example.admin.inventory.R;

public class HomeActivity extends AppCompatActivity {
    CardView cv1,cv2,cv3,cv4,cv5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        cv1=findViewById(R.id.adduserCard);
        cv2=findViewById(R.id.additemcard);
        cv3=findViewById(R.id.purchaseUser);
        cv4=findViewById(R.id.addVendor);
        cv5=findViewById(R.id.purchaseEntry);
        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,show_Userdetails.class));
            }
        });

        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,Additems.class));
            }
        });
        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,PurchaseUserActivity.class));
            }
        });
        cv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,Show_vendorDetails.class));
            }
        });
        cv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,PurchaseEntry.class));
            }
        });

    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            HomeActivity.this.finish();
                            finishAffinity();

                        } catch (Exception er) {
                            er.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

}
