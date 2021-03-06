package com.example.admin.inventory.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.example.admin.inventory.activites.Particular_Vendor;
import com.example.admin.inventory.activites.Particular_customer_list;
import com.example.admin.inventory.activites.Show_outstanding_amount;
import com.example.admin.inventory.activites.updateUsers;
import com.example.admin.inventory.model.Customers;
import com.example.admin.inventory.remote.ApiInterface;

import java.util.ArrayList;
import java.util.List;

public class customerAdapter extends RecyclerView.Adapter<customerAdapter.customerViewHolder> {
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
        customerViewHolder.uid.setText(customers.getId());
        customerViewHolder.cname.setText(customers.getUsername());
        customerViewHolder.caddress.setText(customers.getAddress());
        customerViewHolder.carea.setText(customers.getArea());
        customerViewHolder.cmail.setText(customers.getEmail());
        customerViewHolder.cphone.setText(customers.getPhone());
        customerViewHolder.cref.setText(customers.getReference());
        customerViewHolder.rphone.setText(customers.getR_phone());

/*
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
        });*/

        customerViewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editintent = new Intent(context, updateUsers.class);
                editintent.putExtra("customer Id",customers.getId());
                editintent.putExtra("customer name",customers.getUsername());
                editintent.putExtra("customer address",customers.getAddress());
                editintent.putExtra("customer area",customers.getArea());
                editintent.putExtra("customer email",customers.getEmail());
                editintent.putExtra("customer mobile",customers.getPhone());
                editintent.putExtra("reference",customers.getReference());
                editintent.putExtra("ref mobile",customers.getR_phone());

                context.startActivity(editintent);
   // Toast.makeText(context,"id "+customers.getId(),Toast.LENGTH_SHORT).show();
            }
        });
        customerViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clickListener.onClick(customers.getId());
                return true;
            }
        });
        customerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editsintent = new Intent(context, Particular_customer_list.class);
                editsintent.putExtra("customer id", customers.getId());
                //Toast.makeText(context,"customer id:"+customers.getId(),Toast.LENGTH_SHORT).show();
                context.startActivity(editsintent);
            }
        });
       /* customerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Show_outstanding_amount.class);
                intent.putExtra("customer id",customers.getId());
                context.startActivity(intent);
            }
        });*/
        customerViewHolder.cphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",customers.getPhone(),null));
                context.startActivity(phoneIntent);
            }
        });

        customerViewHolder.rphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent refPhoneintent=new Intent(Intent.ACTION_DIAL,Uri.fromParts("tel",customers.getR_phone(),null));
                context.startActivity(refPhoneintent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return customersEntryList.size();
    }

   /* @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipelayout;
    }*/

    public class customerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        SwipeLayout swipeLayout;
        TextView cname,caddress,carea,cmail,cphone,cref,uid,rphone;
        ImageView edit, delete,view;
        Switch aSwitch;




        public customerViewHolder(@NonNull View itemView, mClickListener clickListener) {
            super(itemView);
//            swipeLayout=itemView.findViewById(R.id.swipelayout);
            uid=itemView.findViewById(R.id.uid);
            cname=itemView.findViewById(R.id.cname);
            caddress=itemView.findViewById(R.id.caddress);
            carea=itemView.findViewById(R.id.carea);
            cmail=itemView.findViewById(R.id.cemail);
            cphone=itemView.findViewById(R.id.cphone);
            cref=itemView.findViewById(R.id.cref);
            rphone=itemView.findViewById(R.id.rphone);
            delete = itemView.findViewById(R.id.deleteicon);
            edit = itemView.findViewById(R.id.editicon);
            view = itemView.findViewById(R.id.tabicon);
            delete.setOnClickListener(this);
            view.setOnClickListener(this);
           edit.setOnClickListener(this);
            itemView.setOnClickListener(this);
            cphone.setOnClickListener(this);
            rphone.setOnClickListener(this);
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
