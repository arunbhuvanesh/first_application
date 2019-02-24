package com.example.admin.inventory.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.inventory.R;
import com.example.admin.inventory.model.ParticularVendor;
import com.example.admin.inventory.model.PurchaseEntry;
import com.example.admin.inventory.model.Vendors;

import java.util.ArrayList;
import java.util.List;

public class SpecificVendorAdapter extends RecyclerView.Adapter<SpecificVendorAdapter.specificViewHolder> {
    private Context context;
    private ArrayList<ParticularVendor> svendor;

    public SpecificVendorAdapter(Context context, ArrayList<ParticularVendor> svendor) {
        this.context = context;
        this.svendor = svendor;
    }

    @NonNull
    @Override
    public SpecificVendorAdapter.specificViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.specificvendor_list, viewGroup, false);
        return new specificViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecificVendorAdapter.specificViewHolder specificViewHolder, int i) {
        ParticularVendor particularVendor=svendor.get(i);
        specificViewHolder.vname.setText(particularVendor.getVName());
        specificViewHolder.itemname.setText(particularVendor.getItemName());
        specificViewHolder.quantity.setText(particularVendor.getQuantity());
        specificViewHolder.date.setText(particularVendor.getDate());
        specificViewHolder.p_amt.setText(particularVendor.getPAmt());
        specificViewHolder.d_amt.setText(particularVendor.getDAmt());
        specificViewHolder.b_amt.setText(particularVendor.getBAmt());

    }

    @Override
    public int getItemCount() {
        return svendor.size();
    }
    public class specificViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView vname,itemname,quantity,date,p_amt,d_amt,b_amt;
        public specificViewHolder(@NonNull View itemView) {
            super(itemView);
            vname=itemView.findViewById(R.id.vname);
            itemname=itemView.findViewById(R.id.itemname);
            quantity=itemView.findViewById(R.id.quantity);
            date=itemView.findViewById(R.id.date);
            p_amt=itemView.findViewById(R.id.pamt);
            d_amt=itemView.findViewById(R.id.damt);
            b_amt=itemView.findViewById(R.id.bamt);
        }

        @Override
        public void onClick(View v) {

        }
    }
    public void updateList(List<ParticularVendor> newList){
        svendor=new ArrayList<>();
        svendor.addAll(newList);
        notifyDataSetChanged();
    }
}
