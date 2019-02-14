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
import com.example.admin.inventory.activites.updateVendors;
import com.example.admin.inventory.model.Vendors;
import com.example.admin.inventory.remote.ApiInterface;


import java.util.ArrayList;
import java.util.List;

public class vendorAdapter extends RecyclerView.Adapter<vendorAdapter.vendorViewHolder>  {
    ApiInterface apiInterface;
    private Context context;
    private List<Vendors> vendorsList;
    private mClickListener clickListener;


    public vendorAdapter(Context context, List<Vendors> vendorsList, mClickListener clickListener) {
        this.context = context;
        this.vendorsList = vendorsList;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public vendorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_vendors, viewGroup, false);
        return new vendorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vendorViewHolder userViewHolder, final int i) {

        final Vendors vendors = vendorsList.get(i);
userViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(context,"vendor id:"+vendors.getVCompany(),Toast.LENGTH_SHORT).show();
    }
});
        userViewHolder.name.setText(vendors.getVName());
        userViewHolder.cname.setText(vendors.getVCompany());
        userViewHolder.address.setText(vendors.getVAddress());
        userViewHolder.phone.setText(vendors.getVPhone());
        userViewHolder.mail.setText(vendors.getVEmail());


        userViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(vendors.getId());
                Toast.makeText(context,"id is:"+vendors.getId(),Toast.LENGTH_SHORT).show();
            }
        });

        /*edit operation, to get the value to passing onUpdateActivity*/


        userViewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent editintent = new Intent(context, updateVendors.class);
                editintent.putExtra("vendor id", vendors.getId());
                editintent.putExtra("vendor name", vendors.getVName());
                editintent.putExtra("company name", vendors.getVCompany());
                editintent.putExtra("vendor address", vendors.getVAddress());
                editintent.putExtra("vendor phone", vendors.getVPhone());
                editintent.putExtra("vendor email",vendors.getVEmail());
                context.startActivity(editintent);

            }
        });

        /*view operation for particular vendor purchase activity*/
        userViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent viewIntent=new Intent(context,PurchaseEntry.class);
                viewIntent.putExtra("vendor id",vendors.getVId());
                context.startActivity(viewIntent);*/
                Toast.makeText(context, "id is:" + vendors.getId(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return vendorsList.size();
    }



    public class vendorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, cname, address, phone,mail;
        ImageView edit, delete, view;
        private mClickListener clickListener;


        public vendorViewHolder(@NonNull View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.vname);
            cname = itemView.findViewById(R.id.cname);
            address = itemView.findViewById(R.id.vaddres);
            phone = itemView.findViewById(R.id.vph);
            mail=itemView.findViewById(R.id.vmail);
            edit = itemView.findViewById(R.id.editicon);
            delete = itemView.findViewById(R.id.deleteicon);
            view = itemView.findViewById(R.id.tabicon);
            delete.setOnClickListener(this);
            edit.setOnClickListener(this);
            view.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(context,"id is:"+getAdapterPosition(),Toast.LENGTH_LONG).show();

        }
    }
    public void updateList(List<Vendors> newList){
        vendorsList=new ArrayList<>();
        vendorsList.addAll(newList);
        notifyDataSetChanged();
    }

}


