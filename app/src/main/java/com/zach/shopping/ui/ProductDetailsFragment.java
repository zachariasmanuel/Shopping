package com.zach.shopping.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zach.shopping.MyApplication;
import com.zach.shopping.R;
import com.zach.shopping.viewmodels.ProductDetailsViewModel;
import com.zach.shopping.viewmodels.ProductDetailsViewModelFactory;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by zac on 10-May-2019
 */
public class ProductDetailsFragment  extends Fragment {

    @Inject
    ProductDetailsViewModelFactory productDetailsViewModelFactory;

    ProductDetailsViewModel viewModel;

    public static ProductDetailsFragment getInstance() {
        return new ProductDetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.product_details_fragment, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((MyApplication) getActivity().getApplication()).getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, productDetailsViewModelFactory).get(ProductDetailsViewModel.class);
        viewModel.getCartProducts();

    }

}
