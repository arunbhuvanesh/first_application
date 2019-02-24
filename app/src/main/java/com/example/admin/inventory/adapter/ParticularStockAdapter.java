package com.example.admin.inventory.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.inventory.R;
import com.example.admin.inventory.model.Quantity;

import java.util.ArrayList;

public class ParticularStockAdapter extends RecyclerView.Adapter<ParticularStockAdapter.ParticularViewHolder> {
    private Context context;
    private ArrayList<Quantity> pStock;

    public ParticularStockAdapter(Context context, ArrayList<Quantity> pStock) {
        this.context = context;
        this.pStock = pStock;
    }

    @NonNull
    @Override
    public ParticularStockAdapter.ParticularViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.particular_stockitem_list,viewGroup,false);
        return new ParticularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticularStockAdapter.ParticularViewHolder particularViewHolder, int i) {
        Quantity quantity=pStock.get(i);
        particularViewHolder.tv2.setText(quantity.getSumQuantity());

    }

    @Override
    public int getItemCount() {
        return pStock.size();
    }

    public class ParticularViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv2;
        public ParticularViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.editText1);
            tv2 = itemView.findViewById(R.id.ptotal);
        }
    }
}
