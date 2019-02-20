package com.example.admin.inventory.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;
import android.support.v7.widget.SearchView;

import com.daimajia.swipe.util.Attributes;
import com.example.admin.inventory.R;
import com.example.admin.inventory.adapter.mClickListener;
import com.example.admin.inventory.adapter.vendorAdapter;
import com.example.admin.inventory.model.Vendors;
import com.example.admin.inventory.remote.ApiClient;
import com.example.admin.inventory.remote.ApiInterface;
import com.facebook.shimmer.ShimmerFrameLayout;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Show_vendorDetails extends AppCompatActivity implements mClickListener, SearchView.OnQueryTextListener {
    private RecyclerView recyclerView;
    private ShimmerFrameLayout shimmerFrameLayout;
    private vendorAdapter adapter;
    ApiInterface apiInterface;
    private List<Vendors> vendorsList;
    private String vendorId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_vendor_details);
        shimmerFrameLayout=findViewById(R.id.shimmerview);


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getVendors();
        initView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                startActivity(new Intent(Show_vendorDetails.this, VendorActivity.class));
            }
        });
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
/*        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);*/
    }

    private void getVendors() {
        Call<List<Vendors>> call = apiInterface.getUsers();
        call.enqueue(new Callback<List<Vendors>>() {
            @Override
            public void onResponse(Call<List<Vendors>> call, Response<List<Vendors>> response) {
                vendorsList = response.body();
                Collections.sort(vendorsList, Vendors.BY_ALPHABETICAL);
                adapter = new vendorAdapter(Show_vendorDetails.this, vendorsList, new mClickListener() {
                    @Override
                    public void onClick(final String id) {
                        vendorId = id;
                        AlertDialog.Builder builder = new AlertDialog.Builder(Show_vendorDetails.this);
                        builder.setMessage("Do you want delete this vendor?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deletevendor(id);
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
                adapter.setMode(Attributes.Mode.Single);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(adapter);
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                    }
                });

            }

            @Override
            public void onFailure(Call<List<Vendors>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deletevendor(String id) {
        Call<ResponseBody> call = apiInterface.deleteVendor(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Show_vendorDetails.this, "vendor successfully removed!!!", Toast.LENGTH_SHORT).show();
                    getVendors();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Show_vendorDetails.this, "vendor not successfully removed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        /*SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        search(searchView);*/
        return true;
    }
/*

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }
*/

    @Override
    public void onClick(String id) {

    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        String userInput=s.toLowerCase();
        List<Vendors> newList=new ArrayList<>();
        for (Vendors vendors:vendorsList)
        {
            if (vendors.getVName().toLowerCase().contains(userInput)||vendors.getVCompany().toLowerCase().contains(userInput)||vendors.getVAddress().toLowerCase().contains(userInput)||vendors.getVPhone().toLowerCase().contains(userInput))
            {
                newList.add(vendors);
            }
        }
        adapter.updateList(newList);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmer();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Show_vendorDetails.this,HomeActivity.class));
    }
}
