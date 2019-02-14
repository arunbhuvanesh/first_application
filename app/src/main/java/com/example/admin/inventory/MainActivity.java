package com.example.admin.inventory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.admin.inventory.activites.HomeActivity;


public class MainActivity extends AppCompatActivity {
    Button button;
    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        button = findViewById(R.id.bt_login);


        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {

                                          /*if (username.getText().toString().equals("admin") && password.getText().toString().equals("1234")) {
*/
                                              startActivity(new Intent(MainActivity.this,HomeActivity.class));
                                              Toast.makeText(getApplicationContext(),"Login successfully",Toast.LENGTH_LONG).show();
                                         //}


                                      }
                                  }
        );

    }
}
