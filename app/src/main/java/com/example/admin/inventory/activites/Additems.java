package com.example.admin.inventory.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.inventory.R;
import com.example.admin.inventory.remote.ApiClient;
import com.example.admin.inventory.remote.ApiInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Additems extends AppCompatActivity {
    Button save;
    EditText item, itemquantity;
    ApiInterface apiInterface;
    String i, q;
    SharedPreferences sharePreference;
    public static final String shared_prefer = "mypreference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additems);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        save = findViewById(R.id.itemsave);
        item = findViewById(R.id.item);
        itemquantity = findViewById(R.id.itemquantity);

        //api calling
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        item.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidUser(item.getText().toString())) {
                    item.setError("Enter charcters only");
                }
            }
        });

        itemquantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
             if (!isValidQuantity(itemquantity.getText().toString()))
             {
                 itemquantity.setError("Enter Numbers only");
             }

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = item.getText().toString();
                q = itemquantity.getText().toString();


                if (i.isEmpty() && q.isEmpty()) {
                    Toasty.error(Additems.this, "Enter the details", Toast.LENGTH_SHORT).show();
                }
                //itemquantity.setText(0);
                else {
                    addItem(i, q);
                }
            }
        });


    }

    private boolean isValidQuantity(String toString) {
        if (toString == null) {
            return false;
        } else {
            String Quantity_Pattern = "^[0-9]+$";
            Pattern pattern = Pattern.compile(Quantity_Pattern);
            Matcher matcher = pattern.matcher(toString);
            return matcher.matches();
        }
    }

    private boolean isValidUser(String q) {


        if (q == null) {
            return false;
        } else {
            String USER_PATTERN = "^[a-zA-Z ]+$";
            Pattern pattern = Pattern.compile(USER_PATTERN);
            Matcher matcher = pattern.matcher(q);
            return matcher.matches();
        }


    }


    private void addItem(String i, String q) {
        Call<ResponseBody> call = apiInterface.additemcheck(i, q);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Additems.this, "Items added successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Additems.this, HomeActivity.class));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Additems.this, "Items not successfully added  ", Toast.LENGTH_SHORT).show();
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
            startActivity(new Intent(Additems.this,MainActivity.class));
            Toasty.info(getApplicationContext(),"working",Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }
}
