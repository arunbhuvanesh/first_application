package com.example.admin.inventory.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.inventory.R;
import com.example.admin.inventory.model.Itemlist;

import java.util.List;


public class spinnerAdapter extends BaseAdapter {
    private List<Itemlist> items;
    private Context context;
    private LayoutInflater layoutInflater;

    public spinnerAdapter(List<Itemlist> items, Context context) {
        this.items = items;
        this.context = context;
        layoutInflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder spinnerHolder;
        if (convertView==null){
            spinnerHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.item_list_spinner,parent,false);
            spinnerHolder.spinnerItemList=convertView.findViewById(R.id.item);
            convertView.setTag(spinnerHolder);
            }
            else{
            spinnerHolder=(ViewHolder)convertView.getTag();
        }
        spinnerHolder.spinnerItemList.setText(items.get(position).getItemName());

        return convertView;
    }
    class ViewHolder{
        TextView spinnerItemList;
    }
}
