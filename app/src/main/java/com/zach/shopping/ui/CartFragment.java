package com.zach.shopping.ui;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zach.shopping.MyApplication;
import com.zach.shopping.R;
import com.zach.shopping.data.db.Cart;
import com.zach.shopping.utilities.Constant;
import com.zach.shopping.viewmodels.CartViewModel;
import com.zach.shopping.viewmodels.CartViewModelFactory;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zac on 09-May-2019
 */
public class CartFragment extends Fragment {

    @Inject
    CartViewModelFactory cartViewModelFactory;

    CartViewModel viewModel;
    ProgressDialog progressDialog;

    @BindView(R.id.cart_list_recycler_view)
    RecyclerView recyclerView;

    private CartRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public static CartFragment getInstance() {
        return new CartFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.cart_fragment, container, false);

        progressDialog = Constant.getProgressDialog(getActivity(), "Please wait...");
        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((MyApplication) getActivity().getApplication()).getAppComponent().doInjection(this);

        viewModel = ViewModelProviders.of(this, cartViewModelFactory).get(CartViewModel.class);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new CartRecyclerViewAdapter(this);
        recyclerView.setAdapter(mAdapter);
        viewModel.getCartItemsResponse().observe(this, this::consumeCartItemsResponse);
        viewModel.getDeleteSuccessResponse().observe(this, this::consumeDeleteSuccessResponse);
        viewModel.getCartProducts();

        mAdapter.setClickListener(new CartRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onRemoveClicked(Cart product) {
                viewModel.removeItemFromCart(product);
            }
        });

    }

    private void consumeCartItemsResponse(List<Cart> cartItems) {
        mAdapter.setData(cartItems);
    }

    private void consumeDeleteSuccessResponse(Boolean success) {
        if (success) {
            viewModel.getCartProducts();
        }
    }
}


