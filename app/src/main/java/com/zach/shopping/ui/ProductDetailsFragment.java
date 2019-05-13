package com.zach.shopping.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.zach.shopping.MyApplication;
import com.zach.shopping.R;
import com.zach.shopping.data.db.Cart;
import com.zach.shopping.viewmodels.ProductDetailsViewModel;
import com.zach.shopping.viewmodels.ProductDetailsViewModelFactory;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fragment to show details of a particular product
 * Created by zac on 10-May-2019
 */
public class ProductDetailsFragment extends Fragment {

    private JsonObject product;
    private boolean isInCart = false;

    @Inject
    ProductDetailsViewModelFactory productDetailsViewModelFactory;

    ProductDetailsViewModel viewModel;

    @BindView(R.id.product_details_name_text_view)
    TextView nameTextView;

    @BindView(R.id.product_details_price_text_view)
    TextView priceTextView;

    @BindView(R.id.product_details_rating_text_view)
    TextView ratingTextView;

    @BindView(R.id.product_details_description_text_view)
    TextView descriptionTextView;

    @BindView(R.id.product_details_image_view)
    ImageView productImageView;

    @BindView(R.id.add_to_cart_button)
    Button addToCartButton;

    public static ProductDetailsFragment getInstance() {
        return new ProductDetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.product_details_fragment, container, false);
        ButterKnife.bind(this, root);

        if ((getActivity()) != null) {
            ((ShoppingActivity) getActivity()).setTitle("");
            ((ShoppingActivity) getActivity()).showCartMenuIcon(true);
            ((ShoppingActivity) getActivity()).showMyOrderMenuIcon(true);
        }

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((MyApplication) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, productDetailsViewModelFactory).get(ProductDetailsViewModel.class);
        viewModel.cartItemsResponse().observe(this, this::consumeCartItemsResponse);

        String productName = product.get("name").getAsString();
        String productPrice = product.get("price").getAsString();
        String productRating = product.get("rating").getAsString();
        String productDescription = product.get("description").getAsString();
        String productImageURL = product.get("image_url").getAsString();

        if (productName != null)
            nameTextView.setText((productName.length() > 20) ? productName.substring(0, 20) : productName);
        else
            nameTextView.setText(getString(R.string.no_name));

        if (productPrice != null)
            priceTextView.setText(String.format("%s%s", getString(R.string.rupees_sysmbol), productPrice));
        else
            priceTextView.setText(getString(R.string.no_price));

        if (productRating != null)
            ratingTextView.setText(String.format("%s%s", getString(R.string.rating_text), productRating));
        else
            ratingTextView.setText(getString(R.string.no_rating));

        if (productDescription != null)
            descriptionTextView.setText(productDescription);
        else
            descriptionTextView.setText("");

        Glide.with(this).load(productImageURL).centerCrop().into(productImageView);

        addToCartButton.setOnClickListener(view -> {
            if (!isInCart) {
                Cart cart = new Cart();
                cart.uid = product.get("id").getAsInt();
                cart.name = productName;
                cart.price = productPrice;
                cart.imageURL = productImageURL;
                cart.rating = productRating;
                cart.description = productDescription;
                viewModel.addToCart(cart);
                isInCart = true;
                addToCartButton.setText(R.string.go_to_cart_button_text);
                Snackbar.make(view, R.string.added_to_cart_snack_bar_text, Snackbar.LENGTH_SHORT).show();
            } else {
                ((ShoppingActivity) getActivity()).loadCartFragment();
            }
        });
        viewModel.getCartProducts();
    }

    public void setProduct(JsonObject product) {
        this.product = product;
    }

    private void consumeCartItemsResponse(List<Cart> cartItems) {
        isInCart = false;
        for (Cart cart : cartItems) {
            if (cart.uid == product.get("id").getAsInt()) {
                isInCart = true;
                break;
            }
        }

        if (isInCart)
            addToCartButton.setText(R.string.go_to_cart_button_text);
        else
            addToCartButton.setText(R.string.add_to_cart_button_text);

    }

}
