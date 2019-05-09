package com.zach.shopping;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zach.shopping.utilities.Constant;
import com.zach.shopping.viewmodels.CartViewModel;
import com.zach.shopping.viewmodels.CartViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zac on 09-May-2019
 */
public class CartFragment extends Fragment {

    @Inject
    CartViewModelFactory cartViewModelFactory;

    @BindView(R.id.result_text_view1)
    TextView resultTextView;

    CartViewModel viewModel;
    ProgressDialog progressDialog;

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

        viewModel.getCartProducts();

    }

}
