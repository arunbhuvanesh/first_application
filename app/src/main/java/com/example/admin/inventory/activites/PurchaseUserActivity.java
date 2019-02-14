package com.example.admin.inventory.activites;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.admin.inventory.R;
import com.example.admin.inventory.adapter.spinnerAdapter;
import com.example.admin.inventory.model.Itemlist;
import com.example.admin.inventory.remote.ApiClient;
import com.example.admin.inventory.remote.ApiInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PurchaseUserActivity extends AppCompatActivity {
    EditText et_customername,et_quantity,et_date,sales_amt,debit_amt,balance_amt;
    Button salessave;
    Spinner item;
    private List<Itemlist> spinneritems;
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

        salessave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cname=et_customername.getText().toString();
                String i=item.getSelectedItem().toString();
                String s_quantity=et_quantity.getText().toString();
                String date=et_date.getText().toString();
                String salesamt=sales_amt.getText().toString();
                String debitamt=debit_amt.getText().toString();
                String balanceamt=balance_amt.getText().toString();


                purchaseUser(cname,i,s_quantity,date,salesamt,debitamt,balanceamt);
            }
        });



    }

    private void purchaseUser(String cname, String i, String s_quantity, String date, String salesamt, String debitamt, String balanceamt) {

    }
}
