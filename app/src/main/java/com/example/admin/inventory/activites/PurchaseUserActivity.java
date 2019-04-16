package com.example.admin.inventory.activites;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.inventory.R;
import com.example.admin.inventory.model.Customers;
import com.example.admin.inventory.model.Itemlist;
import com.example.admin.inventory.model.Vendors;
import com.example.admin.inventory.remote.ApiClient;
import com.example.admin.inventory.remote.ApiInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseUserActivity extends AppCompatActivity {
    EditText et_quantity, et_date, sales_amt, debit_amt, balance_amt;
    Button salessave, choose_item;
    TextView mItemSelected;
    ImageView doclick;
    //AutoCompleteTextView et_customername;
    Spinner item, et_customername;
    private List<Itemlist> spinneritems;
    List<String> lang = new ArrayList<String>();
    List<String> uId = new ArrayList<String>();

    private ArrayList<Customers> customersArrayList;
    List<String> cname = new ArrayList<String>();
    List<String> cId = new ArrayList<String>();
    //spinnerAdapter itemAdapter;
    DatePickerDialog datePickerDialog;
    ApiInterface apiInterface;
    int sub_id;
    Object cus_id;
    boolean[] checkedItems;
    ProgressBar progressBar;
    String c,i,s_quantity, dbtamount, salamount = "", date, salesamt, debitamt, balamt;
    SharedPreferences sharePreference;
    public static final String shared_prefer = "mypreference";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_customername = findViewById(R.id.cname);
        item = findViewById(R.id.item);
        // choose_item=findViewById(R.id.item);
        mItemSelected = findViewById(R.id.itemclick);
        et_quantity = findViewById(R.id.quantity);
        et_date = findViewById(R.id.date);
        sales_amt = findViewById(R.id.salesprice);
        debit_amt = findViewById(R.id.debit_amt);
        balance_amt = findViewById(R.id.balance_amt);
        progressBar = findViewById(R.id.progressbar);
        salessave = findViewById(R.id.salesentry);
        doclick = findViewById(R.id.doclickAdd);
        progressBar.setVisibility(View.GONE);

        //api calling
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);


        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(PurchaseUserActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        et_date.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        doclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PurchaseUserActivity.this, addUser_Activity.class));
            }
        });
/*
        checkedItems = new boolean[spinneritems.size()];

choose_item.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        AlertDialog.Builder mbuilder=new AlertDialog.Builder(PurchaseUserActivity.this);
mbuilder.setTitle("Choose items");
    }

});
*/

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

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /*vendor name load to spinner*/
        cname.add("Select Customer");
        cId.add("0");
        ArrayAdapter<String> customerAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, cname);
        customerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        /*et_customername.setThreshold(2);
        et_customername.setTextColor(Color.BLACK);*/
        et_customername.setAdapter(customerAdapter);
        et_customername.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cus_id = Integer.parseInt(cId.get(position));
//                Toast.makeText(getApplicationContext(), "item id is:" + cus_id, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "item id  error", Toast.LENGTH_SHORT).show();

            }
        });

        sales_amt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                salamount = sales_amt.getText().toString();
            }
        });

 /*       debit_amt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {


                    if ((Integer.valueOf(sales_amt.getText().toString())) < (Integer.valueOf(s.toString()))) {
//                        Toasty.warning(getApplicationContext(), "number is less than product amount", Toast.LENGTH_SHORT).show();
                        debit_amt.setError("Less than Sales Amount,Enter valid Amount");
                        debit_amt.requestFocus();
                        balance_amt.setText("");
                    }
                }catch (Exception e){e.printStackTrace();}
            }
        });*/

        debit_amt.addTextChangedListener(new TextWatcher() {
            int a,b;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    if ((Integer.valueOf(sales_amt.getText().toString())) < (Integer.valueOf(s.toString()))) {
//                        Toasty.warning(getApplicationContext(), "number is less than product amount", Toast.LENGTH_SHORT).show();
                        debit_amt.setError("Less than Sales Amount,Enter valid Amount");
                        debit_amt.requestFocus();
                        balance_amt.setText("");
                        salessave.setVisibility(View.INVISIBLE);
                    }

              else if (!s.toString().equals(""))
                {
                    a=0;
                    b=0;

                    b=Integer.parseInt(s.toString());
                    a=(Integer.parseInt(salamount)-b);
                    balance_amt.setText(String.valueOf(a));
                    salessave.setVisibility(View.VISIBLE);
                }else{
                    balance_amt.setText("");
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            }
        });


        loadJSON();
        loadCustmers();

        salessave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                 c = String.valueOf(cus_id);
                i = String.valueOf(sub_id);
                 s_quantity = et_quantity.getText().toString();
                date = et_date.getText().toString();
                salesamt = sales_amt.getText().toString();
                debitamt = debit_amt.getText().toString();
                //balance_amt.setText(String.valueOf(salesamt - debitamt));
                balamt = balance_amt.getText().toString();

                if (s_quantity.isEmpty()||date.isEmpty()||salesamt.isEmpty()||debitamt.isEmpty()||balamt.isEmpty())
                {
                    Toasty.error(getApplicationContext(),"Enter your details",Toast.LENGTH_SHORT).show();
                }else {
                    purchaseUser(c, i, s_quantity, date, salesamt, debitamt, balamt);
                }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void loadCustmers() {
        Call<ArrayList<Customers>> call = apiInterface.getCustomerEntries();
        call.enqueue(new Callback<ArrayList<Customers>>() {
            @Override
            public void onResponse(Call<ArrayList<Customers>> call, Response<ArrayList<Customers>> response) {
                try {
                    customersArrayList = response.body();
                    Log.d("customer response", "working");

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("customer response", "not working");
                }
                try {
                    int dz = customersArrayList.size();
                    Log.d("customer response", "data");
                    for (int x = 0; x < dz; x++) {
                        cname.add(customersArrayList.get(x).getUsername());
                        cId.add(customersArrayList.get(x).getId());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("c response", " no data");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Customers>> call, Throwable t) {
                t.printStackTrace();
                Log.d(" c response", "not connect");
            }
        });

    }

    private void loadJSON() {
        Call<ArrayList<Itemlist>> call = apiInterface.getItemList();
        call.enqueue(new Callback<ArrayList<Itemlist>>() {
            @Override
            public void onResponse(Call<ArrayList<Itemlist>> call, Response<ArrayList<Itemlist>> response) {
            /*int nsize=response.body().size();
                for(int i=1;i<nsize;i++){
                items=response.body().get(0).getItemName();
                lang.add(item);}

                itemAdapter=new spinnerAdapter(items,PurchaseEntry.this);
                item.setAdapter(itemAdapter);*/
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


    private void purchaseUser(String c, String i, String s_quantity, String date, String salesamt, String debitamt, String balanceamt) {
        Call<ResponseBody> call = apiInterface.salesentry(c, i, s_quantity, date, salesamt, debitamt, balanceamt);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {

                    Log.d("sales response", "response is" + response);
                    progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(PurchaseUserActivity.this, "Sales Entry Added!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PurchaseUserActivity.this, HomeActivity.class));
                    finish();
                } else {
                    Toast.makeText(PurchaseUserActivity.this, "problem on retrofit call!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(PurchaseUserActivity.this, "problem on retrofit call!!", Toast.LENGTH_SHORT).show();
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
            startActivity(new Intent(PurchaseUserActivity.this,MainActivity.class));
            Toasty.info(getApplicationContext(),"working",Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }
}

