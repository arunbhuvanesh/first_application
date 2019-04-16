package com.example.admin.inventory.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.inventory.R;
import com.example.admin.inventory.activites.PurchaseEntry;
import com.example.admin.inventory.model.PQuantity;
import com.example.admin.inventory.model.Quantity;

import java.util.ArrayList;

public class ParticularStockAdapter extends RecyclerView.Adapter<ParticularStockAdapter.ParticularViewHolder> {
    private Context context;
    private ArrayList<PQuantity> pStock;

    public ParticularStockAdapter(Context context, ArrayList<PQuantity> pStock) {
        this.context = context;
        this.pStock = pStock;
        Log.d("Context","response"+context);
        Log.d("Arraylist","response"+pStock);
    }

    @NonNull
    @Override
    public ParticularStockAdapter.ParticularViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.particular_stockitem_list,viewGroup,false);
        return new ParticularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticularStockAdapter.ParticularViewHolder particularViewHolder, int i) {
        PQuantity quantity=pStock.get(i);
//        particularViewHolder.tv1.setText(quantity.getId());
        particularViewHolder.tv2.setText(quantity.getQuantity());


    }

    @Override
    public int getItemCount() {
        return pStock.size();
    }

    public class ParticularViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv1, tv2;

        public ParticularViewHolder(@NonNull View itemView) {
            super(itemView);
            tv2 = itemView.findViewById(R.id.ptotal);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
