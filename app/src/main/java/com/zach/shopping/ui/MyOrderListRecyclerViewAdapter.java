package com.zach.shopping.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zach.shopping.R;
import com.zach.shopping.data.db.Cart;
import com.zach.shopping.data.db.MyOrder;

import java.util.List;

/**
 * Recycler View Adapter for My Order list recycler view
 * Created by zac on 10-May-2019
 */
public class MyOrderListRecyclerViewAdapter extends RecyclerView.Adapter<MyOrderListRecyclerViewAdapter.MyViewHolder> {
    List<MyOrder> myOrders;
    MyOrderFragment context;
    ItemClickListener itemClickListener;

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView myOrderListItemNameTextView;
        TextView myOrderListItemPriceTextView;
        TextView myOrderListItemRatingTextView;
        ImageView myOrderListItemImageView;

        MyViewHolder(View v) {
            super(v);
            myOrderListItemNameTextView = v.findViewById(R.id.my_order_list_item_name);
            myOrderListItemPriceTextView = v.findViewById(R.id.my_order_list_item_price);
            myOrderListItemRatingTextView = v.findViewById(R.id.my_order_list_item_rating);
            myOrderListItemImageView = v.findViewById(R.id.my_order_list_item_image_view);
        }
    }

    public MyOrderListRecyclerViewAdapter(MyOrderFragment context) {
        this.context = context;
    }

    public void setData(List<MyOrder> myOrders) {
        this.myOrders = myOrders;
        notifyDataSetChanged();
    }


    @Override
    public MyOrderListRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                          int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_order_list_item, parent, false);

        MyOrderListRecyclerViewAdapter.MyViewHolder dataObjectHolder = new MyOrderListRecyclerViewAdapter.MyViewHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderListRecyclerViewAdapter.MyViewHolder holder, int position) {
        String productName = myOrders.get(position).name;
        String productPrice = myOrders.get(position).price;
        String productRating = myOrders.get(position).rating;
        String productImageURL = myOrders.get(position).imageURL;
        if (productName != null)
            holder.myOrderListItemNameTextView.setText((productName.length() > 20) ? productName.substring(0, 20) : productName);
        else
            holder.myOrderListItemNameTextView.setText(context.getString(R.string.no_name));

        if (productPrice != null)
            holder.myOrderListItemPriceTextView.setText(String.format("%s%s", context.getString(R.string.rupees_sysmbol), productPrice));
        else
            holder.myOrderListItemPriceTextView.setText(context.getString(R.string.no_price));

        if (productRating != null)
            holder.myOrderListItemRatingTextView.setText(String.format("%s%s", context.getString(R.string.rating_text), productRating));
        else
            holder.myOrderListItemRatingTextView.setText(context.getString(R.string.no_rating));

        Glide.with(context).load(productImageURL).centerCrop().into(holder.myOrderListItemImageView);
    }

    @Override
    public int getItemCount() {
        if (myOrders == null)
            return 0;
        return myOrders.size();
    }

    interface ItemClickListener {
        void onRemoveClicked(Cart product);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}