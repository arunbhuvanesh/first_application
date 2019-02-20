package com.example.admin.inventory.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admin.inventory.R;
import com.example.admin.inventory.adapter.mClickListener;
import com.example.admin.inventory.remote.ApiClient;
import com.example.admin.inventory.remote.ApiInterface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class updateVendors extends AppCompatActivity implements mClickListener {
    EditText username, companyname, address, phone, id,mail;
    Button save;
    ApiInterface apiInterface;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_vendors);
        id = findViewById(R.id.vid);
        username = findViewById(R.id.v_user);
        companyname = findViewById(R.id.v_company);
        address = findViewById(R.id.v_address);
        phone = findViewById(R.id.v_phone);
        mail=findViewById(R.id.v_email);
        save = findViewById(R.id.v_save);
        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

//api calling
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        String i = getIntent().getStringExtra("vendor id");
        id.setText(i);
        String name = getIntent().getStringExtra("vendor name");
        username.setText(name);
        String company = getIntent().getStringExtra("company name");
        companyname.setText(company);
        String addr = getIntent().getStringExtra("vendor address");
        address.setText(addr);
        String no = getIntent().getStringExtra("vendor phone");
        phone.setText(no);
        String mailid=getIntent().getStringExtra("vendor email");
        mail.setText(mailid);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updatevendor(id.getText().toString(),username.getText().toString(), companyname.getText().toString(), address.getText().toString(), phone.getText().toString(),mail.getText().toString());
                progressBar.setVisibility(View.VISIBLE);
            }
        });

    }


    private void updatevendor(String id,String username, String companyname, String address, String phone,String mail) {
         Call<ResponseBody> call=apiInterface.updatevendor(id,username,companyname,address,phone,mail);
         call.enqueue(new Callback<ResponseBody>() {
             @Override
             public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                 if(response.isSuccessful()){


                     Toast.makeText(updateVendors.this,"Vendors Updated successfully!!",Toast.LENGTH_SHORT).show();

                     startActivity(new Intent(updateVendors.this,Show_vendorDetails.class));
                     finish();

                 }

             }

             @Override
             public void onFailure(Call<ResponseBody> call, Throwable t) {

                 Toast.makeText(updateVendors.this,"Vendors not Updated successfully!!",Toast.LENGTH_SHORT).show();
             }
         });
    }


    @Override
    public void onClick(String id) {

    }
}
