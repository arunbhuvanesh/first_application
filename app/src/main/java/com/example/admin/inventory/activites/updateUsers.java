package com.example.admin.inventory.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.inventory.R;
import com.example.admin.inventory.adapter.customerAdapter;
import com.example.admin.inventory.remote.ApiClient;
import com.example.admin.inventory.remote.ApiInterface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class updateUsers extends AppCompatActivity {

    Button save;
    ImageView imageView;
    EditText name,address,area,email,phonenumber,reference,rphone,id;
    ApiInterface apiInterface;
    private RecyclerView recyclerView;
    private customerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_users);

        save=findViewById(R.id.save);
        id=findViewById(R.id.cid);
        name=findViewById(R.id.user);
        address=findViewById(R.id.address);
        area=findViewById(R.id.area);
        email=findViewById(R.id.email);
        phonenumber=findViewById(R.id.phone);
        reference=findViewById(R.id.reference);
        rphone=findViewById(R.id.Refphone);
        imageView=findViewById(R.id.userimage);



        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);

        String i=getIntent().getStringExtra("customer Id");
        id.setText(i);
        String cname=getIntent().getStringExtra("customer name");
        name.setText(cname);
        String addr=getIntent().getStringExtra("customer address");
        address.setText(addr);
        String carea=getIntent().getStringExtra("customer area");
        area.setText(carea);
        String cemail=getIntent().getStringExtra("customer email");
        email.setText(cemail);
        String cphone=getIntent().getStringExtra("customer mobile");
        phonenumber.setText(cphone);
        String cref=getIntent().getStringExtra("reference");
        reference.setText(cref);
        String crphone=getIntent().getStringExtra("ref mobile");
        rphone.setText(crphone);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCustomer(id.getText().toString(),name.getText().toString(),address.getText().toString(),area.getText().toString(),email.getText().toString(),phonenumber.getText().toString(),reference.getText().toString(),rphone.getText().toString());
            }
        });
    }
    private void updateCustomer(String id,String name,String address,String area,String email,String phonenumber,String reference,String rphone)
    {
        Call<ResponseBody> call=apiInterface.updateCustomers(id,name,address,area,email,phonenumber,reference,rphone);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(updateUsers.this,"customer Updated successfully!!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(updateUsers.this,show_Userdetails.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(updateUsers.this,"customer not Updated successfully!!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
