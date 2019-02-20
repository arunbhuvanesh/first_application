package com.example.admin.inventory.activites;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.inventory.R;
import com.example.admin.inventory.model.Itemlist;
import com.example.admin.inventory.model.Vendors;
import com.example.admin.inventory.remote.ApiClient;
import com.example.admin.inventory.remote.ApiInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseEntry extends AppCompatActivity {
    EditText et_productname, et_quantity, et_date, et_price, et_debit, et_balance;
    Spinner item, vendor;
    private ArrayList<Itemlist> spinneritems;
    //private ArrayList<Vendors> vendorslist;
    List<String> lang = new ArrayList<String>();
    List<String> uId = new ArrayList<String>();

//    List<String> vname = new ArrayList<String>();
//    List<String> vId = new ArrayList<String>();
    //spinnerAdapter itemAdapter;
    Button purchasesave;
    DatePickerDialog datePickerDialog;
    ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_entry);
        et_productname = findViewById(R.id.productname);
        vendor = findViewById(R.id.ven_name);
        et_quantity = findViewById(R.id.quanti);
        et_date = findViewById(R.id.date);
        et_price = findViewById(R.id.price);
        item = findViewById(R.id.item);
        et_debit = findViewById(R.id.debit_amt);
        et_balance = findViewById(R.id.balance_amt);
        purchasesave = findViewById(R.id.puchaseentry);
        //List<String> list = new ArrayList<String>();

/*        final Calendar calendar=Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel();
            }
        };
        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(PurchaseEntry.this,date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });*/

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
                        et_date.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, year, month, day);
                datePickerDialog.show();
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
                int sub_id = Integer.parseInt(uId.get(position));
                Toast.makeText(getApplicationContext(), "item id is:" + sub_id, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

/*

        */
/*vendor name load to spinner*//*

        vname.add("Select Vendors");
        vId.add("0");
        ArrayAdapter<String> vendorAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, vname);
        vendorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vendor.setAdapter(vendorAdapter);
        vendor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int ven_id=Integer.parseInt(vId.get(position));
                Toast.makeText(getApplicationContext(), "item id is:" + ven_id, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "item id  error", Toast.LENGTH_SHORT).show();

            }
        });
*/

        loadJSON();
        /*loadVendors();*/

        purchasesave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p_name = et_productname.getText().toString();
                String i = item.getSelectedItem().toString();
                String v_name = vendor.getSelectedItem().toString();
                int quans = Integer.parseInt(et_quantity.getText().toString());
                String dt = et_date.getText().toString();
                int payamt = Integer.parseInt(et_price.getText().toString());
                int deamt = Integer.parseInt(et_debit.getText().toString());
                et_balance.setText(String.valueOf(payamt - deamt));

                int balamt = Integer.parseInt(et_balance.getText().toString());
                //final int c=payamt-deamt;
                //Toast.makeText(getApplicationContext(),"amount"+c,Toast.LENGTH_SHORT).show();


                addPurchase(p_name, i, v_name, quans, dt, payamt, deamt, balamt);
            }
        });
    }

    /*private void loadVendors() {
    }*/
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


    private void addPurchase(String p_name, String i, String v_name, int quans, String dt, int payamt, int deamt, int balamt) {

        Call<ResponseBody> call = apiInterface.purchaseentry(p_name, i, v_name, quans, dt, payamt, deamt, balamt);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {


                    Toast.makeText(PurchaseEntry.this, "Entry successfully Added!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PurchaseEntry.this, HomeActivity.class));
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


}
