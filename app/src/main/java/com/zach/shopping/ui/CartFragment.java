package com.zach.shopping.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zach.shopping.MyApplication;
import com.zach.shopping.R;
import com.zach.shopping.data.db.Cart;
import com.zach.shopping.viewmodels.CartViewModel;
import com.zach.shopping.viewmodels.CartViewModelFactory;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fragment to show Cart list
 * Created by zac on 09-May-2019
 */
public class CartFragment extends Fragment {

    @Inject
    CartViewModelFactory cartViewModelFactory;
    CartViewModel viewModel;

    @BindView(R.id.cart_list_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.cart_checkout_button)
    Button checkoutButton;

    @BindView(R.id.cart_empty_text_view)
    TextView cartEmptyTextView;

    private CartRecyclerViewAdapter mAdapter;

    public static CartFragment getInstance() {
        return new CartFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.cart_fragment, container, false);
        ButterKnife.bind(this, root);

        if ((getActivity()) != null) {
            ((ShoppingActivity) getActivity()).setTitle(getString(R.string.cart_action_bar_text));
            ((ShoppingActivity) getActivity()).showCartMenuIcon(false);
            ((ShoppingActivity) getActivity()).showMyOrderMenuIcon(true);
        }

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((MyApplication) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, cartViewModelFactory).get(CartViewModel.class);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new CartRecyclerViewAdapter(this);
        recyclerView.setAdapter(mAdapter);
        viewModel.getCartItemsResponse().observe(this, this::consumeCartItemsResponse);
        viewModel.getDeleteSuccessResponse().observe(this, this::consumeDeleteSuccessResponse);
        viewModel.getCartProducts();
        mAdapter.setClickListener(product -> viewModel.removeItemFromCart(product));

        checkoutButton.setOnClickListener(view -> {
            if (mAdapter.getData().size() == 0)
                Snackbar.make(view, R.string.cart_is_empty_snack_bar_text, Snackbar.LENGTH_SHORT).show();
            else {
                viewModel.moveAllToMyOrder(mAdapter.getData());
                ((ShoppingActivity) getActivity()).loadMyOrderFragment();
                Snackbar.make(view, R.string.items_ordered_snack_bar_text, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void consumeCartItemsResponse(List<Cart> cartItems) {
        if (cartItems.size() == 0) {
            cartEmptyTextView.setVisibility(View.VISIBLE);
        } else {
            cartEmptyTextView.setVisibility(View.GONE);
        }
        mAdapter.setData(cartItems);
    }

    private void consumeDeleteSuccessResponse(Boolean success) {
        if (success) {
            viewModel.getCartProducts();
        }
    }
}


