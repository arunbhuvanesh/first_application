package com.example.admin.inventory.activites;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class updateUsers extends AppCompatActivity {

    Button save;

    EditText name,address,area,email,phonenumber,reference,rphone,id;
    ApiInterface apiInterface;
    private RecyclerView recyclerView;
    private customerAdapter adapter;
    @BindView(R.id.img_profile)
    ImageView imgProfile;
    boolean isnumbervalid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_users);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        save=findViewById(R.id.save);
        id=findViewById(R.id.cid);
        name=findViewById(R.id.user);
        address=findViewById(R.id.address);
        area=findViewById(R.id.area);
        email=findViewById(R.id.email);
        phonenumber=findViewById(R.id.phone);
        reference=findViewById(R.id.reference);
        rphone=findViewById(R.id.Refphone);
        imgProfile=findViewById(R.id.img_profile);




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


        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidUser(name.getText().toString())) {
                    name.setError("Enter charcters only");
                }
            }
        });
        area.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidArea(area.getText().toString())) {
                    area.setError("Enter charcters only");
                }
            }
        });

        phonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidNumber(phonenumber.getText().toString())) {
                    isnumbervalid = false;
                    //et_mobile.setTextColor(Color.parseColor("#008577"));
                    phonenumber.setError("Enter a 10 digit valid Number");
                } else {
                    isnumbervalid = true;
                    //et_mobile.setTextColor(Color.parseColor("#000000"));
                }


            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidEmail(email.getText().toString())) {
                    email.setError("Enter a valid address");
                }

            }
        });
        reference.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidRef(reference.getText().toString())) {
                    reference.setError("Enter charcters only");
                }
            }
        });
        rphone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidRNumber(rphone.getText().toString())) {
                    isnumbervalid = false;
                    //et_mobile.setTextColor(Color.parseColor("#008577"));
                    rphone.setError("Enter a 10 digit valid Number");
                } else {
                    isnumbervalid = true;
                    //et_mobile.setTextColor(Color.parseColor("#000000"));
                }
            }
        });


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

    private boolean isValidUser(String toString) {
        if (toString == null) {
            return false;
        } else {
            String USER_PATTERN = "^[a-zA-Z ]+$";
            Pattern pattern = Pattern.compile(USER_PATTERN);
            Matcher matcher = pattern.matcher(toString);
            return matcher.matches();
        }
    }

    private boolean isValidArea(String toString) {
        if (toString == null) {
            return false;
        } else {
            String AREA_PATTERN = "^[a-zA-Z ]+$";
            Pattern pattern = Pattern.compile(AREA_PATTERN);
            Matcher matcher = pattern.matcher(toString);
            return matcher.matches();
        }
    }

    private boolean isValidRef(String toString) {
        if (toString == null) {
            return false;
        } else {
            String REF_PATTERN = "^[a-zA-Z ]+$";
            Pattern pattern = Pattern.compile(REF_PATTERN);
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

    private boolean isValidRNumber(String toString) {
        if (toString == null) {
            return false;
        } else {
            String PHONE_PATTERN = "[6-9][0-9]{9}";
            Pattern pattern = Pattern.compile(PHONE_PATTERN);
            Matcher matcher = pattern.matcher(toString);
            return matcher.matches();
        }
    }
}
