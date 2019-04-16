package com.example.admin.inventory.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.inventory.R;
import com.example.admin.inventory.adapter.ParticularStockAdapter;
import com.example.admin.inventory.adapter.QuantityAdapter;
import com.example.admin.inventory.model.Itemlist;
import com.example.admin.inventory.model.PQuantity;
import com.example.admin.inventory.model.Quantity;
import com.example.admin.inventory.remote.ApiClient;
import com.example.admin.inventory.remote.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Total_quantity extends AppCompatActivity {
    TextView tv1;
    Button v;
    Spinner item;
    ImageView bt_add, bt_sub;
    ProgressBar progressBar;
    RecyclerView recyclerView, recyclerView2;
    private ArrayList<Itemlist> spinneritems;
    List<String> lang = new ArrayList<String>();
    List<String> uId = new ArrayList<String>();
    private ArrayList<Quantity> totalStock;
    private ArrayList<PQuantity> pStock;
    List<String> stock = new ArrayList<String>();
    QuantityAdapter quantityAdapter;
    ParticularStockAdapter particularStockAdapter;
    ApiInterface apiInterface;
    int sub_id;
    SharedPreferences sharePreference;
    public static final String shared_prefer = "mypreference";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_quantity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv1 = findViewById(R.id.total);
        item = findViewById(R.id.ven_name);
        v = findViewById(R.id.doclickAdd);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        //bt_sub=findViewById(R.id.subquantity1);
        bt_add = findViewById(R.id.addquantity2);

        bt_add.setVisibility(View.INVISIBLE);
        // bt_sub.setVisibility(View.INVISIBLE);


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
                Log.d("values", "work" + sub_id);
//                Toast.makeText(getApplicationContext(), "item id is:" + sub_id, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*bt_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Total_quantity.this,PurchaseUserActivity.class));
            }
        });*/
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Total_quantity.this, PurchaseEntry.class));
            }
        });

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i = String.valueOf(sub_id);
                particularQuantity(i);
            }
        });

        //Log.d("i value","working"+);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView2 = findViewById(R.id.precycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));


        quantity();
        loadJSON();


    }

    private void particularQuantity(String i) {
        Call<ArrayList<PQuantity>> call = apiInterface.getstock(i);
        call.enqueue(new Callback<ArrayList<PQuantity>>() {
            @Override
            public void onResponse(Call<ArrayList<PQuantity>> call, Response<ArrayList<PQuantity>> response) {
                try {
                    Log.d("paritcular response", "working fine" + response);
                    pStock = response.body();
//                        int id=response.body().
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("paritcular response", "not working fine");
                }
                try {
                    particularStockAdapter = new ParticularStockAdapter(Total_quantity.this, pStock);
                    Log.d("paritcular response", "working fine");
                    // bt_sub.setVisibility(View.VISIBLE);
                    bt_add.setVisibility(View.VISIBLE);
                    recyclerView2.setAdapter(particularStockAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ArrayList<PQuantity>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });
    }




    /*spinner get the values*/


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

    private void quantity() {

        Call<ArrayList<Quantity>> call = apiInterface.getTotalStock();
        call.enqueue(new Callback<ArrayList<Quantity>>() {
            @Override
            public void onResponse(Call<ArrayList<Quantity>> call, Response<ArrayList<Quantity>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                totalStock = response.body();
                quantityAdapter = new QuantityAdapter(Total_quantity.this, totalStock);
                recyclerView.setAdapter(quantityAdapter);
//                Toast.makeText(getApplicationContext(), "welcome", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ArrayList<Quantity>> call, Throwable t) {


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
            startActivity(new Intent(Total_quantity.this,MainActivity.class));
            Toasty.info(getApplicationContext(),"working",Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }
}
