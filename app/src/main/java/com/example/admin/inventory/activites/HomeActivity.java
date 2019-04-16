package com.example.admin.inventory.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.inventory.R;

import es.dmoral.toasty.Toasty;

public class HomeActivity extends AppCompatActivity {
    CardView cv1,cv2,cv3,cv4,cv5,cv6,cv7;
    SharedPreferences sharePreference;
    public static final String shared_prefer = "mypreference";
    public static final String key_username = "keyusername";
    public static final String key_password = "keypassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_home);
        cv1=findViewById(R.id.adduserCard);
        cv2=findViewById(R.id.additemcard);
        cv3=findViewById(R.id.purchaseUser);
        cv4=findViewById(R.id.addVendor);
        cv5=findViewById(R.id.purchaseEntry);
        cv6=findViewById(R.id.total_stocks);
        cv7=findViewById(R.id.History);


        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,show_Userdetails.class));
            }
        });

        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Items already inserted!!..",Toast.LENGTH_SHORT).show();
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
        cv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,Total_quantity.class));
            }
        });
        cv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,HistoryActivity.class));
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.logout)
        {
            sharePreference=getApplicationContext().getSharedPreferences(shared_prefer,MODE_PRIVATE);
            SharedPreferences.Editor editor=sharePreference.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(HomeActivity.this,MainActivity.class));
            Toasty.info(getApplicationContext(),"working",Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
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
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                finishAffinity();
                            }

                        } catch (Exception er) {
                            er.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

}
