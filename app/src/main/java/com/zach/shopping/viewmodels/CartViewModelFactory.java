package com.zach.shopping.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.zach.shopping.data.Repository;

import javax.inject.Inject;

/**
 * View Model Factory to create Cart View Model
 * Created by zac on 10-May-2019
 */
public class CartViewModelFactory implements ViewModelProvider.Factory{

    private Repository repository;

    @Inject
    public CartViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CartViewModel.class)) {
            return (T) new CartViewModel(repository);
        }

        throw new IllegalArgumentException("Unknown class name");
    }
}
