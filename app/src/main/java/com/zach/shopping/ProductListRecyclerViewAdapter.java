package com.zach.shopping;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonArray;

/**
 * Created by zac on 10-May-2019
 */
public class ProductListRecyclerViewAdapter extends RecyclerView.Adapter<ProductListRecyclerViewAdapter.MyViewHolder> {
    JsonArray products;


    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;

        MyViewHolder(View v) {
            super(v);
            nameTextView = v.findViewById(R.id.product_list_item_name);
        }
    }

    public void setData(JsonArray products){
        this.products = products;
        notifyDataSetChanged();
    }

    @Override
    public ProductListRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item, parent, false);

        MyViewHolder dataObjectHolder = new MyViewHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.nameTextView.setText(products.get(position).getAsJsonObject().get("name").toString());
    }

    @Override
    public int getItemCount() {
        if(products == null)
            return 0;
        return products.size();
    }
}