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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zach.shopping.data.ApiResponse;
import com.zach.shopping.utilities.Constant;
import com.zach.shopping.viewmodels.ProductListViewModel;
import com.zach.shopping.viewmodels.ProductListViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zac on 09-May-2019
 */
public class ProductListFragment extends Fragment {

    @Inject
    ProductListViewModelFactory productListViewModelFactory;

    @BindView(R.id.result_text_view)
    TextView resultTextView;

    @BindView(R.id.next_button)
    Button nextButton;

    ProductListViewModel viewModel;

    ProgressDialog progressDialog;

    public static ProductListFragment getInstance() {
       return new ProductListFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.product_list_fragment, container, false);

        progressDialog = Constant.getProgressDialog(getActivity(), "Please wait...");

        ButterKnife.bind(this, root);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ShoppingActivity)getActivity()).loadCartFragment();
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((MyApplication) getActivity().getApplication()).getAppComponent().doInjection(this);


        viewModel = ViewModelProviders.of(this, productListViewModelFactory).get(ProductListViewModel.class);

        viewModel.productFetchResponse().observe(this, this::consumeResponse);

        //viewModel.fetchProductFromDB();

        //viewModel.addToCart();


        if (!Constant.checkInternetConnection(getActivity())) {
            Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.fetchProductsFromApi();
        }
    }

    private void consumeResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {

            case LOADING:
                progressDialog.show();
                break;

            case SUCCESS:
                progressDialog.dismiss();
                renderSuccessResponse(apiResponse.data);

                break;

            case ERROR:
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    private void renderSuccessResponse(JsonElement response) {

        resultTextView.setText(response.toString());
        JsonObject jsonObject = response.getAsJsonObject();

        JsonArray products = jsonObject.getAsJsonArray("products");
        for(JsonElement product : products){
            System.out.println(product.getAsJsonObject().get("name"));
        }

    }
}
