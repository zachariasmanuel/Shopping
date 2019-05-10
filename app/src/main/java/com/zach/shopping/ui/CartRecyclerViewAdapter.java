package com.zach.shopping.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zach.shopping.R;
import com.zach.shopping.data.db.Cart;

import java.util.List;

/**
 * Created by zac on 10-May-2019
 */
public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.MyViewHolder> {
    List<Cart> cartItems;
    CartFragment context;
    ItemClickListener itemClickListener;

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cartListItemNameTextView;
        TextView cartListItemPriceTextView;
        TextView cartListItemRatingTextView;
        ImageView cartListItemImageView;
        Button removeButton;

        MyViewHolder(View v) {
            super(v);
            cartListItemNameTextView = v.findViewById(R.id.cart_list_item_name);
            cartListItemPriceTextView = v.findViewById(R.id.cart_list_item_price);
            cartListItemRatingTextView = v.findViewById(R.id.cart_list_item_rating);
            cartListItemImageView = v.findViewById(R.id.cart_list_item_image_view);
            removeButton = v.findViewById(R.id.cart_list_remove_button);
        }
    }

    public CartRecyclerViewAdapter(CartFragment context) {
        this.context = context;
    }

    public void setData(List<Cart> products) {
        this.cartItems = products;
        notifyDataSetChanged();
    }

    public List<Cart> getData(){return this.cartItems;}

    @Override
    public CartRecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_list_item, parent, false);

        CartRecyclerViewAdapter.MyViewHolder dataObjectHolder = new CartRecyclerViewAdapter.MyViewHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartRecyclerViewAdapter.MyViewHolder holder, int position) {
        String productName = cartItems.get(position).name;
        String productPrice = cartItems.get(position).price;
        String productRating = cartItems.get(position).rating;
        String productImageURL = cartItems.get(position).imageURL;
        if (productName != null)
            holder.cartListItemNameTextView.setText((productName.length() > 20) ? productName.substring(0, 20) : productName);
        else
            holder.cartListItemNameTextView.setText(context.getString(R.string.no_name));

        if (productPrice != null)
            holder.cartListItemPriceTextView.setText(String.format("%s%s", context.getString(R.string.rupees_sysmbol), productPrice));
        else
            holder.cartListItemPriceTextView.setText(context.getString(R.string.no_price));

        if (productRating != null)
            holder.cartListItemRatingTextView.setText(String.format("%s%s", context.getString(R.string.rating_text), productRating));
        else
            holder.cartListItemRatingTextView.setText(context.getString(R.string.no_rating));

        Glide.with(context).load(productImageURL).centerCrop().into(holder.cartListItemImageView);

        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemClickListener != null)
                    itemClickListener.onRemoveClicked(cartItems.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (cartItems == null)
            return 0;
        return cartItems.size();
    }

    interface ItemClickListener {
        void onRemoveClicked(Cart product);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}