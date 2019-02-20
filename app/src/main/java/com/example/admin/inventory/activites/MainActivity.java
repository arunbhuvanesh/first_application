package com.example.admin.inventory.activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.admin.inventory.R;
import com.example.admin.inventory.model.Admin;
import com.example.admin.inventory.remote.ApiClient;
import com.example.admin.inventory.remote.ApiInterface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    Button button;
    EditText username, password;
    ProgressDialog progressDialog;
    ApiInterface apiInterface;
    //ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        button = findViewById(R.id.bt_login);


        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);


button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        String user=username.getText().toString();
        String pwd=password.getText().toString();

       try {
           if (user.isEmpty()) {
               username.setError("enter the username");
               username.requestFocus();
           } else if (pwd.isEmpty()) {
               password.setError("enter the password");
               password.requestFocus();
           } else {
               logincheck(user, pwd);

           }
       }catch (Exception e){e.printStackTrace();}
    }
});

}

    private void logincheck(String user, String pwd) {
        Call<Admin> call=apiInterface.login(user,pwd);
        call.enqueue(new Callback<Admin>() {
            @Override
            public void onResponse(Call<Admin> call, Response<Admin> response) {
                if(response.isSuccessful())
                {
                    progressDialog=new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.setTitle("Login Process");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    progressDialog.setCancelable(false);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Thread.sleep(10000);
                            }catch (Exception e){e.getStackTrace();}
                            progressDialog.dismiss();
                        }
                    }).start();

                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    Toast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Admin> call, Throwable t) {
                progressDialog=new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setTitle("Login Process");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                progressDialog.setCancelable(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(450);
                        }catch (Exception e){e.getStackTrace();}
                        progressDialog.dismiss();
                    }
                }).start();
                Toast.makeText(getApplicationContext(),"Login failed!!check it out..!!!",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
