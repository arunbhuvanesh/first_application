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

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.admin.inventory.R;
import com.example.admin.inventory.activites.updatePurchaseEntry;
import com.example.admin.inventory.activites.updateSalesEntry;
import com.example.admin.inventory.model.ParticularCustomer;


import java.util.ArrayList;
import java.util.List;

public class SpecificCustomerAdapter extends RecyclerView.Adapter<SpecificCustomerAdapter.specificViewHolder> {
    private Context context;
    private ArrayList<ParticularCustomer> scustomer;
    private mClickListener clickListener;


    public SpecificCustomerAdapter(Context context, ArrayList<ParticularCustomer> scustomer, mClickListener clickListener) {
        this.context = context;
        this.scustomer = scustomer;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public SpecificCustomerAdapter.specificViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.specificcustomer_list, viewGroup, false);
        return new specificViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecificCustomerAdapter.specificViewHolder specificViewHolder, int i) {
        final ParticularCustomer particularCustomer=scustomer.get(i);
        specificViewHolder.id.setText(particularCustomer.getId());
        specificViewHolder.cname.setText(particularCustomer.getUsername());
        specificViewHolder.itemname.setText(particularCustomer.getItemName());
        specificViewHolder.quantity.setText(particularCustomer.getQuantity());
        specificViewHolder.date.setText(particularCustomer.getDate());
        specificViewHolder.s_amt.setText("Cost: "+particularCustomer.getSAmt());
        specificViewHolder.d_amt.setText("Deposited: "+particularCustomer.getDAmt());
     /*   specificViewHolder.s_amt.setText(particularCustomer.getSAmt());
        specificViewHolder.d_amt.setText(particularCustomer.getDAmt());*/
        specificViewHolder.b_amt.setText(particularCustomer.getBAmt());



        specificViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clickListener.onClick(particularCustomer.getId());

                return true;
            }
        });

/*
        specificViewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        //from the right
        specificViewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right,specificViewHolder.swipeLayout.findViewById(R.id.bottom_wraper));


        specificViewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });*/

        specificViewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editintent = new Intent(context, updateSalesEntry.class);
                Toast.makeText(context,"customer id"+particularCustomer.getId(),Toast.LENGTH_SHORT).show();
                editintent.putExtra("c_id",particularCustomer.getId());
                editintent.putExtra("customer name", particularCustomer.getUsername());
                editintent.putExtra("item name", particularCustomer.getItemName());
                editintent.putExtra("quantity",particularCustomer.getQuantity());
                editintent.putExtra("date",particularCustomer.getDate());
                editintent.putExtra("sales amount", particularCustomer.getSAmt());
                editintent.putExtra("deposit amount", particularCustomer.getDAmt());
                editintent.putExtra("balance amount",particularCustomer.getBAmt());
                context.startActivity(editintent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return scustomer.size();
    }

   /* @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipelayout;
    }*/

    public class specificViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView id,cname,itemname,quantity,date,s_amt,d_amt,b_amt;
//        SwipeLayout swipeLayout;
        ImageView edit;
        public specificViewHolder(@NonNull View itemView) {
            super(itemView);
//            swipeLayout=itemView.findViewById(R.id.swipelayout);
            id=itemView.findViewById(R.id.sid);
            cname=itemView.findViewById(R.id.cname);
            itemname=itemView.findViewById(R.id.itemname);
            quantity=itemView.findViewById(R.id.quantity);
            date=itemView.findViewById(R.id.date);
            s_amt=itemView.findViewById(R.id.samt);
            d_amt=itemView.findViewById(R.id.damt);
            b_amt=itemView.findViewById(R.id.bamt);
            edit = itemView.findViewById(R.id.editicon);
            edit.setOnClickListener(this);
//            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
    public void updateList(List<ParticularCustomer> newList){
        scustomer=new ArrayList<>();
        scustomer.addAll(newList);
        notifyDataSetChanged();
    }
}
