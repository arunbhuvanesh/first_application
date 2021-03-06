package com.example.admin.inventory.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.example.admin.inventory.R;
import com.example.admin.inventory.activites.Particular_Vendor;
import com.example.admin.inventory.activites.updateVendors;
import com.example.admin.inventory.model.Vendors;
import com.example.admin.inventory.remote.ApiInterface;



import java.util.ArrayList;
import java.util.List;

public class vendorAdapter extends RecyclerView.Adapter<vendorAdapter.vendorViewHolder> {
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

        userViewHolder.name.setText(vendors.getVName());
        userViewHolder.cname.setText(vendors.getVCompany());
        userViewHolder.address.setText(vendors.getVAddress());
        userViewHolder.phone.setText(vendors.getVPhone());
        userViewHolder.mail.setText(vendors.getVEmail());


       /* userViewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        //from the left
        userViewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, userViewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1));
        //from the right
        userViewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right,userViewHolder.swipeLayout.findViewById(R.id.bottom_wraper));

        userViewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
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
      /*  userViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context, "id is:" + vendors.getId(), Toast.LENGTH_SHORT).show();

                return true;
            }
        });*/


        userViewHolder.delete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clickListener.onClick(vendors.getId());
                return true;
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
        userViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent editpintent=new Intent(context, Particular_Vendor.class);
                editpintent.putExtra("vendor id",vendors.getId());
                Toast.makeText(context,"vendor id:"+vendors.getId(),Toast.LENGTH_SHORT).show();
                context.startActivity(editpintent);

            }
        });

        userViewHolder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",vendors.getVPhone(),null));
                context.startActivity(i);
            }
        });



        /*userViewHolder.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animatefab();
            }
        });*/



    }

    /*private void animatefab() {
        if (isOpen)
        {
            fab.startAnimation(fabforward);
            ed.startAnimation(fabclose);
            de.startAnimation(fabclose);
            vie.startAnimation(fabclose);
            ed.setClickable(false);
            de.setClickable(false);
            vie.setClickable(false);
            isOpen=false;

        }
        else
        {
            fab.startAnimation(fabbackward);
            ed.startAnimation(fabopen);
            de.startAnimation(fabopen);
            vie.startAnimation(fabopen);
            ed.setClickable(true);
            de.setClickable(true);
            vie.setClickable(true);
            isOpen=true;
        }

    }
*/
    @Override
    public int getItemCount() {
        return vendorsList.size();
    }





    public class vendorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, cname, address, phone,mail;
        ImageView edit, delete, view;

        /*FloatingActionButton fab,ed,de,vie;
        Animation fabopen,fabclose,fabforward,fabbackward;*/
        private mClickListener clickListener;
//        boolean isOpen;


        public vendorViewHolder(@NonNull View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.vname);
            cname = itemView.findViewById(R.id.cname);
            address = itemView.findViewById(R.id.vaddres);
            phone = itemView.findViewById(R.id.vph);
            mail=itemView.findViewById(R.id.vmail);
            /*fab=itemView.findViewById(R.id.fab);
            ed=itemView.findViewById(R.id.fab2);
            de=itemView.findViewById(R.id.fab3);
            vie=itemView.findViewById(R.id.fab4);*/

            edit = itemView.findViewById(R.id.editicon);
            delete = itemView.findViewById(R.id.deleteicon);
            view = itemView.findViewById(R.id.tabicon);
            delete.setOnClickListener(this);
            edit.setOnClickListener(this);
            view.setOnClickListener(this);
            itemView.setOnClickListener(this);
            phone.setOnClickListener(this);


//            fab.setOnClickListener(this);
           /* fabopen= AnimationUtils.loadAnimation(context,R.anim.fab_animation_open);
            fabclose=AnimationUtils.loadAnimation(context,R.anim.fab_animation_close);
            fabforward=AnimationUtils.loadAnimation(context,R.anim.rotate_forward);
            fabbackward=AnimationUtils.loadAnimation(context,R.anim.rotate_backward);
*/
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


