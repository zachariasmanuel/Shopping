package com.zach.shopping.ui;

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
import com.zach.shopping.data.db.MyOrder;
import com.zach.shopping.viewmodels.MyOrderViewModel;
import com.zach.shopping.viewmodels.MyOrderViewModelFactory;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zac on 10-May-2019
 */
public class MyOrderFragment extends Fragment {

    @Inject
    MyOrderViewModelFactory myOrderViewModelFactory;

    MyOrderViewModel viewModel;

    @BindView(R.id.my_order_recycler_view)
    RecyclerView recyclerView;

    private MyOrderListRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public static MyOrderFragment getInstance() {
        return new MyOrderFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.my_order_fragment, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((MyApplication) getActivity().getApplication()).getAppComponent().doInjection(this);

        viewModel = ViewModelProviders.of(this, myOrderViewModelFactory).get(MyOrderViewModel.class);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyOrderListRecyclerViewAdapter(this);
        recyclerView.setAdapter(mAdapter);
        viewModel.getOrderItemsResponse().observe(this, this::consumeCartItemsResponse);
        viewModel.getOrders();
    }

    private void consumeCartItemsResponse(List<MyOrder> myOrders) {
        mAdapter.setData(myOrders);
    }

}

