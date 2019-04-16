package com.example.admin.inventory.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.admin.inventory.R;
import com.example.admin.inventory.activites.updatePurchaseEntry;
import com.example.admin.inventory.activites.updateVendors;
import com.example.admin.inventory.model.ParticularVendor;
import com.example.admin.inventory.model.PurchaseEntry;
import com.example.admin.inventory.model.Vendors;

import java.util.ArrayList;
import java.util.List;

public class SpecificVendorAdapter extends RecyclerView.Adapter<SpecificVendorAdapter.specificViewHolder>  {
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
        final ParticularVendor particularVendor=svendor.get(i);
        specificViewHolder.vname.setText(particularVendor.getVName());
        specificViewHolder.itemname.setText(particularVendor.getItemName());
        specificViewHolder.quantity.setText(particularVendor.getQuantity());
        specificViewHolder.date.setText(particularVendor.getDate());
        specificViewHolder.p_amt.setText(particularVendor.getPAmt());
        specificViewHolder.d_amt.setText(particularVendor.getDAmt());
        specificViewHolder.b_amt.setText(particularVendor.getBAmt());


        /*specificViewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

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
        specificViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent editintent = new Intent(context, updatePurchaseEntry.class);
                Toast.makeText(context,"vendor id"+particularVendor.getId(),Toast.LENGTH_SHORT).show();
                editintent.putExtra("v_id",particularVendor.getId());
                editintent.putExtra("vendor name", particularVendor.getVName());
                editintent.putExtra("item name", particularVendor.getItemName());
                editintent.putExtra("quantity",particularVendor.getQuantity());
                editintent.putExtra("date",particularVendor.getDate());
                editintent.putExtra("purchase amount", particularVendor.getPAmt());
                editintent.putExtra("deposit amount", particularVendor.getDAmt());
                editintent.putExtra("balance amount",particularVendor.getBAmt());
                context.startActivity(editintent);
                return true;
            }
        });




    }

    @Override
    public int getItemCount() {
        return svendor.size();
    }

    /*@Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipelayout;
    }*/

    public class specificViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView vname,itemname,quantity,date,p_amt,d_amt,b_amt;

        ImageView edit;
        public specificViewHolder(@NonNull View itemView) {
            super(itemView);

            vname=itemView.findViewById(R.id.vname);
            itemname=itemView.findViewById(R.id.itemname);
            quantity=itemView.findViewById(R.id.quantity);
            date=itemView.findViewById(R.id.date);
            edit = itemView.findViewById(R.id.editicon);
            p_amt=itemView.findViewById(R.id.pamt);
            d_amt=itemView.findViewById(R.id.damt);
            b_amt=itemView.findViewById(R.id.bamt);
            edit.setOnClickListener(this);
            itemView.setOnClickListener(this);

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
