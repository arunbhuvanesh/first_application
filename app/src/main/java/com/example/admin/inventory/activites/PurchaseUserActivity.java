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
import com.example.admin.inventory.remote.ApiClient;
import com.example.admin.inventory.remote.ApiInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseUserActivity extends AppCompatActivity {
    EditText et_customername,et_quantity,et_date,sales_amt,debit_amt,balance_amt;
    Button salessave;
    Spinner item;
    private List<Itemlist> spinneritems;
    List<String> lang = new ArrayList<String>();
    List<String> uId = new ArrayList<String>();
    //spinnerAdapter itemAdapter;
    DatePickerDialog datePickerDialog;
    ApiInterface apiInterface;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_user);
        et_customername=findViewById(R.id.cname);
        item=findViewById(R.id.item);
        et_quantity=findViewById(R.id.quantity);
        et_date=findViewById(R.id.date);
        sales_amt=findViewById(R.id.salesprice);
        debit_amt=findViewById(R.id.debit_amt);
        balance_amt=findViewById(R.id.balance_amt);
        salessave=findViewById(R.id.salesentry);

        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar=Calendar.getInstance();
                final  int year=calendar.get(Calendar.YEAR);
                final int month=calendar.get(Calendar.MONTH);
                final int day=calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(PurchaseUserActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        et_date.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        //api calling
        apiInterface=ApiClient.getApiClient().create(ApiInterface.class);

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
                Toast.makeText(getApplicationContext(),"item id is:"+sub_id,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        loadJSON();

        salessave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cname=et_customername.getText().toString();
                String i=item.getSelectedItem().toString();
                int s_quantity=Integer.parseInt(et_quantity.getText().toString());
                String date=et_date.getText().toString();
                int salesamt=Integer.parseInt(sales_amt.getText().toString());
                int debitamt=Integer.parseInt(debit_amt.getText().toString());
                balance_amt.setText(String.valueOf(salesamt-debitamt));

                int balamt=Integer.parseInt(balance_amt.getText().toString());


                purchaseUser(cname,i,s_quantity,date,salesamt,debitamt,balamt);
            }
        });



    }

    private void loadJSON() {
        Call<ArrayList<Itemlist>> call=apiInterface.getItemList();
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
                    Log.d("response","working");
                }catch (Exception e){e.printStackTrace();
                    Log.d("response","not working");}
                try {
                    int tz = spinneritems.size();
                    Log.d("response","data");
                    for(int y=0;y<tz;y++) {
                        lang.add(spinneritems.get(y).getItemName());
                        uId.add(spinneritems.get(y).getId());

                    }}catch(Exception e){e.printStackTrace();
                    Log.d("response","nodata");}



            }

            @Override
            public void onFailure(Call<ArrayList<Itemlist>> call, Throwable t) {
                t.printStackTrace();
                Log.d("response","not connect");

            }
        });

    }


    private void purchaseUser(String cname, String i, int s_quantity, String date, int salesamt, int debitamt, int balanceamt) {
        Call<ResponseBody> call=apiInterface.salesentry(cname,i,s_quantity,date,salesamt,debitamt,balanceamt);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {


                    Toast.makeText(PurchaseUserActivity.this, "Sales Entry Added!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PurchaseUserActivity.this,HomeActivity.class));
                }
                else{
                    Toast.makeText(PurchaseUserActivity.this, "problem on retrofit call!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(PurchaseUserActivity.this, "problem on retrofit call!!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

