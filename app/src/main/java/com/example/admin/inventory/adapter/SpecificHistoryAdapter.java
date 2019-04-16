package com.example.admin.inventory.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.inventory.R;
import com.example.admin.inventory.activites.HistoryActivity;
import com.example.admin.inventory.activites.updateSalesEntry;
import com.example.admin.inventory.model.OutstandingHistory;
import com.example.admin.inventory.model.ParticularCustomer;

import java.util.ArrayList;
import java.util.List;

public class SpecificHistoryAdapter extends RecyclerView.Adapter<SpecificHistoryAdapter.specificViewHolder> {
    private Context context;
    private ArrayList<OutstandingHistory> outstandingHistories;



    public SpecificHistoryAdapter(Context context, ArrayList<OutstandingHistory> outstandingHistories) {
        this.context = context;
        this.outstandingHistories = outstandingHistories;

    }



    @NonNull
    @Override
    public SpecificHistoryAdapter.specificViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_activities, viewGroup, false);
        return new specificViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecificHistoryAdapter.specificViewHolder specificViewHolder, int i) {
        final OutstandingHistory outstandingHistory = outstandingHistories.get(i);
        specificViewHolder.odate.setText(outstandingHistory.getDepositDate());
        specificViewHolder.oamt.setText(outstandingHistory.getDAmt());



    }

    @Override
    public int getItemCount() {
        return outstandingHistories.size();
    }


    public class specificViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView odate, oamt;

        public specificViewHolder(@NonNull View itemView) {
            super(itemView);
            odate = itemView.findViewById(R.id.odate);
            oamt = itemView.findViewById(R.id.oamt);

        }

        @Override
        public void onClick(View v) {

        }
    }

    /*public void updateList(List<ParticularCustomer> newList) {
        outstandingHistories = new ArrayList<>();
        outstandingHistories.addAll(newList);
        notifyDataSetChanged();
    }*/
}
