package com.zach.shopping;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zach.shopping.utilities.ActivityUtils;

public class ShoppingActivity extends AppCompatActivity {


    ProductListFragment productListFragment;
    CartFragment cartFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productListFragment = (ProductListFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if(productListFragment == null){
            productListFragment = ProductListFragment.getInstance();
        }

        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), productListFragment, R.id.content_frame);


//        cartFragment = (CartFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
//
//        if(cartFragment == null){
//            cartFragment = CartFragment.getInstance();
//        }
//
//        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), cartFragment, R.id.content_frame);

    }

    public void loadCartFragment(){
        if(cartFragment == null){
            cartFragment = CartFragment.getInstance();
        }

        ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), cartFragment, R.id.content_frame);
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() == 1) {
            moveTaskToBack(false);
        }
        else {
            super.onBackPressed();
        }

    }
}
