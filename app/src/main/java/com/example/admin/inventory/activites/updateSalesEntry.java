package com.example.admin.inventory.activites;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.inventory.R;
import com.example.admin.inventory.remote.ApiClient;
import com.example.admin.inventory.remote.ApiInterface;

import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class updateSalesEntry extends AppCompatActivity {
    EditText et_quantity, et_date, sales_amt, debit_amt, balance_amt,et_hidebal;
    Button salessave;
    TextView item,et_customername,cid;
    DatePickerDialog datePickerDialog;
    ProgressBar progressBar;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sales_entry);
        et_customername = findViewById(R.id.cname);
        cid=findViewById(R.id.cid);
        item = findViewById(R.id.item);
        et_quantity = findViewById(R.id.quantity);
        et_date = findViewById(R.id.date);
        sales_amt = findViewById(R.id.salesprice);
        debit_amt = findViewById(R.id.debit_amt);
        balance_amt = findViewById(R.id.balance_amt);
        salessave = findViewById(R.id.salesentry);
        et_hidebal=findViewById(R.id.hidebalance);

        progressBar=findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        //api calling
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);


        final String pid = getIntent().getStringExtra("c_id");
        cid.setText(pid);
        String value=getIntent().getStringExtra("customer name");
        et_customername.setText(value);
        String product=getIntent().getStringExtra("item name");
        item.setText(product);
        String q=getIntent().getStringExtra("quantity");
        et_quantity.setText(q);
        String d=getIntent().getStringExtra("date");
        et_date.setText(d);
        String sal=getIntent().getStringExtra("sales amount");
        sales_amt.setText(sal);
        String deposit=getIntent().getStringExtra("deposit amount");
//        debit_amt.setText(deposit);
        final String bal=getIntent().getStringExtra("balance amount");
        balance_amt.setText(bal);
        final String ba=String.valueOf(bal);
        et_hidebal.setText(bal);


        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(updateSalesEntry.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        et_date.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        debit_amt.addTextChangedListener(new TextWatcher() {
            int a,b;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    if ((Integer.valueOf(ba)) < (Integer.valueOf(s.toString()))) {
//                        Toasty.warning(getApplicationContext(), "number is less than product amount", Toast.LENGTH_SHORT).show();
                        debit_amt.setError("less than Balance Amount,Enter Valid Amount");
                        debit_amt.requestFocus();
                        balance_amt.setText("");
                        salessave.setVisibility(View.INVISIBLE);

                    }
                    else if (!s.toString().equals(""))
                    {
                        a=0;
                        b=0;
                        b=Integer.parseInt(s.toString());
                        a=(Integer.parseInt(ba)-b);
                        balance_amt.setText(String.valueOf(a));
                        salessave.setVisibility(View.VISIBLE);

                    }else if (s.toString().equals(""))
                    {
                        balance_amt.setText(bal);
                    }

                    else {
                        balance_amt.setText("");
                    }
                }catch (Exception e){e.printStackTrace();}

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        salessave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String id=pid;
                int s_quantity = Integer.parseInt(et_quantity.getText().toString());
                String date = et_date.getText().toString();
                String salesamt = sales_amt.getText().toString();
                String debitamt = debit_amt.getText().toString();
//                balance_amt.setText(String.valueOf(salesamt - debitamt));

                String balamt = balance_amt.getText().toString();


                updateSales(id,s_quantity, date, salesamt, debitamt, balamt);

            }
        });
    }

    private void updateSales(String id, int s_quantity, String date, String salesamt, String debitamt, String balamt) {

       Call<ResponseBody> call=apiInterface.updateSalesentry(id,s_quantity,date,salesamt,debitamt,balamt);
       call.enqueue(new Callback<ResponseBody>() {
           @Override
           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               try {
                   Log.d("retrofit response", "update purchase" + response);
                   if (response.isSuccessful()) {
                       progressBar.setVisibility(View.VISIBLE);
                       Toast.makeText(updateSalesEntry.this, "Updated successfully!!", Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(updateSalesEntry.this, show_Userdetails.class));

                   }
               }catch (Exception e){e.printStackTrace();}
           }

           @Override
           public void onFailure(Call<ResponseBody> call, Throwable t) {
               Toast.makeText(updateSalesEntry.this, "Updation not successfully!!", Toast.LENGTH_SHORT).show();
           }
       });


    }
}
