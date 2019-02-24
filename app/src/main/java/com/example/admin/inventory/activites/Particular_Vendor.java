package com.example.admin.inventory.activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.admin.inventory.R;
import com.example.admin.inventory.adapter.SpecificVendorAdapter;
import com.example.admin.inventory.model.ParticularVendor;
import com.example.admin.inventory.model.Vendors;
import com.example.admin.inventory.remote.ApiClient;
import com.example.admin.inventory.remote.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Particular_Vendor extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private RecyclerView recyclerView;
    private TextView id;
    private SpecificVendorAdapter specificVendorAdapter;
    ApiInterface apiInterface;
    private ArrayList<ParticularVendor> svendor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular__vendor);
        id=findViewById(R.id.id);

        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);

        String i = getIntent().getStringExtra("vendor id");
        id.setText(i);





        getParitular_vendor(i);
        initView();
    }

    private void getParitular_vendor(String i) {
        Call<ArrayList<ParticularVendor>> call=apiInterface.getParticularVendor(i);
        call.enqueue(new Callback<ArrayList<ParticularVendor>>() {
            @Override
            public void onResponse(Call<ArrayList<ParticularVendor>> call, Response<ArrayList<ParticularVendor>> response) {
                svendor=response.body();
                specificVendorAdapter=new SpecificVendorAdapter(Particular_Vendor.this,svendor);
                recyclerView.setAdapter(specificVendorAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<ParticularVendor>> call, Throwable t) {

            }
        });
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        String userInput=s.toLowerCase();
        ArrayList<ParticularVendor> newList=new ArrayList<>();
        for (ParticularVendor p:svendor)
        {
            if (p.getVName().toLowerCase().contains(userInput)||p.getItemName().toLowerCase().contains(userInput)||p.getQuantity().toLowerCase().contains(userInput)||p.getDate().toLowerCase().contains(userInput)||p.getPAmt().toLowerCase().contains(userInput)||p.getDAmt().toLowerCase().contains(userInput)||p.getBAmt().toLowerCase().contains(userInput))
            {
                newList.add(p);
            }
        }
        specificVendorAdapter.updateList(newList);
        return true;
    }
}
