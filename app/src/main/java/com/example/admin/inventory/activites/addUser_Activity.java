package com.example.admin.inventory.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.inventory.R;
import com.example.admin.inventory.adapter.customerAdapter;
import com.example.admin.inventory.adapter.vendorAdapter;
import com.example.admin.inventory.model.Customers;
import com.example.admin.inventory.model.Vendors;
import com.example.admin.inventory.remote.ApiClient;
import com.example.admin.inventory.remote.ApiInterface;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addUser_Activity extends AppCompatActivity {
    Button save;
    ImageView imageView;
    EditText name,address,area,email,phonenumber,reference;
    ApiInterface apiInterface;
    private RecyclerView recyclerView;
    private customerAdapter adapter;
    private List<Customers> customersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        save=findViewById(R.id.save);
        name=findViewById(R.id.user);
        address=findViewById(R.id.address);
        area=findViewById(R.id.area);
        email=findViewById(R.id.email);
        phonenumber=findViewById(R.id.phone);
        reference=findViewById(R.id.reference);
        imageView=findViewById(R.id.userimage);



        apiInterface=ApiClient.getApiClient().create(ApiInterface.class);
        //init();
        //loadCustomers();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = name.getText().toString();
                String addr = address.getText().toString();
                String areaa = area.getText().toString();
                String mail=email.getText().toString();
                String phone = phonenumber.getText().toString();
                String ref = reference.getText().toString();


                try {
                    if (username.isEmpty()) {
                        name.setError("Name is required");
                        name.requestFocus();
                    } else if (addr.isEmpty()) {
                        address.setError("Address is required");
                        address.requestFocus();
                    } else if (areaa.isEmpty()) {
                        address.setError("Area is required");
                        address.requestFocus();
                    }
                        else if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                            email.setError("Check your email");
                            email.requestFocus();
                        
                    } else if (phone.isEmpty()){
                        phonenumber.setError("Phonenumber required");
                        phonenumber.requestFocus();
                    }
                    else if (phone.length()<10){
                        phonenumber.setError("Enter valid Number");
                        phonenumber.requestFocus();
                    }
                    else if (ref.isEmpty()){
                        reference.setError("its must");
                        reference.requestFocus();
                    }
                    else {
                        addcustomer(username,addr,areaa,mail,phone,ref);
                    }
                        //startActivity(new Intent(addUser_Activity.this, HomeActivity.class));
                }catch (Exception e){e.getStackTrace();}
            }
        });
    }


   /* private void init() {
        recyclerView=findViewById(R.id.userrecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }*/

    private void addcustomer(String username, String addr, String areaa, String mail, String phone, String ref) {
        Call<ResponseBody> call=apiInterface.addCustomers(username,addr,areaa,mail,phone,ref);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Customer added successfully",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(addUser_Activity.this,show_Userdetails.class));

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Customer not added!!",Toast.LENGTH_SHORT).show();

            }
        });
    }


}
