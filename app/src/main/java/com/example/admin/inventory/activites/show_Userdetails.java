package com.example.admin.inventory.activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;
import com.example.admin.inventory.R;
import com.example.admin.inventory.adapter.customerAdapter;
import com.example.admin.inventory.adapter.mClickListener;
import com.example.admin.inventory.model.Customers;
import com.example.admin.inventory.model.Vendors;
import com.example.admin.inventory.remote.ApiClient;
import com.example.admin.inventory.remote.ApiInterface;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class show_Userdetails extends AppCompatActivity implements SearchView.OnQueryTextListener,mClickListener {
    ApiInterface apiInterface;
    private RecyclerView recyclerView;
    FloatingActionButton fab;
//    private ShimmerFrameLayout shimmerFrameLayout;
    ProgressBar progressBar;
    private customerAdapter adapter;
    private List<Customers> customersList;
    private String customerId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__userdetails);
       /* shimmerFrameLayout=findViewById(R.id.shimmerview);
        shimmerFrameLayout.startShimmer();*/
       progressBar=findViewById(R.id.progressbar);
       progressBar.setMax(100);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        apiInterface=ApiClient.getApiClient().create(ApiInterface.class);
        init();
        loadCustomers();

         fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                startActivity(new Intent(show_Userdetails.this,addUser_Activity.class));
            }
        });
    }

    private void init() {
        recyclerView=findViewById(R.id.userrecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    private void loadCustomers() {
        Call<ArrayList<Customers>> call=apiInterface.getCustomerEntries();
        call.enqueue(new Callback<ArrayList<Customers>>() {
            @Override
            public void onResponse(Call<ArrayList<Customers>> call, Response<ArrayList<Customers>> response) {

                customersList=response.body();
                if(customersList.isEmpty()){
                    recyclerView.setVisibility(View.GONE);
//                    tvEmptyTextView.setVisibility(View.VISIBLE);
                }else{
//                    recyclerView.setVisibility(View.VISIBLE);
//                    tvEmptyTextView.setVisibility(View.GONE);
                }
                adapter=new customerAdapter(show_Userdetails.this, customersList, new mClickListener() {
                    @Override
                    public void onClick(final String id) {
                        customerId=id;
                        AlertDialog.Builder builder= new AlertDialog.Builder(show_Userdetails.this);
                        builder.setMessage("Do you want delete this customer?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteCutomer(id);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    }
                });
//                adapter.setMode(Attributes.Mode.Single);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        /*shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);*/
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                    }
                },500);


                recyclerView.setAdapter(adapter);
                recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                    }

                    @Override
                    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (dy>0&&fab.getVisibility()==View.VISIBLE)
                        {
                            fab.hide();
                        }
                        else if (dy<0&&fab.getVisibility()!= View.VISIBLE)
                        {
                            fab.show();
                        }
                    }
                });

            }

            @Override
            public void onFailure(Call<ArrayList<Customers>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteCutomer(String id) {
        Call<ResponseBody> call = apiInterface.deleteCustomers(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(show_Userdetails.this, "customer successfully removed!!!", Toast.LENGTH_SHORT).show();
                    loadCustomers();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(show_Userdetails.this, "customer not successfully removed", Toast.LENGTH_SHORT).show();

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

        String userInput=s.toLowerCase();
        List<Customers> newList=new ArrayList<>();
        for (Customers customers:customersList)
        {
            if (customers.getId().toLowerCase().contains(userInput)||customers.getUsername().toLowerCase().contains(userInput)||customers.getArea().toLowerCase().contains(userInput))
            {
                newList.add(customers);
            }
        }
        adapter.updateList(newList);
        return true;
    }

    @Override
    public void onClick(String id) {

    }
    @Override
    protected void onResume() {
        super.onResume();
//        shimmerFrameLayout.stopShimmer();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        shimmerFrameLayout.stopShimmer();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(show_Userdetails.this,HomeActivity.class));
    }
}
