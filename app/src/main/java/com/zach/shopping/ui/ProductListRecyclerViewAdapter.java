package com.zach.shopping.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zach.shopping.R;

/**
 * Recycler View Adapter for Product List recycler view
 * Created by zac on 10-May-2019
 */
public class ProductListRecyclerViewAdapter extends RecyclerView.Adapter<ProductListRecyclerViewAdapter.MyViewHolder> {
    private JsonArray products;
    private ProductListFragment context;
    private ItemClickListener itemClickListener;


    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productListItemNameTextView;
        TextView productListItemPriceTextView;
        TextView productListItemRatingTextView;
        ImageView productListItemImageView;

        MyViewHolder(View v) {
            super(v);
            productListItemNameTextView = v.findViewById(R.id.product_list_item_name);
            productListItemPriceTextView = v.findViewById(R.id.product_list_item_price);
            productListItemRatingTextView = v.findViewById(R.id.product_list_item_rating);
            productListItemImageView = v.findViewById(R.id.product_list_item_image_view);
        }
    }

    ProductListRecyclerViewAdapter(ProductListFragment context) {
        this.context = context;
    }

    public void setData(JsonArray products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductListRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                          int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String productName = products.get(position).getAsJsonObject().get("name").getAsString();
        String productPrice = products.get(position).getAsJsonObject().get("price").getAsString();
        String productRating = products.get(position).getAsJsonObject().get("rating").getAsString();
        String productImageURL = products.get(position).getAsJsonObject().get("image_url").getAsString();
        if (productName != null)
            holder.productListItemNameTextView.setText((productName.length() > 20) ? productName.substring(0, 20) : productName);
        else
            holder.productListItemNameTextView.setText(context.getString(R.string.no_name));

        if (productPrice != null)
            holder.productListItemPriceTextView.setText(String.format("%s%s", context.getString(R.string.rupees_sysmbol), productPrice));
        else
            holder.productListItemPriceTextView.setText(context.getString(R.string.no_price));

        if (productRating != null)
            holder.productListItemRatingTextView.setText(String.format("%s%s", context.getString(R.string.rating_text), productRating));
        else
            holder.productListItemRatingTextView.setText(context.getString(R.string.no_rating));

        Glide.with(context).load(productImageURL).centerCrop().into(holder.productListItemImageView);

        holder.itemView.setOnClickListener(view -> {
            if (itemClickListener != null)
                itemClickListener.onItemClicked(products.get(position).getAsJsonObject());
        });
    }

    @Override
    public int getItemCount() {
        if (products == null)
            return 0;
        return products.size();
    }

    interface ItemClickListener {
        void onItemClicked(JsonObject product);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}