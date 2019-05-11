package com.zach.shopping.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.JsonObject;
import com.zach.shopping.R;
import com.zach.shopping.utilities.ActivityUtils;

/**
 * Activity to hold all the fragments
 * Created by zac on 09-May-2019
 */
public class ShoppingActivity extends AppCompatActivity {

    ProductListFragment productListFragment;
    CartFragment cartFragment;
    ProductDetailsFragment productDetailsFragment;
    MyOrderFragment myOrderFragment;
    private MenuItem cartMenuItem;
    private MenuItem myOrderMenuItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productListFragment = (ProductListFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);

        if (productListFragment == null) {
            productListFragment = ProductListFragment.getInstance();
        }

        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), productListFragment, R.id.content_frame);
    }

    public void loadCartFragment() {
        if (cartFragment == null) {
            cartFragment = CartFragment.getInstance();
        }

        ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), cartFragment, R.id.content_frame);
    }

    public void loadProductDetailsFragment(JsonObject product) {
        if (productDetailsFragment == null) {
            productDetailsFragment = ProductDetailsFragment.getInstance();
        }
        productDetailsFragment.setProduct(product);
        ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), productDetailsFragment, R.id.content_frame);
    }

    public void loadMyOrderFragment() {
        if (myOrderFragment == null) {
            myOrderFragment = MyOrderFragment.getInstance();
        }
        ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), myOrderFragment, R.id.content_frame);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 1) {
            moveTaskToBack(false);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (getFragmentManager().getBackStackEntryCount() == 1) {
            moveTaskToBack(false);
        } else {
            super.onBackPressed();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        cartMenuItem = menu.findItem(R.id.action_cart);
        myOrderMenuItem = menu.findItem(R.id.action_my_order);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                loadCartFragment();
                return (true);
            case R.id.action_my_order:
                loadMyOrderFragment();
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    public void setTitle(String title) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);
    }

    public void showCartIcon(boolean status) {
        if (cartMenuItem != null)
            cartMenuItem.setVisible(status);
    }

    public void showMyOrderIcon(boolean status) {
        if (myOrderMenuItem != null)
            myOrderMenuItem.setVisible(status);
    }
}

