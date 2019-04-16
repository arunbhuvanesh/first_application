package com.example.admin.inventory.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admin.inventory.R;
import com.example.admin.inventory.adapter.mClickListener;
import com.example.admin.inventory.remote.ApiClient;
import com.example.admin.inventory.remote.ApiInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class updateVendors extends AppCompatActivity implements mClickListener {
    EditText username, companyname, address, phone, id,mail;
    Button save;
    ApiInterface apiInterface;
    ProgressBar progressBar;
    boolean isnumbervalid;

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

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidUser(username.getText().toString())) {
                    username.setError("Enter charcters only");
                }
            }
        });

        companyname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidCompany(companyname.getText().toString())) {
                    companyname.setError("Enter charcters only");
                }
            }
        });

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidNumber(phone.getText().toString())) {
                    isnumbervalid = false;
                    //et_mobile.setTextColor(Color.parseColor("#008577"));
                    phone.setError("Enter a 10 digit valid Number");
                } else {
                    isnumbervalid = true;
                    //et_mobile.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
        mail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidEmail(mail.getText().toString())) {
                    mail.setError("Enter a valid address");
                }
            }
        });

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


    private boolean isValidCompany(String toString) {
        if (toString == null) {
            return false;
        } else {
            String COMPANY_PATTERN = "^[a-zA-Z ]+$";
            Pattern pattern = Pattern.compile(COMPANY_PATTERN);
            Matcher matcher = pattern.matcher(toString);
            return matcher.matches();
        }
    }

    private final static  boolean isValidUser(String toString) {
        if (toString == null) {
            return false;
        } else {
            String USER_PATTERN = "^[a-zA-Z ]+$";
            Pattern pattern = Pattern.compile(USER_PATTERN);
            Matcher matcher = pattern.matcher(toString);
            return matcher.matches();
        }

    }

    private boolean isValidNumber(String toString) {
        if (toString == null) {
            return false;
        } else {
            String PHONE_PATTERN = "[6-9][0-9]{9}";
            Pattern pattern = Pattern.compile(PHONE_PATTERN);
            Matcher matcher = pattern.matcher(toString);
            return matcher.matches();
        }
    }
    public final static boolean isValidEmail(String target) {
        if (target == null) {
            return false;
        } else {
            //android Regex to check the email address Validation
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
