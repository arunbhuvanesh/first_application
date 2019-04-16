package com.example.admin.inventory.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.inventory.R;
import com.example.admin.inventory.remote.ApiClient;
import com.example.admin.inventory.remote.ApiInterface;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorActivity extends AppCompatActivity {
    EditText et_user,et_company,et_address,et_mobile,et_mail;
    ProgressBar progressBar;
    Button vendorsave;
    ApiInterface apiInterface;
    boolean isnumbervalid;
    SharedPreferences sharePreference;
    public static final String shared_prefer = "mypreference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*find id*/
        et_user=findViewById(R.id.v_user);
        et_company=findViewById(R.id.v_company);
        et_address=findViewById(R.id.v_address);
        et_mobile=findViewById(R.id.v_phone);
        et_mail=findViewById(R.id.v_email);
        final TextView textView=findViewById(R.id.textemail);
        progressBar=findViewById(R.id.progressbar);
        vendorsave=findViewById(R.id.v_save);
        progressBar.setVisibility(View.GONE);

        //api calling
        apiInterface=ApiClient.getApiClient().create(ApiInterface.class);


et_user.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!isValidUser(et_user.getText().toString())) {
            et_user.setError("Enter charcters only");
        }

    }
});

et_company.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!isValidCompany(et_company.getText().toString())) {
            et_company.setError("Enter charcters only");
        }
    }
});
        et_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidNumber(et_mobile.getText().toString())) {
                    isnumbervalid = false;
                    //et_mobile.setTextColor(Color.parseColor("#008577"));
                    et_mobile.setError("Enter a 10 digit valid Number");
                } else {
                    isnumbervalid = true;
                    //et_mobile.setTextColor(Color.parseColor("#000000"));
                }

            }
        });

        et_mail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidEmail(et_mail.getText().toString())) {
                    et_mail.setError("Enter a valid address");
                }

            }
        });


        vendorsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    String vName=et_user.getText().toString();
                    String vcName=et_company.getText().toString();
                    String vAddress=et_address.getText().toString();
                    String vPhone=et_mobile.getText().toString();
                    final String vEmail=et_mail.getText().toString();


                     if(vcName.isEmpty()&&vAddress.isEmpty()&&vPhone.isEmpty()&&vEmail.isEmpty())
                {

                  Toasty.error(VendorActivity.this,"Please enter the details",Toast.LENGTH_SHORT).show();

                }
                    /*else if(vName.isEmpty()){
                        et_user.setError("Username mandatory");
                        et_user.requestFocus();
                    }*/

/*                    else if(vcName.isEmpty()){
                    et_company.setError("company Name mandatory");
                    et_company.requestFocus();
                }
                else if(vAddress.isEmpty()){
                    et_address.setError("company Address mandatory");
                    et_address.requestFocus();
                }
                else if(vPhone.length()<10||vPhone.length()>10){
                    et_mobile.setError("please enter 10 digit number ");
                    et_mobile.requestFocus();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(vEmail).matches()){
                    et_mail.setError("Check your Email Address");
                    et_mail.requestFocus();
                }*/

                else {
                        addVendor(vName, vcName, vAddress, vPhone, vEmail);
                    }

                }catch (Exception e){e.getStackTrace();}
//                startActivity(new Intent(VendorActivity.this,HomeActivity.class));
            }
        });
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

    private void addVendor(String vName, String vcName, String vAddress, String vPhone,String vEmail) throws IOException {
        Call<ResponseBody> call=apiInterface.addvendor(vName,vcName,vAddress,vPhone,vEmail);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toasty.success(VendorActivity.this, "Vendor successfully Added!!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.VISIBLE);
                    startActivity(new Intent(VendorActivity.this,Show_vendorDetails.class));
                    finish();
                }
                else{
                    Toasty.info(VendorActivity.this, "problem on retrofit call!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.VISIBLE);
                Toasty.info(VendorActivity.this, "Vendor not successfully Added!!", Toast.LENGTH_SHORT).show();
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
            startActivity(new Intent(VendorActivity.this,MainActivity.class));
            Toasty.info(getApplicationContext(),"working",Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }
}
