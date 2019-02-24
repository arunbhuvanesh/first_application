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
import java.util.List;

public class QuantityAdapter extends RecyclerView.Adapter<QuantityAdapter.QuantityViewHolder> {
    private Context context;
    private ArrayList<Quantity> totalStock;

    public QuantityAdapter(Context context, ArrayList<Quantity> totalStock) {
        this.context = context;
        this.totalStock = totalStock;
    }

    @NonNull
    @Override
    public QuantityAdapter.QuantityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.quantity_list,viewGroup,false);
        return new QuantityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuantityAdapter.QuantityViewHolder quantityViewHolder, int i) {
Quantity quantity=totalStock.get(i);
quantityViewHolder.tv2.setText(quantity.getSumQuantity());

    }

    @Override
    public int getItemCount() {
        return totalStock.size();
    }

    public class QuantityViewHolder extends RecyclerView.ViewHolder {
        TextView tv1, tv2;

        public QuantityViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.editText1);
            tv2 = itemView.findViewById(R.id.total);


        }
    }
}
