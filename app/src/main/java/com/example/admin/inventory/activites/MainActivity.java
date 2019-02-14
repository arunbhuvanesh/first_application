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


public class MainActivity extends AppCompatActivity {
    Button button;
    EditText username, password;
    ProgressDialog progressDialog;
    //ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        button = findViewById(R.id.bt_login);
        //progressBar=findViewById(R.id.progressbar);
        //progressBar.setVisibility(View.INVISIBLE);


button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       /* String user=username.getText().toString();
        String pwd=password.getText().toString();

        if(user.isEmpty()){
            username.setError("enter the username");
            username.requestFocus();
        }
        else if(pwd.isEmpty()) {
            password.setError("enter the password");
            password.requestFocus();
        }
        else{
            if(user.equals("admin")&& pwd.equals("1234")){
                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                //progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_SHORT).show();
                }
            else {
                Toast.makeText(getApplicationContext(),"Login failed!!check it out..!!!",Toast.LENGTH_SHORT).show();
            }
        }*/

        if (username.getText().toString().equals("admin") && password.getText().toString().equals("1234")) {
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
        else {
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
                        Thread.sleep(700);
                    }catch (Exception e){e.getStackTrace();}
                    progressDialog.dismiss();
                }
            }).start();
            Toast.makeText(getApplicationContext(),"Login failed!!check it out..!!!",Toast.LENGTH_SHORT).show();
        }


        }

});

}
}
