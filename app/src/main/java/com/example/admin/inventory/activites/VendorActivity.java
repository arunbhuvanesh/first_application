package com.example.admin.inventory.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admin.inventory.R;
import com.example.admin.inventory.remote.ApiClient;
import com.example.admin.inventory.remote.ApiInterface;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorActivity extends AppCompatActivity {
    EditText et_user,et_company,et_address,et_mobile,et_mail;
    ProgressBar progressBar;
    Button vendorsave;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);


        /*find id*/
        et_user=findViewById(R.id.v_user);
        et_company=findViewById(R.id.v_company);
        et_address=findViewById(R.id.v_address);
        et_mobile=findViewById(R.id.v_phone);
        et_mail=findViewById(R.id.v_email);
        progressBar=findViewById(R.id.progressbar);
        vendorsave=findViewById(R.id.v_save);
        progressBar.setVisibility(View.GONE);

        //api calling
        apiInterface=ApiClient.getApiClient().create(ApiInterface.class);


        vendorsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    String vName=et_user.getText().toString();
                    String vcName=et_company.getText().toString();
                    String vAddress=et_address.getText().toString();
                    String vPhone=et_mobile.getText().toString();
                    String vEmail=et_mail.getText().toString();

                    if (vName.isEmpty()){
                        et_user.setError("Name required");
                        et_user.requestFocus();
                    }
                    else if(vcName.isEmpty()){
                        et_company.setError("company name must");
                        et_company.requestFocus();
                    }
                    else if(vAddress.isEmpty()){
                        et_address.setError("company Address must");
                        et_address.requestFocus();
                    }
                    else if(vPhone.length()<10||vPhone.length()>10){
                        et_mobile.setError("please enter 10 digit number ");
                        et_mobile.requestFocus();
                    }
                    else if (!Patterns.EMAIL_ADDRESS.matcher(vEmail).matches()){
                        et_mail.setError("Check your Email Address");
                    }
                    else{

                        addVendor(vName,vcName,vAddress,vPhone,vEmail);

                    }

                }catch (Exception e){e.getStackTrace();}
//                startActivity(new Intent(VendorActivity.this,HomeActivity.class));
            }
        });
    }

    private void addVendor(String vName, String vcName, String vAddress, String vPhone,String vEmail) throws IOException {
        Call<ResponseBody> call=apiInterface.addvendor(vName,vcName,vAddress,vPhone,vEmail);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(VendorActivity.this, "Vendor successfully Added!!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.VISIBLE);
                    startActivity(new Intent(VendorActivity.this,Show_vendorDetails.class));
                    finish();
                }
                else{
                    Toast.makeText(VendorActivity.this, "problem on retrofit call!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(VendorActivity.this, "Vendor not successfully Added!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
