package com.zach.shopping;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;

/**
 * Created by zac on 10-May-2019
 */
public class ProductListRecyclerViewAdapter extends RecyclerView.Adapter<ProductListRecyclerViewAdapter.MyViewHolder> {
    JsonArray products;
    ProductListFragment context;


    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productListItemnameTextView;
        TextView productListItempriceTextView;
        TextView productListItemratingTextView;
        ImageView productListItemImageView;

        MyViewHolder(View v) {
            super(v);
            productListItemnameTextView = v.findViewById(R.id.product_list_item_name);
            productListItempriceTextView = v.findViewById(R.id.product_list_item_price);
            productListItemratingTextView = v.findViewById(R.id.product_list_item_rating);
            productListItemImageView = v.findViewById(R.id.product_list_item_image_view);
        }
    }

    public ProductListRecyclerViewAdapter(ProductListFragment context) {
        this.context = context;
    }

    public void setData(JsonArray products) {
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String productName = products.get(position).getAsJsonObject().get("name").getAsString();
        String productPrice = products.get(position).getAsJsonObject().get("price").getAsString();
        String productRating = products.get(position).getAsJsonObject().get("rating").getAsString();
        String productImageURL = products.get(position).getAsJsonObject().get("image_url").getAsString();
        if (productName != null)
            holder.productListItemnameTextView.setText((productName.length() > 20) ? productName.substring(0, 20) : productName);
        else
            holder.productListItemnameTextView.setText(context.getString(R.string.no_name));

        if (productPrice != null)
            holder.productListItempriceTextView.setText(String.format("%s%s", context.getString(R.string.rupees_sysmbol), productPrice));
        else
            holder.productListItempriceTextView.setText(context.getString(R.string.no_price));

        if (productRating != null)
            holder.productListItemratingTextView.setText(String.format("%s%s", context.getString(R.string.rating_text), productRating));
        else
            holder.productListItemratingTextView.setText(context.getString(R.string.no_rating));

        Glide.with(context).load(productImageURL).centerCrop().into(holder.productListItemImageView);
    }

    @Override
    public int getItemCount() {
        if (products == null)
            return 0;
        return products.size();
    }
}