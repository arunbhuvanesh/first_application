package com.example.admin.inventory.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.inventory.R;
import com.example.admin.inventory.activites.Particular_customer_list;
import com.example.admin.inventory.model.OutstandingAmt;
import com.example.admin.inventory.model.PQuantity;

import java.util.ArrayList;

public class OutstandingamountAdapter extends RecyclerView.Adapter<OutstandingamountAdapter.ParticularViewHolder> {
    private Context context;
    private ArrayList<OutstandingAmt> outamount;

    public OutstandingamountAdapter(Context context, ArrayList<OutstandingAmt> outamount) {
        this.context = context;
        this.outamount = outamount;
        /*Log.d("Context","response"+context);
        Log.d("Arraylist","response"+pStock);*/
    }

    @NonNull
    @Override
    public OutstandingamountAdapter.ParticularViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.show_outstanding_amount,viewGroup,false);
        return new ParticularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OutstandingamountAdapter.ParticularViewHolder particularViewHolder, int i) {
        OutstandingAmt oamt=outamount.get(i);

        particularViewHolder.tv1.setText(oamt.getId());
        particularViewHolder.tv2.setText("outstanding amount: "+oamt.getOutstandingAmount());
//        particularViewHolder.tv1.setText(quantity.getId());
//        particularViewHolder.tv2.setText(quantity.getQuantity());
       /* Intent intent=new Intent(context, Particular_customer_list.class);
        intent.putExtra("outstanding",oamt.getOutstandingAmount());
        context.startActivity(intent);
*/



    }

    @Override
    public int getItemCount() {
        return outamount.size();
    }

    public class ParticularViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv1, tv2;

        public ParticularViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1=itemView.findViewById(R.id.oid);
            tv2 = itemView.findViewById(R.id.outamount);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
