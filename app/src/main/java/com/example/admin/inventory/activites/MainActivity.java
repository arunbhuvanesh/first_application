package com.example.admin.inventory.activites;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.admin.inventory.R;
import com.example.admin.inventory.model.Admin;
import com.example.admin.inventory.remote.ApiClient;
import com.example.admin.inventory.remote.ApiInterface;
import com.wang.avi.AVLoadingIndicatorView;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    Button button;
    TextView tv1;
    EditText username, password;
//    ProgressDialog progressDialog;
    ApiInterface apiInterface;
    String pwd,user;
    AVLoadingIndicatorView progressBar;
    SharedPreferences sharePreference;
    public static final String shared_prefer = "mypreference";
    public static final String key_username = "keyusername";
    public static final String key_password = "keypassword";
    public static final String key_id = "keyid";
    public static final String Susername = "supernameKey";
    public static final String Spassword = "superpassKey";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        tv1 = findViewById(R.id.errorText);
        button = findViewById(R.id.bt_login);
        progressBar = findViewById(R.id.progressbar);
//        RelativeLayout relativeLayout=findViewById(R.id.container);

        sharePreference = getSharedPreferences(shared_prefer, Context.MODE_PRIVATE);
        if (sharePreference.contains(key_username) && sharePreference.contains(key_password)) {


        try {
           /* if (sharePreference.contains(Susername) && sharePreference.contains(Spassword)) {
                SharedPreferences.Editor editor = sharePreference.edit();
                editor.putString(key_username, sharePreference.getString(Susername, null));
                editor.putString(key_password, sharePreference.getString(Spassword, null));
                editor.commit();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {*/

                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();

        } catch (Exception e) {
            e.printStackTrace();
        }

    } else{
            /*try {
                sharePreference=getApplicationContext().getSharedPreferences(shared_prefer,MODE_PRIVATE);
                SharedPreferences.Editor editor=sharePreference.edit();
                editor.clear();
                editor.apply();
            }catch (Exception e){e.printStackTrace();}*/
        }


        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);

       /* AnimationDrawable animationDrawable= (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(3500);
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.start();*/


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        user = username.getText().toString();
                        pwd = password.getText().toString();


                        if (TextUtils.isEmpty(username.getText().toString() )) {
                          //Toast.makeText(getApplicationContext(),"Enter username and password",Toast.LENGTH_SHORT).show();
                            Toasty.warning(MainActivity.this,"Enter username and password",Toast.LENGTH_SHORT).show();
                        }



                        else {

                            progressBar.setVisibility(View.VISIBLE);
                            logincheck(user, pwd);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

}
    private void logincheck(final String user, final String pwd) {
        Call<Admin> call=apiInterface.login(user,pwd);
        call.enqueue(new Callback<Admin>() {
            @Override
            public void onResponse(Call<Admin> call, Response<Admin> response) {
                if(response.body().getResponse().equals("Login Successfully"))
                {
                    SharedPreferences.Editor editor = sharePreference.edit();
                    editor.putString(key_username,user);
                    editor.putString(key_password, pwd);
                    editor.commit();
                    //Log.d("login response","this is"+response);
                    progressBar.setVisibility(View.GONE);
                    Toasty.success(getApplicationContext(), "Login successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));

                }
                else
                {
                      response.body().getResponse().equals("Login failed");
                        //Log.d("login response","this not enter"+response);
                    progressBar.setVisibility(View.GONE);
                        Toasty.error(getApplicationContext(), "Check Your UserName and Password", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Admin> call, Throwable t) {

                progressBar.setVisibility(View.GONE);

                Toasty.info(getApplicationContext(),"Internet call not working!!!!!!",Toast.LENGTH_SHORT).show();

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
                            MainActivity.this.finish();
                            finishAffinity();

                        } catch (Exception er) {
                            er.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

   /* private final TextWatcher passwordWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
           // Toast.makeText(getApplicationContext(),"brfore edit",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            Toast.makeText(getApplicationContext(),"changing edit",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void afterTextChanged(Editable s) {
             pwd=password.getText().toString();
if (pwd.isEmpty())
{
    password.setError("enter pass");
    password.requestFocus();
}
else if (pwd.length()<4){
    tv1.setText("bad password length");
    password.requestFocus();

}
else if (pwd.length()>6){
    tv1.setText("strong password length");
    password.requestFocus();
}
        }
    };*/
}
