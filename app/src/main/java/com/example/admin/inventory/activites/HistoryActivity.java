package com.example.admin.inventory.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.inventory.R;
import com.example.admin.inventory.adapter.SpecificHistoryAdapter;
import com.example.admin.inventory.model.Customers;
import com.example.admin.inventory.model.OutstandingHistory;
import com.example.admin.inventory.remote.ApiClient;
import com.example.admin.inventory.remote.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {
    Spinner et_customername;
    Button v;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    private SpecificHistoryAdapter adapter;
    private ArrayList<Customers> customersArrayList;
    private ArrayList<OutstandingHistory> outstandingHistories;
    List<String> cname = new ArrayList<String>();
    List<String> cId = new ArrayList<String>();
    ApiInterface apiInterface;
    int cus_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        et_customername = findViewById(R.id.cname);
        v = findViewById(R.id.doclickAdd);
        progressBar=findViewById(R.id.progress);


        //api calling
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

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
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i = String.valueOf(cus_id);
                loadOutamtHistory(i);
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        init();
        loadCustmers();

    }

    private void loadOutamtHistory(String i) {
        Call<ArrayList<OutstandingHistory>> call = apiInterface.getuserHistory(i);
        call.enqueue(new Callback<ArrayList<OutstandingHistory>>() {
            @Override
            public void onResponse(Call<ArrayList<OutstandingHistory>> call, Response<ArrayList<OutstandingHistory>> response) {
                outstandingHistories=response.body();
                adapter=new SpecificHistoryAdapter(HistoryActivity.this,outstandingHistories);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ArrayList<OutstandingHistory>> call, Throwable t) {

            }
        });
    }

    private void init() {
        recyclerView = findViewById(R.id.precycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
}
