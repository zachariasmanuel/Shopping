package com.zach.shopping;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zach.shopping.data.ApiResponse;
import com.zach.shopping.utilities.Constant;
import com.zach.shopping.viewmodels.ShoppingViewModel;
import com.zach.shopping.viewmodels.ViewModelFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingActivity extends AppCompatActivity {

    @Inject
    ViewModelFactory viewModelFactory;

    @BindView(R.id.result_text_view)
    TextView resultTextView;


    ShoppingViewModel viewModel;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = Constant.getProgressDialog(this, "Please wait...");

        ButterKnife.bind(this);
        ((MyApplication) getApplication()).getAppComponent().doInjection(this);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ShoppingViewModel.class);

        viewModel.loginResponse().observe(this, this::consumeResponse);

        if (!Constant.checkInternetConnection(this)) {
            Toast.makeText(ShoppingActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
        } else {
            viewModel.fetchProduct();
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
                Toast.makeText(ShoppingActivity.this, "Error", Toast.LENGTH_SHORT).show();
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
