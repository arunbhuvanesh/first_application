package com.example.admin.inventory.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.admin.inventory.R;
import com.example.admin.inventory.model.Customers;
import com.example.admin.inventory.remote.ApiInterface;

import java.util.ArrayList;
import java.util.List;

public class customerAdapter extends RecyclerSwipeAdapter<customerAdapter.customerViewHolder> {
    ApiInterface apiInterface;
    private Context context;
    private List<Customers> customersEntryList;
    private mClickListener clickListener;

    public customerAdapter(Context context, List<Customers> customersEntryList,mClickListener clickListener) {
        this.context = context;
        this.customersEntryList = customersEntryList;
        this.clickListener = clickListener;
    }



    @NonNull
    @Override
    public customerAdapter.customerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.purchase_list_items,viewGroup,false);
        return new customerViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull customerAdapter.customerViewHolder customerViewHolder, int i) {
        final Customers customers=customersEntryList.get(i);
        customerViewHolder.uid.setText("00"+customers.getId());
        customerViewHolder.cname.setText(customers.getUsername());
        customerViewHolder.caddress.setText(customers.getAddress());
        customerViewHolder.carea.setText(customers.getArea());
        customerViewHolder.cmail.setText(customers.getEmail());
        customerViewHolder.cphone.setText(customers.getPhone());
        customerViewHolder.cref.setText(customers.getReference());


        customerViewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        //from the left
        customerViewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, customerViewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1));
        //from the right
        customerViewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right,customerViewHolder.swipeLayout.findViewById(R.id.bottom_wraper));

        customerViewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
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
        });
        customerViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               clickListener.onClick(customers.getId());
   // Toast.makeText(context,"id "+customers.getId(),Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public int getItemCount() {
        return customersEntryList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipelayout;
    }

    public class customerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        SwipeLayout swipeLayout;
        TextView cname,caddress,carea,cmail,cphone,cref,uid;
        ImageView edit, delete;
        Switch aSwitch;




        public customerViewHolder(@NonNull View itemView, mClickListener clickListener) {
            super(itemView);
            swipeLayout=itemView.findViewById(R.id.swipelayout);
            uid=itemView.findViewById(R.id.uid);
            cname=itemView.findViewById(R.id.cname);
            caddress=itemView.findViewById(R.id.caddress);
            carea=itemView.findViewById(R.id.carea);
            cmail=itemView.findViewById(R.id.cemail);
            cphone=itemView.findViewById(R.id.cphone);
            cref=itemView.findViewById(R.id.cref);
            delete = itemView.findViewById(R.id.deleteicon);
            edit = itemView.findViewById(R.id.editicon);
            aSwitch=itemView.findViewById(R.id.btnLocation);
            delete.setOnClickListener(this);
            aSwitch.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
    public void updateList(List<Customers> newList){
        customersEntryList=new ArrayList<>();
        customersEntryList.addAll(newList);
        notifyDataSetChanged();
    }
}
