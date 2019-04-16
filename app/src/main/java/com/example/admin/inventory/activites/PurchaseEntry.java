package com.example.admin.inventory.activites;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.inventory.R;
import com.example.admin.inventory.model.Itemlist;
import com.example.admin.inventory.model.Vendors;
import com.example.admin.inventory.remote.ApiClient;
import com.example.admin.inventory.remote.ApiInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseEntry extends AppCompatActivity implements TextWatcher {
    EditText et_productname, et_quantity, et_date, et_price, et_debit, et_balance;
    Spinner item, vendor;
    ProgressBar progressBar;
    TextView tv1;

    private ArrayList<Itemlist> spinneritems;
    private ArrayList<Vendors> vendorslist = new ArrayList<>();
    List<String> lang = new ArrayList<String>();
    List<String> uId = new ArrayList<String>();

    List<String> vname = new ArrayList<String>();
    List<String> vId = new ArrayList<String>();
    //spinnerAdapter itemAdapter;
    Button purchasesave;
    DatePickerDialog datePickerDialog;
    ApiInterface apiInterface;
    String p_name, i, v_name, dt, quans, dbtamount, prdamount = "", payamt, deamt, balamt;
    int sub_id, ven_id;
    SharedPreferences sharePreference;
    public static final String shared_prefer = "mypreference";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_entry);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_productname = findViewById(R.id.productname);
        vendor = findViewById(R.id.ven_name);
        et_quantity = findViewById(R.id.quanti);
        et_date = findViewById(R.id.date);
        et_price = findViewById(R.id.price);
        item = findViewById(R.id.item);
        et_debit = findViewById(R.id.debit_amt);
        et_balance = findViewById(R.id.balance_amt);
        purchasesave = findViewById(R.id.puchaseentry);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        tv1 = findViewById(R.id.textView);
        dbtamount = et_debit.getText().toString();

        //List<String> list = new ArrayList<String>();


        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(PurchaseEntry.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        et_date.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
//                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            }
        });


        //api calling
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);


        /*spinner values get */
        lang.add("Select Items");
        uId.add("0");

        ArrayAdapter<String> langAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, lang);
        langAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        item.setAdapter(langAdapter);
        item.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String selectlang = item.getSelectedItem().toString();
                sub_id = Integer.parseInt(uId.get(position));
                Toast.makeText(getApplicationContext(), "item id is:" + sub_id, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /*vendor name load to spinner*/

        vname.add("Select Vendors");
        vId.add("0");
        ArrayAdapter<String> vendorAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, vname);
        vendorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vendor.setAdapter(vendorAdapter);
        vendor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ven_id = Integer.parseInt(vId.get(position));
                Toast.makeText(getApplicationContext(), "item id is:" + ven_id, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "item id  error", Toast.LENGTH_SHORT).show();
            }
        });
        loadJSON();
        loadJSONVendors();

        et_productname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isValidUser(et_productname.getText().toString())) {
                    et_productname.setError("Enter a text only");

                }
            }
        });

        et_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                prdamount = et_price.getText().toString();
            }
        });

        et_debit.addTextChangedListener(new TextWatcher() {
            int a, b;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {


                    if ((Integer.valueOf(et_price.getText().toString())) < (Integer.valueOf(s.toString()))) {
//                        Toasty.warning(getApplicationContext(), "number is less than product amount", Toast.LENGTH_SHORT).show();
                        et_debit.setError("less than Product Amount,Enter Valid Amount");
                        et_debit.requestFocus();
                        et_balance.setText("");
                        purchasesave.setVisibility(View.INVISIBLE);

                    }
                    else if (!s.toString().equals(""))
                    {
                        a = 0;
                        b = 0;
                        //    b=Integer.parseInt(et_price.getText().toString());
                        b = Integer.parseInt(s.toString());
                        a = (Integer.parseInt(prdamount) - b);
                        et_balance.setText(String.valueOf(a));
                        purchasesave.setVisibility(View.VISIBLE);
                    } else {
                        et_balance.setText("");

                    }
                }catch (Exception e){e.printStackTrace();}
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

/*        et_debit.addTextChangedListener(new TextWatcher() {
            int a, b;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //wait am
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (!s.toString().equals("")) {
                        a = 0;
                        b = 0;
                        //    b=Integer.parseInt(et_price.getText().toString());
                        b = Integer.parseInt(s.toString());
                        a = (Integer.parseInt(prdamount) - b);
                        et_balance.setText(String.valueOf(a));
                    } else {
                        et_balance.setText("");
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
//Now its working okkk thanks na..oru doubt product amount 200 debit 500 enter pana error through pananum
                // u should validate first debit amount should not greater than product amount ok
                //did u download the source code of this project no,here lot of activity is there thats y am asking good,keepitup ,bro lot of activty code panalama, ila simplified pananuma
                //it depends on project if there is need means we cant go,if u have weightage problem go with fragment
                //ok bro,ok


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });*/
        purchasesave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    p_name = et_productname.getText().toString();
                    i = String.valueOf(sub_id);
                    v_name = String.valueOf(ven_id);
                    quans = et_quantity.getText().toString();
                    dt = et_date.getText().toString();
                    payamt = et_price.getText().toString();
                    deamt = et_debit.getText().toString();
                    //et_balance.setText(String.valueOf(payamt - deamt));

                    balamt = et_balance.getText().toString();

                    if (p_name.isEmpty() || quans.isEmpty() || dt.isEmpty() || payamt.isEmpty() || deamt.isEmpty() || balamt.isEmpty()) {
                        Toasty.error(getApplicationContext(), "Please enter the details", Toast.LENGTH_SHORT).show();
                    } else {
                        addPurchase(p_name, i, v_name, quans, dt, payamt, deamt, balamt);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private boolean isValidUser(String p_name) {
        if (p_name == null) {
            return false;
        } else {
            String USER_PATTERN = "^[a-zA-Z ]+$";
            Pattern pattern = Pattern.compile(USER_PATTERN);
            Matcher matcher = pattern.matcher(p_name);
            return matcher.matches();
        }
    }


    private void loadJSONVendors() {
        Call<ArrayList<Vendors>> call = apiInterface.getUsers();
        call.enqueue(new Callback<ArrayList<Vendors>>() {
            @Override
            public void onResponse(Call<ArrayList<Vendors>> call, Response<ArrayList<Vendors>> response) {
                try {
                    vendorslist = response.body();
                    Log.d("vendor response", "working");

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("vendor response", "not working");
                }
                try {
                    int dy = vendorslist.size();
                    Log.d("vendor response", "data");
                    for (int y = 0; y < dy; y++) {
                        vname.add(vendorslist.get(y).getVName());
                        vId.add(vendorslist.get(y).getId());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("vendor response", " no data");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Vendors>> call, Throwable t) {
                t.printStackTrace();
                Log.d("response", "not connect");
            }
        });

    }

    /*spinner get the values*/
    private void loadJSON() {
        Call<ArrayList<Itemlist>> call = apiInterface.getItemList();
        call.enqueue(new Callback<ArrayList<Itemlist>>() {
            @Override
            public void onResponse(Call<ArrayList<Itemlist>> call, Response<ArrayList<Itemlist>> response) {
                try {
                    spinneritems = response.body();
                    Log.d("response", "working");
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("response", "not working");
                }
                try {
                    int tz = spinneritems.size();
                    Log.d("response", "data");
                    for (int y = 0; y < tz; y++) {
                        lang.add(spinneritems.get(y).getItemName());
                        uId.add(spinneritems.get(y).getId());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("response", "nodata");
                }


            }

            @Override
            public void onFailure(Call<ArrayList<Itemlist>> call, Throwable t) {
                t.printStackTrace();
                Log.d("response", "not connect");

            }
        });

    }

    private void addPurchase(String p_name, String i, String v_name, String quans, String dt, String payamt, String deamt, String balamt) {

        Call<ResponseBody> call = apiInterface.purchaseentry(p_name, i, v_name, quans, dt, payamt, deamt, balamt);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(PurchaseEntry.this, "Entry successfully Added!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PurchaseEntry.this, HomeActivity.class));
                    finish();
                } else {
                    Toast.makeText(PurchaseEntry.this, "problem on retrofit call!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(PurchaseEntry.this, "Entry successfully Added!!", Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }

    @Override
    public void afterTextChanged(Editable s) {



     /*   if (s.toString().isEmpty()) {
            PurchaseEntry.this.et_balance.setText("-");
        }
        *//*else if (PurchaseEntry.this.et_debit.getText().toString().isEmpty()) {
            PurchaseEntry.this.et_debit.setText("-");
        }*//*
        else {
            Double valueOf = Double.valueOf(Double.parseDouble(PurchaseEntry.this.et_debit.getText().toString()));
            Double s1 = Double.valueOf(Double.parseDouble(s.toString()));
//            String access$200 = PurchaseActivity.this.getCalculationRate();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("per ");
            stringBuilder.append(PurchaseEntry.this.getUnit());
            if (access$200.equals(stringBuilder.toString())) {
                editable = Double.valueOf(.doubleValue() * valueOf.doubleValue());
            } else if (PurchaseActivity.this.getCalculationRate().equals("%")) {
                editable = Double.valueOf((editable.doubleValue() * valueOf.doubleValue()) / 100.0d);
            } else {
                editable = Double.valueOf((editable.doubleValue() * valueOf.doubleValue()) / Double.valueOf(Double.parseDouble(PurchaseActivity.this.getCalculationQuantity())).doubleValue());
            }
            PurchaseActivity.this.totalCostText.setText(String.format("%.2f", new Object[]{editable}));
        }*/


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
            startActivity(new Intent(PurchaseEntry.this,MainActivity.class));
            Toasty.info(getApplicationContext(),"working",Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }
}
