package com.example.admin.inventory.activites;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class updatePurchaseEntry extends AppCompatActivity {
    EditText  et_quantity, et_date, et_price, et_debit, et_balance,et_hidebal;
    TextView id,item,vendor;
    Button purchasesave;
    DatePickerDialog datePickerDialog;
    ApiInterface apiInterface;
    ProgressBar progressBar;
    int sub_id,ven_id;
    String  dbtamount, prdamount = "";

    /*private ArrayList<Itemlist> spinneritems;
    private ArrayList<Vendors> vendorslist=new ArrayList<>();
    List<String> lang = new ArrayList<String>();
    List<String> uId = new ArrayList<String>();

    List<String> vname = new ArrayList<String>();
    List<String> vId = new ArrayList<String>();*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_purchase_entry);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id=findViewById(R.id.vid);
        vendor = findViewById(R.id.ven_name);
        et_quantity = findViewById(R.id.quanti);
        et_date = findViewById(R.id.date);
        et_price = findViewById(R.id.price);
        item = findViewById(R.id.item);
        et_debit = findViewById(R.id.debit_amt);
        et_balance = findViewById(R.id.balance_amt);
        et_hidebal=findViewById(R.id.hidebalance);
        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        purchasesave = findViewById(R.id.puchaseentry);

        //api calling
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        final String pid = getIntent().getStringExtra("v_id");
        id.setText(pid);
        String value=getIntent().getStringExtra("vendor name");
        vendor.setText(value);
        String product=getIntent().getStringExtra("item name");
        item.setText(product);
        String q=getIntent().getStringExtra("quantity");
        et_quantity.setText(q);
        String d=getIntent().getStringExtra("date");
        et_date.setText(d);
        String purchase=getIntent().getStringExtra("purchase amount");
        et_price.setText(purchase);
        String deposit=getIntent().getStringExtra("deposit amount");
//        et_debit.setText(deposit);
        String bal=getIntent().getStringExtra("balance amount");
        et_balance.setText(bal);
        final String ba=String.valueOf(bal);
        et_hidebal.setText(bal);



        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(updatePurchaseEntry.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        et_date.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
et_debit.addTextChangedListener(new TextWatcher() {
     int a,b;
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        try{
        if ((Integer.valueOf(ba)) < (Integer.valueOf(s.toString()))) {
//                        Toasty.warning(getApplicationContext(), "number is less than product amount", Toast.LENGTH_SHORT).show();
            et_debit.setError("less than Balance Amount,Enter Valid Amount");
            et_debit.requestFocus();
            et_balance.setText("");
            purchasesave.setVisibility(View.INVISIBLE);

        }
       else if (!s.toString().equals(""))
        {
            a=0;
            b=0;
            b=Integer.parseInt(s.toString());
            a=(Integer.parseInt(ba)-b);
            et_balance.setText(String.valueOf(a));
            purchasesave.setVisibility(View.VISIBLE);

        }else {
            et_balance.setText("");
        }
        }catch (Exception e){e.printStackTrace();}

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
});

/*        et_price.addTextChangedListener(new TextWatcher() {
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
        });*/
        purchasesave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=pid;
                /*String i = item.getText().toString();
                String v_name = vendor.getText().toString();*/
                int quans = Integer.parseInt(et_quantity.getText().toString());
                String dt = et_date.getText().toString();
                int payamt = Integer.parseInt(et_price.getText().toString());
                int deamt = Integer.parseInt(et_debit.getText().toString());
                et_balance.setText(String.valueOf(payamt - deamt));

                int balamt = Integer.parseInt(et_balance.getText().toString());
                //final int c=payamt-deamt;
                //Toast.makeText(getApplicationContext(),"amount"+c,Toast.LENGTH_SHORT).show();
try {

    updatepurchase(id,quans, dt, payamt, deamt, balamt);
    Log.d("update reponse","checked");

}catch (Exception e){e.printStackTrace();}       }
        });


     /*   loadJSON();
        loadJSONVendors();*/

    }

    private void updatepurchase(String id,int quans, String dt, int payamt, int deamt, int balamt) {

        Call<ResponseBody> call=apiInterface.updatePurchaseentry(id,quans,dt,payamt,deamt,balamt);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("retrofit response","update purchase"+response);
                    if (response.isSuccessful()) {
                        progressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(updatePurchaseEntry.this, "Updated successfully!!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(updatePurchaseEntry.this, Show_vendorDetails.class));
                        finish();
                    }
                }catch (Exception e){e.printStackTrace();}
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }



}
