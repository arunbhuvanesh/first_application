package com.example.admin.inventory.activites;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.inventory.R;
import com.example.admin.inventory.adapter.OutstandingamountAdapter;
import com.example.admin.inventory.adapter.SpecificCustomerAdapter;
import com.example.admin.inventory.adapter.SpecificVendorAdapter;
import com.example.admin.inventory.adapter.mClickListener;
import com.example.admin.inventory.model.OutstandingAmt;
import com.example.admin.inventory.model.ParticularCustomer;
import com.example.admin.inventory.model.ParticularVendor;
import com.example.admin.inventory.remote.ApiClient;
import com.example.admin.inventory.remote.ApiInterface;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Particular_customer_list extends AppCompatActivity implements mClickListener, SearchView.OnQueryTextListener {
    private RecyclerView recyclerView, recyclerView2;
    private TextView id;
    ProgressBar progressBar, progressBar2;
    RelativeLayout relativeLayout;
    EditText date, amount;
    TextView tv, hdate, hamt;
    Button deposit;


    SharedPreferences sharePreference;
    public static final String shared_prefer = "mypreference";
    public static final String out_key = "outstandingkey";
    private ShimmerFrameLayout shimmerFrameLayout;
    ApiInterface apiInterface;
    private SpecificCustomerAdapter specificCustomerAdapter;
    private OutstandingamountAdapter outstandingamountAdapter;
    private ArrayList<OutstandingAmt> outamt;
    private ArrayList<ParticularCustomer> scustomer;
    private String pId = "", i;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_customer_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       /* shimmerFrameLayout = findViewById(R.id.shimmerview);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);*/
        relativeLayout = findViewById(R.id.relative2);
        progressBar2 = findViewById(R.id.progressbar1);
        progressBar = findViewById(R.id.progressbar);


        id = findViewById(R.id.id);
        date = findViewById(R.id.date);
        hdate = findViewById(R.id.hdate);
        amount = findViewById(R.id.amount);
        hamt = findViewById(R.id.hamt);
        deposit = findViewById(R.id.deposit);
        tv = findViewById(R.id.tvout);
        /*String j=getIntent().getStringExtra("outstanding");
        tv.setText(j);*/
        sharePreference = getSharedPreferences(shared_prefer, Context.MODE_PRIVATE);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        i = getIntent().getStringExtra("customer id");
        Log.d("delete", "success" + i);
        id.setText(i);
        tv.setText(i);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(Particular_customer_list.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hdate.setText(date.getText().toString());
            }
        });

        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hamt.setText(amount.getText().toString());
            }
        });

        getParitular_customer(i);
        getoutstanding_amount(i);
        initView();


        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String d = date.getText().toString();
                    String am = amount.getText().toString();
                    String outid = tv.getText().toString();
                    if (d.isEmpty() || am.isEmpty()) {
                        Toasty.error(Particular_customer_list.this, "Enter the details!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        outcalculation(outid, d, am);
                        progressBar.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private void outcalculation(String outid, String d, String am) {
        Call<ResponseBody> call = apiInterface.outstanding_amount_cal(outid, d, am);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    getParitular_customer(i);
                    getoutstanding_amount(i);
                    Toasty.success(getApplicationContext(), "outstanding amount successfully deposited", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toasty.success(getApplicationContext(), "outstanding amount not successfully deposited", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getoutstanding_amount(String i) {
        Call<ArrayList<OutstandingAmt>> call = apiInterface.getoutstandingamount(i);
        call.enqueue(new Callback<ArrayList<OutstandingAmt>>() {
            @Override
            public void onResponse(Call<ArrayList<OutstandingAmt>> call, Response<ArrayList<OutstandingAmt>> response) {

                outamt = response.body();

                outstandingamountAdapter = new OutstandingamountAdapter(Particular_customer_list.this, outamt);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        /*shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);*/
                        recyclerView2.setVisibility(View.VISIBLE);
                        progressBar2.setVisibility(View.GONE);
                        date.setVisibility(View.VISIBLE);
                        relativeLayout.setVisibility(View.VISIBLE);


                    }
                }, 500);
                recyclerView2.setAdapter(outstandingamountAdapter);

            }

            @Override
            public void onFailure(Call<ArrayList<OutstandingAmt>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "problem on internet call", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        recyclerView = findViewById(R.id.srecyclerview);
        recyclerView2 = findViewById(R.id.outamtrecyclerview);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getParitular_customer(String i) {
        Call<ArrayList<ParticularCustomer>> call = apiInterface.getParticularCustomer(i);
        call.enqueue(new Callback<ArrayList<ParticularCustomer>>() {
            @Override
            public void onResponse(Call<ArrayList<ParticularCustomer>> call, Response<ArrayList<ParticularCustomer>> response) {
                scustomer = response.body();
                specificCustomerAdapter = new SpecificCustomerAdapter(Particular_customer_list.this, scustomer, new mClickListener() {
                    @Override
                    public void onClick(final String id) {
                        pId = id;
                        AlertDialog.Builder builder = new AlertDialog.Builder(Particular_customer_list.this);
                        builder.setMessage("Do you want delete this vendor?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteParticularcustomer(id);
                            }
                        })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }

                });
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        /*shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);*/
                        progressBar2.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                    }
                }, 500);
                recyclerView.setAdapter(specificCustomerAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<ParticularCustomer>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "problem on internet call", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void deleteParticularcustomer(final String id) {
        Call<ResponseBody> call = apiInterface.deletePcustomer(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toasty.info(Particular_customer_list.this, "Customer successfully removed!!!", Toast.LENGTH_SHORT).show();
                    getParitular_customer(i);
                    getoutstanding_amount(i);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Particular_customer_list.this, "Customer not successfully removed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String userInput = s.toLowerCase();
        ArrayList<ParticularCustomer> newList = new ArrayList<>();
        for (ParticularCustomer c : scustomer) {
            if (c.getUsername().toLowerCase().contains(userInput) || c.getDate().toLowerCase().contains(userInput) || c.getItemName().toLowerCase().contains(userInput)) {
                newList.add(c);
            }
        }
        specificCustomerAdapter.updateList(newList);
        return true;
    }

    @Override
    public void onClick(String id) {

    }
}
